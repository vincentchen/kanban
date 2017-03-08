package com.idealism.kanban.service;

import java.util.List;

import com.idealism.kanban.model.Msg;
import com.idealism.kanban.vo.MsgInfo;

public interface MsgService {
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadMsgList(MsgInfo msgInfo);

	@SuppressWarnings("rawtypes")
	public abstract void Save(Msg msg, List userlist);

	@SuppressWarnings("rawtypes")
	public abstract void SaveUsers(int msg_id, List userList);

	@SuppressWarnings("rawtypes")
	public abstract void DeleteUser(int user_id, List msgIDs);

	public abstract MsgInfo LoadMsgById(int msg_id);

	public abstract void UpdateRead(int msg_id, int user_id);
}
