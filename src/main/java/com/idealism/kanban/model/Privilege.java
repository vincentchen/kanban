package com.idealism.kanban.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "privilege")
public class Privilege {
	private int privilege_id;
	private String privilege_name;
	private String privilege_dec;
	private String privilege_define;

	@Id
	@GeneratedValue
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

	@Column(columnDefinition="TEXT")
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