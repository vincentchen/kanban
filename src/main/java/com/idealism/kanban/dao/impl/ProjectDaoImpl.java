package com.idealism.kanban.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.ProjectDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.Project;
import com.idealism.kanban.model.ProjectHistory;
import com.idealism.kanban.vo.ProjectInfo;

@Repository("ProjectDao")
public class ProjectDaoImpl extends SuperDao implements ProjectDao {
	private static final String LoadPro = "from Project pj order by pj.pro_id desc";
	private static final String LoadProByID = "from Project pj where pj.pro_id = :pro_id order by pj.pro_id desc";
	private static final String LoadProTargetUnFinishById = "from ProTarget pt where pt.pro_id = :pro_id and (pt.target_status = 0 or pt.target_status = 100)";
	private static final String LoadProTargetFinishedById = "from ProTarget pt where pt.pro_id = :pro_id and pt.target_status = 200";
	private static final String LoadTaskStatus = "select pt.status_value,count(pt.status_value) as cnt from pro_task pt where pt.target_id = :target_id group by pt.status_value";
	private static final String LoadProFiles = "select files.files_id,files.files_name,files.files_ext,files.files_url,files.files_des from system_files files,rel_pro_file rel where files.is_delete!=1 and rel.files_id = files.files_id and rel.pro_id = :pro_id order by files.files_id desc";
	private static final String InsertRelUsers = "insert into rel_project_user (pro_id,user_id) VALUES (:pro_id,:user_id)";
	private static final String DeleteProject = "delete from project where pro_id=:pro_id";
	private static final String DeleteProRelUsers = "delete from rel_project_user where pro_id=:pro_id";
	private static final String DeleteProRelFiles = "delete from rel_pro_file where pro_id=:pro_id";
	private static final String LoadProUsers = "select u.user_id,u.user_name,u.user_photo from users u,rel_project_user rel where u.user_id = rel.user_id and rel.pro_id = :pro_id";
	private static final String CheckRelUsers = "select count(*) as cnt from rel_project_user rel where rel.pro_id=:pro_id and rel.user_id = :user_id";
	private static final String DeleteRelUsers = "delete from rel_project_user where pro_id=:pro_id and user_id=:user_id";
	private static final String LoadTargetGraphByProID = "from ProTarget pt where pt.pro_id = :pro_id";
	private static final String LoadCntTaskByTargerID = "select count(pt.task_id) from pro_task pt where pt.target_id = :target_id";
	private static final String LoadTaskByTargerID = "from ProTask pt where pt.target_id = :target_id order by pt.end_time ASC,pt.end_task_time ASC";
	private static final String LoadHistoryByProID = "from ProjectHistory ph where ph.pro_id = :pro_id";
	private static final String LoadAttentionProtarget = "select * from attention_protarget where user_id=:user_id and target_id=:target_id";
	
	@Override
	public Project LoadProByID(int id) {
		Query qry = getSession().createQuery(LoadProByID);
		qry.setInteger("pro_id", id);
		return (Project) qry.uniqueResult();
	}
	
	@SuppressWarnings("rawtypes")
	public List LoadProTargetUnFinishById(int id){
		Query qry = getSession().createQuery(LoadProTargetUnFinishById);
		qry.setInteger("pro_id", id);
		return qry.list();
	}
	
