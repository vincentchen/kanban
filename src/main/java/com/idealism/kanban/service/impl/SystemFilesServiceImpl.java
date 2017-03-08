package com.idealism.kanban.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.idealism.kanban.dao.SystemFilesDao;
import com.idealism.kanban.model.SystemFiles;
import com.idealism.kanban.service.SystemFilesService;

@Service("SystemFilesService")
public class SystemFilesServiceImpl implements SystemFilesService {
	@Autowired
	@Qualifier("SystemFilesDao")
	private SystemFilesDao systemFilesDao;
	
	/* (non-Javadoc)
	 * @see com.idealism.kanban.service.impl.SystemFilesService#LoadSystemFilesById(int)
	 */
	@Override
	public SystemFiles LoadSystemFilesById(int id){
		return systemFilesDao.LoadSystemFilesById(id);
	}
	
	public void SaveSystemFiles(SystemFiles systemFiles){
		systemFilesDao.SaveSystemFiles(systemFiles);
	}

	@Override
	public int SaveRelTable(String tabName, String tabIdRowName, int tabId, int files_id) {
		return systemFilesDao.SaveRelTable(tabName,tabIdRowName,tabId,files_id);
	}
}
