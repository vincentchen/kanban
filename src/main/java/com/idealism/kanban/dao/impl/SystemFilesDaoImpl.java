package com.idealism.kanban.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.dao.SystemFilesDao;
import com.idealism.kanban.model.SystemFiles;

@Repository("SystemFilesDao")
public class SystemFilesDaoImpl extends SuperDao implements SystemFilesDao {
	private static final String LoadSystemFilesById = "from SystemFiles sf where sf.files_id = :files_id";
	
	/* (non-Javadoc)
	 * @see com.idealism.kanban.dao.impl.SystemFilesDao#LoadSystemFilesById(int)
	 */
	@Override
	public SystemFiles LoadSystemFilesById(int id){
		Query qry = getSession().createQuery(LoadSystemFilesById);
		qry.setInteger("files_id", id);
		return (SystemFiles) qry.uniqueResult();
	}
	
	public void SaveSystemFiles(SystemFiles systemFiles){
		getSession().saveOrUpdate(systemFiles);
	}

	@Override
	public int SaveRelTable(String tabName, String tabIdRowName, int tabId, int files_id) {
		String SQL = "INSERT INTO "+tabName+" ("+tabIdRowName+",files_id) VALUES ("+tabId+","+files_id+")";
		return getSession().createSQLQuery(SQL).executeUpdate();
	}
}
