package org.gingko.app;

import org.gingko.util.DateUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kyia
 */
public class SecUtils {

	// Html format regex
	private static final String REG_EX_HTML = "<[^>]+>";

	/**
	 * 获取昨日的时间字串 “yyyyMMdd”
	 *
	 * @return
	 */
	public static String getYesterdayDate() {
		long now = System.currentTimeMillis();
		long time = now - DateUtils.MILLISECOND_PER_DAY;
		String date = DateUtils.formatTime(time, "yyyyMMdd");
		return date;
	}

	/**
	 * 删除HTML标识
	 *
	 * @param html
	 * @return
	 */
	public static String removeHtmlTag(String html) {
		if (html == null)
			return null;

		Pattern pattern;
		Matcher matcher;
		try {
			pattern = Pattern.compile(REG_EX_HTML, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(html);
			html = matcher.replaceAll("");

			// Blank space
			html = html.replace("&nbsp;", " ");
			html = html.replace("&#x200b;", " ");

			// Close single quote
			html = html.replace("&#x2019;", "\'");

			// Em dash
			html = html.replace("&#x2013;", "-");
			html = html.replace("&#x2014;", "-");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return html;
	}
}
