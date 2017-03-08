package com.idealism.kanban.vo;

import com.idealism.kanban.util.Page;

public class RoleInfo extends Page{
	private int id;
	private int parentID;
	private String priString;
	private int is_cover;
	private int role_id;
	private int parent_id;
	private String role_name;
	private String role_privilege;
	private int is_delete;
	
	private String callBackFunc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getPriString() {
		return priString;
	}

	public void setPriString(String priString) {
		this.priString = priString;
	}

	public int getIs_cover() {
		return is_cover;
	}

	public void setIs_cover(int is_cover) {
		this.is_cover = is_cover;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRole_privilege() {
		return role_privilege;
	}

	public void setRole_privilege(String role_privilege) {
		this.role_privilege = role_privilege;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
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
