package org.gingko.app.persist.domain.usk;

import java.io.Serializable;

/**
 * @author TangYing
 */
public class FormType implements Serializable {

    private String formType;
    private int used;

    public FormType() {

    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
