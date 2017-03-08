package com.idealism.kanban.vo;

public class SystemInstallInfo {
	private String ManagerName = "admin";;
	private String ManagerPassword;
	private String Managerpassword2;
	private String ManagerEmail;

	public String getManagerName() {
		return ManagerName;
	}

	public void setManagerName(String managerName) {
		ManagerName = managerName;
	}

	public String getManagerPassword() {
		return ManagerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		ManagerPassword = managerPassword;
	}

	public String getManagerpassword2() {
		return Managerpassword2;
	}

	public void setManagerpassword2(String managerpassword2) {
		this.Managerpassword2 = managerpassword2;
	}

	public String getManagerEmail() {
		return ManagerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		ManagerEmail = managerEmail;
	}
}