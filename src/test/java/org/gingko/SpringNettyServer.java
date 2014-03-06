package org.gingko;

import org.gingko.server.ServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

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
	}
}
