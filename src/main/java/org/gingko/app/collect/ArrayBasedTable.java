package org.gingko.app.collect;

/**
 * @author Kyia
 *
 * 基于二维数组的表格结构，每行的列数必须相同
 */
public class ArrayBasedTable extends AbstractTable {

	/**
	 * 单元格矩阵
	 */
	protected Cell[][] cells;

	/**
	 * 构造函数
	 *
	 * @param cells
	 */
	public ArrayBasedTable(Cell[][] cells) {
		this.cells = cells;
	}

	/**
	 * 获取单元格
	 *
	 * @return
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * 获取行数
	 *
	 * @return
	 */
	public int row() {
		int row = 0;
		if (cells != null) {
			row = cells.length;
		}
		return row;
	}

	/**
	 * 获取列数
	 *
	 * @return
	 */
	public int column() {
		int column = 0;
		if (row() > 0) {
			column = cells[0].length;
		}
		return column;
	}

	/**
	 * 是否是空表
	 * 并需行和列都大于0，认为该表格不为空
	 *
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		boolean isEmpty = true;
		if (column() > 0) {
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * 二维数组的长度
	 *
	 * @return
	 */
	@Override
	public int size() {
		int size = 0;
		if (!isEmpty()) {
			for (int i = 0; i < cells.length; i++) {
				size += cells[i].length;
			}
		}
		return size;
	}

	/**
	 * 重置
	 */
	@Override
	public void clear() {
		cells = null;
	}

	/**
	 * 增加行
	 *
	 * @param array
	 */
	public void addRow(Cell[] array) throws Exception {
		if (isEmpty()) {
			if (array.length > 0) {
				clear();
				cells = new Cell[1][array.length];
				cells[0] = array;
			}
		} else {
			int row = row();
			int column = column();
			if (array.length != column) {
				throw new TableColumnNotSameException();
			} else {
				Cell[][] newCells = new Cell[row + 1][column];
				for (int i = 0; i < row; i++) {
					newCells[i] = cells[i];
				}
				newCells[row] = array;
				cells = newCells;
			}
		}
	}

	/**
	 * 增加列
	 *
	 * @param array
	 */
	public void addColumn(Cell[] array) throws Exception {
		if (isEmpty()) {
			if (array.length > 0) {
				clear();
				cells = new Cell[array.length][1];
				for (int i = 0; i < array.length; i++) {
					cells[i][0] = array[i];
				}
			}
		} else {
			int row = row();
			int column = column();
			if (array.length != row) {
				throw new TableRowNotSameException();
			} else {
				Cell[][] newCells = new Cell[row][column + 1];
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < column; j++) {
						newCells[i][j] = cells[i][j];
					}
				}
				for (int i = 0; i < row; i++) {
					newCells[i][column] = array[i];
				}
				cells = newCells;
			}
		}
	}

	/**
	 * 纵向合并表格
	 * 增加行
	 *
	 * @param other
	 */
	public void verticalMerge(ArrayBasedTable other) throws Exception {
		// If target is empty
		if (other.isEmpty()) {
			// Do nothing
			return;
		}

		// If it self empty
		if (isEmpty()) {
			clear();
			cells = other.getCells();
			return;
		}

		// Check column
		if (column() != other.column()) {
			throw new TableColumnNotSameException();
		} else {
			// Column is same, add row
			int row = row();
			int column = column();
			int otherRow = other.row();

			// New cells
			Cell[][] newCells = new Cell[row + otherRow][column];
			for (int i = 0; i < row; i++) {
				newCells[i] = cells[i];
			}
			for (int i = row; i < row + otherRow; i++) {
				newCells[i] = other.getCells()[i - row];
			}
			cells = newCells;
		}
	}

	/**
	 * 横行合并表格
	 *
	 * @param other
	 */
	public void horizontalMerge(ArrayBasedTable other) throws Exception {
		// If target is empty
		if (other.isEmpty()) {
			// Do nothing
			return;
		}

		// If it self empty
		if (isEmpty()) {
			clear();
			cells = other.getCells();
			return;
		}

		// Check row
		if (row() != other.row()) {
			throw new TableRowNotSameException();
		} else {
			// Column is same, add row
			int row = row();
			int column = column();
			int otherColumn = other.column();

			// New cells
			Cell[][] newCells = new Cell[row][column + otherColumn];
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < column; j++) {
					newCells[i][j] = cells[i][j];
				}
			}
			for (int i = 0; i < row; i++) {
				for (int j = column; j < column + otherColumn; j++) {
					newCells[i][j] = other.getCells()[i][j - column];
				}
			}
		}
	}
}
