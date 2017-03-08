package com.idealism.kanban.service;

import java.util.List;

import com.idealism.kanban.model.ProTask;
import com.idealism.kanban.vo.ProTaskInfo;

public interface ProTaskService {
	public abstract Boolean CheckCanReceive(int id, int user_id);

	public abstract ProTask LoadProTaskById(int id);

	public abstract int UpdateReceive(int task_id, int user_id);

	public abstract int UpdateBlock(int task_id, int user_id);

	public abstract int UpdateFinish(int task_id, int user_id);

	public abstract Boolean CheckCanOperation(int task_id, int user_id);

	public abstract void Save(ProTask proTask);

	@SuppressWarnings("rawtypes")
	public abstract List LoadTaskFiles(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadRelUsersByTargetId(int target_id);

	public abstract ProTaskInfo LoadProTaskByIdWithInfo(int id);

}
