package org.gingko.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gingko.config.SecProperties;
import org.gingko.util.PathUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserUnit {

	/**
	 * 是否找到单位标识
	 */
	private static boolean isFind = false;

	/**
	 * 单位标识集合
	 */
	private static List<String> unitsList = new ArrayList<String>();

	/**
	 * 先初始化集合
	 */
	static {
		String content = ParserUnit.readFile(PathUtils.getRootPath()
				+ File.separator + "conf/" + SecProperties.unitPath);
		String[] units = content.split(",");
		unitsList = Arrays.asList(units);
	}

	public static void main(String[] args) {
		ParserUnit pu = new ParserUnit();
		String unit = null;
		String str = "D:/workspace/trunk/webroot/data/sec/html/form/20140401/test.htm";
		String content = ParserUnit.readFile(str);
		// 判断下载的内容是否合法
		if (content != null) {
			Document doc = Jsoup.parse(content);
			// 检查条件是否匹配
			if (doc != null) {
				// 以table作为一个单独对象判断
				for (Element table : doc.select("table")) {
					if (!isFind) {
						Element temp = pu.getNext(table);
						int num = 4;
						int i = 0;
						while (i < num && !isFind) {
							temp = pu.getNext(temp);
							unit = pu.isUnitInThisP(temp.text());
							i++;
						}
						i = 0;

						if (!isFind) {
							Elements trs = table.select("tr");
							if (trs.size() < num) {
								num = trs.size();
							}
							while (i < num && !isFind) {
								Element tr = trs.get(i);
								String text=tr.text();
								unit = pu.isUnitInThisP(text);
								i++;
							}
						}
					}
				}
			}
		}
		System.out.println(unit);
	}

	public Element getNext(Element temp) {
		Element pre = temp.previousElementSibling();
		if (pre == null) {
			pre = temp.parent().previousElementSibling();
		}
		return pre;
	}

	public static String readFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists())
			return "";

		BufferedReader bd = null;
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fileInput = new FileInputStream(file);
			InputStreamReader inputStrReader = new InputStreamReader(fileInput,
					"UTF-8");
			bd = new BufferedReader(inputStrReader);
			String temp = ""; //
			while ((temp = bd.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} finally {
			if (bd != null) {
				try {
					bd.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * 判断此标签
	 * <p>
	 * ...
	 * </p>
	 * 是否存在单位关键字段
	 * 
	 * @param p
	 * @return String
	 */
	public String isUnitInThisP(String p) {
		for (String unit : unitsList) {
			if (p.toLowerCase().contains(unit.toLowerCase())) {
				isFind = true;
				return unit;
			}
		}
		return null;
	}
}
