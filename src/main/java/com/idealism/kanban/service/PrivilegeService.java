package com.idealism.kanban.service;

import java.util.List;

import com.idealism.kanban.model.Privilege;

public interface PrivilegeService {

	@SuppressWarnings("rawtypes")
	public List LoadAll();

	@SuppressWarnings("rawtypes")
	public List LoadCustomPri();

	public void Save(Privilege p);

	public int LoadMaxID();

	public Privilege LoadPriByID(int id);

	public void InsertPri(Privilege p);

	public int CheckCanSave(int id, String privilege_define);
	
}
