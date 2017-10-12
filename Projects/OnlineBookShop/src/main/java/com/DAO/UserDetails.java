package com.DAO;

import javax.persistence.*;

/*Method for creating the user details table.*/
@Entity
@Table
public class UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserDetails(String email, String password, String type) {
		super();
		this.email = email;
		this.password = password;
		this.type = type;
	}

	public UserDetails() {
		super();
	}

}
