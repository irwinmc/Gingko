package org.gingko.app.schedule.task;

import org.gingko.app.analysis.SecSimpleAnalyzer;
import org.gingko.app.collect.ArrayBasedTable;
import org.gingko.app.collect.Cell;
import org.gingko.app.parse.SecHtmlDataParser;
import org.gingko.app.parse.SecTableProcessor;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.SecForm;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.app.persist.mapper.SecFormMapper;
import org.gingko.app.persist.mapper.SecHtmlIdxMapper;
import org.gingko.app.persist.mapper.SecIdxMapper;
import org.gingko.config.SecProperties;
import org.gingko.context.AppContext;
import org.gingko.util.FileUtils;
import org.gingko.util.PathUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Tang
 */
public class SecFormParserTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SecFormParserTask.class);

    protected final String USK_127 = "usk127";
    protected final String USK_128 = "usk128";
    protected final String USK_129 = "usk129";
    protected final String NEWLINE = "\r\n";
    protected final String OUTPUT_PATH = "parser";

    private static SecIdxMapper secIdxMapper = (SecIdxMapper) AppContext.getBean(PersistContext.SEC_IDX_MAPPER);
    private static SecHtmlIdxMapper secHtmlIdxMapper = (SecHtmlIdxMapper) AppContext.getBean(PersistContext.SEC_HTML_IDX_MAPPER);
    private static SecFormMapper secFormMapper = (SecFormMapper) AppContext.getBean(PersistContext.SEC_FORM_MAPPER);

    private String date;

    public SecFormParserTask(String date) {
        this.date = date;
    }

    @Override
    public void run() {
        LOG.info("Start to  parser form table.");

        List<SecHtmlIdx> htmlIdxes = secHtmlIdxMapper.selectByDate(date);
        for (SecHtmlIdx secHtmlIdx : htmlIdxes) {
            String localFile = secHtmlIdx.getLocalFile();
            String localFilePath = PathUtils.getWebRootPath() + SecProperties.dataHtmlFormDst + date + File.separator + localFile;
            File file = new File(localFilePath);
            if (!file.exists() && !file.isFile()) {
                LOG.error("Target file not exits. {}", localFilePath);
                continue;
            }

            try {
                parser(file, secHtmlIdx);
            } catch (Exception e) {
                LOG.error("Target parser error. {}", e.getMessage());
            }
        }
    }

    /**
     * Just RUN!
     *
     * @throws Exception
     */
    public void parser(File file, SecHtmlIdx secHtmlIdx) throws Exception {
        // File reader
        Document doc = Jsoup.parse(file, "iso-8859-1", secHtmlIdx.getAnchor());
        // Html data parser
        SecHtmlDataParser parser = new SecHtmlDataParser();
        // Filtered tables
        List<ArrayBasedTable> tableList = new ArrayList<ArrayBasedTable>();
        Elements elements = doc.getElementsByTag("table");
        int i = 0;
        for (Element ele : elements) {
            try {
                ArrayBasedTable table = parser.parseTable(ele);
                if (table == null) {
                    continue;
                }

                table.setName("table" + i);
                if (!table.isEmpty()) {
                    tableList.add(table);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(ele.toString());
            }
            i++;
        }

        // Processor
        SecTableProcessor processor = new SecTableProcessor();
        for (ArrayBasedTable table : tableList) {
            processor.processBasicField(table);
        }
        // ...
        extractFormTable(tableList, secHtmlIdx);
    }

    /**
     * Extract and match table with keyword
     *
     * @param tableList
     * @param secHtmlIdx
     */
    protected void extractFormTable(List<ArrayBasedTable> tableList, SecHtmlIdx secHtmlIdx) {
        // Filter the table
        SecSimpleAnalyzer analyzer = new SecSimpleAnalyzer();
        for (ArrayBasedTable table : tableList) {
            // TODO: 依次添加类型就好了
            if (analyzer.analysis(table, USK_127) > 0.6) {
                saveTable(table, USK_127, secHtmlIdx);
            }

            else if (analyzer.analysis(table, USK_128) > 0.6) {
                saveTable(table, USK_128, secHtmlIdx);
            }

            else if (analyzer.analysis(table, USK_129) > 0.6) {
                saveTable(table, USK_129, secHtmlIdx);
            }
        }
    }

    /**
     * Save valid table
     *
     * @param table
     * @param reportType
     * @param secHtmlIdx
     */
    protected void saveTable(ArrayBasedTable table, String reportType, SecHtmlIdx secHtmlIdx) {
        StringBuffer sb = new StringBuffer();
        sb.append(toHtmlString(table));
        sb.append("<br /><br />");
        sb.append("\r\n");

        // Output file path and check is exist, create it when not exist.
        String outputFilePath = PathUtils.getWebRootPath() + SecProperties.dataHtmlFormDst + date + File.separator + OUTPUT_PATH;
        FileUtils.mkdir(outputFilePath);

        // Write html file
        String fileName = reportType + "-" + UUID.randomUUID().toString() + ".html";
        // Check is existed.
        SecForm secForm = secFormMapper.selectByLocalFile(fileName);
        if (secForm != null) {
            LOG.error("File form table already existed. {}", fileName);
            return;
        }

        String filePath = outputFilePath + File.separator + fileName;
        writeHtmlFile(filePath, reportType, sb.toString());

        // Save form into database
        secForm = new SecForm();
        secForm.setSiid(secHtmlIdx.getSiid());
        secForm.setAnchor(secHtmlIdx.getAnchor());
        secForm.setName(reportType + "-" + secHtmlIdx.getDescription());
        secForm.setFormType(secHtmlIdx.getType());
        secForm.setReportType(reportType);
        secForm.setDate(secHtmlIdx.getDate());
        secForm.setLocalFile(fileName);
        secForm.setState(0);
        secFormMapper.insert(secForm);

        // Add idx valid amount
        SecIdx secIdx = secIdxMapper.selectBySiid(secHtmlIdx.getSiid());
        if (secIdx != null) {
            int amount = secIdx.getAmount() + 1;
            secIdx.setAmount(amount);
            secIdxMapper.update(secIdx);
        }

        LOG.info("File {} parse successful.", fileName);
    }

    /**
     * Table to html string
     *
     * @param table
     * @return
     * @throws Exception
     */
    protected String toHtmlString(ArrayBasedTable table) {
        Cell[][] cells = table.getCells();
        int row = cells.length;
        int column = cells[0].length;

        StringBuffer sb = new StringBuffer();
        sb.append("<table cellpadding='0' cellspacing='0' border='1' id='" + table.getName() + "'>");
        for (int i = 0; i < row; i++) {
            sb.append("<tr>");
            for (int j = 0; j < column; j++) {
                sb.append("<td>");
                sb.append(cells[i][j].getText());
                sb.append("</td>");
            }
            sb.append("</tr>");
            sb.append("\n");
        }
        sb.append("</table>");

        return sb.toString();
    }

    /**
     * Write html file
     *
     * @param filePath
     * @param title
     * @param content
     */
    protected void writeHtmlFile(String filePath, String title, String content) {
        String str =
                "<html>" + NEWLINE +
                        "<head>" + NEWLINE +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" + NEWLINE +
                        "<title>" + title + "</title>" + NEWLINE +
                        // ===== Style begin
                        "<style type=\"text/css\">" + NEWLINE +
                        "* {font:normal 12px Monaco,Menlo,Consolas,\"Courier New\",monospace;}" + NEWLINE +
                        "table {color:#333333;width:100%;border-width:1px;border-color:#729ea5;border-collapse:collapse;}" + NEWLINE  +
                        "table th {background-color:#ded0b0;border-width:1px;padding:8px;border-style:solid;border-color:#bcaf91;text-align:left;}" + NEWLINE +
                        "table tr {background-color:#e9dbbb;}" + NEWLINE +
                        "table td {border-width:1px;padding:8px;border-style:solid;border-color:#bcaf91;}" + NEWLINE +
                        "</style>" + NEWLINE +
                        // ===== Style end
                        "</head>" + NEWLINE +
                        "<body>" + NEWLINE +
                        content + NEWLINE +
                        "</body>" + NEWLINE +
                        "</html>" + NEWLINE;

        FileUtils.writeFile(filePath, str, false);
    }
}
