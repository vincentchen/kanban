package com.idealism.kanban.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "pro_task")
public class ProTask implements Serializable {
	private int task_id;
	private int target_id;
	private String task_des;
	private Date create_time;
	private Date start_time;
	private Date end_time;
	private Date start_task_time;
	private Date end_task_time;

	private int status_value;// 任务状态.0-未开始.100-进行中.200-已完结.300-已取消
	private int create_user_id;
	private int receive_task_user;
	private float estimated_hour;
	private int urgent_degree;

	@Id
	@GeneratedValue
	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getTarget_id() {
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}

	@Column(columnDefinition="TEXT")
	public String getTask_des() {
		return task_des;
	}

	public void setTask_des(String task_des) {
		this.task_des = task_des;
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

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Date getStart_task_time() {
		return start_task_time;
	}

	public void setStart_task_time(Date start_task_time) {
		this.start_task_time = start_task_time;
	}

	public Date getEnd_task_time() {
		return end_task_time;
	}

	public void setEnd_task_time(Date end_task_time) {
		this.end_task_time = end_task_time;
	}

	public int getStatus_value() {
		return status_value;
	}

	public void setStatus_value(int status_value) {
		this.status_value = status_value;
	}

	public int getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}

	public int getReceive_task_user() {
		return receive_task_user;
	}

	public void setReceive_task_user(int receive_task_user) {
		this.receive_task_user = receive_task_user;
	}

	public float getEstimated_hour() {
		return estimated_hour;
	}

	public void setEstimated_hour(float estimated_hour) {
		this.estimated_hour = estimated_hour;
	}

	public int getUrgent_degree() {
		return urgent_degree;
	}

	public void setUrgent_degree(int urgent_degree) {
		this.urgent_degree = urgent_degree;
	}
}