package com.idealism.kanban.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.RoleDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.Department;
import com.idealism.kanban.model.Role;
import com.idealism.kanban.vo.RoleInfo;

@Repository("RoleDao")
public class RoleDaoImpl extends SuperDao implements RoleDao{
	private static final String LoadRoleNameById = "from Role r where r.role_id = :role_id";
	private static final String LoadAllRole = "from Role r where r.is_delete = 0";
	private static final String UpdateName = "update role set role_name=:role_name where role_id=:role_id";
	private static final String Delete = "update role set is_delete=1 where role_id=:role_id ";
	private static final String LoadChild = "from Role r where r.parent_id=:role_id";
	private static final String UpdateParent = "update role set parent_id=:parent_id where role_id=:role_id";
	private static final String LoadRoleById = "from Role r where role_id=:role_id";
	private static final String UpdatePriByRoleId =  "update role set role_privilege=:role_privilege where role_id=:role_id";
	private static final String LoadUsersByRoleId = "select rel.user_id from rel_dept_user rel where rel.role_id=:role_id";
	@Override
	public String LoadRoleNameById(int role_id) {
		Query qry = getSession().createQuery(LoadRoleNameById);
		qry.setInteger("role_id", role_id);
		return ((Role) qry.uniqueResult()).getRole_name();
	}
	
	@SuppressWarnings("rawtypes")
	public List LoadAllRole() {
		return getSession().createQuery(LoadAllRole).list();
	}

	@Override
	public void Save(Role role) {
		getSession().saveOrUpdate(role);
	}

	@Override
	public void UpdateName(int role_id, String role_name) {
		Query qry = getSession().createSQLQuery(UpdateName);
		qry.setInteger("role_id", role_id);
		qry.setString("role_name", role_name);
		qry.executeUpdate();
	}

	@Override
	public void Delete(int role_id) {
		Query qry = getSession().createSQLQuery(Delete);
		qry.setInteger("role_id", role_id);
		qry.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	public List LoadChild(int role_id) {
		Query qry = getSession().createQuery(LoadChild);
		qry.setInteger("parent_id", role_id);
		return qry.list();
	}

	@Override
	public void UpdateParent(int role_id, int parent_id) {
		Query qry = getSession().createSQLQuery(UpdateParent);
		qry.setInteger("role_id", role_id);
		qry.setInteger("parent_id", parent_id);
		qry.executeUpdate();
	}

	@Override
	public String LoadPriByRoleId(int role_id) {
		Role role = (Role) getSession().createQuery(LoadRoleById).setInteger("role_id", role_id).uniqueResult();
		return role.getRole_privilege();
	}

	@Override
	public Role LoadRoleById(int role_id) {
		return (Role) getSession().createQuery(LoadRoleById).setInteger("role_id", role_id).uniqueResult();
	}

	@Override
	public void UpdatePriByRoleId(int role_id, String priVal) {
		getSession().createSQLQuery(UpdatePriByRoleId).setString("role_privilege",priVal).setInteger("role_id", role_id).executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	public List LoadUsersByRoleId(int role_id) {
		return getSession().createSQLQuery(LoadUsersByRoleId).setInteger("role_id", role_id).list();
	}

	@SuppressWarnings("rawtypes")
	public List SearchRole(RoleInfo roleInfo) {
		Criteria c = getSession().createCriteria(Role.class);
		if(roleInfo.getRole_name() != null && !roleInfo.getRole_name().equals("")){
			c.add(Restrictions.like("role_name", roleInfo.getRole_name(), MatchMode.ANYWHERE));
		}
		
		c.add(Restrictions.ne("is_delete", 1));
		
		c.setFirstResult(roleInfo.getFirstResult());
		c.setMaxResults(roleInfo.getPageSize());
		
		List list = c.list();
		return list;
	}

}
