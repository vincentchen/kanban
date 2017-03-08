package com.idealism.kanban.service;

import java.util.Date;
import java.util.List;

import com.idealism.kanban.model.Project;
import com.idealism.kanban.vo.ProjectInfo;

public interface ProjectService {

	public abstract Project LoadProById(int id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadProTargetUnFinishById(int id, int attention, int user_id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadProTargetFinishedById(int id, int attention, int user_id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadProFiles(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadProject(ProjectInfo projectInfo);

	public abstract void SaveInfo(ProjectInfo projectInfo);
	
	public abstract void Save(Project project);

	public abstract void DeleteProject(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadProUsers(int id);

	@SuppressWarnings("rawtypes")
	public abstract void SaveRelUsers(int id, List userlist);

	public abstract void DeleteRelUsers(int id, int user_id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadTargetGraphList(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadHistoryByProID(int id);

	@SuppressWarnings("rawtypes")
	public abstract List SearchProjectHistory(Date history_min_time, Date history_max_time, int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAll();

	public abstract void UpdateStatus(Project project);

}
