package org.gingko.app.parse;

import com.google.common.collect.Maps;
import org.gingko.app.collect.ArrayBasedTable;
import org.gingko.app.collect.Cell;
import org.gingko.app.collect.MapBasedTable;

import java.util.Map;

/**
 * @author Kyia
 */
public class SecTableProcessor {

	/**
	 * 处理Table字段数据
	 *
	 * @param table
	 * @return
	 */
	public ArrayBasedTable processBasicField(ArrayBasedTable table) {
		Cell[][] cells = table.getCells();

		int row = cells.length;
		int column = cells[0].length;
		for (int i = 0; i < row; i++) {
			cells[i][0].setField(true);
		}

		for (int i = 0; i < row; i++) {
			for (int j = 1; j < column; j++) {
				Cell cell = cells[i][j];
				if (cell.getColSpan() > 1 || cell.getRowSpan() > 1) {
					cell.setField(true);
				}
			}
		}

		return table;
	}

	/**
	 * 处理Table数据，根据固定规则处理，考虑配置的可能性
	 *
	 * @param table
	 * @return
	 */
	public ArrayBasedTable processFinanceFormat(ArrayBasedTable table) {
		// 第一列为名称列
		Cell[][] cells = table.getCells();

		// 财务数据处理，空行都用“-”补全，数据单元格以“(”开头处理成负数，用于加减
		int row = cells.length;
		int column = cells[0].length;
		for (int i = 0; i < row; i++) {
			cells[i][0].setField(true);
		}

		// 从第二列开始处理
		for (int i = 0; i < row; i++) {
			for (int j = 1; j < column; j++) {
				Cell cell = cells[i][j];
				if (cell.getColSpan() > 1 || cell.getRowSpan() > 1) {
					cell.setField(true);
				}
				if (!cell.isField()) {
					String str = cell.getText();
					if (str.equals("")) {
						cell.setText("-");
					}
					else if (str.startsWith("(")) {
						str = str.replace("(", "-");
						cell.setText(str);
					}
				}
			}
		}

		return table;
	}

	/**
	 * 内部转储对象，便于处理
	 *
	 * @param table
	 * @return
	 */
	public MapBasedTable transfer(ArrayBasedTable table) {
		// 强制转换开始，这里是所有的单元格
		Cell[][] cells = table.getCells();

		// 这里需要一个开始转换为字段的标签


		return null;
	}
}
