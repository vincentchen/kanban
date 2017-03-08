package com.idealism.kanban.action.admin;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.Privilege;
import com.idealism.kanban.service.PrivilegeService;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.AdminPrivilegeInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("AdminPrivilegeAction")
public class AdminPrivilegeAction extends AjaxActionBase implements ModelDriven<AdminPrivilegeInfo>{
	AdminPrivilegeInfo adminPrivilegeInfo = new AdminPrivilegeInfo();
	PrivilegeService privilegeService;
	@Override
	public AdminPrivilegeInfo getModel() {
		return adminPrivilegeInfo;
	}
	
	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	@Resource
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	@ConfigPrivilege(privilegeName = "PriInfo", privilegeValue = "4")
	public String List(){
		ActionContext context = ActionContext.getContext();
		context.put("Privilege", privilegeService.LoadAll());
		return "list";
	}
	
	@ConfigPrivilege(privilegeName = "PriConfig", privilegeValue = "4")
	public String Custom(){
		ActionContext context = ActionContext.getContext();
		context.put("Privilege", privilegeService.LoadCustomPri());
		return "custom";
	}
	
	@ConfigPrivilege(privilegeName = "PriInfo", privilegeValue = "3")
	public String Edit(){
		Global global = new Global();
		int id = global.GetRegID("Privilege", adminPrivilegeInfo.getId());
		ActionContext context = ActionContext.getContext();
		if(id>0){
			context.put("Privilege", privilegeService.LoadPriByID(id));
		}else {
			context.put("Privilege",adminPrivilegeInfo);
		}
		return "edit";
	}
	
	@ConfigPrivilege(privilegeName = "PriConfig", privilegeValue = "3")
	public void Save() throws IOException{
		Global global = new Global();
		if(adminPrivilegeInfo.getPrivilege_define().equals("") || adminPrivilegeInfo.getPrivilege_name().equals("")){
			Error("error.required.null");
			return;
		}
		int checkCanSave = privilegeService.CheckCanSave(adminPrivilegeInfo.getPrivilege_id(),adminPrivilegeInfo.getPrivilege_define());
		if(checkCanSave == 0){
			Error("error.pri_define.repeat");
			return;
		}
		int id = global.GetRegID("Privilege", adminPrivilegeInfo.getId());
		if(id>0){
			Privilege p = new Privilege();
			p.setPrivilege_id(adminPrivilegeInfo.getPrivilege_id());
			p.setPrivilege_name(adminPrivilegeInfo.getPrivilege_name());
			p.setPrivilege_dec(adminPrivilegeInfo.getPrivilege_dec());
			p.setPrivilege_define(adminPrivilegeInfo.getPrivilege_define());
			privilegeService.Save(p);
			Success();
		}else {
			int max_id = privilegeService.LoadMaxID();
			Privilege p = new Privilege();
			if(max_id<10000){
				p.setPrivilege_id(10000);
			}
			p.setPrivilege_name(adminPrivilegeInfo.getPrivilege_name());
			p.setPrivilege_dec(adminPrivilegeInfo.getPrivilege_dec());
			p.setPrivilege_define(adminPrivilegeInfo.getPrivilege_define());
			privilegeService.InsertPri(p);
			Success();
		}
	}
}
