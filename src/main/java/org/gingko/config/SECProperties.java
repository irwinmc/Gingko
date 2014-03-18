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
public class SecProperties {

	private static final Logger LOG = LoggerFactory.getLogger(SecProperties.class);

	private static final String SEC_PROPS = "sec.properties";
	private static Properties props = new Properties();

	static {
		String filePath = PathUtils.getConfPath() + File.separator + SEC_PROPS;

		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8"));
			props.load(in);

			// SEC
			base = get("base");
			baseArchives = get("base.archives");

			masterIdxFileName = get("master.idx.fileName");
			masterIdxDateFormat = get("master.idx.dateFormat");
			masterIdxUrl = get("master.idx.url");
			masterIdxDst = get("master.idx.dst");

			filterCik = get("filter.cik");
			filterFormType = get("filter.formType");

			dataXlsDst = get("data.xls.dst");
			dataTxtDst = get("data.txt.dst");
			dataHtmlIndexDst = get("data.html.index.dst");
			dataHtmlFormDst = get("data.html.form.dst");

			LOG.info("Remote properties load completed.");
		} catch (IOException e) {
			LOG.error("Remote properties load failed.", e);
		}
	}

	public static String base = "http://www.sec.gov/";
	public static String baseArchives = "http://www.sec.gov/Archives/";

	public static String masterIdxFileName = "master.${date}.idx";
	public static String masterIdxDateFormat = "yyyyMMdd";
	public static String masterIdxUrl = "http://www.sec.gov/Archives/edgar/daily-index/";
	public static String masterIdxDst = "data/sec/daily-index/";

	public static String filterCik = "sec-filter/cik.txt";
	public static String filterFormType = "sec-filter/form-type.txt";

	public static String dataXlsDst = "data/sec/xls/";
	public static String dataTxtDst = "data/sec/txt/";
	public static String dataHtmlIndexDst = "data/sec/html/index/";
	public static String dataHtmlFormDst = "data/sec/html/form/";

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
