package org.gingko.app.parse;

import org.gingko.app.SecUtils;
import org.gingko.app.collect.ArrayBasedTable;
import org.gingko.app.collect.Cell;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 */
public class SecHtmlDataParser {

	// Add need to remove attributes
	private static List<String> removeAttrs = new ArrayList<String>();

	// Html attribute of table colspan for table data cell
	private static final String HTML_COLSPAN = "colspan";

	static {
		removeAttrs.add("style");
		removeAttrs.add("width");
		removeAttrs.add("align");
		removeAttrs.add("valign");
		removeAttrs.add("bgcolor");
		removeAttrs.add("id");
		removeAttrs.add("nowrap");
	}

	/**
	 * 解析数据表格
	 *
	 * @param ele
	 */
	public ArrayBasedTable parseTable(Element ele) {
		// 1. Each element remove above attributes
		clearTableAttrs(ele);

		// 2. Remove html tags in data cell
		removeCellHtmlTag(ele);

		// 3. Delete empty rows
		deleteEmptyRows(ele);

		// 4. Get the table row and column and create table
		// The column which is the max column
		Elements trs = ele.select("tr");
		int row = trs.size();
		int column = 0;
		for (Element tr : trs) {
			Elements tds = tr.select("td");
			// May be each row contains cell whose colspan greater than 2
			int c = 0;
			for (Element td : tds) {
				int colSpan = 1;
				if (!td.attr(HTML_COLSPAN).equals("")) {
					colSpan = Integer.parseInt(td.attr(HTML_COLSPAN));
				}
				c += colSpan;
			}
			if (c > column) {
				column = c;
			}
		}

		// 5. Give the cells data
		Cell[][] cells = new Cell[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				cells[i][j] = new Cell();
			}
		}
		// Process the data
		for (int i = 0; i < row; i++) {
			Element tr = ele.select("tr").get(i);
			Elements tds = tr.select("td");

			// If column is the max column, which no table data cell with colspan
			if (tds.size() == column) {
				for (int j = 0; j < column; j++) {
					Element td = tds.get(j);
					cells[i][j].setText(td.text());
				}
			}
			// Process the colspan
			else if (tds.size() < column) {
				// Column offset, right ->
				int offset = 0;
				for (int j = 0; j < tds.size(); j++) {
					Element td = tds.get(j);
					int colSpan = 1;
					if (!td.attr(HTML_COLSPAN).equals("")) {
						colSpan = Integer.parseInt(td.attr(HTML_COLSPAN));
					}

					if (colSpan == 1) {
						cells[i][offset + j].setText(td.text());
					} else if (colSpan > 1) {
						for (int k = 0; k < colSpan; k++) {
							cells[i][offset + j + k].setColSpan(colSpan);
							cells[i][offset + j + k].setText(td.text());
						}
						offset += colSpan - 1;
					}
				}
			}
		}

		// 6. Check next empty column
		// Delete completely empty column
		boolean[] validColumns = new boolean[column];
		for (int j = 0; j < column; j++) {
			int n = 0;
			for (int i = 0; i < row; i++) {
				Cell cell = cells[i][j];

				String str = cell.getText();
				int colSpan = cell.getColSpan();
				if (colSpan > 1) {
					str = "";
				}

				if (!str.equals("")) {
					n++;
				}
			}
			validColumns[j] = n >= 2;
		}

		// 7. Recalculate the valid column
		int newColumn = 0;
		for (int i = 0; i < validColumns.length; i++) {
			if (validColumns[i]) {
				newColumn++;
			}
		}

		// If no valid column
		ArrayBasedTable table = null;
		if (newColumn > 0) {
			Cell[][] newCells = new Cell[row][newColumn];
			for (int i = 0; i < row; i++) {
				for (int j = 0, k = 0; j < column; j++) {
					if (validColumns[j]) {
						newCells[i][k] = cells[i][j];
						k++;
					}
				}
			}
			table = new ArrayBasedTable(newCells);
		}

		return table;
	}

	/**
	 * 删除所有元素样式、位置等修饰性属性
	 * 仅保留表格中横跨列或者竖跨行
	 *
	 * @param ele
	 * @return
	 */
	private Element clearTableAttrs(Element ele) {
		Elements elements = ele.select("*");
		for (Element e : elements) {
			for (Attribute attr : e.attributes()) {
				String attrName = attr.getKey();
				if (removeAttrs.contains(attrName)) {
					e.removeAttr(attrName);
				}
			}
		}
		return ele;
	}

	/**
	 * 删除所有单元格内HTML标识，仅保留文本
	 * 删除无效单元格内容
	 *
	 * @param ele
	 * @return
	 */
	private Element removeCellHtmlTag(Element ele) {
		Elements trs = ele.select("tr");
		for (Element tr : trs) {
			// For all table data cell
			Elements tds = tr.select("td");
			for (Element td : tds) {
				// Process the html
				String str = SecUtils.removeHtmlTag(td.html()).trim();

				// TODO: 单位格内容有效性验证，非常重要的一步，哪些是无效内容，无意义的需要有大量数据支持
				if (str.equals("$")
						| str.equals(")")
						| str.equals("(")
						| str.equals("(restated)")
						| str.equals("%")) {
					str = "";
				}

				td.html(str.toLowerCase());
			}
		}
		return ele;
	}

	/**
	 * 删除空白行
	 *
	 * @param ele
	 * @return
	 */
	private Element deleteEmptyRows(Element ele) {
		Elements trs = ele.select("tr");
		for (Element tr : trs) {
			// Check row empty, remove row
			String rowCont = "";

			// For all table data cell
			Elements tds = tr.select("td");
			for (Element td : tds) {
				// Get the content
				rowCont += td.text();
			}

			if (rowCont.equals("")) {
				tr.remove();
			}
		}
		return ele;
	}
}
