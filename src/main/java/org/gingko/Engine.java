package org.gingko;

import org.gingko.app.SecSimulator;
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

		// @Test
		SecSimulator simulator = new SecSimulator();
//		simulator.prepare();
//		simulator.download();
//		simulator.parse();
//		simulator.fetch();
		simulator.insertIdx();
	}

	public void stop() {
		scheduleService.destroy();
	}
}
