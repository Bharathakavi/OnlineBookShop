package com.DAO;

import javax.persistence.*;

/*Modal class for the requesting book to store its details to the database.*/
@Entity
@Table
public class RequestBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;
	@Column
	private String author;
	@Column
	private String email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RequestBook(String name, String author) {
		super();
		this.name = name;
		this.author = author;
	}
	public RequestBook() {
		super();
	}
	public RequestBook(String name) {
		super();
		this.name = name;
	}
	
		
}
