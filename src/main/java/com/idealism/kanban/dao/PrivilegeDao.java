package com.idealism.kanban.dao;

import java.util.List;

import com.idealism.kanban.model.Privilege;

public interface PrivilegeDao {

	public abstract List<Privilege> LoadAll();

	@SuppressWarnings("rawtypes")
	public abstract List LoadCustomPri();

	public abstract void Save(Privilege p);

	public abstract int LoadMaxID();

	public abstract Privilege LoadPriByID(int id);

	public abstract void InsertPri(Privilege p);

	public abstract int CheckCanSave(int id, String privilege_define);

}