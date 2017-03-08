package com.idealism.kanban.dao;

import com.idealism.kanban.model.SystemFiles;

public interface SystemFilesDao {

	public abstract SystemFiles LoadSystemFilesById(int id);

	public abstract void SaveSystemFiles(SystemFiles systemFiles);

	public abstract int SaveRelTable(String tabName, String tabIdRowName, int tabId, int files_id);
}