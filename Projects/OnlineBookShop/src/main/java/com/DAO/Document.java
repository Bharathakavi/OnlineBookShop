package com.DAO;

import javax.persistence.*;

@Entity
@Table
public class Document
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String label;

	@Column
	private String parent_id;

	@Column
	private String filename;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Document(String label, String parent_id, String filename) {
		super();
		this.label = label;
		this.parent_id = parent_id;
		this.filename = filename;
	}

	public Document() {
		super();
	}

}
