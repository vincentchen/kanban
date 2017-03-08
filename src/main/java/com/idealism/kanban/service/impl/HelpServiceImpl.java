package com.idealism.kanban.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idealism.kanban.dao.HelpDao;
import com.idealism.kanban.dao.MsgDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.dao.UserHistoryDao;
import com.idealism.kanban.model.Help;
import com.idealism.kanban.model.Msg;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.HelpService;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.HelpInfo;

@Service("HelpService")
public class HelpServiceImpl implements HelpService {
	@Autowired
	@Qualifier("HelpDao")
	private HelpDao helpDao;

	@Autowired
	@Qualifier("UserDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("MsgDao")
	private MsgDao msgDao;

	@Autowired
	@Qualifier("UserHistoryDao")
	private UserHistoryDao HistoryDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadHelpNotMy(HelpInfo helpInfo) {
		List list = helpDao.LoadHelpNotMy(helpInfo);
		Iterator it1 = list.iterator();
		List helpList = new ArrayList();
		Global global = new Global();
		while (it1.hasNext()) {
			Help help = (Help) it1.next();
			HelpInfo info = new HelpInfo();
			info.setId(global.RegisterID("Help", help.getRequest_id()));
			info.setRequest_id(help.getRequest_id());
			info.setSeek_help_user_id(help.getSeek_help_user_id());
			info.setHelp_title(help.getHelp_title());
			info.setHelp_content(help.getHelp_content());
			info.setHelp_user_id(help.getHelp_user_id());
			info.setCreate_time(help.getCreate_time());
			info.setSolve_time(help.getSolve_time());

			info.setSeek_help_user_name(userDao.LoadNameById(help.getSeek_help_user_id()));
			info.setHelp_user_name(userDao.LoadNameById(help.getHelp_user_id()));
			info.setHasAttach(helpDao.HasAttach(help.getRequest_id()));
			helpList.add(info);
		}
		return helpList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadHelpWithMy(HelpInfo helpInfo) {
		List list = helpDao.LoadHelpWithMy(helpInfo);
		Iterator it1 = list.iterator();
		List helpList = new ArrayList();
		Global global = new Global();
		while (it1.hasNext()) {
			Help help = (Help) it1.next();
			HelpInfo info = new HelpInfo();
			info.setId(global.RegisterID("Help", help.getRequest_id()));
			info.setRequest_id(help.getRequest_id());
			info.setSeek_help_user_id(help.getSeek_help_user_id());
			info.setHelp_title(help.getHelp_title());
			info.setHelp_content(help.getHelp_content());
			info.setHelp_user_id(help.getHelp_user_id());
			info.setCreate_time(help.getCreate_time());
			info.setSolve_time(help.getSolve_time());

			info.setSeek_help_user_name(userDao.LoadNameById(help.getSeek_help_user_id()));
			info.setHelp_user_name(userDao.LoadNameById(help.getHelp_user_id()));
			info.setHasAttach(helpDao.HasAttach(help.getRequest_id()));
			helpList.add(info);
		}
		return helpList;
	}

	@Transactional(rollbackFor = Exception.class)
	public Help LoadHelpById(int id) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		int is_read = helpDao.CheckIsRead(id, user_id);
		if (is_read == 0) {
			int msg_id = helpDao.LoadMsgIDByHelpId(id);
			helpDao.UpdateMsgReadStatus(msg_id, user_id);
		}
		return helpDao.LoadHelpById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public HelpInfo LoadHelpInfoById(int id) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int user_id = (Integer) session.getAttribute("user_id");
		int is_read = helpDao.CheckIsRead(id, user_id);
		if (is_read == 0) {
			int msg_id = helpDao.LoadMsgIDByHelpId(id);
			helpDao.UpdateMsgReadStatus(msg_id, user_id);
		}
		Help help = helpDao.LoadHelpById(id);
		HelpInfo helpInfo = new HelpInfo();
		helpInfo.setRequest_id(help.getRequest_id());
		helpInfo.setSeek_help_user_id(help.getSeek_help_user_id());
		helpInfo.setHelp_title(help.getHelp_title());
		helpInfo.setHelp_content(help.getHelp_content());
		helpInfo.setHelp_user_id(help.getHelp_user_id());
		helpInfo.setCreate_time(help.getCreate_time());
		helpInfo.setSolve_time(help.getSolve_time());
		helpInfo.setSeek_help_user_name(userDao.LoadNameById(help.getSeek_help_user_id()));
		helpInfo.setHelp_user_name(userDao.LoadNameById(help.getHelp_user_id()));
		return helpInfo;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void Save(Help help) {
		int oldIdea = 0;
		if (help.getRequest_id() > 0) {
			oldIdea = 1;
		}
		helpDao.Save(help);
		Locale local = new Locale("zh", "CN");
		ResourceBundle bundle = ResourceBundle.getBundle("message", local);
		if (oldIdea == 0) {
			HistoryDao.SaveUserHistory(help.getSeek_help_user_id(), 2, help.getRequest_id(), 1, bundle.getString("help.create"));
		} else {
			return;
		}
		Msg msg = new Msg();
		msg.setSrc_type(7);// 求助消息
		msg.setSrc_id(help.getRequest_id());
		if (help.getHelp_user_id() > 0) {
			String msg_title = userDao.LoadNameById(help.getSeek_help_user_id()) + " " + bundle.getString("help.modified");
			msg.setMsg_title(msg_title);
			msg.setMsg_content(help.getHelp_title());
			msg.setCreate_user_id(help.getSeek_help_user_id());
			msg.setCreate_time(new Date(System.currentTimeMillis()));
			msgDao.Save(msg);
			msgDao.SaveUsers(msg.getMsg_id(), help.getHelp_user_id());
		} else {
			String msg_title = userDao.LoadNameById(help.getSeek_help_user_id()) + " " + bundle.getString("help.need_you");
			msg.setMsg_title(msg_title);
			msg.setMsg_content(help.getHelp_title());
			msg.setCreate_user_id(help.getSeek_help_user_id());
			msg.setCreate_time(new Date(System.currentTimeMillis()));
			msgDao.Save(msg);
			List userList = userDao.LoadMsgConfigByFieldName("help_send");
			Iterator it1 = userList.iterator();
			while (it1.hasNext()) {
				Integer uId = (Integer) it1.next();
				msgDao.SaveUsers(msg.getMsg_id(), uId);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public List LoadHelpFiles(int id) {
		return helpDao.LoadHelpFiles(id);
	}

	@Override
	public Boolean CheckCanDelete(int id, int user_id) {
		BigInteger cnt = helpDao.CheckCanDelete(id, user_id);
		if (cnt.intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void Delete(int id) {
		helpDao.Delete(id);
	}

	@Override
	public void UpdateAccept(int id, int user_id) {
		helpDao.UpdateAccept(id, user_id);
		Help help = helpDao.LoadHelpById(id);
		Locale local = new Locale("zh", "CN");
		ResourceBundle bundle = ResourceBundle.getBundle("message", local);
		Msg msg = new Msg();
		msg.setSrc_type(7);// 求助消息
		msg.setSrc_id(id);
		String msg_title = userDao.LoadNameById(user_id) + " " + bundle.getString("help.accept");
		msg.setMsg_title(msg_title);
		msg.setMsg_content("");
		msg.setCreate_user_id(user_id);
		msg.setCreate_time(new Date(System.currentTimeMillis()));
		msgDao.Save(msg);
		msgDao.SaveUsers(id, help.getSeek_help_user_id());
	}

	@Override
	public Boolean CheckCanAccept(int id) {
		BigInteger cnt = helpDao.CheckCanAccept(id);
		if (cnt.intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean CheckCanRefuse(int id, int user_id) {
		BigInteger cnt = helpDao.CheckCanRefuse(id, user_id);
		if (cnt.intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void UpdateFinish(int id, Date date) {
		helpDao.UpdateFinish(id, date);
		Help help = helpDao.LoadHelpById(id);
		Locale local = new Locale("zh", "CN");
		ResourceBundle bundle = ResourceBundle.getBundle("message", local);
		HistoryDao.SaveUserHistory(help.getHelp_user_id(), 2, help.getRequest_id(), 2, bundle.getString("help.finish"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadAllHelp(HelpInfo helpInfo) {
		List list = helpDao.LoadAllHelp(helpInfo);
		Iterator it1 = list.iterator();
		List helpList = new ArrayList();
		while (it1.hasNext()) {
			Help help = (Help) it1.next();
			HelpInfo info = new HelpInfo();
			info.setRequest_id(help.getRequest_id());
			info.setSeek_help_user_id(help.getSeek_help_user_id());
			info.setHelp_title(help.getHelp_title());
			info.setHelp_content(help.getHelp_content());
			info.setHelp_user_id(help.getHelp_user_id());
			info.setCreate_time(help.getCreate_time());
			info.setSolve_time(help.getSolve_time());

			info.setSeek_help_user_name(userDao.LoadNameById(help.getSeek_help_user_id()));
			info.setHelp_user_name(userDao.LoadNameById(help.getHelp_user_id()));
			info.setHasAttach(helpDao.HasAttach(help.getRequest_id()));
			helpList.add(info);
		}
		return helpList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadAllUserByHelpCnt(HelpInfo info) {
		List list = userDao.LoadAllUsers();
		Iterator it1 = list.iterator();
		List helpList = new ArrayList();
		while (it1.hasNext()) {
			Users users = (Users) it1.next();
			HelpInfo hp = new HelpInfo();
			hp.setSeek_help_user_name(users.getUser_name());
			hp.setSeek_help_cnt(helpDao.LoadCntBySeekHelpUser(users.getUser_id(), info));
			hp.setHelper_cnt(helpDao.LoadCntByHelper(users.getUser_id(), info));
			helpList.add(hp);
		}
		return helpList;
	}

}
