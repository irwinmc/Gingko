package org.gingko.app.persist.domain.sys;

import java.io.Serializable;

/**
 * @author TangYing
 */
public class Identity implements Serializable {

    private int identity;
    private String name;
    private String desc;

    public Identity() {

    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
