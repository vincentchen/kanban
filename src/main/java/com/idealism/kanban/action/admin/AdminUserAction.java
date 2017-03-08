package com.idealism.kanban.action.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.antlr.grammar.v3.ANTLRParser.id_return;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.Help;
import com.idealism.kanban.model.Idea;
import com.idealism.kanban.model.ProTarget;
import com.idealism.kanban.model.ProTask;
import com.idealism.kanban.model.Project;
import com.idealism.kanban.model.UserHistory;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.HelpService;
import com.idealism.kanban.service.IdeaService;
import com.idealism.kanban.service.ProTargetService;
import com.idealism.kanban.service.ProTaskService;
import com.idealism.kanban.service.ProjectService;
import com.idealism.kanban.service.UserManager;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.ProTaskInfo;
import com.idealism.kanban.vo.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("AdminUserAction")
public class AdminUserAction extends AjaxActionBase implements ModelDriven<UserInfo> {
	UserInfo userInfo = new UserInfo();
	private UserManager userManager;
	private HelpService helpService;
	private IdeaService ideaService;
	private ProjectService projectService;
	private ProTargetService proTargetService;
	private ProTaskService proTaskService;
	
	Global global = new Global();
	
	public ProjectService getProjectService() {
		return projectService;
	}

	public IdeaService getIdeaService() {
		return ideaService;
	}

	@Resource
	public void setIdeaService(IdeaService ideaService) {
		this.ideaService = ideaService;
	}

	@Resource
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public ProTargetService getProTargetService() {
		return proTargetService;
	}

	@Resource
	public void setProTargetService(ProTargetService proTargetService) {
		this.proTargetService = proTargetService;
	}

	public ProTaskService getProTaskService() {
		return proTaskService;
	}

	@Resource
	public void setProTaskService(ProTaskService proTaskService) {
		this.proTaskService = proTaskService;
	}

	public HelpService getHelpService() {
		return helpService;
	}

