package com.idealism.kanban.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idealism.kanban.dao.PrivilegeDao;
import com.idealism.kanban.dao.RoleDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.model.Department;
import com.idealism.kanban.model.Privilege;
import com.idealism.kanban.model.Role;
import com.idealism.kanban.service.RoleService;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.util.PrivilegeUtil;
import com.idealism.kanban.vo.DepartmentInfo;
import com.idealism.kanban.vo.RoleInfo;

@Service("RoleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	@Qualifier("RoleDao")
	private RoleDao roleDao;
	
	@Autowired
	@Qualifier("PrivilegeDao")
	private PrivilegeDao privilegeDao;
	
	@Autowired
	@Qualifier("UserDao")
	private UserDao userDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadAllRole() {
		List roleList = roleDao.LoadAllRole();
		Iterator it1 = roleList.iterator();
		Global global = new Global();
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		List list = new ArrayList();
		while (it1.hasNext()) {
			Role role = (Role) it1.next();
			RoleInfo roleInfo = new RoleInfo();
			if(hashMap.get(role.getRole_id()) == null){
				hashMap.put(role.getRole_id(), global.RegisterID("Role", role.getRole_id()));
			}
			if(hashMap.get(role.getParent_id()) == null){
				hashMap.put(role.getParent_id(), global.RegisterID("Role", role.getParent_id()));
			}
			roleInfo.setRole_id(hashMap.get(role.getRole_id()));
			roleInfo.setParent_id(hashMap.get(role.getParent_id()));
			roleInfo.setRole_name(role.getRole_name());
			roleInfo.setRole_privilege(role.getRole_privilege());
			list.add(roleInfo);
		}
		return list;
	}

	@Override
	public void Save(Role role) {
		roleDao.Save(role);
	}

	@Override
	public void UpdateName(int role_id, String role_name) {
		roleDao.UpdateName(role_id,role_name);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void Delete(int role_id) {
		roleDao.Delete(role_id);
		List childList = roleDao.LoadChild(role_id);
		Iterator it = childList.iterator();
		while (it.hasNext()) {
			Role role = (Role) it.next();
			Delete(role.getRole_id());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void UpdateParent(int role_id, int parent_id) {
		roleDao.UpdateParent(role_id,parent_id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadPriByRoleId(int role_id) {
		String priString = roleDao.LoadPriByRoleId(role_id);
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
			if(priString == null){
				priInfo.add(priutil.PriObjToDec("00"));
			}else {
				if(priString.length()<max){
					priInfo.add(priutil.PriObjToDec("00"));
				}else{
					priInfo.add(priutil.PriObjToDec(priString.substring(min, max)));
				}
			}
			list.add(priInfo);
		}
		return list;
	}

	@Override
	public Role LoadRoleById(int role_id) {
		return roleDao.LoadRoleById(role_id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void UpdatePriByRoleId(int role_id, String priString,int is_cover) {
		int priCnt = priString.length()/4;
		String priVal = "";
		int min = 0;
		int max = 0;
		PrivilegeUtil priutil = new PrivilegeUtil();
		for(int i=0;i<priCnt;i++){
			min = i * 4;
			max = min + 4;
			priVal += priutil.PriDecToString(priString.substring(min,max));
		}
		roleDao.UpdatePriByRoleId(role_id,priVal);
		if(is_cover>0){
			List list = roleDao.LoadUsersByRoleId(role_id);
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Integer user = (Integer) iterator.next();
				userDao.UpdatePriByUserID(user, priVal);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List SearchRole(RoleInfo roleInfo) {
		List userList = roleDao.SearchRole(roleInfo);
		Iterator it = userList.iterator();
		List list = new ArrayList();
		Global global = new Global();
		while (it.hasNext()) {
			Role role = (Role) it.next();
			RoleInfo info = new RoleInfo();
			info.setId(global.RegisterID("Role", role.getRole_id()));
			info.setRole_id(role.getRole_id());
			info.setRole_name(role.getRole_name());
			info.setParent_id(global.RegisterID("Role", role.getParent_id()));
			info.setIs_delete(role.getIs_delete());
			
			list.add(info);
		}
		return list;
	}

}
