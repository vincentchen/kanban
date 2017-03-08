package com.idealism.kanban.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.idealism.kanban.model.Install;
import com.idealism.kanban.vo.SystemInstallInfo;

public interface SystemInstallDao {

	public abstract String CanInstallCheck();

	public abstract String CreateInstalLock();

	public abstract String CreateManager(SystemInstallInfo info, Connection myConnection) throws SQLException;

	public abstract int CountSystemPrivilege(Connection myConnection) throws SQLException;

	public abstract String CreateSystemDefaultValue(SystemInstallInfo info, Connection myConnection);

	public abstract String CreateDB(Install model, Connection myConnection) throws SQLException;

	public abstract Connection CreateConnection(Install model);

	public abstract Install DBDataExchange();

	public abstract String DropTable(Install model, Connection myConnection);

}