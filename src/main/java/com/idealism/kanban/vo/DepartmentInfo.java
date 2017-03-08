package com.idealism.kanban.vo;

import java.util.Date;

import com.idealism.kanban.util.Page;

public class DepartmentInfo extends Page {
	private int id;
	private int parentID;
	private int dept_id;
	private int parent_id;
	private String dept_name;
	private int has_child;
	private Date create_time;
	private int is_delete;

	private String callBackFunc;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public int getHas_child() {
		return has_child;
	}

	public void setHas_child(int has_child) {
		this.has_child = has_child;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public String getCallBackFunc() {
		return callBackFunc;
	}

	public void setCallBackFunc(String callBackFunc) {
		this.callBackFunc = callBackFunc;
	}

}
