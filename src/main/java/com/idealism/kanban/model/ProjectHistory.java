package com.idealism.kanban.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_history")
public class ProjectHistory {
	private int history_id;
	private int pro_id;
	private int target_id;
	private int task_id;
	private int history_status;
	private String history_content;
	private Date create_time;

	@Id
	@GeneratedValue
	public int getHistory_id() {
		return history_id;
	}

	public void setHistory_id(int history_id) {
		this.history_id = history_id;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public int getTarget_id() {
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
