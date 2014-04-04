package org.gingko.app.persist.domain.sys;

import java.io.Serializable;

/**
 * @author TangYing
 */
public class IdentityMenu implements Serializable {

    private int id;
    private int identity;
    private String menuId;

    public IdentityMenu() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
