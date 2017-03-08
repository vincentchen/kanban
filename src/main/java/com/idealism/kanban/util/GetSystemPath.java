package com.idealism.kanban.util;

public class GetSystemPath {
	public String GetWebRootPath() {
		String classPath = this.getClass().getResource("/").getPath().toString();
		classPath = classPath.replaceAll("%20", " ");
		int num = classPath.indexOf("WEB-INF");
		return classPath.substring(0, num);
	}

	public String GetWebINFPath() {
		String classPath = this.getClass().getResource("/").getPath().toString();
		classPath = classPath.replaceAll("%20", " ");
		int num = classPath.indexOf("WEB-INF");
		return classPath.substring(0, num + "WEB-INF".length()) + "/";
	}

}