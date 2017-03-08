package com.idealism.kanban.action.admin;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.ProTarget;
import com.idealism.kanban.model.Project;
import com.idealism.kanban.service.ProjectService;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.util.JxlUtil;
import com.idealism.kanban.vo.ProjectInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("AdminProjectAction")
public class AdminProjectAction extends AjaxActionBase implements ModelDriven<ProjectInfo> {
	ProjectInfo projectInfo = new ProjectInfo();
	private ProjectService projectService;

	public ProjectService getProjectService() {
		return projectService;
	}

	@Resource
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public ProjectInfo getModel() {
		return projectInfo;
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String List() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (projectInfo.getSearch() > 0) {
			if (session.getAttribute("ProjectSearch") != null) {
				projectInfo = (ProjectInfo) session.getAttribute("ProjectSearch");
			} else {
				session.removeAttribute("ProjectSearch");
			}
		} else {
			session.removeAttribute("ProjectSearch");
		}
		java.util.List proList = projectService.LoadProject(projectInfo);
		ActionContext context = ActionContext.getContext();
		context.put("ProList", proList);
		return "list";
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public String Edit() {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		ActionContext context = ActionContext.getContext();
		if (id > 0) {
			Project project = projectService.LoadProById(id);
			context.put("Project", project);
		} else {
			context.put("Project", projectInfo);
		}
		return "edit";
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String View() {
		if (projectInfo.getId() > 0) {
			Global global = new Global();
			int id = global.GetRegID("Project", projectInfo.getId());
			if (id > 0) {
				ActionContext context = ActionContext.getContext();
				Project project = projectService.LoadProById(id);
				List targetList = null;
				if (projectInfo.getTargetStatus() == null) {
					targetList = projectService.LoadProTargetUnFinishById(id,0,0);
				} else {
					targetList = projectService.LoadProTargetFinishedById(id,0,0);
				}
				context.put("Project", project);
				context.put("Target", targetList);
				context.put("Info", projectInfo);
				return "view";
			} else {
				return "noRead";
			}
		} else {
			return "noPri";
		}
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String Download() {
		if (projectInfo.getId() > 0) {
			Global global = new Global();
			int id = global.GetRegID("Project", projectInfo.getId());
			if (id > 0) {
				ActionContext context = ActionContext.getContext();
				List files = projectService.LoadProFiles(id);
				context.put("Files", files);
				context.put("ProId", id);
				return "download";
			} else {
				return "noRead";
			}
		} else {
			return "noPri";
		}
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void Save() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (id > 0) {
			Project project = projectService.LoadProById(id);
			project.setPro_name(projectInfo.getPro_name());
			project.setPro_des(projectInfo.getPro_des());
			project.setStart_pro_time(projectInfo.getStart_pro_time());
			project.setEnd_pro_time(projectInfo.getEnd_pro_time());
			project.setEstimated_day(projectInfo.getEstimated_day());
			project.setIs_open(projectInfo.getIs_open());
			projectService.Save(project);
			SetJsonBaseContent(new Global().RegisterID("Project", project.getPro_id()));
			CallBackJSON();
		} else {
			HttpSession session = ServletActionContext.getRequest().getSession();
			int user_id = (Integer) session.getAttribute("user_id");
			projectInfo.setCreate_user_id(user_id);
			projectService.SaveInfo(projectInfo);
			SetJsonBaseContent(new Global().RegisterID("Project", projectInfo.getPro_id()));
			CallBackJSON();
		}
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void ChangeProStatus() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		Project project = projectService.LoadProById(id);
		int status = new DefineConfig().GetProStatusNextProcess(project.getPro_status());
		project.setPro_status(status);
		if (status == 200) {
			project.setEnd_time(new Date(System.currentTimeMillis()));
		} else {
			project.setEnd_time(null);
		}
		projectService.Save(project);
		Success();
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "2")
	public void Delete() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		Project project = projectService.LoadProById(id);
		projectService.DeleteProject(project.getPro_id());
		CallBackJSON();
	}

	@SuppressWarnings("rawtypes")
	public String Users() {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (id > 0) {
			ActionContext context = ActionContext.getContext();
			List userList = projectService.LoadProUsers(id);
			context.put("ProInfo", projectInfo);
			context.put("Users", userList);
			return "users";
		} else {
			return "noSession";
		}
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void AddRelUsers() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (id > 0 && projectInfo.getUserIDs().length() > 0) {
			String[] ary = projectInfo.getUserIDs().split(",");
			List<String> userList = Arrays.asList(ary);
			List userIDs = global.GetRegIDs("UserID", userList);
			if (userIDs.size() > 0) {
				projectService.SaveRelUsers(id, userIDs);
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.select.user");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.session.out");
			CallBackJSON();
		}
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void DeleteRelUsers() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		int user_id = global.GetRegID("UserID", Integer.parseInt(projectInfo.getUserIDs()));
		if (id > 0 && user_id > 0) {
			projectService.DeleteRelUsers(id, user_id);
			CallBackJSON();
		} else {
			SetJsonBaseErrorText("error.session.out");
			CallBackJSON();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String Graph() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (id > 0) {
			String referenceMain = "";// 理论值提示字符串
			String remainderMain = "";// 实际值提示字符串
			String referenceDetail = "";// 理论详细值字符串
			String remainderDetail = "";// 实际详细值字符串
			String ganttString = "";
			Project project = projectService.LoadProById(id);
			List graph = new ArrayList();
			graph.add(project);

			referenceMain = "'" + getText("pro.start_pro_time") + "','" + getText("pro.end_pro_time") + "'";
			remainderMain = "'" + getText("pro.start_time") + "',";
			graph.add(referenceMain);

			List list = projectService.LoadTargetGraphList(id);
			int targetCnt = list.size();
			referenceDetail = "[" + project.getStart_pro_time().getTime() + "," + targetCnt + "],[" + project.getEnd_pro_time().getTime() + ",0]";
			graph.add(referenceDetail);
			SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (list.size() > 0) {
				remainderDetail = "[" + project.getStart_time().getTime() + "," + targetCnt + "],";
				Iterator it1 = list.iterator();
				while (it1.hasNext()) {
					ProTarget target = (ProTarget) it1.next();
					if (target.getTarget_status() == 200) {
						targetCnt--;
						remainderMain += "'" + target.getTarget_name() + "',";
						remainderDetail += "[" + target.getEnd_time().getTime() + "," + targetCnt + "],";
					}
					ganttString += "{'name':'" + target.getTarget_name() + "','desc':'','values':[{'from':'/Date(" + target.getStart_target_time().getTime() + ")/','to':'/Date(" + target.getEnd_target_time().getTime() + ")','desc':'" + target.getTarget_name() + ":" + DateFormat.format(target.getStart_target_time()) + "-" + DateFormat.format(target.getEnd_target_time()) + "','label':'" + target.getTarget_name() + "'}]},";
				}
			}

			if (referenceMain.length() > 0) {
				graph.add(remainderMain.substring(0, remainderMain.length() - 1));
			} else {
				graph.add("");
			}
			if (remainderDetail.length() > 0) {
				graph.add(remainderDetail.substring(0, remainderDetail.length() - 1));
			} else {
				graph.add("");
			}
			if (ganttString.length() > 0) {
				graph.add(ganttString.substring(0, ganttString.length() - 1));
			} else {
				graph.add("");
			}
			ActionContext context = ActionContext.getContext();
			context.put("Graph", graph);
			return "graph";
		} else {
			return "noSession";
		}
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String History() {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (id > 0) {
			List list = projectService.LoadHistoryByProID(id);
			ActionContext context = ActionContext.getContext();
			context.put("History", list);
			return "history";
		} else {
			return "noSession";
		}
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public void DownLoadHistory() throws ParseException, RowsExceededException, WriteException, IOException {
		if (projectInfo.getHistory_min_time() != null && projectInfo.getHistory_max_time() != null) {
			Global global = new Global();
			int id = global.GetRegID("Project", projectInfo.getId());
			if (id > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List proHistory = projectService.SearchProjectHistory(projectInfo.getHistory_min_time(), projectInfo.getHistory_max_time(), id);
				if (proHistory.size() > 0) {
					ActionContext context1 = ActionContext.getContext();
					HttpServletResponse response = (HttpServletResponse) context1.get(StrutsStatics.HTTP_RESPONSE);
					response.setContentType("application/vnd.ms-excel;charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment; filename=" + sdf.format(projectInfo.getHistory_min_time()) + "-" + sdf.format(projectInfo.getHistory_max_time()) + ".xls");
					JxlUtil excel = new JxlUtil();
					excel.CreateProjectHistoryExcel(proHistory, response.getOutputStream());
				} else {
					Error("<script>alert('empty');</script>");
				}
			} else {
				Error("<script>window.location.href='login.jsp'</script>");
			}
		}
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String Cnt() {
		return "cnt";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public void LoadListByAjax() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		List arrList = new ArrayList();
		if (projectInfo.getSearch() > 0) {
			if (session.getAttribute("ProjectSearch") != null) {
				int i = projectInfo.getCurrentPage();
				projectInfo = (ProjectInfo) session.getAttribute("ProjectSearch");
				projectInfo.setCurrentPage(i);
			} else {
				session.removeAttribute("ProjectSearch");
			}
		}
		List proList = projectService.LoadProject(projectInfo);
		Iterator it = proList.iterator();
		while (it.hasNext()) {
			ProjectInfo info = (ProjectInfo) it.next();
			List a = new ArrayList();
			a.add(info.getId());
			a.add(info.getPro_name());
			a.add(info.getPro_des());
			a.add(info.getPro_status());
			if (info.getCreate_user_id() == user_id) {
				a.add(1);
			} else {
				a.add(0);
			}
			arrList.add(a);
		}
		SetJsonBaseContent(arrList);
		CallBackJSON();
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String Search() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		ActionContext context = ActionContext.getContext();
		if (session.getAttribute("ProjectSearch") != null) {
			context.put("Info", (ProjectInfo) session.getAttribute("ProjectSearch"));
		} else {
			context.put("Info", projectInfo);
		}
		return "search";
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public void SaveSearch() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("ProjectSearch", projectInfo);
		CallBackJSON();
	}
}
