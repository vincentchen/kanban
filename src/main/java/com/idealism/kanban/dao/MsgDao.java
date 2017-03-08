package com.idealism.kanban.dao;

import java.util.List;

import com.idealism.kanban.model.Msg;
import com.idealism.kanban.vo.MsgInfo;

public interface MsgDao {
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadMsgList(MsgInfo msgInfo);
	
	public abstract int LoadMsgIsRead(int msg_id,int user_id);

	public abstract void Save(Msg msg);

	public abstract void SaveUsers(int msg_id, Integer user_id);

	public abstract void DeleteUser(Integer user_id, Integer msg_id);

	public abstract Msg LoadMsgById(int msg_id);

	public abstract void UpdateRead(int msg_id, int user_id);

}
