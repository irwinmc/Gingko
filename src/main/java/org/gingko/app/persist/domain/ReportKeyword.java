package org.gingko.app.persist.domain;

import java.io.Serializable;

/**
 * @author Kyia
 */
public class ReportKeyword implements Serializable {

	private int id;
	private String type;
	private String keyword;

	public ReportKeyword() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
