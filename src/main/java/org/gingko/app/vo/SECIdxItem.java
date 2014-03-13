package org.gingko.app.vo;

/**
 * @author Kyia
 *
 * CIK|Company Name|Form Type|Date Filed|File Name
 */
public class SECIdxItem {

	private String cik;
	private String companyName;
	private String formType;
	private String dateField;
	private String fileName;

	public SECIdxItem() {

	}

	public SECIdxItem(String cik, String companyName, String formType, String dateField, String fileName) {
		this.cik = cik;
		this.companyName = companyName;
		this.formType = formType;
		this.dateField = dateField;
		this.fileName = fileName;
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
}
