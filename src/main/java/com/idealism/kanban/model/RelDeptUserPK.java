package com.idealism.kanban.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RelDeptUserPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private int dept_id;
	private int user_id;

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}