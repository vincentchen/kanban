package com.idealism.kanban.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.stereotype.Controller;

import com.idealism.kanban.model.UserHistory;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.DeptManager;
import com.idealism.kanban.service.UserManager;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.util.FileUploadUtil;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.util.JxlUtil;
import com.idealism.kanban.vo.UserHistoryInfo;
import com.idealism.kanban.vo.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("UserAction")
public class UserAction extends AjaxActionBase implements ModelDriven<UserInfo> {
	private UserManager userManager;
	private DeptManager deptManager;

	private UserInfo userInfo = new UserInfo();

	public DeptManager getDeptManager() {
		return deptManager;
	}

	@Resource
	public void setDeptManager(DeptManager deptManager) {
		this.deptManager = deptManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public UserInfo getModel() {
		return userInfo;
	}

	@SuppressWarnings("rawtypes")
	public String InfoPanel() {
		ActionContext context = ActionContext.getContext();
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			Users users = userManager.LoadUserById(user_id);
			List RelDeptRole = userManager.LoadUserRelDeptAndRole(user_id);
			context.put("RelDeptRoleList", RelDeptRole);
			context.put("Users", users);
			return "panel";
		} else {
			return "noLogin";
		}
	}

	@SuppressWarnings("rawtypes")
	public String Setting() {
		ActionContext context = ActionContext.getContext();
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			Users users = userManager.LoadUserById(user_id);
			List list = userManager.LoadUserMsgConfig(user_id);
			context.put("ConfigText", users.getConfig_text());
			context.put("MsgConfig", list);
			return "setting";
		} else {
			return "noLogin";
		}
	}

	public void SaveSetting() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			int row = userManager.SaveUserSetting(user_id, userInfo.getConfig_text());
			if (row > 0) {
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.savephoto.fail");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.login.again");
			CallBackJSON();
		}
	}
	
	public void SaveMsgConfig() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			userManager.SaveMsgConfig(user_id,userInfo);
			Success();
		}else {
			Error("error.login.again");
		}
	}

	public void UploadPhoto() throws IOException {
		if (userInfo.getFile_photo() != null && userInfo.getFile_photoFileName() != null) {
			FileUploadUtil fileUploadUtil = new FileUploadUtil();
			fileUploadUtil.setUpfile(userInfo.getFile_photo());
			fileUploadUtil.setUpfileFileName(userInfo.getFile_photoFileName());
			String error = fileUploadUtil.SaveFile();
			if (error != null) {
				ErrorText(error);
			} else {
				String newPath = fileUploadUtil.getStoragePath();
				HttpSession session = ServletActionContext.getRequest().getSession();
				int user_id = (Integer) session.getAttribute("user_id");
				if (user_id > 0) {
					int row = userManager.UpdateUserPhoto(user_id, newPath);
					if (row > 0) {
						SetJsonBaseContent(newPath);
						CallBackJSON();
					} else {
						SetJsonBaseErrorText("error.savephoto.fail");
						CallBackJSON();
					}
				} else {
					SetJsonBaseErrorText("error.login.again");
					CallBackJSON();
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void TheMonthHistory() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			List list = userManager.TheMonthHistory(user_id);
			List listInfo = DataConversion(list);
			if (listInfo.size() > 0) {
				SetJsonBaseContent(listInfo);
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("msg.nocontent");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("msg.loginagain");
			CallBackJSON();
		}
	}

	@SuppressWarnings("rawtypes")
	public void DownLoadHistory() throws ParseException, RowsExceededException, WriteException, IOException {
		if (userInfo.getHistory_min_time() != "" && userInfo.getHistory_max_time() != "") {
			HttpSession session = ServletActionContext.getRequest().getSession();
			int user_id = (Integer) session.getAttribute("user_id");
			if (user_id > 0) {
				String user_name = (String) session.getAttribute("user_name");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				List userHistory = userManager.SearchUserHistory(sdf.parse(userInfo.getHistory_min_time()), sdf.parse(userInfo.getHistory_max_time()), user_id);
				List listInfo = DataConversion(userHistory);
				ActionContext context1 = ActionContext.getContext();
				HttpServletResponse response = (HttpServletResponse) context1.get(StrutsStatics.HTTP_RESPONSE);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + userInfo.getHistory_min_time() + "-" + userInfo.getHistory_max_time() + new String(user_name.getBytes("gbk"), "iso8859-1") + ".xls");
				JxlUtil excel = new JxlUtil();
				excel.CreateUserHistoryExcel(listInfo, response.getOutputStream());
			} else {
				Error("<script>window.location.href='login.jsp'</script>");
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List DataConversion(List list) {
		List historylist = new ArrayList();
		DefineConfig defineConfig = new DefineConfig();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HashMap historyTypeCnt = new HashMap();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				UserHistoryInfo userHistoryInfo = new UserHistoryInfo();
				UserHistory userHistory = (UserHistory) list.get(i);
				if (null != historyTypeCnt.get(userHistory.getHistory_type())) {
					int cnt = Integer.parseInt(String.valueOf(historyTypeCnt.get(userHistory.getHistory_type())));
					historyTypeCnt.put(userHistory.getHistory_type(), cnt++);
				} else {
					historyTypeCnt.put(userHistory.getHistory_type(), 1);
				}
				userHistoryInfo.setHistoryTypeName(defineConfig.UserHistoryType(userHistory.getHistory_type()));
				userHistoryInfo.setHistoryContent(userHistory.getHistory_content());
				userHistoryInfo.setHistoryLogTime(sdf.format(userHistory.getLog_time()));
				historylist.add(userHistoryInfo);
			}
			Iterator iterator = historyTypeCnt.entrySet().iterator();
			String cntString = "";
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				cntString += defineConfig.UserHistoryType(Integer.parseInt(String.valueOf(entry.getKey()))) + " : " + entry.getValue() + "  ";
			}
			historylist.add(cntString);
		}
		return historylist;
	}

	public void UpdateUserName() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			userInfo.setUser_id(user_id);
			int updateRow = userManager.UpdateUserName(userInfo);
			if (updateRow > 0) {
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.save");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.login.again");
			CallBackJSON();
		}
	}

	public void UpdateUserEmail() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (user_id > 0) {
			userInfo.setUser_id(user_id);
			int updateRow = userManager.UpdateUserEmail(userInfo);
			if (updateRow > 0) {
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.save");
				CallBackJSON();
			}
		} else {
			SetJsonBaseErrorText("error.login.again");
			CallBackJSON();
		}
	}

	@SuppressWarnings("rawtypes")
	public void SearchByAjax() throws IOException {
		List list = userManager.SearchUsers(userInfo);
		Global global = new Global();
		if (list.size() > 0) {
			String content = "<TABLE CLASS='gTab selectTable' style='padding:3px;'>";
			for (int i = 0; i < list.size(); i++) {
				UserInfo info = (UserInfo) list.get(i);
				content += "<TR><TD onclick='" + userInfo.getCallBackFunc() + "(" + global.RegisterID("UserID", info.getUser_id()) + ",\"" + info.getUser_name() + "\")'>" + info.getUser_name() + "</TD></TR>";
			}
			content += "</TABLE>";
			SetJsonBaseContent(content);
			CallBackJSON();
		} else {
			SetJsonBaseErrorText("error.noperson");
			CallBackJSON();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String Search(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if(user_id>0){
			ActionContext context = ActionContext.getContext();
			List deptList = deptManager.LoadAllDept();
			context.put("Info", userInfo);
			context.put("DeptList", deptList);
			return "search";
		}else {
			return "noLogin";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void LoadUsersByDept() throws IOException{
		Global global = new Global();
		int dept_id = global.GetRegID("Dept", userInfo.getId());
		if(dept_id>0){
			List users = userManager.LoadUsersByDept(dept_id);
			List userList = new ArrayList();
			if(userInfo.getUsersSelectType().equals("checkbox")){
				HashMap map = UsersSelectList(userInfo.getUsersSelectList());
				Iterator it = users.iterator();
				while (it.hasNext()) {
					List list = new ArrayList();
					Object[] usersObj = (Object[]) it.next();
					int user_id = Integer.parseInt(usersObj[0].toString());
					if(map.get(user_id) != null){
						list.add(map.get(user_id));
					}else {
						list.add(global.RegisterID("UserID", user_id));
					}
					list.add(usersObj[1]);
					list.add(usersObj[2]);
					userList.add(list);
				}
			}else {
				HashMap map = UsersSelectList(userInfo.getUsersSelectList());
				Iterator it = users.iterator();
				while (it.hasNext()) {
					List list = new ArrayList();
					Object[] usersObj = (Object[]) it.next();
					int user_id = Integer.parseInt(usersObj[0].toString());
					if(map.get(user_id) != null){
						list.add(map.get(user_id));
					}else {
						list.add(global.RegisterID("UserID", user_id));
					}
					list.add(usersObj[1]);
					list.add(usersObj[2]);
					userList.add(list);
				}
			}
			SetJsonBaseContent(userList);
			CallBackJSON();
		}else {
			SetJsonBaseErrorText("error.session.out");
			CallBackJSON();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void LoadUsersByCondition() throws IOException{
		List list = userManager.SearchUsers(userInfo);
		Global global = new Global();
		if (list.size() > 0) {
			List userList = new ArrayList();
			if(userInfo.getUsersSelectType().equals("checkbox")){
				HashMap map = UsersSelectList(userInfo.getUsersSelectList());
				Iterator it = list.iterator();
				while (it.hasNext()) {
					List n = new ArrayList();
					UserInfo usersObj = (UserInfo) it.next();
					int user_id = usersObj.getUser_id();
					if(map.get(user_id) != null){
						n.add(map.get(user_id));
					}else {
						n.add(global.RegisterID("UserID", user_id));
					}
					n.add(usersObj.getUser_name());
					n.add(usersObj.getUser_photo());
					userList.add(n);
				}
			}else {
				HashMap map = UsersSelectList(userInfo.getUsersSelectList());
				Iterator it = list.iterator();
				while (it.hasNext()) {
					List n = new ArrayList();
					UserInfo usersObj = (UserInfo) it.next();
					int user_id = usersObj.getUser_id();
					if(map.get(user_id) != null){
						n.add(map.get(user_id));
					}else {
						n.add(global.RegisterID("UserID", user_id));
					}
					n.add(usersObj.getUser_name());
					n.add(usersObj.getUser_photo());
					userList.add(n);
				}
			}
			SetJsonBaseContent(userList);
			CallBackJSON();
		} else {
			SetJsonBaseErrorText("error.noperson");
			CallBackJSON();
		}
	}
	
	public HashMap<Integer, Integer> UsersSelectList(String usersList){
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		String a[]= usersList.split(",");
		Global global = new Global();
		for(int i=0;i<a.length;i++){
			if(a[i] != null && !a[i].equals("")){
				String b[] = a[i].split(":");
				int userID = Integer.parseInt(b[0].substring(1));
				map.put(global.GetRegID("UserID", userID), userID);
			}
		}
		return map;
	}
	
}
