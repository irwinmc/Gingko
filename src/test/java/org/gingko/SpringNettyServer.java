package org.gingko;

import org.gingko.app.cache.db.ReportCache;
import org.gingko.app.filter.impl.SecFilter;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.app.persist.mapper.SecIdxMapper;
import org.gingko.context.AppContext;
import org.gingko.parse.SecDataParser;
import org.gingko.parse.SecDirDataParser;
import org.gingko.parse.SecSingleDataParser;
import org.gingko.server.ServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 */
public class SpringNettyServer {

	private static final Logger LOG = LoggerFactory.getLogger(SpringNettyServer.class);

	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(GingkoSpringConfig.class);
		// For the destroy method to work.
		context.registerShutdownHook();

		// Start tcp and flash servers
		ServerManager manager = (ServerManager) context.getBean("serverManager");
		try {
			manager.startServers(808);
		} catch (Exception e) {
			LOG.error("Could not start servers cleanly: {}", e);
		}

		// DO
		doTest();
	}

	private static void doTest() {
		// @Test
		// 加载过滤器，TODO: 这里要改成过滤器管理器全局加载
		SecFilter.INSTANCE.load();

		// @Test
		// 加载缓存，TODO: 改成cache统一管理
		ReportCache.INSTANCE.init();
		ReportCache.INSTANCE.initKeyword();

		// TEST
		try {
			new SecSingleDataParser().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
