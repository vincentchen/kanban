package com.idealism.kanban.service;

import java.util.List;

import com.idealism.kanban.model.Role;
import com.idealism.kanban.vo.RoleInfo;

public interface RoleService {
	@SuppressWarnings("rawtypes")
	public abstract List LoadAllRole();

	public abstract void Save(Role role);

	public abstract void UpdateName(int role_id, String role_name);

	public abstract void Delete(int role_id);

	public abstract void UpdateParent(int role_id, int parent_id);

	@SuppressWarnings("rawtypes")
	public abstract List LoadPriByRoleId(int role_id);

	public abstract Role LoadRoleById(int role_id);

	public abstract void UpdatePriByRoleId(int role_id, String priString, int is_cover);

	@SuppressWarnings("rawtypes")
	public abstract List SearchRole(RoleInfo roleInfo);
}
