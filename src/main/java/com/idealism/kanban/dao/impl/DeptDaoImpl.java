package com.idealism.kanban.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.DeptDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.Department;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.vo.DepartmentInfo;

@Repository("DeptDao")
public class DeptDaoImpl extends SuperDao implements DeptDao{
	private static final String LoadAllDept = "from Department dept where dept.is_delete = 0";
	private static final String LoadDeptNameById = "from Department dept where dept.dept_id=:dept_id";
	private static final String UpdateHasChild = "update department set has_child=1 where dept_id=:dept_id";
	private static final String UpdateName = "update department set dept_name=:dept_name where dept_id=:dept_id";
	private static final String Delete = "update department set is_delete=1 where dept_id=:dept_id ";
	private static final String LoadChild = "from Department dept where dept.parent_id=:dept_id";
	private static final String UpdateParent = "update department set parent_id=:parent_id where dept_id=:dept_id";
	
	
	@SuppressWarnings("rawtypes")
	public List LoadAllDept() {
		Query qry = getSession().createQuery(LoadAllDept);
		return qry.list();
	}

	@Override
	public String LoadDeptNameById(int dept_id) {
		Query qry = getSession().createQuery(LoadDeptNameById);
		qry.setInteger("dept_id", dept_id);
		return ((Department) qry.uniqueResult()).getDept_name();
	}

	@Override
	public void UpdateHasChild(int parent_id) {
		Query qry = getSession().createSQLQuery(UpdateHasChild);
		qry.setInteger("dept_id", parent_id);
		qry.executeUpdate();
	}

	@Override
	public void Save(Department department) {
		getSession().saveOrUpdate(department);
	}

	@Override
	public void UpdateName(int dept_id, String dept_name) {
		Query qry = getSession().createSQLQuery(UpdateName);
		qry.setInteger("dept_id", dept_id);
		qry.setString("dept_name", dept_name);
		qry.executeUpdate();
	}

	@Override
	public void Delete(int dept_id) {
		Query qry = getSession().createSQLQuery(Delete);
		qry.setInteger("dept_id", dept_id);
		qry.executeUpdate();
	}
	
	@SuppressWarnings("rawtypes")
	public List LoadChild(int dept_id){
		Query qry = getSession().createQuery(LoadChild);
		qry.setInteger("dept_id", dept_id);
		return qry.list();
	}

	@Override
	public void UpdateParent(int dept_id, int parent_id) {
		Query qry = getSession().createSQLQuery(UpdateParent);
		qry.setInteger("dept_id", dept_id);
		qry.setInteger("parent_id", parent_id);
		qry.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	public List SearchDept(DepartmentInfo deptInfo) {
		Criteria c = getSession().createCriteria(Department.class);
		if(deptInfo.getDept_name() != null && !deptInfo.getDept_name().equals("")){
			c.add(Restrictions.like("dept_name", deptInfo.getDept_name(), MatchMode.ANYWHERE));
		}
		
		c.add(Restrictions.ne("is_delete", 1));
		
		c.setFirstResult(deptInfo.getFirstResult());
		c.setMaxResults(deptInfo.getPageSize());
		
		List list = c.list();
		return list;
	}
}
