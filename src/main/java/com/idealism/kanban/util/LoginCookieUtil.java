package com.idealism.kanban.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.vo.UserCookieInfo;

public class LoginCookieUtil extends CookieUtil {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void AddLoginCookie(Users users, String cookie_key) {
		UserCookieInfo userCookieInfo = new UserCookieInfo();
		userCookieInfo.setUser_account(users.getUser_account());
		userCookieInfo.setPassword(users.getPassword());
		userCookieInfo.setLogin_time(new Date(System.currentTimeMillis()));
		userCookieInfo.setCookie_key(cookie_key);

		String str = sdf.format(userCookieInfo.getLogin_time());

		String cookieStr = userCookieInfo.getCookie_key() + "\t" + userCookieInfo.getUser_account() + "\t" + userCookieInfo.getPassword() + "\t" + str;
		String cookieValue = new Encryption().encryptString(cookieStr);

		AddCookie("idealism_user", cookieValue);
	}

	public void DelLoginCookie() {
		delCookie("idealism_user");
	}

	public UserCookieInfo GetLoginCookie() {
		String cookieString = getCookie("idealism_user");
		if (cookieString == null || cookieString.equals("")) {
			return null;
		}
		String cookieValue = new Encryption().decryptString(cookieString);
		String[] cookie = cookieValue.split("\t");
		UserCookieInfo userCookieInfo = new UserCookieInfo();
		userCookieInfo.setCookie_key(cookie[0]);
		userCookieInfo.setUser_account(cookie[1]);
		userCookieInfo.setPassword(cookie[2]);
		try {
			userCookieInfo.setLogin_time(sdf.parse(cookie[3]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return userCookieInfo;
	}
}