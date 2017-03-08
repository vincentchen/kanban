package com.idealism.kanban.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.UserInfo;

@Repository("UserDao")
public class UserDaoImpl extends SuperDao implements UserDao {
	private static final String LoadByAccount = "from Users u where u.user_account = :user_account";
	private static final String LoadById = "from Users u where u.user_id = :user_id";
	private static final String UpdatePhoto = "update Users u set u.user_photo = :user_photo where u.user_id = :user_id";
	private static final String UpdateConfigText = "update Users u set u.config_text = :config_text where u.user_id = :user_id";
	private static final String UpdateUserName = "update Users u set u.user_name = :user_name where u.user_id = :user_id";
	private static final String UpdateUserEmail = "update Users u set u.email = :email where u.user_id = :user_id";
	private static final String LoadUsersByDept = "select u.user_id,u.user_name,u.user_photo from rel_dept_user rel,users u where u.user_id=rel.user_id and rel.dept_id=:dept_id";
	private static final String LoadUserRelDeptAndRole = "select rel.rel_id,rel.dept_id,rel.user_id,rel.role_id from rel_dept_user rel where rel.user_id = :user_id";
	private static final String UpdatePriByUserID = "update Users u set u.privilege = :privilege where u.user_id = :user_id";
	private static final String LoadAllUsers = "from Users u";
	private static final String LoadUserMsgConfig = "select m.idea_send,m.help_send,m.pro_send,m.protarget_send from user_msg_config m where m.user_id = :user_id";
	private static final String DeleteRelMsgConfig = "delete from user_msg_config where user_id=:user_id";
	private static final String InsertRelMsgConfig = "insert into user_msg_config (user_id,idea_send,help_send,pro_send,protarget_send) values (:user_id,:idea_send,:help_send,:pro_send,:protarget_send)";
	private static final String UpdatePwd = "update Users u set u.password = :password where u.user_id = :user_id";
	private static final String DeleteUser = "update Users u set u.is_delete = 1 where u.user_id = :user_id";
	private static final String ActivationUser = "update Users u set u.is_delete = 0 where u.user_id = :user_id";
	private static final String UpdateResetPwd = "update Users u set u.password = NULL where u.user_id = :user_id";
	private static final String InsertRelDeptUser = "insert into rel_dept_user (dept_id,user_id,role_id) values (:dept_id,:user_id,:role_id)";
	private static final String DeleteRelDeptUser = "delete from rel_dept_user where rel_id=:rel_id";
	
	
	@Override
	public void SaveUser(Users u) {
		getSession().saveOrUpdate(u);
	}

	@Override
	public int UpdateUserName(UserInfo user) {
		return getSession().createQuery(UpdateUserName).setString("user_name", user.getUser_name()).setInteger("user_id", user.getUser_id()).executeUpdate();
	}

	@Override
	public Users LoadUserByAccount(String user_account) {
		Query qry = getSession().createQuery(LoadByAccount);
		qry.setString("user_account", user_account);
		return (Users) qry.uniqueResult();
	}

