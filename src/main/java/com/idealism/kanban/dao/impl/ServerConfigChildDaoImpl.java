package com.idealism.kanban.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idealism.kanban.dao.ServerConfigChildDao;
import com.idealism.kanban.dao.SuperDao;

@Repository("ServerConfigChildDao")
public class ServerConfigChildDaoImpl  extends SuperDao implements ServerConfigChildDao{
	private final static String LoadConfigByFieldName = "select cf.child_field_value from child_server_config cf where cf.child_field_name = :child_field_name";
	private final static String LoadAllPlugin = "select * from child_server_config";
	private final static String UpdateChildServerConfig = "update child_server_config csc set child_field_value=:child_field_value where child_mark=:child_mark and child_field_name=:child_field_name";
	public int LoadConfigByFieldName(String field_name) {
		return (Integer) getSession().createSQLQuery(LoadConfigByFieldName).setString("child_field_name", field_name).uniqueResult();
	}

	@SuppressWarnings("rawtypes")
	public List LoadAllPlugin() {
		return getSession().createSQLQuery(LoadAllPlugin).list();
	}

	@Override
	public int SavePlugin(String child_mark, String child_field_name, int child_field_value) {
		return getSession().createSQLQuery(UpdateChildServerConfig).setString("child_mark", child_mark).setString("child_field_name", child_field_name).setInteger("child_field_value", child_field_value).executeUpdate();
	}

}
