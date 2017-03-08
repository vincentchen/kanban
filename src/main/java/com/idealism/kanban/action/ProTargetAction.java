package com.idealism.kanban.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.idealism.kanban.model.ProTask;
import com.idealism.kanban.service.ProTargetService;
import com.idealism.kanban.service.UserManager;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.util.JxlUtil;
import com.idealism.kanban.vo.ProTargetInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("ProTargetAction")
public class ProTargetAction extends AjaxActionBase implements ModelDriven<ProTargetInfo> {
	private ProTargetInfo proTargetInfo = new ProTargetInfo();
	private ProTargetService proTargetService;
	private UserManager userManager;

	public ProTargetService getProTargetService() {
		return proTargetService;
	}

	@Resource
	public void setProTargetService(ProTargetService proTargetService) {
		this.proTargetService = proTargetService;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}


	@Override
	public ProTargetInfo getModel() {
		return proTargetInfo;
	}

	@SuppressWarnings("rawtypes")
	public String Task() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (proTargetInfo.getId() > 0 && user_id>0) {
			Global global = new Global();
			int id = global.GetRegID("ProTarget", proTargetInfo.getId());
			String type = proTargetInfo.getType();
			if (id > 0 && !type.equals("")) {
				ActionContext context = ActionContext.getContext();
				List task = proTargetService.LoadTask(id, type);
				ProTarget proTarget = proTargetService.LoadProTargetById(id);
				context.put("ProTarget", proTarget);
				int canOperation = proTargetService.checkCanOperation(id,user_id);
				context.put("ProTask", task);
				context.put("canOperation", canOperation);
				return "task";
			} else {
				return "noRead";
			}
		} else {
			return "noPri";
		}
	}

	public String Edit() {
		ActionContext context = ActionContext.getContext();
		if (proTargetInfo.getId() > 0) {
			Global global = new Global();
			int id = global.GetRegID("ProTarget", proTargetInfo.getId());
			if (id > 0) {
				ProTarget proTarget = proTargetService.LoadProTargetById(id);
				context.put("ProTarget", proTarget);
			}
		} else {
			context.put("ProTarget", proTargetInfo);
		}
		return "edit";
	}

	public void Save() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("ProTarget", proTargetInfo.getId());
		if (id > 0) {
			ProTarget protarget = proTargetService.LoadProTargetById(id);
			protarget.setTarget_name(proTargetInfo.getTarget_name());
			protarget.setTarget_des(proTargetInfo.getTarget_des());
			protarget.setUpdate_time(new Date(System.currentTimeMillis()));
			protarget.setStart_target_time(proTargetInfo.getStart_target_time());
			protarget.setEnd_target_time(proTargetInfo.getEnd_target_time());
			protarget.setEstimated_day(proTargetInfo.getEstimated_day());
			proTargetService.Save(protarget);
			if (protarget.getTarget_id() > 0) {
				SetJsonBaseContent(global.RegisterID("ProTarget", protarget.getTarget_id()));
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.save");
				CallBackJSON();
			}
		} else {
			ProTarget protarget = new ProTarget();
			int pro_id = global.GetRegID("Project", proTargetInfo.getProID());
			HttpSession session = ServletActionContext.getRequest().getSession();
			int user_id = (Integer) session.getAttribute("user_id");
			if (pro_id > 0 && user_id > 0) {
				protarget.setPro_id(pro_id);
				protarget.setTarget_name(proTargetInfo.getTarget_name());
				protarget.setTarget_des(proTargetInfo.getTarget_des());
				protarget.setCreate_time(new Date(System.currentTimeMillis()));
				protarget.setStart_target_time(proTargetInfo.getStart_target_time());
				protarget.setEnd_target_time(proTargetInfo.getEnd_target_time());
				protarget.setEstimated_day(proTargetInfo.getEstimated_day());
				protarget.setCreate_user_id(user_id);
				if (proTargetInfo.getImm_start() > 0) {
					protarget.setTarget_status(100);
					protarget.setStart_target_time(new Date(System.currentTimeMillis()));
					protarget.setStart_time(new Date(System.currentTimeMillis()));
				} else {
					protarget.setTarget_status(0);
				}
				proTargetService.Save(protarget);
				if (protarget.getTarget_id() > 0) {
					SetJsonBaseContent(global.RegisterID("ProTarget", protarget.getTarget_id()));
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
	}

	@SuppressWarnings("rawtypes")
	public String View() {
		Global global = new Global();
		int id = global.GetRegID("ProTarget", proTargetInfo.getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (id > 0 && user_id>0) {
			ProTarget protarget = proTargetService.LoadProTargetById(id);
			ActionContext context = ActionContext.getContext();
			int attention = userManager.LoadMsgConfig("protarget_send", user_id);
			List attentionList = proTargetService.LoadAttentionProtarget(user_id,id);
			int hasAttention = 0;
			if(attentionList.size()>0){
				hasAttention = 1;
			}
			int canOperation = proTargetService.checkCanOperation(id,user_id);
			context.put("canOperation", canOperation);
			context.put("Attention", attention);
			context.put("ProTarget", protarget);
			context.put("hasAttention", hasAttention);
			return "view";
		} else {
			return "noRead";
		}
	}

	@SuppressWarnings("rawtypes")
	public String Download() {
		Global global = new Global();
		int id = global.GetRegID("ProTarget", proTargetInfo.getId());
		if (id > 0) {
			ActionContext context = ActionContext.getContext();
			List files = proTargetService.LoadTargetFiles(id);
			ProTarget proTarget = proTargetService.LoadProTargetById(id);
			context.put("Files", files);
			context.put("TargetId", id);
			context.put("ProTarget", proTarget);
			return "download";
		} else {
			return "noRead";
		}
	}
	
	public void ChangeTargetStatus() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if(user_id>0){
			Global global = new Global();
			int id = global.GetRegID("ProTarget", proTargetInfo.getId());
			ProTarget protarget = proTargetService.LoadProTargetById(id);
			if(protarget.getCreate_user_id() == user_id){
				int status = new DefineConfig().GetTargetStatusNextProcess(protarget.getTarget_status());
				protarget.setTarget_status(status);
				if(status == 200){
					protarget.setEnd_time(new Date(System.currentTimeMillis()));
				}else {
					protarget.setEnd_time(null);
				}
				proTargetService.UpdateTargetStatus(protarget);
				Success();
			}else {
				ErrorText("error.cannot.operation");
			}
		}else {
			ErrorText("error.session.out");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String Graph() throws IOException{
		Global global = new Global();
		int id = global.GetRegID("ProTarget", proTargetInfo.getId());
		if(id>0){
			String referenceMain = "";//理论值提示字符串
			String remainderMain = "";//实际值提示字符串
			String referenceDetail = "";//理论详细值字符串
			String remainderDetail = "";//实际详细值字符串
			String ganttString = "";
			ProTarget protarget = proTargetService.LoadProTargetById(id);
			List graph = new ArrayList();
			graph.add(protarget);
			
			referenceMain = "'"+getText("target.start_target_time")+"','"+getText("target.end_target_time")+"'";
			remainderMain = "'"+getText("target.start_time")+"',";
			graph.add(referenceMain);
			
			List list = proTargetService.LoadTaskGraphList(id);
			int taskCnt = list.size();
			referenceDetail = "["+protarget.getStart_target_time().getTime()+","+taskCnt+"],["+protarget.getEnd_target_time().getTime()+",0]";
			graph.add(referenceDetail);
			SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(list.size()>0){
				remainderDetail = "["+protarget.getStart_time().getTime()+","+taskCnt+"],";
				Iterator it1 = list.iterator();
				while(it1.hasNext()){
					ProTask task = (ProTask) it1.next();
					if(task.getStatus_value() == 200){
						taskCnt--;
						remainderMain += "'#"+task.getTask_id()+"',";
						remainderDetail += "["+task.getEnd_time().getTime()+","+taskCnt+"],";
					}
					ganttString += "{'name':'#"+task.getTask_id()+"','desc':'','values':[{'from':'/Date("+task.getStart_task_time().getTime()+")/','to':'/Date("+task.getEnd_task_time().getTime()+")','desc':'"+DateFormat.format(task.getStart_task_time())+"-"+DateFormat.format(task.getEnd_task_time())+"<br />"+task.getTask_des()+"','label':'"+task.getTask_id()+"'}]},";
				}
			}
			
			if(referenceMain.length()>0){
				graph.add(remainderMain.substring(0,remainderMain.length()-1));
			}else {
				graph.add("");
			}
			if(remainderDetail.length()>0){
				graph.add(remainderDetail.substring(0,remainderDetail.length()-1));
			}else {
				graph.add("");
			}
			if(ganttString.length()>0){
				graph.add(ganttString.substring(0,ganttString.length()-1));
			}else {
				graph.add("");
			}
			ActionContext context = ActionContext.getContext();
			context.put("Graph", graph);
			return "graph";
		}else {
			return "noSession";
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String History(){
		Global global = new Global();
		int id = global.GetRegID("ProTarget", proTargetInfo.getId());
		if (id>0) {
			List list = proTargetService.LoadCurrentMonth(id);
			ActionContext context = ActionContext.getContext();
			context.put("History", list);
			return "history";
		}else {
			return "noSession";
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void DownLoadHistory() throws ParseException, RowsExceededException, WriteException, IOException {
		if (proTargetInfo.getHistory_min_time() != null && proTargetInfo.getHistory_max_time() != null) {
			Global global = new Global();
			int id = global.GetRegID("ProTarget", proTargetInfo.getId());
			if (id > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List proHistory = proTargetService.SearchProjectHistory(proTargetInfo.getHistory_min_time(), proTargetInfo.getHistory_max_time(),id);
				if(proHistory.size()>0){
					ActionContext context1 = ActionContext.getContext();
					HttpServletResponse response = (HttpServletResponse) context1.get(StrutsStatics.HTTP_RESPONSE);
					response.setContentType("application/vnd.ms-excel;charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment; filename=" + sdf.format(proTargetInfo.getHistory_min_time()) + "-" + sdf.format(proTargetInfo.getHistory_max_time()) + ".xls");
					JxlUtil excel = new JxlUtil();
					excel.CreateProjectHistoryExcel(proHistory, response.getOutputStream());
				}else {
					Error("<script>alert('empty');</script>");
				}
			} else {
				Error("<script>window.location.href='login.jsp'</script>");
			}
		}
	}
	
	public void AttentionProtarget() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int id = global.GetRegID("ProTarget", proTargetInfo.getId());
		if (id > 0 && user_id > 0) {
			proTargetService.SaveAttention(user_id,id);
			Success();
		}else {
			Error("error.session.out");
		}
	}
}
