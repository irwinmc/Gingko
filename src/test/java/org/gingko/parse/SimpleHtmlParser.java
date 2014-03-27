package org.gingko.parse;

import org.gingko.app.parse.SecHtmlDataParser;
import org.gingko.app.parse.table.Cell;
import org.gingko.app.parse.table.Table;
import org.gingko.util.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;

/**
 * @author Kyia
 */
public class SimpleHtmlParser {

	private static String baseUrl = "http://www.sec.gov/";
	private static String fileName = "1366340-0001193805-14-000478-e611984_s1a-finjan.htm";

	private static String outputFile = "E:/test.html";
	private static String outputFile2 = "E:/test2.html";
	private static String outputFile3 = "E:/test3.html";

	public static void main(String[] args) throws Exception {
		String filePath = getFilePath();
		File input = new File(filePath);
		Document doc = Jsoup.parse(input, "iso-8859-1", baseUrl);

//		File file = new File(outputFile);
//		if (!file.exists()) {
//			file.createNewFile();
//		}

//		FileOutputStream out = new FileOutputStream(file, false);
//		StringBuffer sb = new StringBuffer();

//		// Test table
//		Elements tables = doc.getElementsByTag("table");
//		for (Element table : tables) {
//			Element t = parseTable(table);
//
//			// Remove the only have one table
//			if (t.select("tr").size() <= 1) {
//				continue;
//			}
//
//			// To string
//			sb.append(t.toString());
//			sb.append("<br /><br />");
//			sb.append("\r\n");
//		}
//
//		out.write(sb.toString().getBytes());
//		out.close();

		StringBuffer sb = new StringBuffer();

		Element table = doc.getElementsByTag("table").get(165);
		Element t = parseTable(table);

		sb.append(t.toString());

		FileUtils.writeFile(outputFile2, sb.toString(), false);

		System.out.println("Complete.");
	}

	private static String getFilePath() {
		String path = new File("").getAbsolutePath();
		path += File.separator + "webroot" +
				File.separator + "data" +
				File.separator + "sec" +
				File.separator + "html" +
				File.separator + "form" +
				File.separator + "20140320" +
				File.separator + fileName;

		return path;
	}

	private static Element parseTable(Element table) throws Exception {
		SecHtmlDataParser parser = new SecHtmlDataParser();
		Table t = parser.parseTable(table);
		t = parser.processTable(t);

		Cell[][] cells = t.getCells();
		int row = cells.length;
		int column = cells[0].length;

		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\">");
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

		FileUtils.writeFile(outputFile3, sb.toString(), false);

		return table;
	}
}
