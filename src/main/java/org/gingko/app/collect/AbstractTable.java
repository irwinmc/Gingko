package org.gingko.app.collect;

/**
 * @author Kyia
 */
public abstract class AbstractTable implements Table {

	/**
	 * 表格名称
	 */
	protected String name;

	/**
	 * 表格关键字
	 */
	protected String keywords;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