	@Resource
	public void setHelpService(HelpService helpService) {
		this.helpService = helpService;
	}

	
	public UserManager getUserManager() {
		return userManager;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "4")
	public String List() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (userInfo.getSearch() > 0) {
			if (session.getAttribute("UserSearch") != null) {
				userInfo = (UserInfo) session.getAttribute("UserSearch");
			} else {
				session.removeAttribute("UserSearch");
			}
		} else {
			session.removeAttribute("UserSearch");
		}
		ActionContext context = ActionContext.getContext();
		userInfo.setPageSize(30);
		java.util.List userList = userManager.SearchUsers(userInfo);
		context.put("UserList", userList);
		return "list";
	}

	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "3")
	public String Edit() {
		ActionContext context = ActionContext.getContext();
		Global global = new Global();
		int id = global.GetRegID("UserID", userInfo.getId());
		if (id > 0) {
			Users users = userManager.LoadUserById(id);
			context.put("Info", users);
		} else {
			context.put("Info", userInfo);
		}
		return "edit";
	}

	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "3")
	public void Save() throws IOException {
		int id = global.GetRegID("UserID", userInfo.getId());
		if (id > 0) {
			Users users = userManager.LoadUserById(id);
			users.setUser_account(userInfo.getUser_account());
			users.setUser_name(userInfo.getUser_name());
			users.setEmail(userInfo.getEmail());
			userManager.SaveUser(users);
			SetJsonBaseContent(global.RegisterID("UserID", users.getUser_id()));
			CallBackJSON();
		} else {
			int num = userManager.CheckCanAdd(userInfo.getUser_account());
			if(num>0){
				SetJsonBaseErrorText("error.username.duplication");
				CallBackJSON();
				return;
			}
			Users users = new Users();
			users.setUser_account(userInfo.getUser_account());
			users.setUser_name(userInfo.getUser_name());
			users.setEmail(userInfo.getEmail());
			users.setCreate_time(new Date(System.currentTimeMillis()));
			users.setConfig_text("HELP:5;MSG:5;IDEA:5;DOING:5;TODO:5;FLOW:5;PRO:5;CHAT:5;");
			userManager.SaveAdd(users);
			if (users.getUser_id() > 0) {
				SetJsonBaseContent(global.RegisterID("UserID", users.getUser_id()));
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.save");
				CallBackJSON();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "4")
	public String View() {
		int id = global.GetRegID("UserID", userInfo.getId());
		if (id > 0) {
			Users users = userManager.LoadUserById(id);
			java.util.List relDeptRole = userManager.LoadUserRelDeptAndRole(id);
			java.util.List userPri = userManager.LoadPriByUserID(id);
			ActionContext context = ActionContext.getContext();
			context.put("Users", users);
			context.put("DeptRole", relDeptRole);
			context.put("Pri", userPri);
			return "view";
		} else {
			return "noRead";
		}
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "3")
	public String Pri() {
		int id = global.GetRegID("UserID", userInfo.getId());
		if (id > 0) {
			Users users = userManager.LoadUserById(id);
			java.util.List list = userManager.LoadPriByUserID(id);
			ActionContext context = ActionContext.getContext();
			context.put("Pri", list);
			context.put("User", users);
			return "pri";
		} else {
			return "noSession";
		}
	}

	@ConfigPrivilege(privilegeName = "RoleInfoPri", privilegeValue = "3")
	public void PriSave() throws IOException {
		int id = global.GetRegID("UserID", userInfo.getId());
		if (id > 0) {
			userManager.UpdatePriByRoleId(id, userInfo.getPrivilege());
			Success();
		} else {
			Error("error.save");
		}
	}

	@Override
	public UserInfo getModel() {
		return userInfo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "4")
	public void LoadListByAjax() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (userInfo.getSearch() > 0) {
			if (session.getAttribute("UserSearch") != null) {
				int i = userInfo.getCurrentPage();
				userInfo = (UserInfo) session.getAttribute("UserSearch");
				userInfo.setCurrentPage(i);
			} else {
				session.removeAttribute("UserSearch");
			}
		} else {
			session.removeAttribute("UserSearch");
		}
		java.util.List userList = userManager.SearchUsers(userInfo);
		List arrList = new ArrayList();
		Iterator it = userList.iterator();
		while (it.hasNext()) {
			UserInfo info = (UserInfo) it.next();
			List a = new ArrayList();
			a.add(info.getId());
			a.add(info.getUser_account());
			a.add(info.getUser_name());
			a.add(info.getEmail());
			a.add(info.getLogin_time_str());
			a.add(info.getIs_delete());
			arrList.add(a);
		}
		SetJsonBaseContent(arrList);
		CallBackJSON();
	}

	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "4")
	public void SaveSearch() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("UserSearch", userInfo);
		Success();
	}
	
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "4")
	public String History(){
		int id = global.GetRegID("UserID", userInfo.getId());
		if(id > 0){
			Users users = userManager.LoadUserById(id);
			ActionContext context = ActionContext.getContext();
			context.put("Users", users);
			return "history";
		}else {
			return "noSession";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "4")
	public void LoadHistoryByAjax() throws IOException, ParseException{
		int id = global.GetRegID("UserID", userInfo.getId());
		if(id > 0){
			userInfo.setUser_id(id);
			List list = userManager.LoadHistoryByInfo(userInfo);
			Iterator iterator = list.iterator();
			List returnList = new ArrayList();
			DefineConfig defineConfig = new DefineConfig();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			while (iterator.hasNext()) {
				UserHistory history = (UserHistory) iterator.next();
				List arr = new ArrayList();
				arr.add(sdf.format(history.getLog_time()));
				arr.add(defineConfig.UserHistoryType(history.getHistory_type()));
				arr.add(defineConfig.UserHistoryStatus(history.getHistory_status()));
				String urlString = "";
				switch (history.getHistory_type()) {
				case 2:
					Help help = helpService.LoadHelpById(history.getHistory_type_id());
					int help_id = global.RegisterID("Help", history.getHistory_type_id());
					urlString += "<a href='AdminHelpView?id="+help_id+"'>"+help.getHelp_title()+"</a>";
					break;
				case 3:
					Idea idea = ideaService.LoadIdeaById(history.getHistory_type_id());
					int idea_id = global.RegisterID("Idea", history.getHistory_type_id());
					urlString += "<a href='AdminIdeaView?id="+idea_id+"'>"+idea.getIdea_title()+"</a>";
					break;
				case 4:
					Project project = projectService.LoadProById(history.getHistory_type_id());
					int pro_id = global.RegisterID("Project", history.getHistory_type_id());
					urlString += "<a href='AdminProjectView?id="+pro_id+"'>"+project.getPro_name()+"</a>";
					break;
				case 5:
					ProTarget proTarget = proTargetService.LoadProTargetById(history.getHistory_type_id());
					int protarget_id = global.RegisterID("ProTarget", history.getHistory_type_id());
					urlString += "<a href='AdminProTargetView?id="+protarget_id+"'>"+proTarget.getTarget_name()+"</a>";
					break;
				case 6:
					ProTask proTask = proTaskService.LoadProTaskById(history.getHistory_type_id());
					int protask_id = global.RegisterID("ProTask", history.getHistory_type_id());
					urlString += "<a href='AdminProTaskView?id="+protask_id+"'>#"+proTask.getTask_id()+"</a>";
					break;
				default:
					break;
				}
				arr.add(urlString);
				returnList.add(arr);
			}
			SetJsonBaseContent(returnList);
			CallBackJSON();
		}else {
			SetJsonBaseError("error.session.out");
			CallBackJSON();
		}
	}
	
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "2")
	public void DeleteUser() throws IOException{
		int id = global.GetRegID("UserID", userInfo.getId());
		if(id>0){
			userManager.DeleteUser(id);
			Success();
		}else {
			Error("error.save");
		}
		
	}
	
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "2")
	public void ActivationUser() throws IOException{
		int id = global.GetRegID("UserID", userInfo.getId());
		if(id>0){
			userManager.ActivationUser(id);
			Success();
		}else {
			Error("error.save");
		}
	}
	
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "3")
	public void ResetPwd() throws IOException{
		int id = global.GetRegID("UserID", userInfo.getId());
		if(id>0){
			userManager.UpdateResetPwd(id);
			Success();
		}else {
			Error("error.save");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "3")
	public String RelDept(){
		int id = global.GetRegID("UserID", userInfo.getId());
		if(id>0){
			List list = userManager.LoadUserRelDeptAndRole(id);
			Users users = userManager.LoadUserById(id);
			ActionContext context = ActionContext.getContext();
			context.put("RelDeptAndRole", list);
			context.put("Users", users);
			return "reldept";
		}else{
			return "noLogin";
		}
	}
}
