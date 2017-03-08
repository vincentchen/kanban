package com.idealism.kanban.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.idealism.kanban.model.Idea;
import com.idealism.kanban.service.IdeaService;
import com.idealism.kanban.service.ServerConfigChild;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.IdeaInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class AdminIdeaAction extends AjaxActionBase implements ModelDriven<IdeaInfo> {
	IdeaInfo ideaInfo = new IdeaInfo();
	private IdeaService ideaService;
	private ServerConfigChild sConfigChild;

	public IdeaService getIdeaService() {
		return ideaService;
	}

	@Resource
	public void setIdeaService(IdeaService ideaService) {
		this.ideaService = ideaService;
	}
	
	public ServerConfigChild getsConfigChild() {
		return sConfigChild;
	}

	@Resource
	public void setsConfigChild(ServerConfigChild sConfigChild) {
		this.sConfigChild = sConfigChild;
	}


	@Override
	public IdeaInfo getModel() {
		return ideaInfo;
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "4")
	public String List() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (ideaInfo.getSearch() > 0) {
			if (session.getAttribute("IdeaSearch") != null) {
				ideaInfo = (IdeaInfo) session.getAttribute("IdeaSearch");
			} else {
				session.removeAttribute("IdeaSearch");
			}
		} else {
			session.removeAttribute("IdeaSearch");
		}
		List ideaList = ideaService.LoadAdminIdeaList(ideaInfo);
		ActionContext context = ActionContext.getContext();
		context.put("Audit",sConfigChild.LoadConfigByFieldName("IdeaAudit"));
		context.put("IdeaInfo", ideaList);
		return "list";
	}

	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "3")
	public String Edit() {
		ActionContext context = ActionContext.getContext();
		Global global = new Global();
		int id = global.GetRegID("Idea", ideaInfo.getId());
		if (id > 0) {
			Idea idea = ideaService.LoadIdeaById(id);
			context.put("Info", idea);
		} else {
			context.put("Info", ideaInfo);
		}
		return "edit";
	}

	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "3")
	public void Save() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Idea", ideaInfo.getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (id > 0 && user_id > 0) {
			Idea idea = ideaService.LoadIdeaById(id);
			idea.setIdea_title(ideaInfo.getIdea_title());
			idea.setIdea_content(ideaInfo.getIdea_content());
			idea.setIs_anonymous(ideaInfo.getIs_anonymous());
			if (ideaInfo.getIs_anonymous() == 1 || ideaInfo.getIs_anonymous() == 0) {
				idea.setCreate_user_id(user_id);
			} else {
				idea.setCreate_user_id(0);
			}
			ideaService.Save(idea);
			if (idea.getIdea_id() > 0) {
				SetJsonBaseContent(global.RegisterID("Idea", idea.getIdea_id()));
				CallBackJSON();
			} else {
				SetJsonBaseErrorText("error.save");
				CallBackJSON();
			}
		} else if (user_id > 0) {
			Idea idea = new Idea();
			idea.setIdea_title(ideaInfo.getIdea_title());
			idea.setIdea_content(ideaInfo.getIdea_content());
			idea.setCreate_time(new Date(System.currentTimeMillis()));
			if (ideaInfo.getIs_anonymous() == 1 || ideaInfo.getIs_anonymous() == 0) {
				idea.setCreate_user_id(user_id);
			}
			idea.setIs_anonymous(ideaInfo.getIs_anonymous());
			ideaService.Save(idea);
			if (idea.getIdea_id() > 0) {
				SetJsonBaseContent(global.RegisterID("Idea", idea.getIdea_id()));
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

	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "4")
	public String View() {
		Global global = new Global();
		int id = global.GetRegID("Idea", ideaInfo.getId());
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		if (id > 0 && user_id>0) {
			ideaService.UpdateReadStatus(id,user_id);
			ActionContext context = ActionContext.getContext();
			Idea idea = ideaService.LoadIdeaById(id);
			String author = "";
			if(idea.getCreate_user_id()>0 && idea.getIs_anonymous() == 0){
				author = ideaService.LoadUserNameById(idea.getCreate_user_id());
			}
			
			context.put("Idea", idea);
			context.put("Author", author);
			context.put("Audit",sConfigChild.LoadConfigByFieldName("IdeaAudit"));
			context.put("HasAttach", ideaService.HasAttach(id));
			context.put("HasChoose", ideaService.HasChoose(id, user_id));
			return "view";
		} else {
			return "noRead";
		}
	}

	@SuppressWarnings("rawtypes")
	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "4")
	public String Download() {
		Global global = new Global();
		int id = global.GetRegID("Idea", ideaInfo.getId());
		if (id > 0) {
			ActionContext context = ActionContext.getContext();
			List files = ideaService.LoadIdeaFiles(id);
			context.put("Files", files);
			context.put("IdeaId", id);
			return "download";
		} else {
			return "noRead";
		}
	}

	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "4")
	public String Search() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		ActionContext context = ActionContext.getContext();
		if (session.getAttribute("IdeaSearch") != null) {
			context.put("Info", (IdeaInfo) session.getAttribute("IdeaSearch"));
		} else {
			context.put("Info", ideaInfo);
		}
		return "search";
	}

	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "4")
	public void SaveSearch() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("IdeaSearch", ideaInfo);
		CallBackJSON();
	}

	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "3")
	public void SaveVoteByUser() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		Global global = new Global();
		int id = global.GetRegID("Idea", ideaInfo.getId());
		if (user_id > 0 && id > 0) {
			ideaService.SaveVoteByUser(id, user_id, ideaInfo.getChoose());
			Success();
		} else {
			ErrorText("error.session.out");
		}
	}

	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "2")
	public void Delete() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Idea", ideaInfo.getId());
		if (id > 0) {
			ideaService.Delete(id);
			Success();
		} else {
			ErrorText("error.session.out");
		}
	}

	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "3")
	public void Audit() throws IOException {
		Global global = new Global();
		int id = global.GetRegID("Idea", ideaInfo.getId());
		if (id > 0) {
			ideaService.UpdateByAuditIdea(id);
			Success();
		} else {
			ErrorText("error.session.out");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConfigPrivilege(privilegeName = "IdeaVerifyPri", privilegeValue = "4")
	public void LoadListByAjax() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		List arrList = new ArrayList();
		if (ideaInfo.getSearch() > 0) {
			if (session.getAttribute("IdeaSearch") != null) {
				int i = ideaInfo.getCurrentPage();
				ideaInfo = (IdeaInfo) session.getAttribute("IdeaSearch");
				ideaInfo.setCurrentPage(i);
			} else {
				session.removeAttribute("IdeaSearch");
			}
		}
		ideaInfo.setUser_id(user_id);
		List ideaList = ideaService.LoadAdminIdeaList(ideaInfo);
		Iterator it = ideaList.iterator();
		while (it.hasNext()) {
			IdeaInfo info = (IdeaInfo) it.next();
			List a = new ArrayList();
			a.add(info.getId());
			if (info.getCreate_user_name() != null) {
				a.add(info.getCreate_user_name());
			} else {
				a.add("");
			}
			a.add(info.getIdea_title());
			if (info.getCreate_user_id() == user_id) {
				a.add(1);
			} else {
				if (info.getHasAttach()) {
					a.add(1);
				} else {
					a.add(0);
				}
			}
			a.add(info.getCntGood());
			a.add(info.getCntBad());
			if (info.getHasChoose() == false && info.getCreate_user_id() != user_id) {
				a.add(1);
			} else {
				a.add(0);
			}
			if (info.getCreate_user_id() == user_id) {
				a.add(1);
			} else {
				a.add(0);
			}
			a.add(info.getIdea_content());
			if (info.getBy_audit() > 0) {
				a.add(1);
			} else {
				a.add(0);
			}
			arrList.add(a);
		}
		SetJsonBaseContent(arrList);
		CallBackJSON();
	}
}
