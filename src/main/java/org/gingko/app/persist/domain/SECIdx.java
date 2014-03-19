package org.gingko.app.persist.domain;

import java.io.Serializable;

/**
 * @author Kyia
 *
 * CIK|Company Name|Form Type|Date Filed|File Name
 */
public class SecIdx implements Serializable {

	private int id;
	private String cik;
	private String companyName;
	private String formType;
	private String dateField;
	private String fileName;
	/** fill document index html url */
	private String fillingHtmlUrl;
	/** local index html file */
	private String localFile;

	public SecIdx() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getLocalFile() {
		return localFile;
	}

	public void setLocalFile(String localFile) {
		this.localFile = localFile;
	}
}
