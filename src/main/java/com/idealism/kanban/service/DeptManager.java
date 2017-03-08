package com.idealism.kanban.service;

import java.util.List;

import com.idealism.kanban.model.Department;
import com.idealism.kanban.vo.DepartmentInfo;

public interface DeptManager {

	@SuppressWarnings("rawtypes")
	public List LoadAllDept();

	public void AddChild(Department department);

	public void Save(Department department);

	public void UpdateName(int dept_id, String dept_name);

	public void Delete(int dept_id);

	public void UpdateParent(int dept_id, int parent_id);

	@SuppressWarnings("rawtypes")
	public List SearchDept(DepartmentInfo adminDeptInfo);

}
