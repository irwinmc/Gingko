package org.gingko.app.parse.table;

/**
 * @author Kyia
 *
 * 最简单的表格数据
 */
public class Table {

	/** 单元格矩阵 */
	protected Cell[][] cells;

	/** 表格名称 */
	protected String name;

	/**
	 * 空构造函数
	 */
	public Table() {
		this(0, 0);
	}

	/**
	 * 根据行数、列数构造
	 *
	 * @param row
	 * @param column
	 */
	public Table(int row, int column) {
		this.cells = new Cell[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				this.cells[i][j] = new Cell();
			}
		}
	}

	/**
	 * 根据表名、行数、列数构造表格
	 *
	 * @param name
	 * @param row
	 * @param column
	 */
	public Table(String name, int row, int column) {
		this(row, column);
		this.name = name;
	}

	/**
	 * 单元格构造，不严谨
	 *
	 * @param cells
	 */
	public Table(Cell[][] cells) {
		this.cells = cells;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
}
