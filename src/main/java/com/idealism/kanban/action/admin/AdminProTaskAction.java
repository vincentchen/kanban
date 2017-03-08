package com.idealism.kanban.action.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.ProTask;
import com.idealism.kanban.service.ProTaskService;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.ProTaskInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("AdminProTaskAction")
public class AdminProTaskAction extends AjaxActionBase implements ModelDriven<ProTaskInfo> {
	private ProTaskInfo proTaskInfo = new ProTaskInfo();
	private ProTaskService proTaskService;
	Global global = new Global();
	
	public ProTaskService getToDoService() {
		return proTaskService;
	}

	@Resource
	public void setToDoService(ProTaskService proTaskService) {
		this.proTaskService = proTaskService;
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String View() {
		int id = global.GetRegID("ProTask", proTaskInfo.getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (id > 0 && user_id>0) {
			ActionContext context = ActionContext.getContext();
			ProTaskInfo proTask = proTaskService.LoadProTaskByIdWithInfo(id);
			context.put("ProTask", proTask);
			context.put("info", proTaskInfo);
			return "view";
		} else {
			return "noRead";
		}
	}
	
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void Receive() throws IOException {
		int id = global.GetRegID("ProTask", proTaskInfo.getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (id > 0 && user_id > 0) {
			Boolean cando = proTaskService.CheckCanReceive(id,user_id);
			if (cando) {
				int updateRow = proTaskService.UpdateReceive(id, user_id);
				if (updateRow > 0) {
					CallBackJSON();
				} else {
					SetJsonBaseErrorText("error.protask.nosave.receive");
					CallBackJSON();
				}
			} else {
				SetJsonBaseErrorText("error.protask.cannot.receive");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.login.again");
			CallBackJSON();
		}
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void ForcedReceive() throws IOException {
		int id = global.GetRegID("ProTask", proTaskInfo.getId());
		int user_id = global.GetRegID("UserID", proTaskInfo.getUser_id());
		if (id > 0 && user_id > 0) {
			Boolean cando = proTaskService.CheckCanReceive(id,user_id);
			if (cando) {
				int updateRow = proTaskService.UpdateReceive(id, user_id);
				if (updateRow > 0) {
					CallBackJSON();
				} else {
					SetJsonBaseErrorText("error.protask.nosave.receive");
					CallBackJSON();
				}
			} else {
				SetJsonBaseErrorText("error.protask.cannot.receive");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.login.again");
			CallBackJSON();
		}
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void Block() throws IOException {
		int id = global.GetRegID("ProTask", proTaskInfo.getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (id > 0 && user_id > 0) {
			Boolean cando = proTaskService.CheckCanOperation(id, user_id);
			if (cando) {
				int updateRow = proTaskService.UpdateBlock(id, user_id);
				if (updateRow > 0) {
					CallBackJSON();
				} else {
					SetJsonBaseErrorText("error.protask.nosave.receive");
					CallBackJSON();
				}
			} else {
				SetJsonBaseErrorText("error.cannot.operation");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.login.again");
			CallBackJSON();
		}
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void Finish() throws IOException {
		int id = global.GetRegID("ProTask", proTaskInfo.getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (id > 0 && user_id > 0) {
			Boolean cando = proTaskService.CheckCanOperation(id, user_id);
			if (cando) {
				int updateRow = proTaskService.UpdateFinish(id, user_id);
				if (updateRow > 0) {
					CallBackJSON();
				} else {
					SetJsonBaseErrorText("error.protask.nosave.receive");
					CallBackJSON();
				}
			} else {
				SetJsonBaseErrorText("error.cannot.operation");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.login.again");
			CallBackJSON();
		}
	}

	@Override
	public ProTaskInfo getModel() {
		return proTaskInfo;
	}
	
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public String Edit(){
		ActionContext context = ActionContext.getContext();
		if (proTaskInfo.getId() > 0) {
			int id = global.GetRegID("ProTask", proTaskInfo.getId());
			if (id > 0) {
				ProTask proTask = proTaskService.LoadProTaskById(id);
				context.put("ProTask", proTask);
			}
		} else {
			int target_id = global.GetRegID("ProTarget", proTaskInfo.getTargetID());
			List userList = proTaskService.LoadRelUsersByTargetId(target_id);
			context.put("UserList", userList);
			context.put("ProTask", proTaskInfo);
		}
		return "edit";
	}

	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "3")
	public void Save() throws IOException {
		int id = global.GetRegID("ProTask", proTaskInfo.getId());
		if (id > 0) {
			ProTask proTask = proTaskService.LoadProTaskById(id);
			proTask.setTask_des(proTaskInfo.getTask_des());
			proTask.setStart_task_time(proTaskInfo.getStart_task_time());
			proTask.setEnd_task_time(proTaskInfo.getEnd_task_time());
			proTask.setEstimated_hour(proTaskInfo.getEstimated_hour());
			proTask.setUrgent_degree(proTaskInfo.getUrgent_degree());
			proTaskService.Save(proTask);
			if(proTask.getTask_id()>0){
				CallBackJSON();
			}else {
				SetJsonBaseErrorText("error.save");
				CallBackJSON();
			}
		} else {
			ProTask proTask = new ProTask();
			int target_id = global.GetRegID("ProTarget", proTaskInfo.getTargetID());
			HttpSession session = ServletActionContext.getRequest().getSession();
			int user_id = (Integer) session.getAttribute("user_id");
			if(target_id>0 && user_id>0){
				proTask.setTarget_id(target_id);
				proTask.setTask_des(proTaskInfo.getTask_des());
				proTask.setCreate_time(new Date(System.currentTimeMillis()));
				proTask.setCreate_user_id(user_id);
				proTask.setStart_task_time(proTaskInfo.getStart_task_time());
				proTask.setEnd_task_time(proTaskInfo.getEnd_task_time());
				proTask.setEstimated_hour(proTaskInfo.getEstimated_hour());
				proTask.setUrgent_degree(proTaskInfo.getUrgent_degree());
				if(proTaskInfo.getReceive_task_user()>0){
					int receive_task_user_id = global.GetRegID("UserID", proTaskInfo.getReceive_task_user());
					if(receive_task_user_id>0){
						proTask.setReceive_task_user(receive_task_user_id);
						proTask.setStatus_value(100);
					}else{
						proTask.setStatus_value(0);
					}
				} else {
					proTask.setStatus_value(0);
				}
				proTaskService.Save(proTask);
				if(proTask.getTask_id()>0){
					CallBackJSON();
				}else {
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
	@ConfigPrivilege(privilegeName = "ProPri", privilegeValue = "4")
	public String Download() {
		int id = global.GetRegID("ProTask", proTaskInfo.getId());
		if (id > 0) {
			ActionContext context = ActionContext.getContext();
			List files = proTaskService.LoadTaskFiles(id);
			context.put("Files", files);
			context.put("TaskID", id);
			return "download";
		} else {
			return "noRead";
		}
	}
}
