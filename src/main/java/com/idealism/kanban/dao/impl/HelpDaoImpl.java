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

import com.idealism.kanban.dao.HelpDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.Help;
import com.idealism.kanban.vo.HelpInfo;

@Repository("HelpDao")
public class HelpDaoImpl extends SuperDao implements HelpDao{
	private final static String HasAttach = "select count(*) as count from rel_help_file file where file.request_id=:request_id";
	private final static String LoadHelpById = "from Help hp where hp.request_id = :request_id";
	private final static String LoadHelpFiles = "select files.files_id,files.files_name,files.files_ext,files.files_url,files.files_des from system_files files,rel_help_file rel where files.is_delete!=1 and rel.files_id = files.files_id and rel.request_id = :request_id order by files.files_id desc";
	private static final String CheckCanDelete = "select count(*) as cnt from seek_help help where help.request_id = :request_id and help.seek_help_user_id = :seek_help_user_id and help.help_user_id = 0";
	private static final String Delete = "update Help hp set hp.is_delete=1 where hp.request_id=:request_id";
	private static final String UpdateAccept = "update Help hp set hp.help_user_id=:help_user_id where hp.request_id=:request_id";
	private static final String CheckCanAccept = "select count(*) as cnt from seek_help help where help.request_id = :request_id and help.help_user_id = 0";
	private static final String CheckCanRefuse = "select count(*) as cnt from seek_help help where help.request_id = :request_id and help.seek_help_user_id = :seek_help_user_id and help.help_user_id > 0";
	private static final String UpdateFinish = "update Help hp set hp.solve_time=:solve_time where hp.request_id=:request_id";
	private static final String CheckIsRead = "select rel.is_read from msg,rel_msg_user rel where msg.msg_id = rel.msg_id and msg.src_type = 7 and msg.src_id = :id and rel.user_id = :user_id";
	private static final String UpdateMsgReadStatus = "update rel_msg_user set is_read = 1 where msg_id=:msg_id and user_id = :user_id";
	private static final String LoadMsgIDByHelpId = "select msg_id from msg where src_type=7 and src_id = :src_id";
	
