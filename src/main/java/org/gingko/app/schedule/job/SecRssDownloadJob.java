package org.gingko.app.schedule.job;

import org.gingko.app.download.impl.SecRssDownloader;
import org.gingko.app.schedule.task.SecRssDownloadTask;
import org.gingko.context.AppContext;
import org.gingko.services.TaskManagerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 每天早上6点至10点每隔30秒轮询去下载最新的htm文件
 *
 * @author Hy
 */
public class SecRssDownloadJob implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(SecRssDownloadJob.class);

	private static TaskManagerService taskManagerService = (TaskManagerService) AppContext.getBean(AppContext.TASK_SERVICE);

	/**
	 * @param context
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Downloader
		LOG.info("Start  to execute the job");

		SecRssDownloader downloader = new SecRssDownloader();

		//Exexute
		taskManagerService.execute(new SecRssDownloadTask(downloader));
	}
}
