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
@Table(name="tb_diary")
@SequenceGenerator(name= "seq_tb_diary", sequenceName = "SEQ_ID_DIARY", initialValue=1, allocationSize = 1)
public class TbDiary {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seq_tb_diary")
//	@JsonIgnore
//	@JsonSetter
	@Column(name="id_diary")
	private Integer id_diary;
	
	@Column(name="title")
	private String title;
	
	@Column(name="body")
	private String body;
	
	@JsonIgnore
	@JsonSetter
	@Column(name="created_at")
	private Timestamp created_at;
	
	@JsonIgnore
	@JsonSetter
	@Column(name="updated_at")
	private Timestamp updated_at;

	public Integer getId_diary() {
		return id_diary;
	}

	public void setId_diary(Integer id_diary) {
		this.id_diary = id_diary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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
