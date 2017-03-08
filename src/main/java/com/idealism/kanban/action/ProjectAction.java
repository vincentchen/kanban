package com.idealism.kanban.action;

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
import com.idealism.kanban.service.UserManager;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.util.JxlUtil;
import com.idealism.kanban.vo.ProjectInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("ProjectAction")
public class ProjectAction extends AjaxActionBase implements ModelDriven<ProjectInfo> {
	private ProjectInfo projectInfo = new ProjectInfo();
	private ProjectService projectService;
	private UserManager userManager;

	public ProjectService getProjectService() {
		return projectService;
	}

	@Resource
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public ProjectInfo getModel() {
		return projectInfo;
	}

	@SuppressWarnings("rawtypes")
	public String View() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (id > 0 && user_id > 0) {
			ActionContext context = ActionContext.getContext();
			Project project = projectService.LoadProById(id);
			int attention = userManager.LoadMsgConfig("protarget_send", user_id);
			List targetList = null;
			if (projectInfo.getTargetStatus() == null) {
				targetList = projectService.LoadProTargetUnFinishById(id, attention, user_id);
			} else {
				targetList = projectService.LoadProTargetFinishedById(id, attention, user_id);
			}
			context.put("Project", project);
			context.put("Target", targetList);
			context.put("Info", projectInfo);
			context.put("Attention", attention);
			return "view";
		} else {
			return "noRead";
		}
	}

	public String Description() {
		if (projectInfo.getId() > 0) {
			Global global = new Global();
			int id = global.GetRegID("Project", projectInfo.getId());
			if (id > 0) {
				ActionContext context = ActionContext.getContext();
				Project project = projectService.LoadProById(id);
				context.put("Project", project);
				return "description";
			} else {
				return "noRead";
			}
		} else {
			return "noPri";
		}
	}

	@SuppressWarnings("rawtypes")
	public String Download() {
		if (projectInfo.getId() > 0) {
			Global global = new Global();
			int id = global.GetRegID("Project", projectInfo.getId());
			if (id > 0) {
				ActionContext context = ActionContext.getContext();
				List files = projectService.LoadProFiles(id);
				Project project = projectService.LoadProById(id);
				context.put("Files", files);
				context.put("ProId", id);
				context.put("Project", project);
				return "download";
			} else {
				return "noRead";
			}
		} else {
			return "noPri";
		}
	}

	@SuppressWarnings("rawtypes")
	public String List() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			if (projectInfo.getSearch() > 0) {
				if (session.getAttribute("ProjectSearch") != null) {
					projectInfo = (ProjectInfo) session.getAttribute("ProjectSearch");
				} else {
					session.removeAttribute("ProjectSearch");
				}
			} else {
				session.removeAttribute("ProjectSearch");
			}
			projectInfo.setCreate_user_id(user_id);
			ActionContext context = ActionContext.getContext();
			List proList = projectService.LoadProject(projectInfo);
			context.put("ProList", proList);
			return "list";
		} else {
			return "noPri";
		}
	}

	public void Add() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			projectInfo.setCreate_user_id(user_id);
			projectService.SaveInfo(projectInfo);
			SetJsonBaseContent(new Global().RegisterID("Project", projectInfo.getPro_id()));
			CallBackJSON();
		} else {
			SetJsonBaseErrorText("error.session.out");
			CallBackJSON();
		}
	}

	public void Save() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (user_id > 0 && id > 0) {
			Project project = projectService.LoadProById(id);
			project.setPro_name(projectInfo.getPro_name());
			project.setPro_des(projectInfo.getPro_des());
			project.setStart_pro_time(projectInfo.getStart_pro_time());
			project.setEnd_pro_time(projectInfo.getEnd_pro_time());
			project.setEstimated_day(projectInfo.getEstimated_day());
			project.setIs_open(projectInfo.getIs_open());
			projectService.Save(project);
			CallBackJSON();
		} else {
			SetJsonBaseErrorText("error.session.out");
			CallBackJSON();
		}
	}

	public void ChangeProStatus() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			Global global = new Global();
			int id = global.GetRegID("Project", projectInfo.getId());
			Project project = projectService.LoadProById(id);
			if (project.getCreate_user_id() == user_id) {
				int status = new DefineConfig().GetProStatusNextProcess(project.getPro_status());
				project.setPro_status(status);
				if (status == 200) {
					project.setEnd_time(new Date(System.currentTimeMillis()));
				} else {
					project.setEnd_time(null);
				}
				projectService.UpdateStatus(project);
				Success();
			} else {
				ErrorText("error.cannot.operation");
			}
		} else {
			ErrorText("error.session.out");
		}
	}

	public void Delete() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			Global global = new Global();
			int id = global.GetRegID("Project", projectInfo.getId());
			Project project = projectService.LoadProById(id);
			if (project.getCreate_user_id() == user_id) {
				projectService.DeleteProject(project.getPro_id());
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.cannot.operation");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.session.out");
			CallBackJSON();
		}
	}

	@SuppressWarnings("rawtypes")
	public String Users() {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (id > 0) {
			ActionContext context = ActionContext.getContext();
			List userList = projectService.LoadProUsers(id);
			Project project = projectService.LoadProById(id);
			context.put("Project", project);
			context.put("Users", userList);
			return "users";
		} else {
			return "noSession";
		}
	}

	@SuppressWarnings("rawtypes")
	public void AddRelUsers() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Project", projectInfo.getId());
		if (id > 0 && projectInfo.getUserIDs().length() > 0) {
			HttpSession session = ServletActionContext.getRequest().getSession();
			int user_id = (Integer) session.getAttribute("user_id");
			Project project = projectService.LoadProById(id);
			if(project.getCreate_user_id() != user_id){
				return;
			}
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void LoadListByAjax() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		List arrList = new ArrayList();
		if (user_id > 0) {
			if (projectInfo.getSearch() > 0) {
				if (session.getAttribute("ProjectSearch") != null) {
					int i = projectInfo.getCurrentPage();
					projectInfo = (ProjectInfo) session.getAttribute("ProjectSearch");
					projectInfo.setCurrentPage(i);
				} else {
					session.removeAttribute("ProjectSearch");
				}
			}
			projectInfo.setCreate_user_id(user_id);
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
		}
		SetJsonBaseContent(arrList);
		CallBackJSON();
	}

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

	public void SaveSearch() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("ProjectSearch", projectInfo);
		CallBackJSON();
	}

}