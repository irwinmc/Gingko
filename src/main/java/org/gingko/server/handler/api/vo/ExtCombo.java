package org.gingko.server.handler.api.vo;

import org.gingko.app.persist.domain.Report;
import org.gingko.app.persist.domain.sys.Group;
import org.gingko.app.persist.domain.sys.Menu;
import org.gingko.app.persist.domain.usk.FormType;

public class ExtCombo {

	private Object value;
	private Object display;

	public ExtCombo(Menu menu) {
		this.value = menu.getMenuId();
		this.display = menu.getText();
	}

    public ExtCombo(Group group) {
        this.value = group.getGroupId();
        this.display = group.getName();
    }

    public ExtCombo(FormType formType) {
        this.value = formType.getFormType();
        this.display = formType.getFormType();
    }

    public ExtCombo(Report report) {
        this.value = report.getType();
        this.display = report.getName();
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
