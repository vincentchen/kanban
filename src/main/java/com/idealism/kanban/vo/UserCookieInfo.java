package com.idealism.kanban.vo;

import java.io.Serializable;
import java.util.Date;

public class UserCookieInfo implements Serializable {
	private String user_account;
	private String password;
	private Date login_time;
	private String cookie_key;

	public String getCookie_key() {
		return cookie_key;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public String getPassword() {
		return password;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setCookie_key(String cookie_key) {
		this.cookie_key = cookie_key;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

}