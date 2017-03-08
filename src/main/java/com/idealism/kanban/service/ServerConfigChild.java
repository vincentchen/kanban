package com.idealism.kanban.service;

import java.util.List;

public interface ServerConfigChild {
	public int LoadConfigByFieldName(String field_name);

	@SuppressWarnings("rawtypes")
	public List LoadAllPlugin();

	public void SavePlugin(String[] arr) throws Exception;
}
