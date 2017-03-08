package com.idealism.kanban.dao;

import java.util.List;

import com.idealism.kanban.model.ProTarget;
import com.idealism.kanban.model.ProTask;

public interface ProTaskDao {

	public abstract Boolean CheckCanReceive(int id, int user_id);

	public abstract Boolean CheckCanOperation(int task_id, int user_id);

	public abstract int UpdateReceive(int task_id, int user_id);

	public abstract int UpdateBlock(int task_id, int user_id);

	public abstract int UpdateFinish(int task_id, int user_id);

	public abstract ProTask LoadProTaskById(int id);

	public abstract void Save(ProTask proTask);

	@SuppressWarnings("rawtypes")
	public abstract List LoadTaskFiles(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadRelProUser(int task_id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAttention(int task_id, int uId);

	public abstract List LoadRelUsersByTargetId(int target_id);

	public abstract ProTarget LoadProTargetByTaskID(int task_id);
}
