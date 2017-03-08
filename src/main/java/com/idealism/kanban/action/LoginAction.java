package com.idealism.kanban.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.service.Login;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.LoginCookieUtil;
import com.idealism.kanban.vo.LoginInfo;
import com.opensymphony.xwork2.ModelDriven;

@Controller("LoginAction")
public class LoginAction extends AjaxActionBase implements ModelDriven<LoginInfo> {
	private LoginInfo loginInfo = new LoginInfo();
	private Login loginService;

	public Login getLoginService() {
		return loginService;
	}

	@Resource
	public void setLoginService(Login loginService) {
		this.loginService = loginService;
	}

	public void CheckIn() throws IOException {
		if (loginInfo.getUser_account().equals("")) {
			ErrorText("error.login.nameorpwd");
			return;
		}
		
		String errorString = loginService.SaveLogin(loginInfo);
		if (errorString != null) {
			ErrorText(errorString);
			return;
		} else {
			Success();
			return;
		}
	}
	
	public void SavePWD() throws IOException{
		HttpSession s = ServletActionContext.getRequest().getSession();
		int id = (Integer) s.getAttribute("user_id");
		if(id>0 && loginInfo.getPassword() != null){
			loginService.SavePwd(id,loginInfo.getPassword());
			Success();
		}else{
			ErrorText("error.save");
		}
	}
	
	public String Out(){
		HttpSession s = ServletActionContext.getRequest().getSession();
		s.removeAttribute("user_id");
		s.removeAttribute("user_account");
		s.removeAttribute("user_name");
		s.removeAttribute("password");
		s.removeAttribute("email");
		s.removeAttribute("user_photo");
		s.removeAttribute("user_account");
		s.removeAttribute("config_text");
		
		LoginCookieUtil cookieUtil = new LoginCookieUtil();
		cookieUtil.DelLoginCookie();
		
		return "out";
	}

	@Override
	public LoginInfo getModel() {
		return loginInfo;
	}
}