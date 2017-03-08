package com.idealism.kanban.service;

import com.idealism.kanban.model.SystemFiles;

public interface SystemFilesService {

	public abstract SystemFiles LoadSystemFilesById(int id);

	public abstract void SaveSystemFiles(SystemFiles systemFiles);

	public abstract int SaveRelTable(String tabName, String tabIdRowName, int tabId, int files_id);
}