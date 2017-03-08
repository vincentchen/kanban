package com.idealism.kanban.service;

import com.idealism.kanban.vo.SystemInstallInfo;

public interface SystemInstall {

	public abstract String Setup(SystemInstallInfo info) throws Exception;

}