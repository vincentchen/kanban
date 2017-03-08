package com.idealism.kanban.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

/**
 * cookie的增加、删除、查询
 */
public class CookieUtil {
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();

	public void AddCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 14);
		response.addCookie(cookie);
	}

	public String getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public void delCookie(String name) {
		Cookie cookie = new Cookie(name, null); 
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie); 
	}
}
