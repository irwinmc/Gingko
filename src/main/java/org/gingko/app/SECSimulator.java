package org.gingko.app;

import org.gingko.app.cache.SECCache;
import org.gingko.app.download.impl.SECDownloader;
import org.gingko.app.filter.impl.SECFilter;
import org.gingko.app.vo.SECIdxItem;
import org.gingko.context.AppContext;
import org.gingko.services.TaskManagerService;
import org.gingko.util.DateUtils;

import java.util.LinkedList;

/**
 * @author Kyia
 */
public class SECSimulator {

	private TaskManagerService taskManagerService = (TaskManagerService) AppContext.getBean(AppContext.TASK_SERVICE);

	public void prepare() {
		SECFilter.INSTANCE.load();
	}

	public void download() {
		// 昨天的时间
		long time = System.currentTimeMillis() - DateUtils.MILLISECOND_PER_DAY;

		// 第一步先下载idx文件
		SECDownloader downloader = new SECDownloader();
		String dst = downloader.downloadMasterIdx(time);

		// 解析idx并且放入内存
		SECCache.INSTANCE.init(dst);
		LinkedList<SECIdxItem> list = SECCache.INSTANCE.getIdxItems();

		// 检查
		if (list.isEmpty()) {
			// 有错误
		} else {
			// 批量下载
			downloader.multiThreadeDownloadIndexHtm(list);
		}
	}

	public void parseHtmlIdx() {

	}
}
