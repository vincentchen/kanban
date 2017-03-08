package com.idealism.kanban.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.DeskTopDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.vo.DeskTopInfo;

@Repository("DeskTopDao")
public class DeskTopDaoImpl extends SuperDao implements DeskTopDao {
	private static final String LoadToDo = "select pt.task_id,pt.target_id,pt.task_des,pt.create_time,pt.start_time,pt.end_time,pt.status_value,pt.create_user_id,pt.receive_task_user,pt.estimated_hour,pt.urgent_degree from pro_task pt,pro_target target,rel_project_user rel,project pro where pt.receive_task_user=0 and pt.target_id = target.target_id and target.pro_id = rel.pro_id and pro.pro_id = rel.pro_id and pro.pro_status = 100 and rel.user_id = :user_id";
	private static final String LoadDoingByUserID = "select * from pro_task pt where pt.receive_task_user = :receive_task_user and pt.status_value = 100";
	private static final String LoadProByUserID = "select pro.pro_id,pro.pro_name from rel_project_user rel,project pro where pro.pro_id = rel.pro_id and rel.user_id = :user_id and pro.pro_status = 100 order by pro.pro_id desc";
	private static final String LoatProTaskStatus = "select target.pro_id,pt.status_value from pro_task pt,pro_target target where pt.target_id = target.target_id and target.pro_id IN (:idList)";
	private static final String LoadIdeaExcludeUserID = "select i.idea_id,i.idea_title from idea i where i.idea_id not in(select v.idea_id FROM idea_vote v where i.idea_id = v.idea_id and v.user_id <> :user_id) and i.create_time >= :create_time and i.create_user_id <> :create_user_id and i.by_audit = 1 order by i.idea_id desc";
	private static final String LoadHelpExcludeUserID = "from Help sh where sh.help_user_id = 0";
	private static final String LoadMsgByUserId = "select msg.msg_id,msg.src_type,msg.src_id,msg.msg_title,msg.msg_content,msg.create_time,msg.create_user_id,msg.attach_flag,rel.is_read from msg,rel_msg_user rel where msg.msg_id = rel.msg_id and rel.user_id = :user_id";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idealism.kanban.dao.impl.DeskTopDao#LoadToDo(int)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List LoadToDo(DeskTopInfo deskTopInfo) {
		Query qry = getSession().createSQLQuery(LoadToDo);
		qry.setInteger("user_id", deskTopInfo.getUser_id());

		qry.setFirstResult(deskTopInfo.getFirstResult());
		qry.setMaxResults(deskTopInfo.getPageSize());
		return qry.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idealism.kanban.dao.impl.DeskTopDao#LoadDoingByUserID(int)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List LoadDoingByUserID(DeskTopInfo deskTopInfo) {
		Query qry = getSession().createSQLQuery(LoadDoingByUserID);
		qry.setInteger("receive_task_user", deskTopInfo.getUser_id());
		qry.setFirstResult(deskTopInfo.getFirstResult());
		qry.setMaxResults(deskTopInfo.getPageSize());
		return qry.list();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List LoadProByUserID(DeskTopInfo deskTopInfo) {
		Query qry = getSession().createSQLQuery(LoadProByUserID);
		qry.setInteger("user_id", deskTopInfo.getUser_id());
		qry.setFirstResult(deskTopInfo.getFirstResult());
		qry.setMaxResults(deskTopInfo.getPageSize());
		return qry.list();
	}
	
	@SuppressWarnings("rawtypes")
	public List LoatProTaskStatus(List idlList){
		Query qry = getSession().createSQLQuery(LoatProTaskStatus);
		qry.setParameterList("idList", idlList);
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadIdeaExcludeUserID(DeskTopInfo deskTopInfo) {
		Query qry = getSession().createSQLQuery(LoadIdeaExcludeUserID);
		
		Date now = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(now);
		cl.add(Calendar.MONTH, -2);
		Date startDate = cl.getTime();
		
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
		String start = dd.format(startDate);
		
		qry.setString("create_time", start);
		qry.setInteger("user_id", deskTopInfo.getUser_id());
		qry.setInteger("create_user_id", deskTopInfo.getUser_id());
		qry.setFirstResult(deskTopInfo.getFirstResult());
		qry.setMaxResults(deskTopInfo.getPageSize());
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadHelpExcludeUserID(DeskTopInfo deskTopInfo) {
		Query qry = getSession().createQuery(LoadHelpExcludeUserID);
		qry.setFirstResult(deskTopInfo.getFirstResult());
		qry.setMaxResults(deskTopInfo.getPageSize());
		return qry.list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadMsgByUserID(DeskTopInfo deskTopInfo) {
		Query qry = getSession().createSQLQuery(LoadMsgByUserId);
		qry.setInteger("user_id", deskTopInfo.getUser_id());
		qry.setFirstResult(deskTopInfo.getFirstResult());
		qry.setMaxResults(deskTopInfo.getPageSize());
		return qry.list();
	}

}