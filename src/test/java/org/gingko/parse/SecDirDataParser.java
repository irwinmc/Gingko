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
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 */
public class SecDirDataParser {

	private static final Logger LOG = LoggerFactory.getLogger(SecDirDataParser.class);

	protected String baseUrl = "http://www.sec.gov/";

	protected final String NEWLINE = "\r\n";

	/**
	 * Get absolute file path
	 *
	 * @return
	 */
	protected String getFilePath() {
		String path = PathUtils.getWebRootPath() + SecProperties.dataHtmlFormDst;
		path += "20140320" + File.separator;

		return path;
	}

	/**
	 * Just run
	 *
	 * @throws Exception
	 */
	public void run() throws Exception {
		String path = getFilePath();
		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".htm");
			}
		});

		// For each file
		for (File file : files) {
			runFile(file);
		}

		LOG.info("Complete.");
	}

	/**
	 * Run file
	 *
	 * @param file
	 * @throws Exception
	 */
	protected void runFile(File file) throws Exception {
		// File reader
		Document doc = Jsoup.parse(file, "iso-8859-1", baseUrl);

		// Html data parser
		SecHtmlDataParser parser = new SecHtmlDataParser();
		// Filtered tables
		List<ArrayBasedTable> tableList = new ArrayList<ArrayBasedTable>();
		Elements elements = doc.getElementsByTag("table");
		int i = 0;
		for (Element ele : elements) {
			try {
				ArrayBasedTable table = parser.parseTable(ele);
				if (table != null) {
					table.setName("table" + i);
					if (!table.isEmpty()) {
						tableList.add(table);
					}
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

			// To Html
			sb.append(toHtmlString(table));
			sb.append("<br /><br />");
			sb.append(NEWLINE);
		}

		String outputFile = getFilePath() + "p" + File.separator + "p-" + file.getName();
		writeHtmlFile(outputFile, doc.title(), sb.toString());

		// ...
		locateUsk(tableList, file.getName(), "usk127", 0.6);
		locateUsk(tableList, file.getName(), "usk128", 0.45);
		locateUsk(tableList, file.getName(), "usk129", 0.5);
	}

	/**
	 * Locate USK 127 test
	 *
	 * @param tableList
	 * @param fileName
	 */
	protected void locateUsk(List<ArrayBasedTable> tableList, String fileName, String type, double ratio) {
		// Clear
		StringBuffer sb = new StringBuffer();

		// Filter the table
		SecSimpleAnalyzer analyzer = new SecSimpleAnalyzer();

		// USK 127
		List<ArrayBasedTable> l = new ArrayList<ArrayBasedTable>();
		for (ArrayBasedTable table : tableList) {
			double hit = analyzer.analysis(table, type);
			if (hit > ratio) {
				l.add(table);
			}
		}

		if (!l.isEmpty()) {
			for (ArrayBasedTable table : l) {
				sb.append(toHtmlString(table));
				sb.append("<br /><br />");
				sb.append("\r\n");
			}

			String outputFile = getFilePath() + "p" + File.separator + type + "-" + fileName;
			writeHtmlFile(outputFile, type, sb.toString());
		}
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
					"</head>" + NEWLINE +
					"<body>" + NEWLINE +
						content + NEWLINE +
					"</body>" + NEWLINE +
				"</html>" + NEWLINE;

		FileUtils.writeFile(filePath, str, false);
	}
}
