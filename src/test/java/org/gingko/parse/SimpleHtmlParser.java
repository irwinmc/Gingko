package org.gingko.parse;

import org.gingko.app.parse.table.HtmlTable;
import org.gingko.app.vo.SECHtmlIdxItem;
import org.gingko.util.StringUtils;
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
	private static String fileName = "0000018498-14-000009-index.htm";

	public static void main(String[] args) throws Exception {
		String filePath = getFilePath();
		File input = new File(filePath);
		Document doc = Jsoup.parse(input, "UTF-8", baseUrl);

		Element e = doc.select("table[summary=Document Format Files]").first();
		if (e != null) {
			parseTable(e);
		} else {
			System.out.print("Table not exists!");
		}
	}

	private static String getFilePath() {
		String path = new File("").getAbsolutePath();
		path += File.separator + "webroot" +
				File.separator + "data" +
				File.separator + "sec" +
				File.separator + "html" +
				File.separator + "index" +
				File.separator + "20140313" +
				File.separator + fileName;

		return path;
	}

	private static HtmlTable parseTable(Element e) {
		List<SECHtmlIdxItem> list = new ArrayList<SECHtmlIdxItem>();

		// 以tr每行作为一个单独对象判断
		for (Element tr : e.select("tr")) {
//			if (!tr.select("th").toString().equals("")) {
//				// Table header 只能作为验证
//				System.out.println(ele);
//			}
			Elements tds = tr.select("td");
			if (tds.size() >= 5) {
				String document = tds.get(2).text();
				String anchor = tds.get(2).select("a[href]").first().attr("href");
				System.out.println(anchor);
			}
		}
		return null;
	}
}
