package org.gingko.server.handler.api.vo;

import org.gingko.app.persist.domain.Menu;

public class ExtCombo {

	private Object value;
	private Object display;

	public ExtCombo(Menu menu) {
		this.value = menu.getMenuId();
		this.display = menu.getText();
	}

	public ExtCombo(String value, String display) {
		this.value = value;
		this.display = display;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getDisplay() {
		return display;
	}

	public void setDisplay(Object display) {
		this.display = display;
	}
}
