package org.gingko.app.persist.domain.usk;

import java.io.Serializable;

/**
 * @author TangYing
 */
public class Cik implements Serializable {

    private String id;
    private String cik;
    private String code;

    public Cik() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCik() {
        return cik;
    }

    public void setCik(String cik) {
        this.cik = cik;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o.getClass() != this.getClass())
            return false;
        Cik c = (Cik) o;
        return c.getCik().equals(cik) && c.getCode().equals(code);
    }
}
