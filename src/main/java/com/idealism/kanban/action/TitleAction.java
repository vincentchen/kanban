package com.idealism.kanban.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.service.TitleManager;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.vo.TitleInfo;

@Controller("TitleAction")
public class TitleAction extends AjaxActionBase {
	private TitleManager titleManager;
	private TitleInfo titleInfo;

	public TitleInfo getTitleInfo() {
		return titleInfo;
	}

	public TitleManager getTitleManager() {
		return titleManager;
	}

	@Resource
	public void setTitleManager(TitleManager titleManager) {
		this.titleManager = titleManager;
	}

	public String UserTitle() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		titleInfo = titleManager.UserTitleCnt(user_id);

		return SUCCESS;
	}
}