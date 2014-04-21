package org.gingko.app.collect;

import java.util.Map;

/**
 * @author Kyia
 *         <p/>
 *         仅适用于没有跨行跨列的单纯数据，纯粹的数据映射关系
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

	/**
	 * Get row map
	 *
	 * @param columnKey
	 * @return
	 */
	public Map<String, String> rowMap(String columnKey) {
		return columnKey == null ? null : backingMap.get(columnKey);
	}

	/**
	 * Get
	 *
	 * @param rowKey
	 * @param columnKey
	 * @return
	 */
	public String get(String rowKey, String columnKey) {
		return (rowKey == null || columnKey == null)
				? null
				: (rowMap(columnKey) == null ? null : rowMap(columnKey).get(rowKey));
	}

	/**
	 * Check contains
	 *
	 * @param rowKey
	 * @param columnKey
	 * @return
	 */
	public boolean contains(String rowKey, String columnKey) {
		return rowKey != null && columnKey != null
				&& (rowMap(columnKey) != null && rowMap(columnKey).containsKey(rowKey));
	}

	/**
	 * Check contains column
	 *
	 * @param columnKey
	 * @return
	 */
	public boolean containsColumn(String columnKey) {
		if (columnKey == null) {
			return false;
		}
		if (backingMap.containsKey(columnKey)) {
			return true;
		}
		return false;
	}

	/**
	 * Check contains row
	 *
	 * @param rowKey
	 * @return
	 */
	public boolean containsRow(String rowKey) {
		if (rowKey == null) {
			return false;
		}
		for (Map<String, String> map : backingMap.values()) {
			if (map.containsKey(rowKey)) {
				return true;
			}
		}
		return false;
	}
}
