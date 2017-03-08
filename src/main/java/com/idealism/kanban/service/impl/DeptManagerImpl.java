package com.idealism.kanban.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idealism.kanban.dao.DeptDao;
import com.idealism.kanban.model.Department;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.DeptManager;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.DepartmentInfo;
import com.idealism.kanban.vo.UserInfo;

@Service("DeptManager")
public class DeptManagerImpl implements DeptManager {
	@Autowired
	@Qualifier("DeptDao")	
	private DeptDao deptDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadAllDept() {
		List deptList = deptDao.LoadAllDept();
		Iterator it1 = deptList.iterator();
		Global global = new Global();
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		List list = new ArrayList();
		while (it1.hasNext()) {
			Department dept = (Department) it1.next();
			DepartmentInfo deptInfo = new DepartmentInfo();
			if(hashMap.get(dept.getDept_id()) == null){
				hashMap.put(dept.getDept_id(), global.RegisterID("Dept", dept.getDept_id()));
			}
			if(hashMap.get(dept.getParent_id()) == null){
				hashMap.put(dept.getParent_id(), global.RegisterID("Dept", dept.getParent_id()));
			}
			deptInfo.setDept_id(hashMap.get(dept.getDept_id()));
			deptInfo.setParent_id(hashMap.get(dept.getParent_id()));
			deptInfo.setDept_name(dept.getDept_name());
			deptInfo.setCreate_time(dept.getCreate_time());
			deptInfo.setHas_child(dept.getHas_child());
			list.add(deptInfo);
		}
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	public void AddChild(Department department) {
		deptDao.UpdateHasChild(department.getParent_id());
		deptDao.Save(department);
	}

	@Override
	public void Save(Department department) {
		deptDao.Save(department);
	}

	@Override
	public void UpdateName(int dept_id, String dept_name) {
		deptDao.UpdateName(dept_id,dept_name);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void Delete(int dept_id) {
		deptDao.Delete(dept_id);
		List childList = deptDao.LoadChild(dept_id);
		Iterator it = childList.iterator();
		while (it.hasNext()) {
			Department dept = (Department) it.next();
			Delete(dept.getDept_id());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void UpdateParent(int dept_id, int parent_id) {
		if(parent_id>0){
			deptDao.UpdateHasChild(parent_id);
		}
		deptDao.UpdateParent(dept_id,parent_id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List SearchDept(DepartmentInfo adminDeptInfo) {
		List userList = deptDao.SearchDept(adminDeptInfo);
		Iterator it = userList.iterator();
		List list = new ArrayList();
		Global global = new Global();
		while (it.hasNext()) {
			Department dept = (Department) it.next();
			DepartmentInfo info = new DepartmentInfo();
			info.setId(global.RegisterID("Dept", dept.getDept_id()));
			info.setDept_id(dept.getDept_id());
			info.setDept_name(dept.getDept_name());
			info.setParent_id(global.RegisterID("Dept", dept.getParent_id()));
			info.setHas_child(dept.getHas_child());
			info.setCreate_time(dept.getCreate_time());
			info.setIs_delete(dept.getIs_delete());
			
			list.add(info);
		}
		return list;
	}
	
}
