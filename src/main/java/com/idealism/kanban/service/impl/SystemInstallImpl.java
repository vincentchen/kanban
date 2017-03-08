package com.idealism.kanban.service.impl;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.idealism.kanban.dao.SystemInstallDao;
import com.idealism.kanban.dao.impl.SystemInstallDaoImpl;
import com.idealism.kanban.model.Install;
import com.idealism.kanban.service.SystemInstall;
import com.idealism.kanban.vo.SystemInstallInfo;

public class SystemInstallImpl implements SystemInstall {
	private SystemInstallDao systemInstallDao = new SystemInstallDaoImpl();

	@Override
	public String Setup(SystemInstallInfo info) throws Exception {
		String errString = "";

		errString = systemInstallDao.CanInstallCheck();
		if (errString != "") {
			return errString;
		}

		// 数据提取
		Install model = systemInstallDao.DBDataExchange();
		if (model == null) {
			return "error.install.initialize";
		}

		// 判断数据库是否存在，不存在则创建
		try {
			Class.forName(model.getDriverClassName());
		} catch (ClassNotFoundException e1) {
			return "error.system.jdbc";
		}

		Connection myConnection = null;
		myConnection = systemInstallDao.CreateConnection(model);
		if (myConnection == null) {
			return "error.install.kanbandb";
		}
		
		//删除已有数据库表
		errString = systemInstallDao.DropTable(model, myConnection);
		if (errString != "" && errString != null) {
			return errString;
		}

		// 生成数据库
		errString = systemInstallDao.CreateDB(model, myConnection);
		if (errString != "" && errString != null) {
			return errString;
		}

		// 生成系统预设值
		errString = systemInstallDao.CreateSystemDefaultValue(info, myConnection);
		if (errString != "" && errString != null) {
			return errString;
		}

		// 生成管理员帐号
		errString = systemInstallDao.CreateManager(info, myConnection);
		if (errString != "" && errString != null) {
			return errString;
		}
		// 创建锁
		errString = systemInstallDao.CreateInstalLock();
		if (errString != "" && errString != null) {
			return errString;
		}
		return "";
	}
}