	@SuppressWarnings("rawtypes")
	public List LoadHelpNotMy(HelpInfo helpInfo) {
		Criteria c = getSession().createCriteria(Help.class);
		if(helpInfo.getMax_create_time() != null && !helpInfo.getMax_create_time().equals("")){
			c.add(Restrictions.le("create_time", helpInfo.getMax_create_time()));
		}
		if(helpInfo.getMin_create_time() != null && !helpInfo.getMin_create_time().equals("")){
			c.add(Restrictions.ge("create_time", helpInfo.getMin_create_time()));
		}
		if(helpInfo.getMax_solve_time() != null && !helpInfo.getMax_solve_time().equals("")){
			c.add(Restrictions.le("solve_time", helpInfo.getMax_solve_time()));
		}
		if(helpInfo.getMin_solve_time() != null && !helpInfo.getMin_solve_time().equals("")){
			c.add(Restrictions.ge("solve_time", helpInfo.getMin_solve_time()));
		}
		if(helpInfo.getHelp_title() != null && !helpInfo.getHelp_title().equals("")){
			c.add(Restrictions.like("help_title", helpInfo.getHelp_title(), MatchMode.ANYWHERE));
		}
		if(helpInfo.getHelp_content() != null && !helpInfo.getHelp_content().equals("")){
			c.add(Restrictions.like("help_content", helpInfo.getHelp_content(), MatchMode.ANYWHERE));
		}
		c.add(Restrictions.eq("is_delete", 0));
		c.add(Restrictions.and(Restrictions.ne("seek_help_user_id", helpInfo.getUser_id()),Restrictions.ne("help_user_id", helpInfo.getUser_id())));
		c.setFirstResult(helpInfo.getFirstResult());
		c.setMaxResults(helpInfo.getPageSize());
		c.addOrder(Order.desc("request_id"));
		List list = c.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List LoadHelpWithMy(HelpInfo helpInfo) {
		Criteria c = getSession().createCriteria(Help.class);
		if(helpInfo.getMax_create_time() != null && !helpInfo.getMax_create_time().equals("")){
			c.add(Restrictions.le("create_time", helpInfo.getMax_create_time()));
		}
		if(helpInfo.getMin_create_time() != null && !helpInfo.getMin_create_time().equals("")){
			c.add(Restrictions.ge("create_time", helpInfo.getMin_create_time()));
		}
		if(helpInfo.getMax_solve_time() != null && !helpInfo.getMax_solve_time().equals("")){
			c.add(Restrictions.le("solve_time", helpInfo.getMax_solve_time()));
		}
		if(helpInfo.getMin_solve_time() != null && !helpInfo.getMin_solve_time().equals("")){
			c.add(Restrictions.ge("solve_time", helpInfo.getMin_solve_time()));
		}
		if(helpInfo.getHelp_title() != null && !helpInfo.getHelp_title().equals("")){
			c.add(Restrictions.like("help_title", helpInfo.getHelp_title(), MatchMode.ANYWHERE));
		}
		if(helpInfo.getHelp_content() != null && !helpInfo.getHelp_content().equals("")){
			c.add(Restrictions.like("help_content", helpInfo.getHelp_content(), MatchMode.ANYWHERE));
		}
		c.add(Restrictions.or(Restrictions.eq("seek_help_user_id", helpInfo.getUser_id()),Restrictions.eq("help_user_id", helpInfo.getUser_id())));
		c.add(Restrictions.eq("is_delete", 0));
		c.setFirstResult(helpInfo.getFirstResult());
		c.setMaxResults(helpInfo.getPageSize());
		c.addOrder(Order.desc("request_id"));
		List list = c.list();
		return list;
	}
	
	

	@Override
	public Boolean HasAttach(int id) {
		Query qry = getSession().createSQLQuery(HasAttach);
		qry.setInteger("request_id", id);
		BigInteger cnt = (BigInteger)qry.uniqueResult();
		if(cnt.intValue()>0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Help LoadHelpById(int id) {
		Query qry = getSession().createQuery(LoadHelpById);
		qry.setInteger("request_id", id);
		return (Help) qry.uniqueResult();
	}

	@Override
	public void Save(Help help) {
		getSession().saveOrUpdate(help);
	}

	@SuppressWarnings("rawtypes")
	public List LoadHelpFiles(int id) {
		Query qry = getSession().createSQLQuery(LoadHelpFiles);
		qry.setInteger("request_id", id);
		return qry.list();
	}

	@Override
	public BigInteger CheckCanDelete(int id, int user_id) {
		Query qry = getSession().createSQLQuery(CheckCanDelete);
		qry.setInteger("request_id", id);
		qry.setInteger("seek_help_user_id", user_id);
		return (BigInteger) qry.uniqueResult();
	}

	@Override
	public void Delete(int id) {
		getSession().createSQLQuery(Delete).setInteger("request_id", id).executeUpdate();
	}

	@Override
	public void UpdateAccept(int id, int user_id) {
		getSession().createQuery(UpdateAccept).setInteger("request_id", id).setInteger("help_user_id", user_id).executeUpdate();
	}

	@Override
	public BigInteger CheckCanAccept(int id) {
		Query qry = getSession().createSQLQuery(CheckCanAccept);
		qry.setInteger("request_id", id);
		return (BigInteger) qry.uniqueResult();
	}

	@Override
	public BigInteger CheckCanRefuse(int id, int user_id) {
		Query qry = getSession().createSQLQuery(CheckCanRefuse);
		qry.setInteger("request_id", id);
		qry.setInteger("seek_help_user_id", user_id);
		return (BigInteger) qry.uniqueResult();
	}

	@Override
	public void UpdateFinish(int id, Date date) {
		getSession().createQuery(UpdateFinish).setInteger("request_id", id).setTimestamp("solve_time", date).executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	public List LoadAllHelp(HelpInfo helpInfo) {
		Criteria c = getSession().createCriteria(Help.class);
		if(helpInfo.getMax_create_time() != null && !helpInfo.getMax_create_time().equals("")){
			c.add(Restrictions.le("create_time", helpInfo.getMax_create_time()));
		}
		if(helpInfo.getMin_create_time() != null && !helpInfo.getMin_create_time().equals("")){
			c.add(Restrictions.ge("create_time", helpInfo.getMin_create_time()));
		}
		if(helpInfo.getMax_solve_time() != null && !helpInfo.getMax_solve_time().equals("")){
			c.add(Restrictions.le("solve_time", helpInfo.getMax_solve_time()));
		}
		if(helpInfo.getMin_solve_time() != null && !helpInfo.getMin_solve_time().equals("")){
			c.add(Restrictions.ge("solve_time", helpInfo.getMin_solve_time()));
		}
		if(helpInfo.getHelp_title() != null && !helpInfo.getHelp_title().equals("")){
			c.add(Restrictions.like("help_title", helpInfo.getHelp_title(), MatchMode.ANYWHERE));
		}
		if(helpInfo.getHelp_content() != null && !helpInfo.getHelp_content().equals("")){
			c.add(Restrictions.like("help_content", helpInfo.getHelp_content(), MatchMode.ANYWHERE));
		}
		c.add(Restrictions.eq("is_delete", 0));
		c.setFirstResult(helpInfo.getFirstResult());
		c.setMaxResults(helpInfo.getPageSize());
		c.addOrder(Order.desc("request_id"));
		List list = c.list();
		return list;
	}

	@Override
	public int LoadCntBySeekHelpUser(int user_id, HelpInfo info) {
		String hql = "select count(*) as count from Help hp where hp.seek_help_user_id=:user_id";
		if(info.getMax_create_time() != null && !info.getMax_create_time().equals("")){
			hql += " and hp.create_time <=:max_create_time";
		}
		if(info.getMin_create_time() != null && !info.getMin_create_time().equals("")){
			hql += " and hp.create_time >:min_create_time";
		}
		Query qry = getSession().createQuery(hql);
		qry.setInteger("user_id", user_id);
		if(info.getMax_create_time() != null && !info.getMax_create_time().equals("")){
			qry.setDate("max_create_time",info.getMax_create_time());
		}
		if(info.getMin_create_time() != null && !info.getMin_create_time().equals("")){
			qry.setDate("min_create_time",info.getMin_create_time());
		}
		return ((Number)qry.uniqueResult()).intValue();
	}

	@Override
	public int LoadCntByHelper(int user_id, HelpInfo info) {
		String hql = "select count(*) as count from Help hp where hp.help_user_id=:user_id";
		if(info.getMax_create_time() != null && !info.getMax_create_time().equals("")){
			hql += " and hp.create_time <=:max_create_time";
		}
		if(info.getMin_create_time() != null && !info.getMin_create_time().equals("")){
			hql += " and hp.create_time >:min_create_time";
		}
		Query qry = getSession().createQuery(hql);
		qry.setInteger("user_id", user_id);
		if(info.getMax_create_time() != null && !info.getMax_create_time().equals("")){
			qry.setDate("max_create_time",info.getMax_create_time());
		}
		if(info.getMin_create_time() != null && !info.getMin_create_time().equals("")){
			qry.setDate("min_create_time",info.getMin_create_time());
		}
		return ((Number)qry.uniqueResult()).intValue();
	}

	@Override
	public int CheckIsRead(int id, int user_id) {
		Query qry = getSession().createSQLQuery(CheckIsRead);
		qry.setInteger("id", id);
		qry.setInteger("user_id", user_id);
		Object resultObject = qry.uniqueResult();
		if(resultObject!=null){
			return (Integer)resultObject;
		}else {
			return 1;
		}
	}

	@Override
	public int LoadMsgIDByHelpId(int id) {
		return (Integer) getSession().createSQLQuery(LoadMsgIDByHelpId).setInteger("src_id", id).uniqueResult();
	}

	@Override
	public void UpdateMsgReadStatus(int msg_id, int user_id) {
		getSession().createSQLQuery(UpdateMsgReadStatus).setInteger("msg_id", msg_id).setInteger("user_id", user_id).executeUpdate();
	}

}
