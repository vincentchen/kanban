package com.idealism.kanban.action.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.idealism.kanban.model.Role;
import com.idealism.kanban.service.RoleService;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.DepartmentInfo;
import com.idealism.kanban.vo.RoleInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class AdminRoleAction extends AjaxActionBase implements ModelDriven<RoleInfo> {
	RoleInfo roleInfo = new RoleInfo();
	RoleService roleService;

	@Override
	public RoleInfo getModel() {
		return roleInfo;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@ConfigPrivilege(privilegeName = "RoleInfoPri", privilegeValue = "4")
	public String List(){
		ActionContext context = ActionContext.getContext();
		context.put("RoleList", roleService.LoadAllRole());
		return "list";
	}

	@ConfigPrivilege(privilegeName = "RoleInfoPri", privilegeValue = "1")
	public void Add() throws IOException{
		Role role = new Role();
		Global global = new Global();
		int role_id = global.GetRegID("Role", roleInfo.getParentID());
		if(role_id>0){
			role.setParent_id(role_id);
		}else {
			role.setRole_name(roleInfo.getRole_name());
		}
		roleService.Save(role);
		if(role.getRole_id()>0){
			SetJsonBaseContent(global.RegisterID("Role", role.getRole_id()));
			CallBackJSON();
		}else {
			SetJsonBaseError("error.save");
			CallBackJSON();
		}
	}
	
	@ConfigPrivilege(privilegeName = "RoleInfoPri", privilegeValue = "3")
	public void Rename() throws IOException{
		Global global = new Global();
		int role_id = global.GetRegID("Role", roleInfo.getId());
		if(role_id>0){
			roleService.UpdateName(role_id,roleInfo.getRole_name());
			CallBackJSON();
		}else {
			SetJsonBaseError("error.save");
			CallBackJSON();
		}
	}
	
	@ConfigPrivilege(privilegeName = "RoleInfoPri", privilegeValue = "2")
	public void Delete() throws IOException{
		Global global = new Global();
		int role_id = global.GetRegID("Role", roleInfo.getId());
		if(role_id>0){
			roleService.Delete(role_id);
			CallBackJSON();
		}else {
			SetJsonBaseError("error.save");
			CallBackJSON();
		}
	}
	
	@ConfigPrivilege(privilegeName = "RoleInfoPri", privilegeValue = "3")
	public void UpdateParent() throws IOException{
		Global global = new Global();
		int role_id = global.GetRegID("Role", roleInfo.getId());
		int parent_id = 0;
		if(roleInfo.getParentID()>0){
			parent_id = global.GetRegID("Role", roleInfo.getParentID());
		}
		if(role_id>0){
			roleService.UpdateParent(role_id,parent_id);
			CallBackJSON();
		}else {
			SetJsonBaseError("error.save");
			CallBackJSON();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "RoleInfoPri", privilegeValue = "3")
	public String Pri(){
		Global global = new Global();
		int role_id = global.GetRegID("Role", roleInfo.getId());
		if(role_id>0){
			Role role = roleService.LoadRoleById(role_id);
			java.util.List list = roleService.LoadPriByRoleId(role_id);
			ActionContext context = ActionContext.getContext();
			context.put("Pri", list);
			context.put("Role", role);
			return "pri";
		}else {
			return "noSession";
		}
	}
	
	@ConfigPrivilege(privilegeName = "RoleInfoPri", privilegeValue = "3")
	public void PriSave() throws IOException{
		Global global = new Global();
		int role_id = global.GetRegID("Role", roleInfo.getId());
		if(role_id>0){
			roleService.UpdatePriByRoleId(role_id,roleInfo.getPriString(),roleInfo.getIs_cover());
			Success();
		}else {
			Error("error.save");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void SearchByAjax() throws IOException {
		List list = roleService.SearchRole(roleInfo);
		if (list.size() > 0) {
			String content = "<TABLE CLASS='gTab selectTable' style='padding:3px;'>";
			for (int i = 0; i < list.size(); i++) {
				RoleInfo info = (RoleInfo) list.get(i);
				content += "<TR><TD onclick='" + roleInfo.getCallBackFunc() + "(" + info.getId() + ",\"" + info.getRole_name() + "\")'>" + info.getRole_name() + "</TD></TR>";
			}
			content += "</TABLE>";
			SetJsonBaseContent(content);
			CallBackJSON();
		} else {
			SetJsonBaseErrorText("error.norole");
			CallBackJSON();
		}
	}
}
