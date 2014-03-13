package org.gingko.app.download;

import org.gingko.app.filter.impl.SECFilter;
import org.gingko.app.parse.SECMasterIdxParser;
import org.gingko.app.schedule.task.SECDownloadDataTask;
import org.gingko.app.vo.SECIdxItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Kyia
 */
public class SECDownloaderTest {

	private static final Logger LOG = LoggerFactory.getLogger(SECDownloaderTest.class);

	private static String dst = "E:/Workspace/Java/gingko/webroot/data/sec/daily-index/master.20140311.idx";

	// 所有索引文件
	private static List<SECIdxItem> allItems = new ArrayList<SECIdxItem>();

	// 过滤之后的索引文件
	private static List<SECIdxItem> filteredItems = new ArrayList<SECIdxItem>();

	private static SECMasterIdxParser parser = new SECMasterIdxParser();
	private static SECFilter filter = new SECFilter();

	public static void filter() {
		List<SECIdxItem> list = parser.parseMasterIdx(dst);
		allItems.addAll(list);

		filter.load();
		for (SECIdxItem secIdxItem : allItems) {
			if (filter.doFilter(secIdxItem)) {
				filteredItems.add(secIdxItem);
			}
		}
	}

	public static void main(String[] args) {
		filter();

		LOG.info("{} idx items in master idx file.", allItems.size());
		LOG.info("{} idx items after filtering.", filteredItems.size());

		ScheduledThreadPoolExecutor se = new ScheduledThreadPoolExecutor(20);
		for (SECIdxItem i : filteredItems) {
			SECDownloadDataTask task = new SECDownloadDataTask(i);
			se.execute(task);
		}
	}
}
