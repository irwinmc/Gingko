package org.gingko.parse;

import org.gingko.app.analysis.SecSimpleAnalyzer;
import org.gingko.app.collect.ArrayBasedTable;
import org.gingko.app.collect.Cell;
import org.gingko.app.parse.SecHtmlDataParser;
import org.gingko.app.parse.SecTableProcessor;
import org.gingko.config.SecProperties;
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

/**
 * @author Kyia
 */
public class SecDataParser {

	private static final Logger LOG = LoggerFactory.getLogger(SecDataParser.class);

	protected String baseUrl = "http://www.sec.gov/";
	protected String fileName = "892450-0001193125-14-107138-d696596d6k.htm";

	protected String outputFile = "E:/test.html";
	protected String outputFileSingle = "E:/test_single.html";

	protected String outputFile127 = "E:/test_usk127.html";
	protected String outputFile128 = "E:/test_usk128.html";
	protected String outputFile129 = "E:/test_usk129.html";

	protected final String NEWLINE = "\r\n";

	public SecDataParser() {

	}

	/**
	 * Get absolute file path
	 *
	 * @return
	 */
	protected String getFilePath() {
		String path = PathUtils.getWebRootPath() + SecProperties.dataHtmlFormDst;
		path += "20140320" + File.separator + fileName;

		return path;
	}

	/**
	 * Just RUN!
	 *
	 * @throws Exception
	 */
	public void run() throws Exception {
		// File reader
		String filePath = getFilePath();
		File input = new File(filePath);
		Document doc = Jsoup.parse(input, "iso-8859-1", baseUrl);

		// Html data parser
		SecHtmlDataParser parser = new SecHtmlDataParser();

		// Filtered tables
		List<ArrayBasedTable> tableList = new ArrayList<ArrayBasedTable>();
		Elements elements = doc.getElementsByTag("table");
		int i = 0;
		for (Element ele : elements) {
			try {
				ArrayBasedTable table = parser.parseTable(ele);
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
		// Print the page
		StringBuffer sb = new StringBuffer();
		for (ArrayBasedTable table : tableList) {
			// Process table
			table = processor.processBasicField(table);

			sb.append(toHtmlString(table));
			sb.append("<br /><br />");
			sb.append(NEWLINE);
		}
		writeHtmlFile(outputFile, doc.title(), sb.toString());

		// ...
		locateUsk127(tableList);
		locateUsk128(tableList);
		locateUsk129(tableList);

		LOG.info("Complete.");
	}

	/**
	 * Locate USK 127 test
	 *
	 * @param tableList
	 */
	protected void locateUsk127(List<ArrayBasedTable> tableList) {
		// Clear
		StringBuffer sb = new StringBuffer();

		// Filter the table
		SecSimpleAnalyzer analyzer = new SecSimpleAnalyzer();
		String type = "usk127";
		// USK 127
		for (ArrayBasedTable table : tableList) {
			double hit = analyzer.analysis(table, type);
			if (hit > 0.6) {
				sb.append(toHtmlString(table));
				sb.append("<br /><br />");
				sb.append("\r\n");
			}
		}
		writeHtmlFile(outputFile127, type, sb.toString());
	}

	/**
	 * Locate USK 128 test
	 *
	 * @param tableList
	 */
	protected void locateUsk128(List<ArrayBasedTable> tableList) {
		// Clear
		StringBuffer sb = new StringBuffer();

		// Filter the table
		SecSimpleAnalyzer analyzer = new SecSimpleAnalyzer();
		String type = "usk128";
		// USK 128
		for (ArrayBasedTable table : tableList) {
			double hit = analyzer.analysis(table, type);
			if (hit > 0.45) {
//				System.out.println(hit);
				sb.append(toHtmlString(table));
				sb.append("<br /><br />");
				sb.append("\r\n");
			}
		}
		writeHtmlFile(outputFile128, type, sb.toString());
	}

	/**
	 * Locate USK 129 test
	 *
	 * @param tableList
	 */
	protected void locateUsk129(List<ArrayBasedTable> tableList) {
		// Clear
		StringBuffer sb = new StringBuffer();

		// Filter the table
		SecSimpleAnalyzer analyzer = new SecSimpleAnalyzer();
		String type = "usk129";
		// USK 129
		for (ArrayBasedTable table : tableList) {
			double hit = analyzer.analysis(table, type);
			if (hit > 0.5) {
				sb.append(toHtmlString(table));
				sb.append("<br /><br />");
				sb.append("\r\n");
			}
		}
		writeHtmlFile(outputFile129, type, sb.toString());
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
				Cell cell = cells[i][j];
				if (cell.isField()) {
					sb.append("<th>");
					sb.append(cell.getText());
					sb.append("</th>");
				} else {
					sb.append("<td>");
					sb.append(cell.getText());
					sb.append("</td>");
				}
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
