package com.idealism.kanban.action;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.vo.IndexInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;


@Controller("IndexAction")
public class IndexAction extends AjaxActionBase implements ModelDriven<IndexInfo> {
	private IndexInfo indexInfo = new IndexInfo();
	
	@Override
	public IndexInfo getModel() {
		return indexInfo;
	}
	
	public String GetHomePage(){
		ActionContext context = ActionContext.getContext();
		context.put("url", indexInfo.getDesktopUrl());
		return SUCCESS;
	}
	
}