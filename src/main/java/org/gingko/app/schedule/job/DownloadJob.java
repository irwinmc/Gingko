package org.gingko.app.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kyia
 */
public class DownloadJob implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(DownloadJob.class);

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		LOG.info("Download job executed.");
	}
}
