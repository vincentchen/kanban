package com.idealism.kanban.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.idealism.kanban.vo.UserInfo;

public interface UserHistoryDao {

	@SuppressWarnings("rawtypes")
	public abstract List CurrentMonthHistory(int user_id);

	@SuppressWarnings("rawtypes")
	public abstract List SearchUserHistory(Date startTime, Date endTime, int user_id);

	public abstract void SaveUserHistory(int seek_help_user_id, int history_type, int history_type_id, int history_status,String history_content);

	@SuppressWarnings("rawtypes")
	public abstract List LoadHistoryByInfo(UserInfo userInfo) throws ParseException;

}