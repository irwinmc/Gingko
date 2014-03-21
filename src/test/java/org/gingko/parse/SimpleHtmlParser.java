package org.gingko.parse;

import org.gingko.app.parse.table.HtmlTable;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 */
public class SimpleHtmlParser {

	private static String baseUrl = "http://www.sec.gov/";
	private static String fileName = "1228454-0001562762-14-000077-bcbp-20131231x10ka.htm";

	public static void main(String[] args) throws Exception {
		String filePath = getFilePath();
		File input = new File(filePath);
		Document doc = Jsoup.parse(input, "UTF-8", baseUrl);

		System.out.println("!");
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

	private static HtmlTable parseTable(Element e) {
		return null;
	}
}
