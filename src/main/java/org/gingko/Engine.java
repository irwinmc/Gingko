package org.gingko;

import org.gingko.app.filter.impl.SecFilter;
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

		// 加载过滤器，TODO: 这里要改成过滤器管理器全局加载
		SecFilter.INSTANCE.load();
	}

	public void stop() {
		scheduleService.destroy();
	}
}
