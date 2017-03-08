package com.idealism.kanban.action.admin;

import org.springframework.stereotype.Controller;

import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;

@Controller("AdminDesktopAction")
public class AdminDesktopAction extends AjaxActionBase {
	
	@ConfigPrivilege(privilegeName = "KeyPri", privilegeValue = "4")
	public String Desktop() {
		
		return "desktop";
	}
}
