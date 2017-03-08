package com.idealism.kanban.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.idealism.kanban.model.Users;
import com.idealism.kanban.vo.UserInfo;

public interface UserManager {
	public abstract Users LoadUserById(int user_id);

	public abstract boolean UserExists(UserInfo userInfo);

	public abstract void SaveUser(Users u);

	@SuppressWarnings("rawtypes")
	public abstract List SearchUsers(UserInfo userInfo);

	@SuppressWarnings("rawtypes")
	public abstract List TheMonthHistory(int user_id);

	@SuppressWarnings("rawtypes")
	public abstract List SearchUserHistory(Date startTime, Date endTime, int user_id);

	public abstract int UpdateUserPhoto(int user_id, String Path);

	public abstract int SaveUserSetting(int user_id, String config_text);

	public abstract int UpdateUserName(UserInfo user);

	public abstract int UpdateUserEmail(UserInfo user);

	@SuppressWarnings("rawtypes")
	public abstract List LoadUsersByDept(int dept_id);

	public abstract void SaveAdd(Users users);

	@SuppressWarnings("rawtypes")
	public abstract List LoadUserRelDeptAndRole(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadPriByUserID(int id);

	public abstract void UpdatePriByRoleId(int id, String privilege);

	@SuppressWarnings("rawtypes")
	public abstract List LoadUserMsgConfig(int user_id);

	public abstract void SaveMsgConfig(int user_id, UserInfo userInfo);

	public abstract int LoadMsgConfig(String field, int user_id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadHistoryByInfo(UserInfo userInfo) throws ParseException;

	public abstract int CheckCanAdd(String user_account);

	public abstract void DeleteUser(int id);

	public abstract void ActivationUser(int id);

	public abstract void InsertRelDeptUser(int user_id, int dept_id, int role_id);

	public abstract void DeleteRelDeptUser(int rel_id);

	public abstract void UpdateResetPwd(int id);

}