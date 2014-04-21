package org.gingko;

import org.gingko.app.cache.db.ReportCache;
import org.gingko.app.filter.impl.SecFilter;
import org.gingko.app.schedule.job.SyncCikJob;
import org.gingko.context.AppContext;
import org.gingko.services.ScheduleService;
import org.quartz.JobExecutionException;

/**
 * @author Kyia
 */
public enum Engine {

	INSTANCE;

	private ScheduleService scheduleService = (ScheduleService) AppContext.getBean(AppContext.SCHEDULE_SERVICE);

	public void start() {
		scheduleService.init();

        loadCache();
    }

	public void stop() {
		scheduleService.destroy();
	}

    public void loadCache() {
        try {
            new SyncCikJob().execute(null);
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }

        SecFilter.INSTANCE.load();

        ReportCache.INSTANCE.init();
    }
}
