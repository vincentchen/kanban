package com.idealism.kanban.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seek_help")
public class Help {
	private int request_id;
	private int seek_help_user_id;
	private String help_title;
	private String help_content;
	private int help_user_id;
	private Date create_time;
	private Date solve_time;
	private int is_delete;

	@Id
	@GeneratedValue
	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public int getSeek_help_user_id() {
		return seek_help_user_id;
	}

	public void setSeek_help_user_id(int seek_help_user_id) {
		this.seek_help_user_id = seek_help_user_id;
	}

	public String getHelp_title() {
		return help_title;
	}

	public void setHelp_title(String help_title) {
		this.help_title = help_title;
	}

	@Column(columnDefinition="TEXT")
	public String getHelp_content() {
		return help_content;
	}

	public void setHelp_content(String help_content) {
		this.help_content = help_content;
	}

	public int getHelp_user_id() {
		return help_user_id;
	}

	public void setHelp_user_id(int help_user_id) {
		this.help_user_id = help_user_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getSolve_time() {
		return solve_time;
	}

	public void setSolve_time(Date solve_time) {
		this.solve_time = solve_time;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

}