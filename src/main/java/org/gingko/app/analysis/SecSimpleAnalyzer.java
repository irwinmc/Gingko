package org.gingko.app.analysis;

import org.gingko.app.cache.db.ReportCache;
import org.gingko.app.collect.ArrayBasedTable;
import org.gingko.app.collect.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 *         <p/>
 *         分析定位表格内容，分析是否是指定表格
 */
public class SecSimpleAnalyzer {

	private static final Logger LOG = LoggerFactory.getLogger(SecSimpleAnalyzer.class);

	/**
	 * 分析表格字段
	 *
	 * @param table
	 */
	public double analysis(ArrayBasedTable table, String type) {
		List<String> keywords = ReportCache.INSTANCE.getKeywords(type);
		if (keywords.isEmpty()) {
			LOG.warn("Keyword is empty for type {}", type);
			return 0.0;
		}

		// 文本信息
		List<String> texts = extractText(table);
		int total = keywords.size();
		int hit = 0;
		for (String keyword : keywords) {
			for (String text : texts) {
				if (text.toLowerCase().contains(keyword)) {
					hit++;
					break;
				}
			}
		}

		double hitRatio = (float) hit / (float) total;
		return hitRatio;
	}

	/**
	 * 从字段列中获取文本信息，字段列定义在 {@link org.gingko.app.parse.SecHtmlDataParser}
	 *
	 * @param table
	 */
	private List<String> extractText(ArrayBasedTable table) {
		List<String> texts = new ArrayList<String>();

		Cell[][] cells = table.getCells();
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				Cell cell = cells[i][j];
				if (cell.isField()) {
					texts.add(cell.getText());
				}
			}
		}

		return texts;
	}
}
