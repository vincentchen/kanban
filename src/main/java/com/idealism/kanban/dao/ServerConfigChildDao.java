package com.idealism.kanban.dao;

import java.util.List;

public interface ServerConfigChildDao {

	public int LoadConfigByFieldName(String field_name);

	@SuppressWarnings("rawtypes")
	public List LoadAllPlugin();

	public int SavePlugin(String child_mark, String child_field_name, int child_field_value);
}
