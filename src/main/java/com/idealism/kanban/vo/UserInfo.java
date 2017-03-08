package com.idealism.kanban.vo;

import java.io.File;
import java.util.Date;

import com.idealism.kanban.util.Page;

public class UserInfo extends Page {
	private int id;
	private String usersSelectType;
	private String callBackFunc;
	private String usersSelectList;
	private String userIDs;

	private String config_text;
	private String email;
	private File file_photo;
	private String file_photoFileName;
	private String history_max_time;
	private String history_min_time;
	private String max_create_time;
	private String max_login_time;
	private String min_create_time;
	private String min_login_time;
	private String password;
	private String user_account;
	private int user_id;
	private String user_name;
	private String user_photo;
	private Date create_time;
	private Date login_time;
	private String privilege;
	private String cookie_key;
	private int is_delete;
	
	private int idea_send;
	private int help_send;
	private int pro_send;
	private int protarget_send;
	
	private String login_time_str;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsersSelectType() {
		return usersSelectType;
	}

	public void setUsersSelectType(String usersSelectType) {
		this.usersSelectType = usersSelectType;
	}

	public String getCallBackFunc() {
		return callBackFunc;
	}

	public void setCallBackFunc(String callBackFunc) {
		this.callBackFunc = callBackFunc;
	}

	public String getUsersSelectList() {
		return usersSelectList;
	}

	public void setUsersSelectList(String usersSelectList) {
		this.usersSelectList = usersSelectList;
	}
	

	public String getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(String userIDs) {
		this.userIDs = userIDs;
	}

	public String getConfig_text() {
		return config_text;
	}

	public String getEmail() {
		return email;
	}

	public File getFile_photo() {
		return file_photo;
	}

	public String getFile_photoFileName() {
		return file_photoFileName;
	}

	public String getHistory_max_time() {
		return history_max_time;
	}

	public String getHistory_min_time() {
		return history_min_time;
	}

	public String getMax_create_time() {
		return max_create_time;
	}

	public String getMax_login_time() {
		return max_login_time;
	}

	public String getMin_create_time() {
		return min_create_time;
	}

	public String getMin_login_time() {
		return min_login_time;
	}

	public String getPassword() {
		return password;
	}

	public String getUser_account() {
		return user_account;
	}

	public int getUser_id() {
		return user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getUser_photo() {
		return user_photo;
	}

	public void setConfig_text(String config_text) {
		this.config_text = config_text;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFile_photo(File file_photo) {
		this.file_photo = file_photo;
	}

	public void setFile_photoFileName(String file_photoFileName) {
		this.file_photoFileName = file_photoFileName;
	}

	public void setHistory_max_time(String history_max_time) {
		this.history_max_time = history_max_time;
	}

	public void setHistory_min_time(String history_min_time) {
		this.history_min_time = history_min_time;
	}

	public void setMax_create_time(String max_create_time) {
		this.max_create_time = max_create_time;
	}

	public void setMax_login_time(String max_login_time) {
		this.max_login_time = max_login_time;
	}

	public void setMin_create_time(String min_create_time) {
		this.min_create_time = min_create_time;
	}

	public void setMin_login_time(String min_login_time) {
		this.min_login_time = min_login_time;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getCookie_key() {
		return cookie_key;
	}

	public void setCookie_key(String cookie_key) {
		this.cookie_key = cookie_key;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public int getIdea_send() {
		return idea_send;
	}

	public void setIdea_send(int idea_send) {
		this.idea_send = idea_send;
	}

	public int getHelp_send() {
		return help_send;
	}

	public void setHelp_send(int help_send) {
		this.help_send = help_send;
	}

	public int getPro_send() {
		return pro_send;
	}

	public void setPro_send(int pro_send) {
		this.pro_send = pro_send;
	}

	public int getProtarget_send() {
		return protarget_send;
	}

	public void setProtarget_send(int protarget_send) {
		this.protarget_send = protarget_send;
	}

	public String getLogin_time_str() {
		return login_time_str;
	}

	public void setLogin_time_str(String login_time_str) {
		this.login_time_str = login_time_str;
	}

}