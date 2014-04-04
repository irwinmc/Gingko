package org.gingko.server.handler.api.vo;

import org.gingko.app.persist.domain.sys.Group;
import org.gingko.app.persist.domain.sys.Menu;
import org.gingko.app.persist.domain.usk.FormType;

public class ExtEditor {

	private String xtype;
	private boolean allowBlank;

    public ExtEditor(String xtype, boolean allowBlank) {
        this.xtype = xtype;
        this.allowBlank = allowBlank;
    }

    public String getXtype() {
        return xtype;
    }

    public void setXtype(String xtype) {
        this.xtype = xtype;
    }

    public boolean isAllowBlank() {
        return allowBlank;
    }

    public void setAllowBlank(boolean allowBlank) {
        this.allowBlank = allowBlank;
    }
}
