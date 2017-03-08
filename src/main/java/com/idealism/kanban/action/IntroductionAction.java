package com.idealism.kanban.action;

import org.springframework.stereotype.Controller;

import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.vo.IntroductionInfo;
import com.opensymphony.xwork2.ModelDriven;

@Controller("IntroductionAction")
public class IntroductionAction extends AjaxActionBase implements ModelDriven<IntroductionInfo> {
	private IntroductionInfo info = new IntroductionInfo();

	public IntroductionInfo getInfo() {
		return info;
	}

	public void setInfo(IntroductionInfo info) {
		this.info = info;
	}

	public IntroductionInfo getModel() {
		return info;
	}
	
	public String List(){

		return "list";
	}
	
}