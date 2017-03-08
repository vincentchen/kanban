package com.idealism.kanban.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rel_dept_user")
public class RelDeptUser {
	private RelDeptUserPK relDeptUserPK;
	private int role_id;
	private Date temp;

	@Id
	public RelDeptUserPK getRelDeptUserPK() {
		return relDeptUserPK;
	}

	public void setRelDeptUserPK(RelDeptUserPK relDeptUserPK) {
		this.relDeptUserPK = relDeptUserPK;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public Date getTemp() {
		return temp;
	}

	public void setTemp(Date temp) {
		this.temp = temp;
	}

}