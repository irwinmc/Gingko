package org.gingko;

import org.gingko.app.filter.impl.SECFilter;
import org.gingko.context.AppContext;
import org.gingko.services.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kyia
 */
public enum Engine {

	INSTANCE;

	private static final Logger LOG = LoggerFactory.getLogger(Engine.class);

	private ScheduleService scheduleService = (ScheduleService) AppContext.getBean(AppContext.SCHEDULE_SERVICE);

	public void start() {
		scheduleService.init();

		new SECFilter().load();
	}

	public void stop() {
		scheduleService.destroy();
	}
}
