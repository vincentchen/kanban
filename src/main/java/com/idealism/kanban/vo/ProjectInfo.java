package com.idealism.kanban.vo;

import java.util.Date;

import com.idealism.kanban.util.Page;

public class ProjectInfo extends Page {
	private int id;
	private String userIDs;
	private int pro_id;
	private String targetStatus;
	private String pro_name;
	private String pro_des;
	private Date create_time;
	private String create_time_str;
	private Date start_time;
	private Date datetime;
	private Date end_time;
	private Date start_pro_time;
	private Date end_pro_time;
	private int estimated_day;
	private int pro_status;
	private int imm_start;
	private String task_type;
	private int create_user_id;
	private String create_user_name;
	private Date history_max_time;
	private Date history_min_time;
	private int is_open;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(String userIDs) {
		this.userIDs = userIDs;
	}


	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public String getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(String targetStatus) {
		this.targetStatus = targetStatus;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getPro_des() {
		return pro_des;
	}

	public void setPro_des(String pro_des) {
		this.pro_des = pro_des;
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

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Date getStart_pro_time() {
		return start_pro_time;
	}

	public void setStart_pro_time(Date start_pro_time) {
		this.start_pro_time = start_pro_time;
	}

	public Date getEnd_pro_time() {
		return end_pro_time;
	}

	public void setEnd_pro_time(Date end_pro_time) {
		this.end_pro_time = end_pro_time;
	}

	public int getEstimated_day() {
		return estimated_day;
	}

	public void setEstimated_day(int estimated_day) {
		this.estimated_day = estimated_day;
	}

	public int getPro_status() {
		return pro_status;
	}

	public void setPro_status(int pro_status) {
		this.pro_status = pro_status;
	}

	public int getImm_start() {
		return imm_start;
	}

	public void setImm_start(int imm_start) {
		this.imm_start = imm_start;
	}

	public String getTask_type() {
		return task_type;
	}

	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}

	public int getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public Date getHistory_max_time() {
		return history_max_time;
	}

	public void setHistory_max_time(Date history_max_time) {
		this.history_max_time = history_max_time;
	}

	public Date getHistory_min_time() {
		return history_min_time;
	}

	public void setHistory_min_time(Date history_min_time) {
		this.history_min_time = history_min_time;
	}

	public int getIs_open() {
		return is_open;
	}

	public void setIs_open(int is_open) {
		this.is_open = is_open;
	}
	
	

}
