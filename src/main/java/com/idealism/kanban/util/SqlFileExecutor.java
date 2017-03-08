package com.idealism.kanban.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlFileExecutor {

	/**
	 * 读取 SQL 文件，获取 SQL 语句
	 * 
	 * @param sqlFile
	 *            SQL 脚本文件
	 * @return List<sql> 返回所有 SQL 语句的 List
	 * @throws Exception
	 */
	public List<String> loadSql(String sqlFile) throws Exception {
		List<String> sqlList = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sqlFile), "UTF-8"));
			StringBuffer sqlSb = new StringBuffer();
			String Read = "";
			while ((Read = br.readLine()) != null) {
				sqlSb.append(Read);
			}
			br.close();

			// Windows 下换行是 \r\n, Linux 下是 \n
			String[] sqlArr = sqlSb.toString().split(";");
			for (int i = 0; i < sqlArr.length; i++) {
				String sql = sqlArr[i].replaceAll("--.*", "").trim();
				if (!sql.equals("")) {
					sqlList.add(sql);
				}
			}

			return sqlList;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void execute(Statement stmt, String sqlFile) throws Exception {
		List<String> sqlList = loadSql(sqlFile);
		for (String sql : sqlList) {
			stmt.addBatch(sql + ";");
		}
		stmt.executeBatch();
		stmt.close();
	}
}