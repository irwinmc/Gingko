package org.gingko.app.schedule.task;

import org.gingko.app.download.impl.SECDownloader;
import org.gingko.app.vo.SECIdxItem;
import org.gingko.context.AppContext;
import org.gingko.services.TaskManagerService;

/**
 * @author Kyia
 */
public class SECDownloadDataTask implements Runnable {

	private TaskManagerService taskManagerService = (TaskManagerService) AppContext.getBean(AppContext.TASK_SERVICE);

	private SECIdxItem secIdxItem;

	public SECDownloadDataTask(SECIdxItem secIdxItem) {
		this.secIdxItem = secIdxItem;
	}

	public void run() {

	}
}
