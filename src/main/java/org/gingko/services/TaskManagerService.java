package org.gingko.services;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Kyia
 */
public class TaskManagerService extends ScheduledThreadPoolExecutor {

	public TaskManagerService(int corePoolSize) {
		super(corePoolSize);
	}
}
