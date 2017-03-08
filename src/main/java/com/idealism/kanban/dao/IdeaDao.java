package com.idealism.kanban.dao;

import java.math.BigInteger;
import java.util.List;

import com.idealism.kanban.model.Idea;
import com.idealism.kanban.vo.IdeaInfo;

public interface IdeaDao {

	@SuppressWarnings("rawtypes")
	public abstract List LoadIdeaList(IdeaInfo ideaInfo);
	
	public abstract Boolean HasAttach(int id);
	
	public abstract Boolean HasChoose(int id,int user_id);

	public abstract Idea LoadIdeaById(int id);

	public abstract void Save(Idea idea);

	@SuppressWarnings("rawtypes")
	public abstract List LoadIdeaFiles(int id);

	public abstract void SaveVoteByUser(int id, int user_id);

	public abstract BigInteger CheckCanOperation(int id, int user_id);

	public abstract void Delete(int id);

	public abstract void UpdateByAuditIdea(int id);

	public abstract int CheckIsRead(int id, int user_id);

	public abstract void UpdateMsgReadStatus(int msg_id, int user_id);

	public abstract int LoadMsgIDByIdeaId(int id);

}
