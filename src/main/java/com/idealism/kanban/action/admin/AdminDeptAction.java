package com.idealism.kanban.action.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.Department;
import com.idealism.kanban.service.DeptManager;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.DepartmentInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("AdminDeptAction")
public class AdminDeptAction extends AjaxActionBase implements ModelDriven<DepartmentInfo>{
	private DepartmentInfo adminDeptInfo = new DepartmentInfo();
	private DeptManager deptManager;
	
	
	public DeptManager getDeptManager() {
		return deptManager;
	}

	@Resource
	public void setDeptManager(DeptManager deptManager) {
		this.deptManager = deptManager;
	}


	@Override
	public DepartmentInfo getModel() {
		return adminDeptInfo;
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "OrgInfoPri", privilegeValue = "4")
	public String List(){
		ActionContext context = ActionContext.getContext();
		List deptList = deptManager.LoadAllDept();
		context.put("DeptList", deptList);
		return "list";
	}
	
	@ConfigPrivilege(privilegeName = "OrgInfoPri", privilegeValue = "1")
	public void Add() throws IOException{
		Department department = new Department();
		Global global = new Global();
		int dept_id = global.GetRegID("Dept", adminDeptInfo.getParentID());
		if(dept_id>0){
			department.setParent_id(dept_id);
			department.setHas_child(0);
			department.setCreate_time(new Date(System.currentTimeMillis()));
			deptManager.AddChild(department);
		}else {
			department.setDept_name(adminDeptInfo.getDept_name());
			department.setCreate_time(new Date(System.currentTimeMillis()));
			deptManager.Save(department);
		}
		if(department.getDept_id()>0){
			SetJsonBaseContent(global.RegisterID("Dept", department.getDept_id()));
			CallBackJSON();
		}else {
			SetJsonBaseError("error.save");
			CallBackJSON();
		}
	}
	
	@ConfigPrivilege(privilegeName = "OrgInfoPri", privilegeValue = "3")
	public void Rename() throws IOException{
		Global global = new Global();
		int dept_id = global.GetRegID("Dept", adminDeptInfo.getId());
		if(dept_id>0){
			deptManager.UpdateName(dept_id,adminDeptInfo.getDept_name());
			CallBackJSON();
		}else {
			SetJsonBaseError("error.save");
			CallBackJSON();
		}
	}
	
	@ConfigPrivilege(privilegeName = "OrgInfoPri", privilegeValue = "2")
	public void Delete() throws IOException{
		Global global = new Global();
		int dept_id = global.GetRegID("Dept", adminDeptInfo.getId());
		if(dept_id>0){
			deptManager.Delete(dept_id);
			CallBackJSON();
		}else {
			SetJsonBaseError("error.save");
			CallBackJSON();
		}
	}
	
	@ConfigPrivilege(privilegeName = "OrgInfoPri", privilegeValue = "3")
	public void UpdateParent() throws IOException{
		Global global = new Global();
		int dept_id = global.GetRegID("Dept", adminDeptInfo.getId());
		int parent_id = 0;
		if(adminDeptInfo.getParentID()>0){
			parent_id = global.GetRegID("Dept", adminDeptInfo.getParentID());
		}
		if(dept_id>0){
			deptManager.UpdateParent(dept_id,parent_id);
			CallBackJSON();
		}else {
			SetJsonBaseError("error.save");
			CallBackJSON();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void SearchByAjax() throws IOException {
		List list = deptManager.SearchDept(adminDeptInfo);
		if (list.size() > 0) {
			String content = "<TABLE CLASS='gTab selectTable' style='padding:3px;'>";
			for (int i = 0; i < list.size(); i++) {
				DepartmentInfo info = (DepartmentInfo) list.get(i);
				content += "<TR><TD onclick='" + adminDeptInfo.getCallBackFunc() + "(" + info.getId() + ",\"" + info.getDept_name() + "\")'>" + info.getDept_name() + "</TD></TR>";
			}
			content += "</TABLE>";
			SetJsonBaseContent(content);
			CallBackJSON();
		} else {
			SetJsonBaseErrorText("error.nodept");
			CallBackJSON();
		}
	}
}
