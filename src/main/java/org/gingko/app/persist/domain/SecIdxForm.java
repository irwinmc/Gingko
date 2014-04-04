package org.gingko.app.persist.domain;

import java.io.Serializable;

/**
 * @author TangYing
 */
public class SecIdxForm implements Serializable {

    private int id;
	private String siid;
    private String formType;
	private String reportType;
    private String name;
    private String date;
	private String localFile;
    private String anchor;
    private int state;

	public SecIdxForm() {

	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiid() {
        return siid;
    }

    public void setSiid(String siid) {
        this.siid = siid;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalFile() {
        return localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
