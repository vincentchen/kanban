package com.idealism.kanban.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.ProTargetDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.ProTarget;
import com.idealism.kanban.model.ProjectHistory;

@Repository("ProTargetDao")
public class ProTargetDaoImpl extends SuperDao implements ProTargetDao {
	private static final String LoadTaskTodo = "from ProTask pt where pt.target_id = :target_id and pt.status_value = 0";
	private static final String LoadTaskDoing = "from ProTask pt where pt.target_id = :target_id and pt.status_value = 100";
	private static final String LoadTaskDone = "from ProTask pt where pt.target_id = :target_id and (pt.status_value = 200 or pt.status_value = 300)";
	private static final String LoadTarget = "from ProTarget pt where pt.target_id = :target_id";
	private static final String LoadTargetFiles = "select files.files_id,files.files_name,files.files_ext,files.files_url,files.files_des from system_files files,rel_target_file rel where files.is_delete!=1 and rel.files_id = files.files_id and rel.target_id = :target_id order by files.files_id desc";
	private static final String LoadTaskGraphList = "from ProTask tk where tk.target_id = :target_id";
	private static final String LoadCurrentMonth = "from ProjectHistory ph where ph.target_id = :target_id";
	private static final String LoadAttentionProtarget = "select * from attention_protarget where user_id=:user_id and target_id=:target_id";
	private static final String DeleteAttention = "delete from attention_protarget where user_id=:user_id and target_id=:target_id";
	private static final String InsertAttention = "insert into attention_protarget (user_id,target_id) VALUES (:user_id,:target_id)";
	private static final String LoadRelProUser = "select user_id from rel_project_user where pro_id=:pro_id";
	private static final String checkCanOperation = "select target.pro_id from pro_target target,rel_project_user rel where target.pro_id = rel.pro_id and rel.user_id = :user_id and target.target_id = :target_id";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idealism.kanban.dao.impl.ProTargetDao#LoadTask(int,
	 * java.lang.String)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List LoadTaskTodo(int id) {
		Query qry = getSession().createQuery(LoadTaskTodo);
		qry.setInteger("target_id", id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadTaskDoing(int id) {
		Query qry = getSession().createQuery(LoadTaskDoing);
		qry.setInteger("target_id", id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadTaskDone(int id) {
		Query qry = getSession().createQuery(LoadTaskDone);
		qry.setInteger("target_id", id);
		return qry.list();
	}

	@Override
	public ProTarget LoadProTargetById(int id) {
		Query qry = getSession().createQuery(LoadTarget);
		qry.setInteger("target_id", id);
		return (ProTarget) qry.uniqueResult();
	}

	@Override
	public void Save(ProTarget protarget) {
		getSession().saveOrUpdate(protarget);
	}

	@SuppressWarnings("rawtypes")
	public List LoadProFiles(int id) {
		Query qry = getSession().createSQLQuery(LoadTargetFiles);
		qry.setInteger("target_id", id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadTaskGraphList(int id) {
		Query qry = getSession().createQuery(LoadTaskGraphList);
		qry.setInteger("target_id", id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadCurrentMonth(int id) {
		Query qry = getSession().createQuery(LoadCurrentMonth);
		qry.setInteger("target_id", id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List SearchProjectHistory(Date history_min_time, Date history_max_time,int id) {
		Criteria c = getSession().createCriteria(ProjectHistory.class);
		if (null != history_min_time && null != history_max_time) {
			c.add(Restrictions.le("create_time", history_max_time));
			c.add(Restrictions.ge("create_time", history_min_time));
		}
		c.add(Restrictions.eq("target_id", id));
		c.addOrder(Order.asc("history_id"));
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadAttention(int user_id, int target_id) {
		return getSession().createSQLQuery(LoadAttentionProtarget).setInteger("user_id", user_id).setInteger("target_id", target_id).list();
	}

	@Override
	public void DeleteAttention(int user_id, int id) {
		getSession().createSQLQuery(DeleteAttention).setInteger("user_id", user_id).setInteger("target_id", id).executeUpdate();
	}

	@Override
	public void InsertAttention(int user_id, int id) {
		getSession().createSQLQuery(InsertAttention).setInteger("user_id", user_id).setInteger("target_id", id).executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	public List LoadRelProUser(int pro_id) {
		return getSession().createSQLQuery(LoadRelProUser).setInteger("pro_id",pro_id).list();
	}

	@Override
	public int checkCanOperation(int id, int user_id) {
		Object object = getSession().createSQLQuery(checkCanOperation).setInteger("target_id", id).setInteger("user_id", user_id).uniqueResult();
		if (object != null) {
			return 1;
		}
		return 0;
	}
}
