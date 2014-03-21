package org.gingko.app.schedule.job;

import org.gingko.app.SecUtils;
import org.gingko.app.download.impl.SecDownloader;
import org.gingko.app.schedule.task.SecDownloadTask;
import org.gingko.context.AppContext;
import org.gingko.services.TaskManagerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Kyia
 *         <p/>
 *         每日10点下载Master Idx文件，如果下载失败每过5分钟进行检查，直到文件下载成功为止。
 */
public class SecDownloadJob implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(SecDownloadJob.class);

	private static TaskManagerService taskManagerService = (TaskManagerService) AppContext.getBean(AppContext.TASK_SERVICE);

	/**
	 * @param context
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Downloader
		SecDownloader downloader = new SecDownloader();

		// Date string for yesterday
		String date = SecUtils.getYesterdayDate();

		// Execute the task, it'll call itself again.
		// Or make it work manually
		taskManagerService.execute(new SecDownloadTask(downloader, date));
	}
}
