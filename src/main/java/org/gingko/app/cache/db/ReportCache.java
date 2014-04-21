package org.gingko.app.cache.db;

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
	private final List<String> reportList = new ArrayList<String>();

	/**
	 * 报表关键字映射，列表里是关键字单词
	 */
	private final Map<String, List<String>> reportKeywordMap = new HashMap<String, List<String>>();

    public void init() {
        initReport();
        initKeyword();
    }

	/**
	 * 初始化报表
	 */
	public void initReport() {
		reportList.clear();

		// 这里存入需要过滤的报表类型
		List<Report> reports = reportMapper.select();
		for (Report r : reports) {
			String type = r.getType();
			reportList.add(type);
		}
	}

	/**
	 * 初始化关键字
	 */
	public void initKeyword() {
		reportKeywordMap.clear();

		// 过滤出报表关键字，每个报表有自己的关键字集合，考虑使用Lucene
		List<ReportKeyword> reportKeywords = reportKeywordMapper.select();
		for (ReportKeyword rk : reportKeywords) {
			String type = rk.getType();
			String keyword = rk.getKeyword();

			List<String> l = reportKeywordMap.get(type);
			if (l == null) {
				l = new ArrayList<String>();
				reportKeywordMap.put(type, l);
			}
			l.add(keyword);
		}
	}

	/**
	 * 获取关键字，不直接更改内存中cache的关键字
	 *
	 * @param type
	 * @return
	 */
	public List<String> getKeywords(String type) {
		List<String> list = new ArrayList<String>();
		if (reportKeywordMap.containsKey(type)) {
			List<String> l = reportKeywordMap.get(type);
			list.addAll(l);
		}
		return list;
	}
}
