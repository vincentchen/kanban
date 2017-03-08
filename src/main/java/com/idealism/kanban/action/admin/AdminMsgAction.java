package com.idealism.kanban.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.Msg;
import com.idealism.kanban.service.MsgService;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.MsgInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("AdminMsgAction")
public class AdminMsgAction extends AjaxActionBase implements ModelDriven<MsgInfo> {
	private MsgInfo msgInfo = new MsgInfo();
	private MsgService msgService;

	@Override
	public MsgInfo getModel() {
		return msgInfo;
	}

	public MsgService getMsgService() {
		return msgService;
	}

	@Resource
	public void setMsgService(MsgService msgService) {
		this.msgService = msgService;
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "MsgInfo", privilegeValue = "4")
	public String List() {
		ActionContext context = ActionContext.getContext();
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(msgInfo.getSearch()>0){
			if(session.getAttribute("MsgSearch") != null){
				msgInfo = (MsgInfo)session.getAttribute("MsgSearch");
			}else {
				session.removeAttribute("MsgSearch");
			}
		}else {
			session.removeAttribute("MsgSearch");
		}
		List msgList = msgService.LoadMsgList(msgInfo);
		context.put("MsgList", msgList);
		context.put("MsgInfo", msgInfo);
		return "list";
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "MsgInfo", privilegeValue = "1")
	public void Add() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		String[] ary = msgInfo.getUserIdList().split(",");
		Global global = new Global();
		List<String> userList = Arrays.asList(ary);
		if (user_id > 0 && userList.size() > 0 && !msgInfo.getMsg_title().equals("")) {
			Msg msg = new Msg();
			msg.setMsg_title(msgInfo.getMsg_title());
			msg.setMsg_content(msgInfo.getMsg_content());
			msg.setSrc_type(4);// 4:用户消息
			msg.setCreate_time(new Date(System.currentTimeMillis()));
			msg.setCreate_user_id(user_id);
			List userIDs = global.GetRegIDs("UserID", userList);
			if (userIDs.size() > 0) {
				msgService.Save(msg, userIDs);
				if (msg.getMsg_id() > 0) {
					Success();
				} else {
					ErrorText("error.save");
				}
			} else {
				ErrorText("error.select.user");
			}
		} else {
			ErrorText("error.session.out");
		}
	}
	
	@ConfigPrivilege(privilegeName = "MsgInfo", privilegeValue = "4")
	public void Redirect() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int msg_id = global.GetRegID("Msg", msgInfo.getId());
		if(user_id > 0 && msg_id>0){
			MsgInfo msg = msgService.LoadMsgById(msg_id);
			msgService.UpdateRead(msg_id,user_id);
			SetJsonBaseContent("Admin"+msg.getSrc_url());
			CallBackJSON();
		}else {
			SetJsonBaseErrorText("error.session.out");
			CallBackJSON();
		}
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "MsgInfo", privilegeValue = "2")
	public void Delete() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if(msgInfo.getMsgIDs().length() <= 0){
			ErrorText("select.no");
		}
		String[] ary = msgInfo.getMsgIDs().split(",");
		List<String> msgList = Arrays.asList(ary);
		if (msgList.size() > 0 && user_id > 0) {
			Global global = new Global();
			List msgIDs = global.GetRegIDs("Msg", msgList);
			if (msgIDs.size() > 0) {
				msgService.DeleteUser(user_id, msgIDs);
				Success();
			} else {
				ErrorText("error.session.out");
			}
		}
	}

	@ConfigPrivilege(privilegeName = "MsgInfo", privilegeValue = "4")
	public String View() {
		Global global = new Global();
		int msg_id = global.GetRegID("Msg", msgInfo.getId());
		if (msg_id > 0) {
			ActionContext context = ActionContext.getContext();
			MsgInfo msgInfo = msgService.LoadMsgById(msg_id);
			context.put("MsgInfo", msgInfo);
			return "view";
		} else {
			return "noLogin";
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConfigPrivilege(privilegeName = "MsgInfo", privilegeValue = "4")
	public void LoadListByAjax() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (msgInfo.getSearch() > 0) {
			if (session.getAttribute("MsgSearch") != null) {
				int i = msgInfo.getCurrentPage();
				msgInfo = (MsgInfo) session.getAttribute("MsgSearch");
				msgInfo.setCurrentPage(i);
			} else {
				session.removeAttribute("MsgSearch");
			}
		}
		List msgList = msgService.LoadMsgList(msgInfo);
		ArrayList list = new ArrayList();
		Iterator it1 = msgList.iterator();
		while (it1.hasNext()) {
			MsgInfo info = (MsgInfo) it1.next();
			List arr = new ArrayList();
			arr.add(info.getId());
			arr.add(info.getSrc_type_name());
			arr.add(info.getMsg_title());
			arr.add(info.getCreate_time_str());
			arr.add(info.getMsg_content());
			list.add(arr);
		}
		SetJsonBaseContent(list);
		CallBackJSON();
	}

	@ConfigPrivilege(privilegeName = "MsgInfo", privilegeValue = "4")
	public String Search() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		ActionContext context = ActionContext.getContext();
		if (session.getAttribute("MsgSearch") != null) {
			context.put("Info", (MsgInfo) session.getAttribute("MsgSearch"));
		} else {
			context.put("Info", msgInfo);
		}
		return "search";
	}

	@ConfigPrivilege(privilegeName = "MsgInfo", privilegeValue = "4")
	public void SaveSearch() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("MsgSearch", msgInfo);
		CallBackJSON();
	}
	
	@ConfigPrivilege(privilegeName = "MsgBroadcast", privilegeValue = "4")
	public String Broadcast(){
		
		return "broadcast";
	}

}
