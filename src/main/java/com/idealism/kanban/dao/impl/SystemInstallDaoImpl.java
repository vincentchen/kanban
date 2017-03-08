package com.idealism.kanban.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.idealism.kanban.dao.SystemInstallDao;
import com.idealism.kanban.model.Install;
import com.idealism.kanban.service.impl.SystemInstallImpl;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.util.Encryption;
import com.idealism.kanban.util.GetSystemPath;
import com.idealism.kanban.util.SqlFileExecutor;
import com.idealism.kanban.vo.SystemInstallInfo;

public class SystemInstallDaoImpl implements SystemInstallDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idealism.kanban.dao.impl.SystemInstallDao#CanInstallCheck()
	 */
	@Override
	public String CanInstallCheck() {
		String lockPath = new GetSystemPath().GetWebRootPath() + "data/install.lock";
		File filename = new File(lockPath);
		if (filename.exists()) {
			return "error.install.exit";
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idealism.kanban.dao.impl.SystemInstallDao#CreateInstalLock()
	 */
	@Override
	public String CreateInstalLock() {
		String lockPath = new GetSystemPath().GetWebRootPath() + "data/install.lock";
		File filename = new File(lockPath);
		if (!filename.exists()) {
			try {
				filename.createNewFile();
			} catch (IOException e) {
				return "error.install.lock";
			}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idealism.kanban.dao.impl.SystemInstallDao#CreateManager(com.idealism
	 * .kanban.vo.SystemInstallInfo, java.sql.Connection)
	 */
	@Override
	public String CreateManager(SystemInstallInfo info, Connection myConnection) throws SQLException {
		// 生成权限List
		int privilege = 0;
		try {
			privilege = CountSystemPrivilege(myConnection);
		} catch (SQLException e) {
			myConnection.close();
			return "error.system.datanotread";
		}
		if (0 == privilege) {
			return "error.install.initialize";
		}
		// 遍历，赋值15
		int i;
		String ManagerPrivilege = "";
		for (i = 0; i < privilege; i++) {
			ManagerPrivilege += "15";
		}

		// Save帐号
		String sql = "INSERT INTO users (user_account,user_name,password,create_time,privilege,email,config_text) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = myConnection.prepareStatement(sql);
			stmt.setString(1, info.getManagerName());
			stmt.setString(2, info.getManagerName());
			stmt.setString(3, new Encryption().encryptString(info.getManagerPassword()));
			stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			stmt.setString(5, ManagerPrivilege);
			stmt.setString(6, info.getManagerEmail());
			DefineConfig defineConfig = new DefineConfig();
			stmt.setString(7, defineConfig.getConfigTextString());
			stmt.execute();
			myConnection.commit();
			stmt.close();
		} catch (SQLException e) {
			myConnection.close();
			return "error.install.manager";
		}

		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idealism.kanban.dao.impl.SystemInstallDao#CountSystemPrivilege(java
	 * .sql.Connection)
	 */
	@Override
	public int CountSystemPrivilege(Connection myConnection) throws SQLException {
		int CountPrivilege = 0;
		Statement stmt = myConnection.createStatement();
		String sql = "select count(*) as cnt from privilege";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			CountPrivilege = rs.getInt("cnt");
		}
		rs.close();
		stmt.close();
		return CountPrivilege;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idealism.kanban.dao.impl.SystemInstallDao#CreateSystemDefaultValue
	 * (com.idealism.kanban.vo.SystemInstallInfo, java.sql.Connection)
	 */
	@Override
	public String CreateSystemDefaultValue(SystemInstallInfo info, Connection myConnection) {
		String dbPath = new GetSystemPath().GetWebRootPath() + "data/install_data.sql";
		SqlFileExecutor sqlFileExecutor = new SqlFileExecutor();

		try {
			Statement stmt = myConnection.createStatement();
			sqlFileExecutor.execute(stmt, dbPath);
			myConnection.commit();
		} catch (Exception e) {
			try {
				myConnection.rollback();
				myConnection.close();
			} catch (SQLException e1) {
				return "error.system.mysql.engine";
			}
			return "error.install.createdb";
		}

		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idealism.kanban.dao.impl.SystemInstallDao#CreateDB(com.idealism.kanban
	 * .model.Install, java.sql.Connection)
	 */
	@Override
	public String CreateDB(Install model, Connection myConnection) throws SQLException {

		String dbPath = new GetSystemPath().GetWebRootPath() + "data/"+model.getUseMysqlSql();
		SqlFileExecutor sqlFileExecutor = new SqlFileExecutor();
		// 执行SQL语句
		try {
			Statement stmt = myConnection.createStatement();
			sqlFileExecutor.execute(stmt, dbPath);
			myConnection.commit();
			stmt.close();
		} catch (Exception e) {
			try {
				myConnection.rollback();
				myConnection.close();
			} catch (SQLException e1) {
				return "error.system.mysql.engine";
			}
			return "error.install.createdb";
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idealism.kanban.dao.impl.SystemInstallDao#CreateConnection(com.idealism
	 * .kanban.model.Install)
	 */
	@Override
	public Connection CreateConnection(Install model) {
		Connection myConnection = null;
		try {
			myConnection = DriverManager.getConnection(model.getConnect() + "://" + model.getDBUrl() + "/" + model.getDBName() + "?useUnicode=" + model.getUseUnicode() + "&characterEncoding=" + model.getCharacterEncoding(), model.getDBUserName(), model.getDBPassword());
			myConnection.setAutoCommit(false);
		} catch (SQLException e) {
			try {
				myConnection = DriverManager.getConnection(model.getConnect() + "://" + model.getDBUrl() + "/mysql?useUnicode=" + model.getUseUnicode() + "&characterEncoding=" + model.getCharacterEncoding(), model.getDBUserName(), model.getDBPassword());
				Statement myOperation = myConnection.createStatement();
				String sql = "create database " + model.getDBName() + " DEFAULT CHARACTER SET utf8;";
				myOperation.execute(sql);
				myOperation.close();
				myConnection.close();
				myConnection = DriverManager.getConnection(model.getConnect() + "://" + model.getDBUrl() + "/" + model.getDBName() + "?useUnicode=" + model.getUseUnicode() + "&characterEncoding=" + model.getCharacterEncoding(), model.getDBUserName(), model.getDBPassword());
				myConnection.setAutoCommit(false);
			} catch (SQLException e1) {
				return null;
			}
		}
		return myConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idealism.kanban.dao.impl.SystemInstallDao#DBDataExchange()
	 */
	@Override
	public Install DBDataExchange() {
		Install model = null;
		GetSystemPath getSystemPath = new GetSystemPath();
		String path = getSystemPath.GetWebINFPath()+"classes/";
		try {
			model = new Install();
			Properties p = new Properties();
			p.load(new FileInputStream(path + "jdbc.properties"));
			model.setDriverClassName(p.getProperty("DriverClassName"));
			model.setConnect(p.getProperty("Connect"));
			model.setDBUrl(p.getProperty("DBUrl"));
			model.setDBName(p.getProperty("DBName"));
			model.setUseUnicode(p.getProperty("UseUnicode"));
			model.setCharacterEncoding(p.getProperty("CharacterEncoding"));
			model.setDBUserName(p.getProperty("DBUserName"));
			model.setDBPassword(p.getProperty("DBPassword"));
			model.setUseMysqlSql(p.getProperty("useMysqlSql"));
		} catch (Exception e) {
			return model;
		}

		return model;
	}

	@Override
	public String DropTable(Install model, Connection myConnection) {
		String dbPath = new GetSystemPath().GetWebRootPath() + "data/droptabel.sql";
		SqlFileExecutor sqlFileExecutor = new SqlFileExecutor();
		// 执行SQL语句
		try {
			Statement stmt = myConnection.createStatement();
			sqlFileExecutor.execute(stmt, dbPath);
			myConnection.commit();
			stmt.close();
		} catch (Exception e) {
			try {
				myConnection.rollback();
				myConnection.close();
			} catch (SQLException e1) {
				return "error.system.mysql.engine";
			}
			return "error.install.createdb";
		}
		return "";
	}
}