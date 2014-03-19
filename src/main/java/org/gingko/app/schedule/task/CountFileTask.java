package org.gingko.app.schedule.task;

import org.gingko.context.AppContext;
import org.gingko.services.TaskManagerService;

/**
 * Created by Administrator on 14-3-19.
 */
public class CountFileTask implements Runnable {

	private static TaskManagerService taskManagerService = (TaskManagerService) AppContext.getBean(AppContext.TASK_SERVICE);

	public CountFileTask() {

	}

	@Override
	public void run() {

	}
}
