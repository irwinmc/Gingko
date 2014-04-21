package org.gingko.app.persist.domain;

import java.io.Serializable;

/**
 * @author Kyia
 *
 * CIK|Company Name|Form Type|Date Filed|File Name
 */
public class SecIdx implements Serializable {

    /** sec idx id (cik and fileName) */
    private String siid;
	private String cik;
	private String companyName;
	private String formType;
	private String dateField;
	private String fileName;
	/** fill document index html url */
	private String fillingHtmlUrl;
	/** date time, according to the idx date */
	private String date;
    private String code;
    private int amount;

	public SecIdx() {

	}

	public String getCik() {
		return cik;
	}

	public void setCik(String cik) {
		this.cik = cik;
	}

    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getDateField() {
		return dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFillingHtmlUrl() {
		return fillingHtmlUrl;
	}

	public void setFillingHtmlUrl(String fillingHtmlUrl) {
		this.fillingHtmlUrl = fillingHtmlUrl;
	}

	public String getSiid() {
		return siid;
	}

	public void setSiid(String siid) {
		this.siid = siid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
