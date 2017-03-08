package com.idealism.kanban.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.ProTaskDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.ProTarget;
import com.idealism.kanban.model.ProTask;

@Repository("ProTaskDao")
public class ProTaskDaoImpl extends SuperDao implements ProTaskDao {
	private static final String LoadById = "from ProTask pt where pt.task_id = :task_id";
	private static final String UpdateReceive = "update ProTask pt set pt.receive_task_user=:user_id,pt.status_value=100,pt.start_time=:start_time where pt.task_id=:task_id";
	private static final String UpdateBlock = "update ProTask pt set pt.receive_task_user=:user_id,pt.status_value=300,pt.end_time=:end_time where pt.task_id=:task_id";
	private static final String UpdateFinish = "update ProTask pt set pt.receive_task_user=:user_id,pt.status_value=200,pt.end_time=:end_time where pt.task_id=:task_id";
	private static final String LoadTaskFiles = "select files.files_id,files.files_name,files.files_ext,files.files_url,files.files_des from system_files files,rel_task_file rel where files.is_delete!=1 and rel.files_id = files.files_id and rel.task_id = :task_id order by files.files_id desc";
	private static final String LoadRelProUser = "select rel.user_id from pro_target pt,pro_task task,rel_project_user rel where task.target_id = pt.target_id and pt.pro_id = rel.pro_id and task.task_id = :task_id";
	private static final String LoadAttention = "select pt.target_id from attention_protarget att,pro_task pt where user_id=:user_id and pt.target_id = att.target_id and pt.task_id = :task_id";
	private static final String LoadRelUsersByTargetId = "select u.user_id,u.user_name from pro_target target,rel_project_user rel,users u where target.pro_id = rel.pro_id and rel.user_id = u.user_id and target.target_id = :target_id";
	private static final String LoadProTargetByTaskID = "select target from ProTarget target,ProTask task where target.target_id = task.target_id and task.task_id = :task_id";
	
	public Boolean CheckCanReceive(int id,int user_id) {
		Query qry = getSession().createQuery(LoadById);
		qry.setInteger("task_id", id);
		ProTask proTask = (ProTask) qry.uniqueResult();
		if (proTask == null || proTask.getReceive_task_user() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public Boolean CheckCanOperation(int task_id, int user_id) {
		Query qry = getSession().createQuery(LoadById);
		qry.setInteger("task_id", task_id);
		ProTask proTask = (ProTask) qry.uniqueResult();
		if (proTask == null || proTask.getReceive_task_user() != user_id) {
			return false;
		} else {
			return true;
		}
	}

	public ProTask LoadProTaskById(int id) {
		Query qry = getSession().createQuery(LoadById);
		qry.setInteger("task_id", id);
		return (ProTask) qry.uniqueResult();
	}

	@Override
	public int UpdateReceive(int task_id, int user_id) {
		return getSession().createQuery(UpdateReceive).setInteger("user_id", user_id).setInteger("task_id", task_id).setTimestamp("start_time", Calendar.getInstance().getTime()).executeUpdate();
	}

	@Override
	public int UpdateBlock(int task_id, int user_id) {
		return getSession().createQuery(UpdateBlock).setInteger("user_id", user_id).setInteger("task_id", task_id).setTimestamp("end_time", Calendar.getInstance().getTime()).executeUpdate();
	}

	@Override
	public int UpdateFinish(int task_id, int user_id) {
		return getSession().createQuery(UpdateFinish).setInteger("user_id", user_id).setInteger("task_id", task_id).setTimestamp("end_time", Calendar.getInstance().getTime()).executeUpdate();
	}

	@Override
	public void Save(ProTask proTask) {
		getSession().saveOrUpdate(proTask);
	}

	@SuppressWarnings("rawtypes")
	public List LoadTaskFiles(int id) {
		return getSession().createSQLQuery(LoadTaskFiles).setInteger("task_id",id).list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadRelProUser(int task_id) {
		return getSession().createSQLQuery(LoadRelProUser).setInteger("task_id",task_id).list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadAttention(int task_id, int uId) {
		return getSession().createSQLQuery(LoadAttention).setInteger("task_id",task_id).setInteger("user_id",uId).list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadRelUsersByTargetId(int target_id) {
		return getSession().createSQLQuery(LoadRelUsersByTargetId).setInteger("target_id",target_id).list();
	}

	@Override
	public ProTarget LoadProTargetByTaskID(int task_id) {
		return (ProTarget) getSession().createQuery(LoadProTargetByTaskID).setInteger("task_id", task_id).uniqueResult();
	}

}
