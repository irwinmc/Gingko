package org.gingko.app.persist.domain.sys;

import java.io.Serializable;

/**
 * @author TangYing
 */
public class Group implements Serializable {

    private int groupId;
    private String name;
    private String host;

    public Group() {

    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
