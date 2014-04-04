package org.gingko.app.persist.domain.usk;

import java.io.Serializable;

/**
 * @author TangYing
 */
public class FormType implements Serializable {

    private int id;
    private int groupId;
    private String formType;

    public FormType() {

    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
