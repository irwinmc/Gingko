package org.gingko.app.cache;

import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.Report;
import org.gingko.app.persist.domain.ReportKeyword;
import org.gingko.app.persist.mapper.ReportKeywordMapper;
import org.gingko.app.persist.mapper.ReportMapper;
import org.gingko.context.AppContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kyia
 */
public enum ReportCache {

	INSTANCE;

	// Database mapper
	private static ReportMapper reportMapper = (ReportMapper) AppContext.getBean(PersistContext.REPORT_MAPPER);
	private static ReportKeywordMapper reportKeywordMapper = (ReportKeywordMapper) AppContext.getBean(PersistContext.REPORT_KEYWORD_MAPPER);

	/**
	 * 需要解析的报表类型列表
	 */
	private List<String> reportList = new ArrayList<>();

	/**
	 * 报表关键字映射，列表里是关键字单词
	 */
	private Map<String, List<String>> reportKeywordMap = new HashMap<>();

	/**
	 * 初始化
	 */
	public void init() {
		// 这里存入需要过滤的报表类型
		List<Report> reports = reportMapper.select();
		for (Report report : reports) {
			String type = report.getType();

			// 放入内存
			reportList.add(type);
			// 初始化MAP
			reportKeywordMap.put(type, new ArrayList<String>());
		}

		// 过滤出报表关键字，关键字有组合意义，必须同时满足才可
		List<ReportKeyword> reportKeywords = reportKeywordMapper.select();
		for (ReportKeyword reportKeyword : reportKeywords) {
			String type = reportKeyword.getType();
			if (reportKeywordMap.containsKey(type)) {
				List<String> l = reportKeywordMap.get(type);

			}
		}
	}
}
