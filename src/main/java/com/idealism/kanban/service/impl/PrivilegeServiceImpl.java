package com.idealism.kanban.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.idealism.kanban.dao.PrivilegeDao;
import com.idealism.kanban.model.Privilege;
import com.idealism.kanban.service.PrivilegeService;

@Service("PrivilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {
	@Autowired
	@Qualifier("PrivilegeDao")
	private PrivilegeDao privilegeDao;
	
	@SuppressWarnings("rawtypes")
	public List LoadAll() {
		return privilegeDao.LoadAll();
	}

	@SuppressWarnings("rawtypes")
	public List LoadCustomPri() {
		return privilegeDao.LoadCustomPri();
	}

	@Override
	public void Save(Privilege p) {
		privilegeDao.Save(p);
	}

	@Override
	public int LoadMaxID() {
		return privilegeDao.LoadMaxID();
	}

	@Override
	public Privilege LoadPriByID(int id) {
		return privilegeDao.LoadPriByID(id);
	}

	@Override
	public void InsertPri(Privilege p) {
		privilegeDao.InsertPri(p);
	}

	@Override
	public int CheckCanSave(int id,String privilege_define) {
		return privilegeDao.CheckCanSave(id,privilege_define);
	}

}
