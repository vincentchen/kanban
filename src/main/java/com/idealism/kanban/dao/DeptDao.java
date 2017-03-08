package com.idealism.kanban.dao;

import java.util.List;

import com.idealism.kanban.model.Department;
import com.idealism.kanban.vo.DepartmentInfo;

public interface DeptDao {

	@SuppressWarnings("rawtypes")
	public abstract List LoadAllDept();

	public abstract String LoadDeptNameById(int dept_id);

	public abstract void UpdateHasChild(int parent_id);

	public abstract void Save(Department department);

	public abstract void UpdateName(int dept_id, String dept_name);

	public abstract void Delete(int dept_id);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadChild(int dept_id);

	public abstract void UpdateParent(int dept_id, int parent_id);

	@SuppressWarnings("rawtypes")
	public abstract List SearchDept(DepartmentInfo deptInfo);
}
