package com.idealism.kanban.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.Help;
import com.idealism.kanban.service.HelpService;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.HelpInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("HelpAction")
public class HelpAction extends AjaxActionBase implements ModelDriven<HelpInfo> {
	private HelpInfo helpInfo = new HelpInfo();
	private HelpService helpService;

	public HelpService getHelpService() {
		return helpService;
	}

	@Resource
	public void setHelpService(HelpService helpService) {
		this.helpService = helpService;
	}

	@Override
	public HelpInfo getModel() {
		return helpInfo;
	}

	@SuppressWarnings("rawtypes")
	public String List() {
		ActionContext context = ActionContext.getContext();
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			if(helpInfo.getSearch()>0){
				if(session.getAttribute("HelpSearch") != null){
					helpInfo = (HelpInfo) session.getAttribute("HelpSearch");
				}else {
					session.removeAttribute("HelpSearch");
				}
			}else {
				session.removeAttribute("HelpSearch");
			}
			helpInfo.setUser_id(user_id);
			if (helpInfo.getSearch_type() == 1) {
				List helpList = helpService.LoadHelpNotMy(helpInfo);
				context.put("HelpList", helpList);
				context.put("HelpInfo", helpInfo);
			} else {
				List helpList = helpService.LoadHelpWithMy(helpInfo);
				context.put("HelpList", helpList);
				context.put("HelpInfo", helpInfo);
			}
			return "list";
		} else {
			return "noLogin";
		}
	}

	public String Edit() {
		ActionContext context = ActionContext.getContext();
		Global global = new Global();
		int id = global.GetRegID("Help", helpInfo.getId());
		if (id > 0) {
			Help help = helpService.LoadHelpById(id);
			context.put("Info", help);
		} else {
			context.put("Info", helpInfo);
		}
		return "edit";
	}

	public void Save() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Help", helpInfo.getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (id > 0 && user_id > 0) {
			Help help = helpService.LoadHelpById(id);
			help.setHelp_title(helpInfo.getHelp_title());
			help.setHelp_content(helpInfo.getHelp_content());
			int help_user_id = global.GetRegID("UserID", helpInfo.getHelp_user_id());
			if (help_user_id > 0) {
				help.setHelp_user_id(help_user_id);
			}

			helpService.Save(help);
			if (help.getRequest_id() > 0) {
				SetJsonBaseContent(global.RegisterID("Help", help.getRequest_id()));
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.save");
				CallBackJSON();
			}
		} else if (user_id > 0) {
			Help help = new Help();
			help.setHelp_title(helpInfo.getHelp_title());
			help.setHelp_content(helpInfo.getHelp_content());
			int help_user_id = global.GetRegID("UserID", helpInfo.getHelp_user_id());
			if (help_user_id > 0) {
				help.setHelp_user_id(help_user_id);
			}
			help.setCreate_time(new Date(System.currentTimeMillis()));
			help.setSeek_help_user_id(user_id);
			helpService.Save(help);
			if (help.getRequest_id() > 0) {
				SetJsonBaseContent(global.RegisterID("Help", help.getRequest_id()));
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.save");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.session.out");
			CallBackJSON();
		}
	}

	public String View() {
		Global global = new Global();
		int id = global.GetRegID("Help", helpInfo.getId());
		if (id > 0) {
			HelpInfo info = helpService.LoadHelpInfoById(id);
			ActionContext context = ActionContext.getContext();
			context.put("Help", info);
			return "view";
		} else {
			return "noRead";
		}
	}

	@SuppressWarnings("rawtypes")
	public String Download() {
		Global global = new Global();
		int id = global.GetRegID("Help", helpInfo.getId());
		if (id > 0) {
			ActionContext context = ActionContext.getContext();
			List files = helpService.LoadHelpFiles(id);
			Help help = helpService.LoadHelpById(id);
			context.put("Files", files);
			context.put("RequestId", id);
			context.put("Help", help);
			return "download";
		} else {
			return "noRead";
		}
	}

	public void Delete() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int id = global.GetRegID("Help", helpInfo.getId());
		if (user_id > 0 && id > 0) {
			Boolean can = helpService.CheckCanDelete(id, user_id);
			if (can) {
				helpService.Delete(id);
				Success();
			} else {
				ErrorText("error.cannot.operation");
			}
		} else {
			ErrorText("error.session.out");
		}
	}

	public void UpdateAccept() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int id = global.GetRegID("Help", helpInfo.getId());
		if (user_id > 0 && id > 0) {
			Boolean can = helpService.CheckCanAccept(id);
			if (can) {
				helpService.UpdateAccept(id, user_id);
				Success();
			} else {
				ErrorText("error.cannot.operation");
			}
		} else {
			ErrorText("error.session.out");
		}
	}

	public void Refuse() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int id = global.GetRegID("Help", helpInfo.getId());
		if (user_id > 0 && id > 0) {
			Boolean can = helpService.CheckCanRefuse(id, user_id);
			if (can) {
				helpService.UpdateAccept(id,0);
				Success();
			} else {
				ErrorText("error.cannot.operation");
			}
		} else {
			ErrorText("error.session.out");
		}
	}
	
	public void Finish() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int id = global.GetRegID("Help", helpInfo.getId());
		if (user_id > 0 && id > 0) {
			Boolean can = helpService.CheckCanRefuse(id, user_id);
			if (can) {
				helpService.UpdateFinish(id,new Date(System.currentTimeMillis()));
				Success();
			} else {
				ErrorText("error.cannot.operation");
			}
		} else {
			ErrorText("error.session.out");
		}
	}
	
	public String Search(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		ActionContext context = ActionContext.getContext();
		if(session.getAttribute("HelpSearch") != null){
			context.put("Info", (HelpInfo)session.getAttribute("HelpSearch"));
		}else {
			context.put("Info", helpInfo);
		}
		return "search";
	}
	
	public void SaveSearch() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("HelpSearch", helpInfo);
		CallBackJSON();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void LoadListByAjax() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		List helpList;
		List arrList = new ArrayList();
		if (user_id > 0) {
			if(helpInfo.getSearch()>0){
				if(session.getAttribute("HelpSearch") != null){
					int i = helpInfo.getCurrentPage();
					helpInfo = (HelpInfo) session.getAttribute("HelpSearch");
					helpInfo.setCurrentPage(i);
				}else {
					session.removeAttribute("HelpSearch");
				}
			}
			helpInfo.setUser_id(user_id);
			if (helpInfo.getSearch_type() == 1) {
				helpList = helpService.LoadHelpNotMy(helpInfo);
			} else {
				helpList = helpService.LoadHelpWithMy(helpInfo);
			}
			Iterator it = helpList.iterator();
			while (it.hasNext()) {
				HelpInfo info = (HelpInfo) it.next();
				List a = new ArrayList();
				a.add(info.getId());
				a.add(info.getHelp_title());
				if(info.getSeek_help_user_id() == user_id){
					a.add(1);
				}else {
					if(info.getHasAttach()){
						a.add(1);
					}else {
						a.add(0);
					}
				}
				if(info.getHelp_user_id() <= 0 && info.getSeek_help_user_id() != user_id){
					a.add(1);
				}else {
					a.add(0);
				}
				if(info.getSeek_help_user_id() == user_id && info.getHelp_user_id() >0 && info.getSolve_time() == null){
					a.add(1);
				}else {
					a.add(0);
				}
				if(info.getSeek_help_user_id() == user_id && info.getSolve_time() == null){
					a.add(1);
				}else {
					a.add(0);
				}
				a.add(info.getHelp_content());
				arrList.add(a);
			}
		}
		SetJsonBaseContent(arrList);
		CallBackJSON();
	}
}
