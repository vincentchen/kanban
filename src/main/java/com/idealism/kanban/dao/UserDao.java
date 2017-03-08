package com.idealism.kanban.dao;

import java.util.List;

import com.idealism.kanban.model.Users;
import com.idealism.kanban.vo.UserInfo;

public interface UserDao {
	public abstract void SaveUser(Users u);

	public abstract int UpdateUserName(UserInfo user);

	public abstract int UpdateUserEmail(UserInfo user);

	public abstract Users LoadUserByID(int id);

	@SuppressWarnings("rawtypes")
	public abstract List SearchUsers(UserInfo user);

	public abstract Users LoadUserByAccount(String account);

	public abstract int UpdateUserPhoto(int user_id, String path);

	public abstract int UpdateUserConfigText(int user_id, String config_text);
	
	public abstract String LoadNameById(int id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadUsersByDept(int dept_id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadUserRelDeptAndRole(int id);

	public abstract String LoadPriByUserID(int id);
	
	public abstract void UpdatePriByUserID(int user_id,String priStr);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadAllUsers();

	@SuppressWarnings("rawtypes")
	public abstract List LoadUserMsgConfig(int user_id);

	public abstract void DeleteRelMsgConfig(int user_id);

	public abstract void InsertRelMsgConfig(int user_id, UserInfo userInfo);

	public abstract int LoadMsgConfig(String field, int user_id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadMsgConfigByFieldName(String field);

	public abstract void UpdatePwd(int id, String password);

	public abstract void DeleteUser(int id);

	public abstract void UpdateActivationUser(int id);

	public abstract void UpdateResetPwd(int id);

	public abstract void InsertRelDeptUser(int user_id, int dept_id, int role_id);

	public abstract void DeleteRelDeptUser(int rel_id);
}