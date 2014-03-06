package org.gingko.main;

import org.gingko.context.AppSpringConfig;
import org.gingko.server.GingkoServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Kyia
 */
public class CommandLine {

	protected CommandLine() {

	}

	public static void main(String args[]) {
		CommandLine cli = new CommandLine();
		try {
			// Get configuration
			GingkoServer server = cli.getConfiguration(args);
			if (server == null) {
				return;
			}

			// Start the server
			server.start();

			cli.addShutdownHook(server);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addShutdownHook(final GingkoServer server) {
		Runnable shutdownHook = new Runnable() {
			@Override
			public void run() {
				server.stop();
			}
		};

		// Add shutdown hook
		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new Thread(shutdownHook));
	}

	protected GingkoServer getConfiguration(String args[]) throws Exception {
		GingkoServer server = null;
		if (args.length == 0) {
			System.out.println("Using default configuration.");

			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppSpringConfig.class);
			//ctx.registerShutdownHook();

			server = new GingkoServer();
		}
		return server;
	}
}
