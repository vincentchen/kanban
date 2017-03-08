package com.idealism.kanban.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.PrivilegeDao;
import com.idealism.kanban.dao.SuperDao;
import com.idealism.kanban.model.Privilege;

@Repository("PrivilegeDao")
public class PrivilegeDaoImpl extends SuperDao implements PrivilegeDao {
	private static final String LoadAll = "from Privilege p order by p.privilege_id asc";
	private static final String LoadCustomPri = "from Privilege p where p.privilege_id >= 10000 order by p.privilege_id asc";
	private static final String LoadMaxID = "select max(p.privilege_id) from Privilege p";
	private static final String LoadPriByID = "from Privilege p where p.privilege_id = :privilege_id";
	private static final String InsertPri = "INSERT INTO privilege (privilege_id,privilege_name,privilege_dec,privilege_define) VALUES(:privilege_id,:privilege_name,:privilege_dec,:privilege_define)";
	private static final String CheckCanSave = "from Privilege p where p.privilege_id <> :privilege_id and p.privilege_define = :privilege_define";
	
	@SuppressWarnings("unchecked")
	public List<Privilege> LoadAll() {
		return getSession().createQuery(LoadAll).list();
	}

	@SuppressWarnings("rawtypes")
	public List LoadCustomPri() {
		return getSession().createQuery(LoadCustomPri).list();
	}

	@Override
	public void Save(Privilege p) {
		getSession().saveOrUpdate(p);
	}

	@Override
	public int LoadMaxID() {
		return (Integer)getSession().createQuery(LoadMaxID).uniqueResult();
	}

	@Override
	public Privilege LoadPriByID(int id) {
		return (Privilege) getSession().createQuery(LoadPriByID).setInteger("privilege_id", id).uniqueResult();
	}

	@Override
	public void InsertPri(Privilege p) {
		getSession().createSQLQuery(InsertPri).setInteger("privilege_id",p.getPrivilege_id())
		.setString("privilege_name", p.getPrivilege_name())
		.setString("privilege_dec",p.getPrivilege_dec())
		.setString("privilege_define", p.getPrivilege_define()).executeUpdate();
	}

	@Override
	public int CheckCanSave(int id, String privilege_define) {
		Object obj = getSession().createQuery(CheckCanSave).setInteger("privilege_id", id).setString("privilege_define", privilege_define).uniqueResult();
		if(obj != null){
			return 0;
		}else {
			return 1;
		}
	}
}