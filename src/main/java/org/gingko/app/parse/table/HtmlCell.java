package org.gingko.app.parse.table;

/**
 * @author Kyia
 */
public class HtmlCell {

	/** 所属表格类 */
	protected HtmlTable table;

	/** 行标号 */
	protected int row = 0;

	/** 列标号 */
	protected int col = 0;

	/** 规定单元格可横跨的行数 */
	protected int rowSpan = 1;

	/** 规定单元格可横跨的列数 */
	protected int colSpan = 1;

	/** 单元格中文字内容 */
	protected String text;

	/**
	 * 空构造函数
	 */
	public HtmlCell() {

	}

	public HtmlTable getTable() {
		return table;
	}

	public void setTable(HtmlTable table) {
		this.table = table;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
