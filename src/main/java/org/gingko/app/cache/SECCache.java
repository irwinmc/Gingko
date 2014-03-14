package org.gingko.app.cache;

import org.gingko.app.filter.impl.SECFilter;
import org.gingko.app.parse.SECMasterIdxParser;
import org.gingko.app.vo.SECIdxItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Kyia
 */
public enum SECCache {

	INSTANCE;

	private static final Logger LOG = LoggerFactory.getLogger(SECCache.class);

	// 将每日过滤之后的Master Idx Item存在内存之中
	private LinkedList<SECIdxItem> idxItems = new LinkedList<SECIdxItem>();

	/**
	 * 初始化
	 *
	 * @param dst
	 */
	public void init(String dst) {
		idxItems.clear();

		SECMasterIdxParser parser = new SECMasterIdxParser();
		List<SECIdxItem> list = parser.parseMasterIdx(dst);
		for (SECIdxItem secIdxItem : list) {
			if (SECFilter.INSTANCE.doFilter(secIdxItem)) {
				idxItems.add(secIdxItem);
			}
		}
	}

	public void destroy() {
		idxItems.clear();
	}

	public LinkedList<SECIdxItem> getIdxItems() {
		return idxItems;
	}
}
