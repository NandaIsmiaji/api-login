package com.apilogin.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name="tb_users")
@SequenceGenerator(name= "seq_tb_users", sequenceName = "SEQ_ID_USERS", initialValue=1, allocationSize = 1)
public class TbUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_tb_users")
	@Column(name="users_id")
	private Integer users_id;
	
	@Column(name="username",length = 255, nullable = true)
	private String username;
	
	@JsonIgnore
	@JsonSetter
	@Column(name="password", nullable = true)
	private String password;
	
	@Column(name="email",length = 255, nullable = false)
	private String email;
	
	@JsonIgnore
	@JsonSetter
	@Column(name="token",length = 255)
	private String token;
	
	@JsonIgnore
	@JsonSetter
	@Column(name="created_at")
	private Timestamp created_at;
	
	@JsonIgnore
	@JsonSetter
	@Column(name="updated_at")
	private Timestamp updated_at;

	public Integer getUsers_id() {
		return users_id;
	}

	public void setUsers_id(Integer users_id) {
		this.users_id = users_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	
}
