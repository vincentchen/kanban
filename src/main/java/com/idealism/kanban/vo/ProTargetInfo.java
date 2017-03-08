package com.idealism.kanban.vo;

import java.util.Date;

public class ProTargetInfo {
	private int id;
	private int proID;
	private String type;
	private int imm_start;
	private int target_id;
	private int pro_id;
	private String target_name;
	private String target_des;
	private Date create_time;
	private int create_user_id;
	private Date update_time;
	private Date start_time;
	private Date end_time;
	private Date start_target_time;
	private Date end_target_time;
	private int estimated_day;
	private int target_status;
	private Date history_max_time;
	private Date history_min_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProID() {
		return proID;
	}

	public void setProID(int proID) {
		this.proID = proID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getImm_start() {
		return imm_start;
	}

	public void setImm_start(int imm_start) {
		this.imm_start = imm_start;
	}

	public int getTarget_id() {
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

	public String getTarget_des() {
		return target_des;
	}

	public void setTarget_des(String target_des) {
		this.target_des = target_des;
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

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Date getStart_target_time() {
		return start_target_time;
	}

	public void setStart_target_time(Date start_target_time) {
		this.start_target_time = start_target_time;
	}

	public Date getEnd_target_time() {
		return end_target_time;
	}

	public void setEnd_target_time(Date end_target_time) {
		this.end_target_time = end_target_time;
	}

	public int getEstimated_day() {
		return estimated_day;
	}

	public void setEstimated_day(int estimated_day) {
		this.estimated_day = estimated_day;
	}

	public int getTarget_status() {
		return target_status;
	}

	public void setTarget_status(int target_status) {
		this.target_status = target_status;
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

}
