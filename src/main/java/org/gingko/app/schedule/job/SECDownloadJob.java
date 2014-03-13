package org.gingko.app.schedule.job;

import org.gingko.app.filter.impl.SECFilter;
import org.gingko.app.parse.SECMasterIdxParser;
import org.gingko.app.vo.SECIdxItem;
import org.gingko.context.AppContext;
import org.gingko.services.TaskManagerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 */
public class SECDownloadJob implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(SECDownloadJob.class);

	private TaskManagerService taskManagerService = (TaskManagerService) AppContext.getBean(AppContext.TASK_SERVICE);

	// 所有索引文件
	private String dst = "E:/Workspace/Java/gingko/webroot/data/sec/daily-index/master.20140311.idx";
	private List<SECIdxItem> allItems = new ArrayList<SECIdxItem>();
	private List<SECIdxItem> filteredItems = new ArrayList<SECIdxItem>();
	private SECMasterIdxParser parser = new SECMasterIdxParser();
	private SECFilter filter = new SECFilter();

	public void filter() {
		List<SECIdxItem> list = parser.parseMasterIdx(dst);
		allItems.addAll(list);

		filter.load();
		for (SECIdxItem secIdxItem : allItems) {
			if (filter.doFilter(secIdxItem)) {
				filteredItems.add(secIdxItem);
			}
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

	}
}
