package com.idealism.kanban.dao;

import java.util.Date;
import java.util.List;

import com.idealism.kanban.model.ProTarget;

public interface ProTargetDao {

	@SuppressWarnings("rawtypes")
	public abstract List LoadTaskTodo(int id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadTaskDoing(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadTaskDone(int id);

	public abstract ProTarget LoadProTargetById(int id);

	public abstract void Save(ProTarget protarget);

	@SuppressWarnings("rawtypes")
	public abstract List LoadProFiles(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadTaskGraphList(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadCurrentMonth(int id);

	@SuppressWarnings("rawtypes")
	public abstract List SearchProjectHistory(Date history_min_time, Date history_max_time, int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAttention(int user_id, int target_id);

	public abstract void DeleteAttention(int user_id, int id);

	public abstract void InsertAttention(int user_id, int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadRelProUser(int pro_id);

	public abstract int checkCanOperation(int id, int user_id);

}