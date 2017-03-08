package com.idealism.kanban.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project {
	private int pro_id;
	private String pro_name;
	private String pro_des;
	private Date create_time;
	private Date start_time;
	private Date end_time;
	private Date start_pro_time;
	private Date end_pro_time;
	private int estimated_day;
	private int pro_status;
	private int create_user_id;
	private int is_open;

	@Id
	@GeneratedValue
	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	@Column(columnDefinition="TEXT")
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

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
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

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
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

	public int getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}

	public int getIs_open() {
		return is_open;
	}

	public void setIs_open(int is_open) {
		this.is_open = is_open;
	}

}
