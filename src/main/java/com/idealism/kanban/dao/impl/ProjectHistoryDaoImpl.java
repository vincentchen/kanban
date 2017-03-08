package com.idealism.kanban.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.ProjectHistoryDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.ProjectHistory;

@Repository("ProjectHistoryDao")
public class ProjectHistoryDaoImpl extends SuperDao implements ProjectHistoryDao {

	@Override
	public void SaveHistory(int pro_id, int target_id, int task_id, int status, String content, Date create_time) {
		ProjectHistory projectHistory = new ProjectHistory();
		projectHistory.setPro_id(pro_id);
		projectHistory.setTarget_id(target_id);
		projectHistory.setTask_id(task_id);
		projectHistory.setHistory_status(status);
		projectHistory.setHistory_content(content);
		projectHistory.setCreate_time(create_time);
		
		getSession().saveOrUpdate(projectHistory);
	}
	
}