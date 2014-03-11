package org.gingko;

import org.gingko.context.AppContext;
import org.gingko.server.ServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kyia
 */
public class GingkoServer {

	private static final Logger LOG = LoggerFactory.getLogger(GingkoServer.class);

	private boolean started = false;

	public GingkoServer() {

	}

	public void start() {
		ServerManager serverManager = (ServerManager) AppContext.getBean(AppContext.SERVER_MANAGER);
		try {
			serverManager.startServers();

			// Engine start
			Engine.INSTANCE.start();
			started = true;
		} catch (Exception e) {
			LOG.error("Unable to start servers cleanly: {}", e);
		}
	}

	public void stop() {
		ServerManager serverManager = (ServerManager) AppContext.getBean(AppContext.SERVER_MANAGER);
		try {
			serverManager.stopServers();

			// Engine stop
			Engine.INSTANCE.stop();
			started = false;
			LOG.info("Server stopped.");
		} catch (Exception e) {
			LOG.error("Unable to stop servers cleanly: {}", e);
		}
	}

	public boolean isStopped() {
		return !started;
	}
}
