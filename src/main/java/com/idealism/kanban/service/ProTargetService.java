package com.idealism.kanban.service;

import java.util.Date;
import java.util.List;

import com.idealism.kanban.model.ProTarget;

public interface ProTargetService {
	@SuppressWarnings("rawtypes")
	public abstract List LoadTask(int id,String type);

	public abstract ProTarget LoadProTargetById(int id);

	public abstract void Save(ProTarget protarget);

	@SuppressWarnings("rawtypes")
	public abstract List LoadTargetFiles(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadTaskGraphList(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadCurrentMonth(int id);

	@SuppressWarnings("rawtypes")
	public abstract List SearchProjectHistory(Date history_min_time, Date history_max_time, int id);

	public abstract void SaveAttention(int user_id, int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAttentionProtarget(int user_id, int target_id);

	public abstract void UpdateTargetStatus(ProTarget protarget);

	public abstract int checkCanOperation(int id, int user_id);
}
