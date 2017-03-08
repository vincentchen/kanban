package com.idealism.kanban.model;

public class Install {
	private String DriverClassName = "com.mysql.jdbc.Driver";
	private String Connect = "jdbc:mysql";
	private String UseUnicode = "true";
	private String CharacterEncoding = "UTF-8";

	private String DBUrl = "";
	private String DBName = "";
	private String DBUserName = "";
	private String DBPassword = "";
	
	private String useMysqlSql = "";

	public String getDriverClassName() {
		return DriverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		DriverClassName = driverClassName;
	}

	public String getConnect() {
		return Connect;
	}

	public void setConnect(String connect) {
		Connect = connect;
	}

	public String getUseUnicode() {
		return UseUnicode;
	}

	public void setUseUnicode(String useUnicode) {
		UseUnicode = useUnicode;
	}

	public String getCharacterEncoding() {
		return CharacterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		CharacterEncoding = characterEncoding;
	}

	public String getDBUrl() {
		return DBUrl;
	}

	public void setDBUrl(String dBUrl) {
		DBUrl = dBUrl;
	}

	public String getDBName() {
		return DBName;
	}

	public void setDBName(String dBName) {
		DBName = dBName;
	}

	public String getDBUserName() {
		return DBUserName;
	}

	public void setDBUserName(String dBUserName) {
		DBUserName = dBUserName;
	}

	public String getDBPassword() {
		return DBPassword;
	}

	public void setDBPassword(String dBPassword) {
		DBPassword = dBPassword;
	}

	public String getUseMysqlSql() {
		return useMysqlSql;
	}

	public void setUseMysqlSql(String useMysqlSql) {
		this.useMysqlSql = useMysqlSql;
	}

}