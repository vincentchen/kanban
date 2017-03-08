package com.idealism.kanban.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RelMsgUserPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private int msg_id;
	private int user_id;

	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
