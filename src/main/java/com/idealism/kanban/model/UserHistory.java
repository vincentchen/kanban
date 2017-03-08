package com.idealism.kanban.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_history")
public class UserHistory implements Serializable {
	private int history_id;
	private int user_id;
	private int history_type;
	private int history_type_id;
	private int history_status;
	private String history_content;
	private Date log_time;

	@Id
	@GeneratedValue
	public int getHistory_id() {
		return history_id;
	}

	public void setHistory_id(int history_id) {
		this.history_id = history_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getHistory_type() {
		return history_type;
	}

	public void setHistory_type(int history_type) {
		this.history_type = history_type;
	}

	public int getHistory_type_id() {
		return history_type_id;
	}

	public void setHistory_type_id(int history_type_id) {
		this.history_type_id = history_type_id;
	}

	public int getHistory_status() {
		return history_status;
	}

	public void setHistory_status(int history_status) {
		this.history_status = history_status;
	}

	@Column(columnDefinition="TEXT")
	public String getHistory_content() {
		return history_content;
	}

	public void setHistory_content(String history_content) {
		this.history_content = history_content;
	}

	public Date getLog_time() {
		return log_time;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}
}