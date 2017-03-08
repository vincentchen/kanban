package com.idealism.kanban.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "msg")
public class Msg {
	private int msg_id;
	private int src_type;// 消息类型(1:系统消息,2:项目消息,3:审批消息,4:用户消息,5广播消息,6创意消息,7求助消息,8交流消息)
	private int src_id;
	private String msg_title;
	private String msg_content;
	private Date create_time;
	private int create_user_id;
	private int attach_flag;

	@Id
	@GeneratedValue
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

	@Column(columnDefinition="TEXT")
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

}