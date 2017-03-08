package com.idealism.kanban.service;

import java.util.List;

import com.idealism.kanban.model.Idea;
import com.idealism.kanban.vo.IdeaInfo;

public interface IdeaService {
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadIdeaList(IdeaInfo ideaInfo, int user_id);

	public abstract Idea LoadIdeaById(int id);

	public abstract void Save(Idea idea);

	@SuppressWarnings("rawtypes")
	public abstract List LoadIdeaFiles(int id);

	public abstract void SaveVoteByUser(int id, int user_id, int choose);

	public abstract Boolean CheckCanOperation(int id, int user_id);

	public abstract void Delete(int id);

	public abstract void UpdateByAuditIdea(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAdminIdeaList(IdeaInfo ideaInfo);
	
	public String LoadUserNameById(int id);
	
	public Boolean HasAttach(int id);
	
	public Boolean HasChoose(int id,int user_id);

	public abstract void UpdateReadStatus(int id, int user_id);

}
