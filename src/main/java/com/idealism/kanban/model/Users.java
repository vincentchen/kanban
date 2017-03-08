package com.idealism.kanban.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users implements Serializable {
	private int user_id;
	private String user_account;
	private String user_name;
	private String password;
	private String user_photo;
	private Date create_time;
	private Date login_time;
	private String privilege;
	private String email;
	private String cookie_key;
	private String config_text;
	private int is_delete;

	@Id
	@GeneratedValue
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(columnDefinition="TEXT")
	public String getUser_photo() {
		return user_photo;
	}

	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	@Column(columnDefinition="TEXT")
	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	@Column(columnDefinition="TEXT")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(columnDefinition="TEXT")
	public String getCookie_key() {
		return cookie_key;
	}

	public void setCookie_key(String cookie_key) {
		this.cookie_key = cookie_key;
	}

	@Column(columnDefinition="TEXT")
	public String getConfig_text() {
		return config_text;
	}

	public void setConfig_text(String config_text) {
		this.config_text = config_text;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	
}
