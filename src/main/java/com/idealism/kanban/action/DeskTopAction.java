package com.idealism.kanban.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.DeskTopService;
import com.idealism.kanban.service.UserManager;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.DeskTopInfo;
import com.idealism.kanban.vo.HelpInfo;
import com.idealism.kanban.vo.MsgInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("DeskTopAction")
public class DeskTopAction extends AjaxActionBase implements ModelDriven<DeskTopInfo> {
	private UserManager userManager;
	private DeskTopService deskTopService;

	private DeskTopInfo deskTopInfo = new DeskTopInfo();

	public UserManager getUserManager() {
		return userManager;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public DeskTopService getDeskTopService() {
		return deskTopService;
	}

	@Resource
	public void setDeskTopService(DeskTopService deskTopService) {
		this.deskTopService = deskTopService;
	}

	@SuppressWarnings("rawtypes")
	public String UserDesktop() {
		ActionContext context = ActionContext.getContext();
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			Users users = userManager.LoadUserById(user_id);
			DefineConfig defineConfig = new DefineConfig();
			defineConfig.setConfigTextString(users.getConfig_text());
			deskTopInfo.setUser_id(users.getUser_id());

			if (defineConfig.GetConfigValue("TODO") > 0) {
				deskTopInfo.setPageSize(defineConfig.GetConfigValue("TODO"));
				List ToDo = deskTopService.LoadToDo(deskTopInfo);
				context.put("TODO", ToDo);
			}

			if (defineConfig.GetConfigValue("DOING") > 0) {
				deskTopInfo.setPageSize(defineConfig.GetConfigValue("DOING"));
				List Doing = deskTopService.LoadDoingByUserID(deskTopInfo);
				context.put("DOING", Doing);
			}

			if (defineConfig.GetConfigValue("PRO") > 0) {
				deskTopInfo.setPageSize(defineConfig.GetConfigValue("PRO"));
				List proList = deskTopService.LoadProByUserID(deskTopInfo);
				context.put("PRO", proList);
			}

			if (defineConfig.GetConfigValue("IDEA") > 0) {
				deskTopInfo.setPageSize(defineConfig.GetConfigValue("IDEA"));
				List ideaList = deskTopService.LoadIdeaExcludeUserID(deskTopInfo);
				context.put("IDEA", ideaList);
			}

			if (defineConfig.GetConfigValue("HELP") > 0) {
				deskTopInfo.setPageSize(defineConfig.GetConfigValue("HELP"));
				List ideaList = deskTopService.LoadHelpExcludeUserID(deskTopInfo);
				context.put("HELP", ideaList);
			}
			
			if(defineConfig.GetConfigValue("MSG") > 0){
				deskTopInfo.setPageSize(defineConfig.GetConfigValue("MSG"));
				List msgList = deskTopService.LoadMsgByUserID(deskTopInfo);
				context.put("MSG", msgList);
			}

			context.put("Config", defineConfig.getConfigText());
			context.put("Users", users);
		} else {
			return "noLogin";
		}

		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void TODOByAjaxList() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		deskTopInfo.setUser_id(user_id);
		List list = deskTopService.LoadToDo(deskTopInfo);
		List todoList = new ArrayList();
		Iterator iterator = list.iterator();
		Global global = new Global();
		while(iterator.hasNext()){
			List arr = new ArrayList();
			Object[] arg = (Object[])iterator.next();
			arr.add(arg[0]);
			arr.add(arg[2]);
			arr.add(arg[10]);
			arr.add(global.RegisterID("ProTask", (Integer)arg[0]));
			todoList.add(arr);
		}
		SetJsonBaseContent(todoList);
		CallBackJSON();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void DOINGByAjaxList() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		deskTopInfo.setUser_id(user_id);
		List list = deskTopService.LoadDoingByUserID(deskTopInfo);
		List doingList = new ArrayList();
		Iterator iterator = list.iterator();
		Global global = new Global();
		SimpleDateFormat yMd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat HH = new SimpleDateFormat("HH");   
		while(iterator.hasNext()){
			List arr = new ArrayList();
			Object[] arg = (Object[])iterator.next();
			arr.add(arg[0]);
			arr.add(arg[2]);
			if(global.getDayIsToday((Date) arg[6])){
				String str = getText("today")+HH.format((Date) arg[6])+getText("before");
				arr.add(str);
			}else{
				String str = yMd.format((Date) arg[6]);
				arr.add(str);
			}
			arr.add(global.RegisterID("ProTask", (Integer)arg[0]));
			doingList.add(arr);
		}
		SetJsonBaseContent(doingList);
		CallBackJSON();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void PROByAjaxList() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		deskTopInfo.setUser_id(user_id);
		List list = deskTopService.LoadProByUserID(deskTopInfo);
		List proList = new ArrayList();
		Iterator iterator = list.iterator();
		Global global = new Global();
		while(iterator.hasNext()){
			List arr = new ArrayList();
			HashMap<String, Object> arg = (HashMap<String, Object>)iterator.next();
			arr.add(global.RegisterID("Project", (Integer) arg.get("ID")));
			arr.add(arg.get("NAME"));
			arr.add(arg.get("TODO"));
			arr.add(arg.get("DOING"));
			arr.add(arg.get("DONE"));
			proList.add(arr);
		}
		SetJsonBaseContent(proList);
		CallBackJSON();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void HELPByAjaxList() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		deskTopInfo.setUser_id(user_id);
		List list = deskTopService.LoadHelpExcludeUserID(deskTopInfo);
		List helpList = new ArrayList();
		Iterator iterator = list.iterator();
		Global global = new Global();
		while(iterator.hasNext()){
			List arr = new ArrayList();
			HelpInfo helpInfo = (HelpInfo)iterator.next();
			arr.add(global.RegisterID("Help", helpInfo.getRequest_id()));
			arr.add(helpInfo.getHelp_title());
			arr.add(helpInfo.getSeek_help_user_name());
			helpList.add(arr);
		}
		SetJsonBaseContent(helpList);
		CallBackJSON();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void IDEAByAjaxList() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		deskTopInfo.setUser_id(user_id);
		List list = deskTopService.LoadIdeaExcludeUserID(deskTopInfo);
		List ideaList = new ArrayList();
		Iterator iterator = list.iterator();
		Global global = new Global();
		while(iterator.hasNext()){
			List arr = new ArrayList();
			Object[] arg = (Object[])iterator.next();
			arr.add(global.RegisterID("Idea", (Integer)arg[0]));
			arr.add(arg[1]);
			ideaList.add(arr);
		}
		SetJsonBaseContent(ideaList);
		CallBackJSON();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void MSGByAjaxList() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		deskTopInfo.setUser_id(user_id);
		List list = deskTopService.LoadMsgByUserID(deskTopInfo);
		List msgList = new ArrayList();
		Iterator iterator = list.iterator();
		Global global = new Global();
		while(iterator.hasNext()){
			List arr = new ArrayList();
			MsgInfo msgInfo = (MsgInfo)iterator.next();
			arr.add(global.RegisterID("Idea", msgInfo.getMsg_id()));
			arr.add(msgInfo.getMsg_title());
			arr.add(msgInfo.getSrc_type_name());
			arr.add(msgInfo.getSrc_url());
			msgList.add(arr);
		}
		SetJsonBaseContent(msgList);
		CallBackJSON();
	}

	@Override
	public DeskTopInfo getModel() {
		return deskTopInfo;
	}
}