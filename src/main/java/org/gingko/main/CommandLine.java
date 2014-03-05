package org.gingko.main;

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

			// Start the server

			cli.addShutdownHook();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addShutdownHook() {
		Runnable shutdownHook = new Runnable() {
			@Override
			public void run() {
				// Stop the server
			}
		};

		// Add shutdown hook
		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new Thread(shutdownHook));
	}


}
