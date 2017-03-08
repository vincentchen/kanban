package com.idealism.kanban.vo;

import java.io.Serializable;

public class LoginInfo implements Serializable {
	private String user_account;
	private String password;
	private String AutoLogin;// 自动登录

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAutoLogin() {
		return AutoLogin;
	}

	public void setAutoLogin(String autoLogin) {
		AutoLogin = autoLogin;
	}

}
