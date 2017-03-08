package com.idealism.kanban.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idealism.kanban.dao.DeptDao;
import com.idealism.kanban.dao.PrivilegeDao;
import com.idealism.kanban.dao.RoleDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.dao.UserHistoryDao;
import com.idealism.kanban.model.Privilege;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.UserManager;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.util.PrivilegeUtil;
import com.idealism.kanban.vo.RelDeptUserInfo;
import com.idealism.kanban.vo.UserInfo;

@Service("UserManager")
public class UserManagerImpl implements UserManager {
	@Autowired
	@Qualifier("UserDao")
	private UserDao userdao;

	@Autowired
	@Qualifier("UserHistoryDao")
	private UserHistoryDao userHistoryDao;

	@Autowired
	@Qualifier("DeptDao")
	private DeptDao deptDao;
	
	@Autowired
	@Qualifier("PrivilegeDao")
	private PrivilegeDao privilegeDao;
	
	@Autowired
	@Qualifier("RoleDao")
	private RoleDao roleDao;


	@Override
	public int SaveUserSetting(int user_id, String config_text) {
		return userdao.UpdateUserConfigText(user_id, config_text);
	}

	@Override
	public Users LoadUserById(int user_id) {
		return userdao.LoadUserByID(user_id);
	}

	@Override
	public int UpdateUserPhoto(int user_id, String Path) {
		return userdao.UpdateUserPhoto(user_id, Path);
	}

	
	@Override
	public boolean UserExists(UserInfo userInfo) {
		Users users = userdao.LoadUserByAccount(userInfo.getUser_account());
		if (users != null && users.getUser_id() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idealism.kanban.service.impl.UserManager#SaveUser(com.idealism.kanban
	 * .model.Users)
	 */
	@Override
	public void SaveUser(Users u) {
		userdao.SaveUser(u);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idealism.kanban.service.impl.UserManager#SearchUsers(com.idealism
	 * .kanban.vo.UserInfo)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List SearchUsers(UserInfo userInfo) {
		List userList = userdao.SearchUsers(userInfo);
		Iterator it = userList.iterator();
		List list = new ArrayList();
		Global global = new Global();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		while (it.hasNext()) {
			Users users = (Users) it.next();
			UserInfo info = new UserInfo();
			info.setId(global.RegisterID("UserID", users.getUser_id()));
			info.setUser_id(users.getUser_id());
			info.setUser_name(users.getUser_name());
			info.setUser_account(users.getUser_account());
			info.setPassword(users.getPassword());
			info.setUser_photo(users.getUser_photo());
			info.setCreate_time(users.getCreate_time());
			info.setLogin_time(users.getLogin_time());
			info.setPrivilege(users.getPrivilege());
			info.setEmail(users.getEmail());
			info.setCookie_key(users.getCookie_key());
			info.setConfig_text(users.getConfig_text());
			if(users.getLogin_time() != null){
				info.setLogin_time_str(format.format(users.getLogin_time()));
			}else {
				info.setLogin_time_str("");
			}
			info.setIs_delete(users.getIs_delete());
			list.add(info);
		}
		return list;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List TheMonthHistory(int user_id) {
		return userHistoryDao.CurrentMonthHistory(user_id);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List SearchUserHistory(Date startTime, Date endTime, int user_id) {
		return userHistoryDao.SearchUserHistory(startTime, endTime, user_id);
	}

	@Override
	public int UpdateUserName(UserInfo user) {
		return userdao.UpdateUserName(user);
	}

	@Override
	public int UpdateUserEmail(UserInfo user) {
		return userdao.UpdateUserEmail(user);
	}

	@SuppressWarnings("rawtypes")
	public List LoadUsersByDept(int dept_id) {
		return userdao.LoadUsersByDept(dept_id);
	}


	@Override
	public void SaveAdd(Users users) {
		List<Privilege> PriList = privilegeDao.LoadAll();
		String priString = "";
		Iterator<Privilege> iterator = PriList.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			priString += "00";
		}
		users.setPrivilege(priString);
		userdao.SaveUser(users);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadUserRelDeptAndRole(int id) {
		List relList = userdao.LoadUserRelDeptAndRole(id);
		Iterator it = relList.iterator();
		List list = new ArrayList();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			RelDeptUserInfo relDeptUserInfo = new RelDeptUserInfo();
			relDeptUserInfo.setRel_id((Integer)obj[0]);
			int dept_id = (Integer)obj[1];
			int role_id = (Integer)obj[3];
			if(dept_id>0){
				relDeptUserInfo.setDept_id(dept_id);
				relDeptUserInfo.setDept_name(deptDao.LoadDeptNameById(dept_id));
			}
			if(role_id>0){
				relDeptUserInfo.setRole_id(role_id);
				relDeptUserInfo.setRole_name(roleDao.LoadRoleNameById(role_id));
			}
			list.add(relDeptUserInfo);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadPriByUserID(int id) {
		String priString = userdao.LoadPriByUserID(id);
		List priList = privilegeDao.LoadAll();
		List list = new ArrayList();
		int priCnt = priList.size();
		int min = 0;
		int max = 0;
		PrivilegeUtil priutil = new PrivilegeUtil();
		for(int i=0;i<priCnt;i++){
			List priInfo = new ArrayList();
			min = i * 2;
			max = min + 2;
			Privilege privilege = (Privilege)priList.get(i);
			priInfo.add(privilege.getPrivilege_id());
			priInfo.add(privilege.getPrivilege_name());
			priInfo.add(privilege.getPrivilege_define());
			priInfo.add(privilege.getPrivilege_dec());
			if(priString.length()<max){
				priInfo.add(priutil.PriObjToDec("00"));
			}else{
				priInfo.add(priutil.PriObjToDec(priString.substring(min, max)));
			}
			list.add(priInfo);
		}
		return list;
	}

	@Override
	public void UpdatePriByRoleId(int id, String privilege) {
		int priCnt = privilege.length()/4;
		String priVal = "";
		int min = 0;
		int max = 0;
		PrivilegeUtil priutil = new PrivilegeUtil();
		for(int i=0;i<priCnt;i++){
			min = i * 4;
			max = min + 4;
			priVal += priutil.PriDecToString(privilege.substring(min,max));
		}
		userdao.UpdatePriByUserID(id, priVal);
	}

	@SuppressWarnings("rawtypes")
	public List LoadUserMsgConfig(int user_id) {
		return userdao.LoadUserMsgConfig(user_id);
	}

	@Transactional(rollbackFor = Exception.class)
	public void SaveMsgConfig(int user_id,UserInfo userInfo) {
		userdao.DeleteRelMsgConfig(user_id);
		userdao.InsertRelMsgConfig(user_id,userInfo);
	}

	@Override
	public int LoadMsgConfig(String field, int user_id) {
		return userdao.LoadMsgConfig(field,user_id);
	}

	@SuppressWarnings("rawtypes")
	public List LoadHistoryByInfo(UserInfo userInfo) throws ParseException {
		return userHistoryDao.LoadHistoryByInfo(userInfo);
	}

	
	public int CheckCanAdd(String user_account) {
		Users users = userdao.LoadUserByAccount(user_account);
		if(users != null){
			return 1;
		}
		return 0;
	}

	@Override
	public void DeleteUser(int id) {
		userdao.DeleteUser(id);
	}

	@Override
	public void ActivationUser(int id) {
		userdao.UpdateActivationUser(id);
	}

	@Override
	public void UpdateResetPwd(int id) {
		userdao.UpdateResetPwd(id);
	}

	@Override
	public void InsertRelDeptUser(int user_id, int dept_id, int role_id) {
		userdao.InsertRelDeptUser(user_id,dept_id,role_id);
	}

	@Override
	public void DeleteRelDeptUser(int rel_id) {
		userdao.DeleteRelDeptUser(rel_id);
	}
}