	@Override
	public Users LoadUserByID(int id) {
		Users users = (Users) getSession().createQuery(LoadById).setInteger("user_id", id).uniqueResult();
		return users;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List SearchUsers(UserInfo info) {
		Criteria c = getSession().createCriteria(Users.class);

		if (info.getUser_account() != null && !info.getUser_account().equals("")) {
			c.add(Restrictions.like("user_account", info.getUser_account(), MatchMode.ANYWHERE));
		}

		if (info.getUser_name() != null && !info.getUser_name().equals("")) {
			c.add(Restrictions.like("user_name", info.getUser_name(), MatchMode.ANYWHERE));
		}

		if (info.getEmail() != null && !info.getEmail().equals("")) {
			c.add(Restrictions.like("email", info.getEmail(), MatchMode.ANYWHERE));
		}

		if (info.getUserIDs() != null && !info.getUserIDs().equals("")) {
			String[] ary = info.getUserIDs().split(",");
			List arr = new ArrayList();
			Global global = new Global();
			for (String item : ary) {
				arr.add(global.GetRegID("UserID", Integer.parseInt(item)));
			}
			c.add(Restrictions.in("user_id", arr));
		}

		if (info.getOrderByField() != null && info.getOrderByField().equals("") && info.getOrderBySort() != null && info.getOrderBySort().equals("")) {
			if (info.getOrderBySort() == "ASC") {
				c.addOrder(Order.asc(info.getOrderByField()));
			} else {
				c.addOrder(Order.desc(info.getOrderByField()));
			}
		}

		c.add(Restrictions.ne("is_delete", 1));
		
		c.setFirstResult(info.getFirstResult());
		c.setMaxResults(info.getPageSize());
		List list = c.list();
		return list;
	}

	@Override
	@Transactional
	public int UpdateUserPhoto(int user_id, String path) {
		return getSession().createQuery(UpdatePhoto).setString("user_photo", path).setInteger("user_id", user_id).executeUpdate();
	}

	@Override
	@Transactional
	public int UpdateUserConfigText(int user_id, String config_text) {
		return getSession().createQuery(UpdateConfigText).setString("config_text", config_text).setInteger("user_id", user_id).executeUpdate();
	}

	@Override
	public int UpdateUserEmail(UserInfo user) {
		return getSession().createQuery(UpdateUserEmail).setString("email", user.getEmail()).setInteger("user_id", user.getUser_id()).executeUpdate();
	}

	public String LoadNameById(int id) {
		if (id <= 0) {
			return null;
		}
		Users users = (Users) getSession().load(Users.class, id);
		if (users != null) {
			return users.getUser_name();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List LoadUsersByDept(int dept_id) {
		return getSession().createSQLQuery(LoadUsersByDept).setInteger("dept_id", dept_id).list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadUserRelDeptAndRole(int id) {
		return getSession().createSQLQuery(LoadUserRelDeptAndRole).setInteger("user_id", id).list();
	}

	public String LoadPriByUserID(int id) {
		Users users = (Users) getSession().createQuery(LoadById).setInteger("user_id", id).uniqueResult();
		return users.getPrivilege();
	}

	public void UpdatePriByUserID(int user_id, String priStr) {
		getSession().createSQLQuery(UpdatePriByUserID).setInteger("user_id", user_id).setString("privilege", priStr).executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	public List LoadAllUsers() {
		return getSession().createQuery(LoadAllUsers).list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadUserMsgConfig(int user_id) {
		return getSession().createSQLQuery(LoadUserMsgConfig).setInteger("user_id", user_id).list();
	}

	public void DeleteRelMsgConfig(int user_id) {
		getSession().createSQLQuery(DeleteRelMsgConfig).setInteger("user_id", user_id).executeUpdate();
	}

	public void InsertRelMsgConfig(int user_id, UserInfo userInfo) {
		getSession().createSQLQuery(InsertRelMsgConfig).setInteger("user_id", user_id).setInteger("idea_send", userInfo.getIdea_send()).setInteger("help_send", userInfo.getHelp_send()).setInteger("pro_send", userInfo.getPro_send()).setInteger("protarget_send", userInfo.getProtarget_send()).executeUpdate();
	}

	@Override
	public int LoadMsgConfig(String field, int user_id) {
		String LoadMsgConfig = "select "+field+" from user_msg_config where user_id=:user_id";
		Object obj = getSession().createSQLQuery(LoadMsgConfig).setInteger("user_id", user_id).uniqueResult();
		if(obj == null){
			return 0;
		}else{
			return Integer.parseInt(obj.toString());
		}
			
	}

	@SuppressWarnings("rawtypes")
	public List LoadMsgConfigByFieldName(String field) {
		String LoadMsgConfigByFieldName = "select user_id from user_msg_config where "+field+" = 1";
		return (List) getSession().createSQLQuery(LoadMsgConfigByFieldName).list();
	}

	@Override
	public void UpdatePwd(int id, String password) {
		getSession().createQuery(UpdatePwd).setInteger("user_id", id).setString("password", password).executeUpdate();
	}

	@Override
	public void DeleteUser(int id) {
		getSession().createQuery(DeleteUser).setInteger("user_id", id).executeUpdate();
	}

	@Override
	public void UpdateActivationUser(int id) {
		getSession().createQuery(ActivationUser).setInteger("user_id", id).executeUpdate();
	}

	@Override
	public void UpdateResetPwd(int id) {
		getSession().createQuery(UpdateResetPwd).setInteger("user_id", id).executeUpdate();
	}

	@Override
	public void InsertRelDeptUser(int user_id, int dept_id, int role_id) {
		getSession().createSQLQuery(InsertRelDeptUser).setInteger("user_id", user_id).setInteger("dept_id", dept_id).setInteger("role_id", role_id).executeUpdate();
	}

	@Override
	public void DeleteRelDeptUser(int rel_id) {
		getSession().createSQLQuery(DeleteRelDeptUser).setInteger("rel_id", rel_id).executeUpdate();
	}
}
