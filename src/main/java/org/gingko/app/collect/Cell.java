package org.gingko.app.collect;

/**
 * @author Kyia
 */
public class Cell {

	/** 规定单元格可横跨的行数 */
	protected int rowSpan = 1;

	/** 规定单元格可横跨的列数 */
	protected int colSpan = 1;

	/** 单元格中文字内容 */
	protected String text = "";

	/** 是否是名称列，用于匹配，搜索等，如果单列colSpan大于1，也可以认为是名称列 */
	protected boolean isField = false;

	/**
	 * 空构造函数
	 */
	public Cell() {

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

	public boolean isField() {
		return isField;
	}

	public void setField(boolean isField) {
		this.isField = isField;
	}
}
