package com.idealism.kanban.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.dao.TitleDao;

@Repository("TitleDao")
public class TitleDaoImpl extends SuperDao implements TitleDao {
	private static final String LoadMsgCntByUserWhereNoRead = "select count(*) as cnt from RelMsgUser msg where msg.src_type = 1 and msg.src_type = 4 and msg.src_type = 5 and msg.relMsgUserPK.user_id = ? and msg.is_read = 0";
	private static final String LoadFlowCntByUserWhereNoRead = "select count(*) as cnt from RelMsgUser msg where msg.src_type = 3 and msg.relMsgUserPK.user_id = ? and msg.is_read = 0";
	private static final String LoadIdeaCntByUserWhereNoRead = "select count(*) as cnt from RelMsgUser msg where msg.src_type = 6 and msg.relMsgUserPK.user_id = ? and msg.is_read = 0";
	private static final String LoadProCntByUserWhereNoRead = "select count(*) as cnt from RelMsgUser msg where msg.src_type = 2 and msg.relMsgUserPK.user_id = ? and msg.is_read = 0";
	private static final String LoadHelpCntByUserWhereNoRead = "select count(*) as cnt from RelMsgUser msg where msg.src_type = 7 and msg.relMsgUserPK.user_id = ? and msg.is_read = 0";
	private static final String LoadChatCntByUserWhereNoRead = "select count(*) as cnt from RelMsgUser msg where msg.src_type = 8 and msg.relMsgUserPK.user_id = ? and msg.is_read = 0";

	private static final String LoadCntByUserWhereNoRead = "select msg.src_type,count(msg.src_type) as cnt from msg,rel_msg_user rel where rel.user_id = ? and rel.is_read = 0 and msg.msg_id = rel.msg_id group by msg.src_type";

	@SuppressWarnings("rawtypes")
	public List LoadCntByUserWhereNoRead(int user_id) {
		Query qry = getSession().createSQLQuery(LoadCntByUserWhereNoRead);
		qry.setInteger(0, user_id);
		return qry.list();
	}

	@Override
	public int LoadMsgCntByUserWhereNoRead(int user_id) {
		Query qry = getSession().createQuery(LoadMsgCntByUserWhereNoRead);
		qry.setInteger(0, user_id);
		return ((Long) qry.uniqueResult()).intValue();
	}

	@Override
	public int LoadFlowCntByUserWhereNoRead(int user_id) {
		Query qry = getSession().createQuery(LoadFlowCntByUserWhereNoRead);
		qry.setInteger(0, user_id);
		return ((Long) qry.uniqueResult()).intValue();
	}

	@Override
	public int LoadIdeaCntByUserWhereNoRead(int user_id) {
		Query qry = getSession().createQuery(LoadIdeaCntByUserWhereNoRead);
		qry.setInteger(0, user_id);
		return ((Long) qry.uniqueResult()).intValue();
	}

	@Override
	public int LoadProCntByUserWhereNoRead(int user_id) {
		Query qry = getSession().createQuery(LoadProCntByUserWhereNoRead);
		qry.setInteger(0, user_id);
		return ((Long) qry.uniqueResult()).intValue();
	}

	@Override
	public int LoadHelpCntByUserWhereNoRead(int user_id) {
		Query qry = getSession().createQuery(LoadHelpCntByUserWhereNoRead);
		qry.setInteger(0, user_id);
		return ((Long) qry.uniqueResult()).intValue();
	}

	@Override
	public int LoadChatCntByUserWhereNoRead(int user_id) {
		Query qry = getSession().createQuery(LoadChatCntByUserWhereNoRead);
		qry.setInteger(0, user_id);
		return ((Long) qry.uniqueResult()).intValue();
	}

}