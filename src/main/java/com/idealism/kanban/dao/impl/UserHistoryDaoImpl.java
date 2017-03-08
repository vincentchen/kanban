package com.idealism.kanban.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.dao.UserHistoryDao;
import com.idealism.kanban.model.UserHistory;
import com.idealism.kanban.vo.UserInfo;

@Repository("UserHistoryDao")
public class UserHistoryDaoImpl extends SuperDao implements UserHistoryDao {
	private static final String LoadCurrentMonthUserHistory = "from UserHistory uh where uh.user_id = ? and log_time >= ?";

	@Override
	@SuppressWarnings("rawtypes")
	public List CurrentMonthHistory(int user_id) {
		Query qry = getSession().createQuery(LoadCurrentMonthUserHistory);
		qry.setInteger(0, user_id);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		qry.setDate(1, beginTime);
		return qry.list();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List SearchUserHistory(Date startTime, Date endTime, int user_id) {
		Criteria c = getSession().createCriteria(UserHistory.class);
		if (null != startTime && null != endTime && 0 != user_id) {
			c.add(Restrictions.le("log_time", endTime));
			c.add(Restrictions.ge("log_time", startTime));
			c.add(Restrictions.eq("user_id", user_id));
		}
		c.addOrder(Order.asc("history_id"));
		List list = c.list();
		return list;
	}

	@Override
	public void SaveUserHistory(int seek_help_user_id, int history_type, int history_type_id, int history_status,String history_content) {
		UserHistory userHistory = new UserHistory();
		userHistory.setHistory_status(history_status);
		userHistory.setHistory_type_id(history_type_id);
		userHistory.setHistory_type(history_type);
		userHistory.setUser_id(seek_help_user_id);
		userHistory.setHistory_content(history_content);
		userHistory.setLog_time(new Date(System.currentTimeMillis()));
		getSession().saveOrUpdate(userHistory);
	}

	@SuppressWarnings("rawtypes")
	public List LoadHistoryByInfo(UserInfo userInfo) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Criteria c = getSession().createCriteria(UserHistory.class);
		if (null != userInfo.getHistory_max_time() && null != userInfo.getHistory_min_time() && 0 != userInfo.getUser_id()) {
			c.add(Restrictions.lt("log_time", sdf.parse(userInfo.getHistory_max_time())));
			c.add(Restrictions.ge("log_time", sdf.parse(userInfo.getHistory_min_time())));
			c.add(Restrictions.eq("user_id", userInfo.getUser_id()));
		}
		c.addOrder(Order.asc("log_time"));
		List list = c.list();
		return list;
	}
	
}