package com.idealism.kanban.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.IdeaDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.Idea;
import com.idealism.kanban.vo.IdeaInfo;

@Repository("IdeaDao")
public class IdeaDaoImpl extends SuperDao implements IdeaDao {
	private final static String HasAttach = "select count(*) as count from rel_idea_file file where file.idea_id=:idea_id";
	private final static String HasChoose = "select count(*) as count from idea_vote vote where vote.idea_id=:idea_id and vote.user_id=:user_id";
	private static final String LoadIdeaById = "from Idea i where i.idea_id = :idea_id";
	private static final String LoadIdeaFiles = "select files.files_id,files.files_name,files.files_ext,files.files_url,files.files_des from system_files files,rel_idea_file rel where files.is_delete!=1 and rel.files_id = files.files_id and rel.idea_id = :idea_id order by files.files_id desc";
	private static final String InsertVote = "insert into idea_vote (idea_id,user_id) values (:idea_id,:user_id)";
	private static final String CheckCanOperation = "select count(*) as cnt from idea i where i.idea_id = :idea_id and i.create_user_id = :user_id";
	private static final String Delete = "update idea set is_delete = 1 where idea_id=:idea_id";
	private static final String UpdateByAuditIdea = "update idea set by_audit = 1 where idea_id=:idea_id";
	private static final String CheckIsRead = "select rel.is_read from msg,rel_msg_user rel where msg.msg_id = rel.msg_id and msg.src_type = 6 and msg.src_id = :id and rel.user_id = :user_id";
	private static final String UpdateMsgReadStatus = "update rel_msg_user set is_read = 1 where msg_id=:msg_id and user_id = :user_id";
	private static final String LoadMsgIDByIdeaId = "select msg_id from msg where src_type=6 and src_id = :src_id";
	
	
	@SuppressWarnings("rawtypes")
	public List LoadIdeaList(IdeaInfo ideaInfo) {
		Criteria c = getSession().createCriteria(Idea.class);
		if(ideaInfo.getIdea_title()!=null && !ideaInfo.getIdea_title().equals("")){
			c.add(Restrictions.like("idea_title", ideaInfo.getIdea_title(), MatchMode.ANYWHERE));
		}
		if(ideaInfo.getBy_audit()>0){
			c.add(Restrictions.eq("by_audit", 1));
		}
		c.add(Restrictions.ne("is_delete", 1));
		c.setFirstResult(ideaInfo.getFirstResult());
		c.setMaxResults(ideaInfo.getPageSize());
		c.addOrder(Order.desc("idea_id"));
		return c.list();
	}
	
	public Boolean HasAttach(int id) {
		Query qry = getSession().createSQLQuery(HasAttach);
		qry.setInteger("idea_id", id);
		BigInteger cnt = (BigInteger)qry.uniqueResult();
		if(cnt.intValue()>0){
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean HasChoose(int id,int user_id) {
		Query qry = getSession().createSQLQuery(HasChoose);
		qry.setInteger("idea_id", id);
		qry.setInteger("user_id", user_id);
		BigInteger cnt = (BigInteger)qry.uniqueResult();
		if(cnt.intValue()>0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Idea LoadIdeaById(int id) {
		Query qry = getSession().createQuery(LoadIdeaById);
		qry.setInteger("idea_id", id);
		return (Idea) qry.uniqueResult();
	}

	@Override
	public void Save(Idea idea) {
		getSession().saveOrUpdate(idea);
	}

	@SuppressWarnings("rawtypes")
	public List LoadIdeaFiles(int id) {
		Query qry = getSession().createSQLQuery(LoadIdeaFiles);
		qry.setInteger("idea_id", id);
		return qry.list();
	}

	public void SaveVoteByUser(int id, int user_id) {
		getSession().createSQLQuery(InsertVote).setInteger("idea_id", id).setInteger("user_id", user_id).executeUpdate();
	}

	@Override
	public BigInteger CheckCanOperation(int id, int user_id) {
		Query qry = getSession().createSQLQuery(CheckCanOperation);
		qry.setInteger("idea_id", id);
		qry.setInteger("user_id", user_id);
		return (BigInteger) qry.uniqueResult();
	}

	@Override
	public void Delete(int id) {
		getSession().createSQLQuery(Delete).setInteger("idea_id", id).executeUpdate();
	}

	@Override
	public void UpdateByAuditIdea(int id) {
		getSession().createSQLQuery(UpdateByAuditIdea).setInteger("idea_id", id).executeUpdate();
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
	public void UpdateMsgReadStatus(int msg_id, int user_id) {
		getSession().createSQLQuery(UpdateMsgReadStatus).setInteger("msg_id", msg_id).setInteger("user_id", user_id).executeUpdate();
	}

	@Override
	public int LoadMsgIDByIdeaId(int id) {
		return (Integer) getSession().createSQLQuery(LoadMsgIDByIdeaId).setInteger("src_id", id).uniqueResult();
	}

}
