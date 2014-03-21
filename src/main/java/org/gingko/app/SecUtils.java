package org.gingko.app;

import org.gingko.util.DateUtils;

/**
 * @author Kyia
 */
public class SecUtils {

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
}
