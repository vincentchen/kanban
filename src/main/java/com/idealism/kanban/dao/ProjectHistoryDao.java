package com.idealism.kanban.dao;

import java.util.Date;


public interface ProjectHistoryDao {
	
	public abstract void SaveHistory(int pro_id,int target_id,int task_id,int status,String content,Date create_time);
	
}