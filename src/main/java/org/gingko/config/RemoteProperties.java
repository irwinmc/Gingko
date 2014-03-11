package org.gingko.config;

import org.gingko.util.PathUtils;
import org.gingko.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author Kyia
 */
@Component
public class RemoteProperties {

	private static final Logger LOG = LoggerFactory.getLogger(RemoteProperties.class);

	private static final String REMOTE_PROPS = "remote.properties";
	private static Properties props = new Properties();

	static {
		String filePath = PathUtils.getConfPath() + File.separator + REMOTE_PROPS;

		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8"));
			props.load(in);

			// SEC
			SECIdx = get("sec.idx");
			SECBase = get("sec.base");

			LOG.info("Remote properties load completed.");
		} catch (IOException e) {
			LOG.error("Remote properties load failed.", e);
		}
	}

	public static String SECIdx = "http://www.sec.gov/Archives/edgar/daily-index/master.date.idx";
	public static String SECBase = "http://www.sec.gov/Archives/";

	/**
	 * Get from properties file with blank filter
	 *
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return StringUtils.replaceBlank(props.getProperty(key));
	}
}
