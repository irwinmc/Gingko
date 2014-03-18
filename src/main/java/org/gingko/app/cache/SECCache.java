package org.gingko.app.cache;

import org.gingko.app.filter.impl.SecFilter;
import org.gingko.app.parse.SecMasterIdxParser;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 */
public enum SecCache {

	INSTANCE;

	private static final Logger LOG = LoggerFactory.getLogger(SecCache.class);

	// 备份索引，考虑先将所有内容保存，之后可以通过修改配置或者其他方式进行重载

	// 将每日过滤之后的Master Idx Item存在内存之中
	private List<SecIdx> idxItems = new ArrayList<SecIdx>();

	// 每日的Html Idx对象
	private List<SecHtmlIdx> htmlIdxItems = new ArrayList<SecHtmlIdx>();

	/**
	 * 初始化Master Idx Items
	 * 从文件读取并解析放入内存使用
	 * TODO: 可以考虑写入数据库
	 *
	 * @param dst
	 */
	public void putIdxItems(String dst) {
		idxItems.clear();

		SecMasterIdxParser parser = new SecMasterIdxParser();
		List<SecIdx> list = parser.parseMasterIdx(dst);
		for (SecIdx secIdx : list) {
			if (SecFilter.INSTANCE.doFilter(secIdx)) {
				idxItems.add(secIdx);
			}
		}
	}

	/**
	 * 初始化Html Idx Items
	 * 把解析HTML索引文件之后生成的对象加入到内存中
	 * TODO: 可以考虑写入数据库
	 *
	 * @param list
	 */
	public void putHtmlIdxItems(List<SecHtmlIdx> list) {
		htmlIdxItems.clear();

		// 这里也有一步过滤即配置过滤
		// 将需要判断的内容进行解析，不需要的可以抛弃
		// 90%以上的内容包括在第一列数据中，即过滤器中
		for (SecHtmlIdx secHtmlIdx : list) {
			if (SecFilter.INSTANCE.doFilter(secHtmlIdx)) {
				htmlIdxItems.add(secHtmlIdx);
			}
		}
	}

	/**
	 * 清理内存中对象
	 */
	public void clear() {
		idxItems.clear();
		htmlIdxItems.clear();
	}

	public List<SecIdx> getIdxItems() {
		return idxItems;
	}

	public List<SecHtmlIdx> getHtmlIdxItems() {
		return htmlIdxItems;
	}
}
