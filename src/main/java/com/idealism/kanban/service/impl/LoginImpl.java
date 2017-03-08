package com.idealism.kanban.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.idealism.kanban.dao.PrivilegeDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.model.Privilege;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.Login;
import com.idealism.kanban.util.Encryption;
import com.idealism.kanban.util.LoginCookieUtil;
import com.idealism.kanban.util.PrivilegeUtil;
import com.idealism.kanban.vo.LoginInfo;

@Service("LoginService")
public class LoginImpl implements Login {
	@Autowired
	@Qualifier("UserDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("PrivilegeDao")
	private PrivilegeDao privilegeDao;

	@Override
	public String SaveLogin(LoginInfo loginInfo) {
		Users users = LoginCheck(loginInfo.getUser_account());
		if(users == null){
			return "error.login.nameorpwd";
		}
		if(users.getPassword() == null){
			users.setLogin_time(new Date(System.currentTimeMillis()));
			userDao.SaveUser(users);
			MakeUpPri(users);
			return null;
		}
		if (users != null && loginInfo.getPassword().equals(new Encryption().decryptString(users.getPassword())) && users.getIs_delete() == 0) {
			if (loginInfo.getAutoLogin().equals("true")) {
				LoginCookieUtil cookieUtil = new LoginCookieUtil();
				String uuidStr = UUID.randomUUID().toString();
				users.setCookie_key(uuidStr);
				cookieUtil.AddLoginCookie(users, uuidStr);
			}
			users.setLogin_time(new Date(System.currentTimeMillis()));
			userDao.SaveUser(users);
			MakeUpPri(users);
		} else {
			return "error.login.nameorpwd";
		}
		return null;
	}

	@Override
	public Users LoginCheck(String account) {
		return userDao.LoadUserByAccount(account);
	}


	@Override
	public void MakeUpPri(Users users) {
		List<Privilege> PriList = privilegeDao.LoadAll();
		HttpSession s = ServletActionContext.getRequest().getSession();
		String UserPri = users.getPrivilege();
		s.setAttribute("user_id", users.getUser_id());
		s.setAttribute("user_account", users.getUser_account());
		s.setAttribute("user_name", users.getUser_name());
		s.setAttribute("password", users.getPassword());
		s.setAttribute("email", users.getEmail());
		s.setAttribute("user_photo", users.getUser_photo());
		s.setAttribute("config_text", users.getConfig_text());
		PrivilegeUtil priutil = new PrivilegeUtil();
		int min = 0;
		int max = 0;
		for (int i = 0; i < PriList.size(); i++) {
			min = i * 2;
			max = min + 2;
			
			if(UserPri.length()<max){
				s.setAttribute(PriList.get(i).getPrivilege_define(), priutil.PriObjToDec("00"));
			}else {
				s.setAttribute(PriList.get(i).getPrivilege_define(), priutil.PriObjToDec(UserPri.substring(min, max)));
			}
		}
	}

	@Override
	public void SavePwd(int id, String password) {
		String pwd = new Encryption().encryptString(password);
		userDao.UpdatePwd(id,pwd);
		HttpSession s = ServletActionContext.getRequest().getSession();
		s.setAttribute("password", pwd);
	}

	@Override
	public void UpdateLogin(Users users) {
		users.setLogin_time(new Date(System.currentTimeMillis()));
		userDao.SaveUser(users);
	}

}
