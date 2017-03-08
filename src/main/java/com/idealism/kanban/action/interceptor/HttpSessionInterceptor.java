package com.idealism.kanban.action.interceptor;

import java.sql.Date;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.Login;
import com.idealism.kanban.util.Encryption;
import com.idealism.kanban.util.LoginCookieUtil;
import com.idealism.kanban.vo.UserCookieInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class HttpSessionInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		int doLoginCookie = 0;
		if (!ServletActionContext.getRequest().isRequestedSessionIdValid()) {
			doLoginCookie = 1;
		}

		HttpSession s = ServletActionContext.getRequest().getSession();
		// 开始Login检测
		String user_account = (String) s.getAttribute("user_account");
		if (user_account == null || s.getAttribute("user_id") == null) {
			doLoginCookie = 1;
		}
		
		if (doLoginCookie == 1) {
			LoginCookieUtil cookieUtil = new LoginCookieUtil();
			UserCookieInfo userCookieInfo = cookieUtil.GetLoginCookie();

			if (userCookieInfo != null) {
				WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext((ServletContext) ActionContext.getContext().get(StrutsStatics.SERVLET_CONTEXT));
				Login loginService = (Login) ctx.getBean("LoginService");
				if ((userCookieInfo.getLogin_time().getTime() - new Date(System.currentTimeMillis()).getTime()) < (60 * 60 * 24 * 14)) {
					Users users = loginService.LoginCheck(userCookieInfo.getUser_account());
					if(users == null){
						return "noLogin";
					}
					if(users.getIs_delete() == 0){
						if(users.getPassword().equals("") || users.getPassword() == null){
							return "noPWD";
						}
						String pWDString = new Encryption().decryptString(userCookieInfo.getPassword());
						if(users != null && pWDString != null && userCookieInfo.getPassword() != null && userCookieInfo.getCookie_key() != null && users.getCookie_key() != null){
							if (pWDString.equals(new Encryption().decryptString(users.getPassword())) && users.getCookie_key().equals(userCookieInfo.getCookie_key())) {
								loginService.UpdateLogin(users);
								loginService.MakeUpPri(users);
							} else {
								return "noLogin";
							}
						}
					}
				} else {
					return "noLogin";
				}
			} else {
				return "noLogin";
			}
		}

		HttpSession s2 = ServletActionContext.getRequest().getSession();
		if (s2.getAttribute("user_id") == null) {
			return "noLogin";
		}
		
		if(s2.getAttribute("password") == null){
			return "noPWD";
		}
		return invocation.invoke();
	}

}