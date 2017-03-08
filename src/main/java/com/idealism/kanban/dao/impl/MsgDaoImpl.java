package com.idealism.kanban.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.MsgDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.Msg;
import com.idealism.kanban.model.RelMsgUser;
import com.idealism.kanban.vo.MsgInfo;

@Repository("MsgDao")
public class MsgDaoImpl extends SuperDao implements MsgDao {
	private static final String LoadMsgIsRead = "from RelMsgUser rel where rel.relMsgUserPK.msg_id = :msg_id and rel.relMsgUserPK.user_id = :user_id";
	private static final String InsertRelUser = "insert into rel_msg_user (msg_id,user_id) value (:msg_id,:user_id)";
	private static final String DeleteUser = "delete from rel_msg_user where msg_id=:msg_id and user_id=:user_id";
	private static final String LoadMsgById = "from Msg m where m.msg_id = :msg_id";
	private static final String UpdateRead = "update rel_msg_user set is_read=1 where msg_id=:msg_id and user_id=:user_id";
	
	@SuppressWarnings("rawtypes")
	public List LoadMsgList(MsgInfo msgInfo) {
		Criteria c = getSession().createCriteria(Msg.class);
		if (msgInfo.getSrc_type() > 0) {
			c.add(Restrictions.eq("src_type", msgInfo.getSrc_type()));
		}
		if (msgInfo.getMsg_title() != null && !msgInfo.getMsg_title().equals("")) {
			c.add(Restrictions.like("msg_title", msgInfo.getMsg_title(), MatchMode.ANYWHERE));
		}
		if (msgInfo.getMsg_content() != null && !msgInfo.getMsg_content().equals("")) {
			c.add(Restrictions.like("msg_content", msgInfo.getMsg_content(), MatchMode.ANYWHERE));
		}

		if(msgInfo.getUser_id()>0){
			if(msgInfo.getShowwhat() == 1){
				c.add(Restrictions.sqlRestriction("{alias}.msg_id IN (select rel.msg_id from rel_msg_user rel where rel.user_id ="+msgInfo.getUser_id()+" and rel.is_read = 1)"));
			}else {
				c.add(Restrictions.sqlRestriction("{alias}.msg_id IN (select rel.msg_id from rel_msg_user rel where rel.user_id ="+msgInfo.getUser_id()+" order by rel.is_read desc)"));
			}
		}

		c.setFirstResult(msgInfo.getFirstResult());
		c.setMaxResults(msgInfo.getPageSize());
		c.addOrder(Order.desc("msg_id"));

		return c.list();
	}
	
	public int LoadMsgIsRead(int msg_id,int user_id){
		Query qry = getSession().createQuery(LoadMsgIsRead);
		qry.setInteger("user_id", user_id);
		qry.setInteger("msg_id", msg_id);
		return ((RelMsgUser)qry.uniqueResult()).getIs_read();
	}

	@Override
	public void Save(Msg msg) {
		getSession().saveOrUpdate(msg);
	}

	@Override
	public void SaveUsers(int msg_id, Integer user_id) {
		Query qry = getSession().createSQLQuery(InsertRelUser);
		qry.setInteger("user_id", user_id);
		qry.setInteger("msg_id", msg_id);
		qry.executeUpdate();
	}

	@Override
	public void DeleteUser(Integer user_id,Integer msg_id) {
		Query qry = getSession().createSQLQuery(DeleteUser);
		qry.setInteger("user_id", user_id);
		qry.setInteger("msg_id", msg_id);
		qry.executeUpdate();
	}

	@Override
	public Msg LoadMsgById(int msg_id) {
		Query qry = getSession().createQuery(LoadMsgById);
		qry.setInteger("msg_id", msg_id);
		return (Msg) qry.uniqueResult();
	}

	@Override
	public void UpdateRead(int msg_id, int user_id) {
		Query qry = getSession().createSQLQuery(UpdateRead);
		qry.setInteger("user_id", user_id);
		qry.setInteger("msg_id", msg_id);
		qry.executeUpdate();
	}

}
