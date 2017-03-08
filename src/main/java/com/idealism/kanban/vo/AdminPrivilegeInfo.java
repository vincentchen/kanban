package com.idealism.kanban.vo;

import com.idealism.kanban.util.Page;

public class AdminPrivilegeInfo extends Page {
	private int id;
	private int privilege_id;
	private String privilege_name;
	private String privilege_dec;
	private String privilege_define;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrivilege_id() {
		return privilege_id;
	}

	public void setPrivilege_id(int privilege_id) {
		this.privilege_id = privilege_id;
	}

	public String getPrivilege_name() {
		return privilege_name;
	}

	public void setPrivilege_name(String privilege_name) {
		this.privilege_name = privilege_name;
	}

	public String getPrivilege_dec() {
		return privilege_dec;
	}

	public void setPrivilege_dec(String privilege_dec) {
		this.privilege_dec = privilege_dec;
	}

	public String getPrivilege_define() {
		return privilege_define;
	}

	public void setPrivilege_define(String privilege_define) {
		this.privilege_define = privilege_define;
	}

}
