package com.idealism.kanban.action.admin;

import org.springframework.stereotype.Controller;

import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;

@Controller("AdminTitleAction")
public class AdminTitleAction extends AjaxActionBase{
	
	@ConfigPrivilege(privilegeName = "KeyPri", privilegeValue = "4")
	public String Title() {
		return "title";
	}

}
