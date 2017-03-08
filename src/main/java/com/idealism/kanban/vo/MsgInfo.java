package com.idealism.kanban.vo;

import java.util.Date;

import com.idealism.kanban.util.Page;

public class MsgInfo extends Page {
	private int msg_id;
	private int src_type;// 消息类型(1:系统消息,2:项目消息,3:审批消息,4:用户消息,5广播消息,6创意消息,7求助消息,8交流消息)
	private int src_id;
	private String msg_title;
	private String msg_content;
	private Date create_time;
	private String create_time_str;
	private int create_user_id;
	private int attach_flag;
	private int is_read;
	private String src_type_name;
	private String src_url;
	private String create_user_name;
	private int showwhat;
	private String userIdList;

	private String msgIDs;
	private int id;

	public int getShowwhat() {
		return showwhat;
	}

	public void setShowwhat(int showwhat) {
		this.showwhat = showwhat;
	}

	private int user_id;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	public int getSrc_type() {
		return src_type;
	}

	public void setSrc_type(int src_type) {
		this.src_type = src_type;
	}

	public int getSrc_id() {
		return src_id;
	}

	public void setSrc_id(int src_id) {
		this.src_id = src_id;
	}

	public String getMsg_title() {
		return msg_title;
	}

	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getCreate_time_str() {
		return create_time_str;
	}

	public void setCreate_time_str(String create_time_str) {
		this.create_time_str = create_time_str;
	}

	public int getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}

	public int getAttach_flag() {
		return attach_flag;
	}

	public void setAttach_flag(int attach_flag) {
		this.attach_flag = attach_flag;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

	public String getSrc_type_name() {
		return src_type_name;
	}

	public void setSrc_type_name(String src_type_name) {
		this.src_type_name = src_type_name;
	}

	public String getSrc_url() {
		return src_url;
	}

	public void setSrc_url(String src_url) {
		this.src_url = src_url;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}

	public String getMsgIDs() {
		return msgIDs;
	}

	public void setMsgIDs(String msgIDs) {
		this.msgIDs = msgIDs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
