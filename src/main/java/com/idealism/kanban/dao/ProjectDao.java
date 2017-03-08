package com.idealism.kanban.dao;

import java.util.Date;
import java.util.List;

import com.idealism.kanban.model.Project;
import com.idealism.kanban.vo.ProjectInfo;

public interface ProjectDao {

	public abstract Project LoadProByID(int id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadProTargetUnFinishById(int id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadProTargetFinishedById(int id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadProTaskById(int id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadProFiles(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadProject(ProjectInfo projectInfo);

	public abstract void Save(Project project);
	
	public abstract void InsertRelUsers(int pro_id,int user_id);

	public abstract void DeleteProject(int id);
	
	public abstract void DeleteProRelUsers(int id);
	
	public abstract void DeleteProRelFiles(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadProUsers(int id);

	public abstract Boolean CheckRelUsers(int id, int user_id);

	public abstract void DeleteRelUsers(int id, int user_id);


	@SuppressWarnings("rawtypes")
	public abstract List LoadTargetGraphByProID(int pro_id);

	public abstract int LoadCntTaskByTargerID(int target_id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadTaskByTargetID(int target_id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadHistoryByProID(int id);

	@SuppressWarnings("rawtypes")
	public abstract List SearchProjectHistory(Date history_min_time, Date history_max_time, int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAll();

	@SuppressWarnings("rawtypes")
	public abstract List LoadAttentionProtarget(int target_id, int user_id);

}
