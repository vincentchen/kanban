package com.idealism.kanban.action.admin;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.idealism.kanban.service.UserManager;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.RelDeptUserInfo;
import com.opensymphony.xwork2.ModelDriven;


@Controller("AdminRelDeptUser")
public class AdminRelDeptUser extends AjaxActionBase implements ModelDriven<RelDeptUserInfo>{
	private RelDeptUserInfo relDeptUserInfo = new RelDeptUserInfo();
	private UserManager userManager;
	
	Global global = new Global();
	
	public UserManager getUserManager() {
		return userManager;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public RelDeptUserInfo getModel() {
		return relDeptUserInfo;
	}
	
	@ConfigPrivilege(privilegeName = "UserInfoPri", privilegeValue = "3")
	public void InsertRelDeptUser() throws IOException{
		int user_id = global.GetRegID("UserID", relDeptUserInfo.getUser_id());
		int dept_id = global.GetRegID("Dept", relDeptUserInfo.getDept_id());
		int role_id = 0;
		if(relDeptUserInfo.getRole_id()>0){
			role_id = global.GetRegID("Role", relDeptUserInfo.getRole_id());
		}
		if(user_id>0 && dept_id>0){
			userManager.InsertRelDeptUser(user_id,dept_id,role_id);
			Success();
		}else {
			ErrorText("error.save");
		}
	}
	
	public void DeleteRelDeptUser() throws IOException{
		int rel_id = global.GetRegID("RelID", relDeptUserInfo.getRel_id());
		if(rel_id>0){
			userManager.DeleteRelDeptUser(rel_id);
			Success();
		}else {
			ErrorText("error.save");
		}
	}
}