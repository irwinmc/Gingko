package org.gingko.app.schedule.job;

import org.gingko.server.handler.api.actions.SettingAction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author TangYing
 * <p/>每日在idx下载之前或者工作开始之前下载，具体下载时间待定（还要考虑cik库的更新时间）
 */
public class SyncCikJob implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(SyncCikJob.class);

	/**
	 * @param context
	 * @throws org.quartz.JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        String result = SettingAction.INSTANCE.syncCik();
        LOG.info("Cik sync completed, {}", result);
	}
}
