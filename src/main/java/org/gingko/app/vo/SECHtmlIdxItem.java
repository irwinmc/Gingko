package org.gingko.app.vo;

/**
 * @author Kyia
 *
 * Seq|Description|Document|Type|Size
 */
public class SECHtmlIdxItem {

	private int seq;
	private String description;
	private String document;
	private String type;
	private int size;
	/** Document anchor */
	private String anchor;

	public SECHtmlIdxItem() {

	}

	public SECHtmlIdxItem(int seq, String description, String document, String type, int size, String anchor) {
		this.seq = seq;
		this.description = description;
		this.document = document;
		this.type = type;
		this.size = size;
		this.anchor = anchor;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
}
