package com.idealism.kanban.vo;

import java.util.Date;

import com.idealism.kanban.util.Page;

public class HelpInfo extends Page {
	private int id;
	private Date create_time;
	private String help_content;
	private String help_title;
	private int help_user_id;
	private String help_user_name;
	private Date max_create_time;
	private Date max_solve_time;
	private Date min_create_time;
	private Date min_solve_time;
	private int request_id;
	private int search_type;
	private int seek_help_user_id;
	private String seek_help_user_name;
	private Date solve_time;
	private int user_id;
	private Boolean hasAttach;
	
	private int seek_help_cnt;
	private int helper_cnt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getHasAttach() {
		return hasAttach;
	}

	public void setHasAttach(Boolean hasAttach) {
		this.hasAttach = hasAttach;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public String getHelp_content() {
		return help_content;
	}

	public String getHelp_title() {
		return help_title;
	}

	public int getHelp_user_id() {
		return help_user_id;
	}

	public String getHelp_user_name() {
		return help_user_name;
	}

	public Date getMax_create_time() {
		return max_create_time;
	}

	public Date getMax_solve_time() {
		return max_solve_time;
	}

	public Date getMin_create_time() {
		return min_create_time;
	}

	public Date getMin_solve_time() {
		return min_solve_time;
	}

	public int getRequest_id() {
		return request_id;
	}

	public int getSearch_type() {
		return search_type;
	}

	public int getSeek_help_user_id() {
		return seek_help_user_id;
	}

	public String getSeek_help_user_name() {
		return seek_help_user_name;
	}

	public Date getSolve_time() {
		return solve_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public void setHelp_content(String help_content) {
		this.help_content = help_content;
	}

	public void setHelp_title(String help_title) {
		this.help_title = help_title;
	}

	public void setHelp_user_id(int help_user_id) {
		this.help_user_id = help_user_id;
	}

	public void setHelp_user_name(String help_user_name) {
		this.help_user_name = help_user_name;
	}

	public void setMax_create_time(Date max_create_time) {
		this.max_create_time = max_create_time;
	}

	public void setMax_solve_time(Date max_solve_time) {
		this.max_solve_time = max_solve_time;
	}

	public void setMin_create_time(Date min_create_time) {
		this.min_create_time = min_create_time;
	}

	public void setMin_solve_time(Date min_solve_time) {
		this.min_solve_time = min_solve_time;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public void setSearch_type(int search_type) {
		this.search_type = search_type;
	}

	public void setSeek_help_user_id(int seek_help_user_id) {
		this.seek_help_user_id = seek_help_user_id;
	}

	public void setSeek_help_user_name(String seek_help_user_name) {
		this.seek_help_user_name = seek_help_user_name;
	}

	public void setSolve_time(Date solve_time) {
		this.solve_time = solve_time;
	}

	public int getSeek_help_cnt() {
		return seek_help_cnt;
	}

	public void setSeek_help_cnt(int seek_help_cnt) {
		this.seek_help_cnt = seek_help_cnt;
	}

	public int getHelper_cnt() {
		return helper_cnt;
	}

	public void setHelper_cnt(int helper_cnt) {
		this.helper_cnt = helper_cnt;
	}
	
	
}
