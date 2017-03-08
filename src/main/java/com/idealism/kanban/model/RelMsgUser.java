package com.idealism.kanban.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rel_msg_user")
public class RelMsgUser {
	private RelMsgUserPK relMsgUserPK;
	private int is_read;

	@Id
	public RelMsgUserPK getRelMsgUserPK() {
		return relMsgUserPK;
	}

	public void setRelMsgUserPK(RelMsgUserPK relMsgUserPK) {
		this.relMsgUserPK = relMsgUserPK;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

}