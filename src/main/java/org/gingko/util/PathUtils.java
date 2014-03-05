package org.gingko.util;

import java.io.File;

/**
 * Path utilities
 *
 * @author Kyia
 */
public class PathUtils {

	private static String rootPath;

	private static final String CONF_PATH = File.separator + "conf" + File.separator;
	private static final String HTML_PATH = File.separator + "html" + File.separator;

	static {
		rootPath = new File("").getAbsolutePath();
	}

	public static String getRootPath() {
		return rootPath;
	}

	public static String getConfPath() {
		return getRootPath() + CONF_PATH;
	}

	public static String getHtmlPath() {
		return getRootPath() + HTML_PATH;
	}
}
