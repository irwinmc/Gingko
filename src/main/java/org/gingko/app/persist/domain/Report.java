package org.gingko.app.persist.domain;

import java.io.Serializable;

/**
 * @author Kyia
 */
public class Report implements Serializable {

	private String type;
	private String name;

	public Report() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
