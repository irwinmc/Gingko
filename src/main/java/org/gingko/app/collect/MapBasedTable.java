package org.gingko.app.collect;

import java.util.Map;

/**
 * @author Kyia
 *
 * 仅适用于没有跨行跨列的单纯数据，纯粹的数据映射关系
 */
public class MapBasedTable extends AbstractTable {

	protected Map<String, Map<String, String>> backingMap;

	public MapBasedTable(Map<String, Map<String, String>> backingMap) {
		this.backingMap = backingMap;
	}

	@Override
	public boolean isEmpty() {
		return backingMap.isEmpty();
	}

	@Override
	public int size() {
		int size = 0;
		for (Map<String, String> map : backingMap.values()) {
			size += map.size();
		}
		return size;
	}

	@Override
	public void clear() {
		backingMap.clear();
	}
}
