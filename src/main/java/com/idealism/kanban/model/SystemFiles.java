package com.idealism.kanban.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_files")
public class SystemFiles {
	private int files_id;
	private String files_name;
	private String files_ext;
	private String files_url;
	private String files_des;
	private int is_delete;

	@Id
	@GeneratedValue
	public int getFiles_id() {
		return files_id;
	}

	public void setFiles_id(int files_id) {
		this.files_id = files_id;
	}

	public String getFiles_name() {
		return files_name;
	}

	public void setFiles_name(String files_name) {
		this.files_name = files_name;
	}

	@Column(columnDefinition="TEXT")
	public String getFiles_ext() {
		return files_ext;
	}

	public void setFiles_ext(String files_ext) {
		this.files_ext = files_ext;
	}

	@Column(columnDefinition="TEXT")
	public String getFiles_url() {
		return files_url;
	}

	public void setFiles_url(String files_url) {
		this.files_url = files_url;
	}

	@Column(columnDefinition="TEXT")
	public String getFiles_des() {
		return files_des;
	}

	public void setFiles_des(String files_des) {
		this.files_des = files_des;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
}
