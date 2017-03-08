package com.idealism.kanban.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.idealism.kanban.service.ServerConfigChild;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.vo.ServerInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("AdminServiceAction")
public class AdminServerAction extends AjaxActionBase implements ModelDriven<ServerInfo>{
	ServerInfo serviceInfo = new ServerInfo();
	ServerConfigChild sChild;
	
	public ServerInfo getModel() {
		return serviceInfo;
	}
	
	public ServerConfigChild getsChild() {
		return sChild;
	}

	@Resource
	public void setsChild(ServerConfigChild sChild) {
		this.sChild = sChild;
	}

	@ConfigPrivilege(privilegeName = "ServerPri", privilegeValue = "4")
	public String Status(){
		
		return "status";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConfigPrivilege(privilegeName = "PluginPri", privilegeValue = "4")
	public String Plugin(){
		ActionContext context = ActionContext.getContext();
		List list = sChild.LoadAllPlugin();
		Iterator it = list.iterator();
		List pluginsList = new ArrayList();
		while (it.hasNext()) {
			Object[] type = (Object[]) it.next();
			List rowList = new ArrayList();
			rowList.add(type[0]);
			rowList.add(type[1]);
			rowList.add((Integer)type[2]);
			rowList.add(getText("plugin."+(String) type[0]));
			rowList.add(getText("plugin."+(String) type[1]));
			pluginsList.add(rowList);
		}
		context.put("Plugins", pluginsList);
		return "plugin";
	}
	
	@ConfigPrivilege(privilegeName = "PluginPri", privilegeValue = "3")
	public void PluginSave() throws IOException{
		String configString = serviceInfo.getConfigString();
		String[] arr = configString.split(",");
		try {
			sChild.SavePlugin(arr);
			Success();
		} catch (Exception e) {
			ErrorText("error.save");
		}
	}
}