	@SuppressWarnings("rawtypes")
	public List LoadProTargetFinishedById(int id){
		Query qry = getSession().createQuery(LoadProTargetFinishedById);
		qry.setInteger("pro_id", id);
		return qry.list();
	}
	
	
	@SuppressWarnings("rawtypes")
	public List LoadProTaskById(int id){
		Query qry = getSession().createSQLQuery(LoadTaskStatus);
		qry.setInteger("target_id", id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadProFiles(int id) {
		Query qry = getSession().createSQLQuery(LoadProFiles);
		qry.setInteger("pro_id", id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadProject(ProjectInfo projectInfo) {
		Criteria c = getSession().createCriteria(Project.class);
		if(projectInfo.getPro_name() != null && !projectInfo.getPro_name().equals("")){
			c.add(Restrictions.like("pro_name", projectInfo.getPro_name(),MatchMode.ANYWHERE));
		}
		if(projectInfo.getPro_des() != null && !projectInfo.getPro_des().equals("")){
			c.add(Restrictions.like("pro_des", projectInfo.getPro_des(),MatchMode.ANYWHERE));
		}
		
		if(projectInfo.getCreate_user_id()>0){
			c.add(Restrictions.sqlRestriction("{alias}.pro_id IN (select rel.pro_id from rel_project_user rel where rel.user_id="+projectInfo.getCreate_user_id()+") or {alias}.is_open = 1"));
		}
		c.setFirstResult(projectInfo.getFirstResult());
		c.setMaxResults(projectInfo.getPageSize());
		c.addOrder(Order.desc("pro_id"));
		return c.list();
	}

	@Override
	public void Save(Project project) {
		getSession().saveOrUpdate(project);
	}
	
	public void InsertRelUsers(int pro_id,int user_id){
		getSession().createSQLQuery(InsertRelUsers).setInteger("pro_id", pro_id).setInteger("user_id", user_id).executeUpdate();
	}

	@Override
	public void DeleteProject(int id) {
		getSession().createSQLQuery(DeleteProject).setInteger("pro_id", id).executeUpdate();
	}
	
	public void DeleteProRelUsers(int id){
		getSession().createSQLQuery(DeleteProRelUsers).setInteger("pro_id", id).executeUpdate();
	}
	
	public void DeleteProRelFiles(int id){
		getSession().createSQLQuery(DeleteProRelFiles).setInteger("pro_id", id).executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	public List LoadProUsers(int id) {
		Query qry = getSession().createSQLQuery(LoadProUsers);
		qry.setInteger("pro_id", id);
		return qry.list();
	}

	@Override
	public Boolean CheckRelUsers(int id, int user_id) {
		Query qry = getSession().createSQLQuery(CheckRelUsers);
		qry.setInteger("pro_id", id);
		qry.setInteger("user_id", user_id);
		BigInteger cnt = (BigInteger)qry.uniqueResult();
		if(cnt.intValue()>0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void DeleteRelUsers(int id, int user_id) {
		getSession().createSQLQuery(DeleteRelUsers).setInteger("pro_id", id).setInteger("user_id", user_id).executeUpdate();
	}


	@SuppressWarnings("rawtypes")
	public List LoadTargetGraphByProID(int pro_id) {
		Query qry = getSession().createQuery(LoadTargetGraphByProID);
		qry.setInteger("pro_id", pro_id);
		return qry.list();
	}

	@Override
	public int LoadCntTaskByTargerID(int target_id) {
		Query qry = getSession().createSQLQuery(LoadCntTaskByTargerID);
		qry.setInteger("target_id", target_id);
		BigInteger cnt = (BigInteger)qry.uniqueResult();
		return cnt.intValue();
	}

	@SuppressWarnings("rawtypes")
	public List LoadTaskByTargetID(int target_id) {
		Query qry = getSession().createQuery(LoadTaskByTargerID);
		qry.setInteger("target_id", target_id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadHistoryByProID(int id) {
		Query qry = getSession().createQuery(LoadHistoryByProID);
		qry.setInteger("pro_id", id);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List SearchProjectHistory(Date history_min_time, Date history_max_time,int id) {
		Criteria c = getSession().createCriteria(ProjectHistory.class);
		if (null != history_min_time && null != history_max_time) {
			c.add(Restrictions.le("create_time", history_max_time));
			c.add(Restrictions.ge("create_time", history_min_time));
		}
		c.add(Restrictions.eq("pro_id", id));
		c.addOrder(Order.asc("create_time"));
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadAll() {
		return getSession().createQuery(LoadPro).list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadAttentionProtarget(int target_id, int user_id) {
		return getSession().createSQLQuery(LoadAttentionProtarget).setInteger("user_id", user_id).setInteger("target_id", target_id).list();
	}
	
}
