package com.idealism.kanban.service;

import com.idealism.kanban.model.Users;
import com.idealism.kanban.vo.LoginInfo;

public interface Login {

	public Users LoginCheck(String account);

	public void MakeUpPri(Users users);

	public void SavePwd(int id, String password);

	public String SaveLogin(LoginInfo loginInfo);

	public void UpdateLogin(Users users);

}