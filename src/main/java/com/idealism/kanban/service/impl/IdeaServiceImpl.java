package com.idealism.kanban.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idealism.kanban.dao.IdeaDao;
import com.idealism.kanban.dao.MsgDao;
import com.idealism.kanban.dao.ServerConfigChildDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.dao.UserHistoryDao;
import com.idealism.kanban.model.Idea;
import com.idealism.kanban.model.Msg;
import com.idealism.kanban.service.IdeaService;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.IdeaInfo;

@Service("IdeaService")
public class IdeaServiceImpl implements IdeaService {
	@Autowired
	@Qualifier("IdeaDao")
	private IdeaDao ideaDao;

	@Autowired
	@Qualifier("UserDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("MsgDao")
	private MsgDao msgDao;
	
	@Autowired
	@Qualifier("ServerConfigChildDao")
	private ServerConfigChildDao sChildDao;
	
	@Autowired
	@Qualifier("UserHistoryDao")
	private UserHistoryDao HistoryDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadIdeaList(IdeaInfo ideaInfo,int user_id) {
		int is_Audit = sChildDao.LoadConfigByFieldName("IdeaAudit");
		if(is_Audit>0){
			ideaInfo.setBy_audit(1);
		}
		List list = ideaDao.LoadIdeaList(ideaInfo);
		Iterator it1 = list.iterator();
		List ideaList = new ArrayList();
		Global global = new Global();
		while (it1.hasNext()) {
			Idea idea = (Idea) it1.next();
			IdeaInfo i = new IdeaInfo();
			i.setId(global.RegisterID("Idea", idea.getIdea_id()));
			i.setIdea_id(idea.getIdea_id());
			i.setIdea_title(idea.getIdea_title());
			i.setIdea_content(idea.getIdea_content());
			i.setCreate_time(idea.getCreate_time());
			if (idea.getIs_anonymous() > 0) {
				i.setCreate_user_id(idea.getCreate_user_id());
				i.setCreate_user_name("");
			} else {
				i.setCreate_user_id(idea.getCreate_user_id());
				i.setCreate_user_name(userDao.LoadNameById(idea.getCreate_user_id()));
			}
			i.setBy_audit(idea.getBy_audit());
			i.setHasAttach(ideaDao.HasAttach(idea.getIdea_id()));
			i.setHasChoose(ideaDao.HasChoose(idea.getIdea_id(), ideaInfo.getUser_id()));
			i.setCntGood(idea.getGood());
			i.setCntBad(idea.getBad());
			i.setIs_read(ideaDao.CheckIsRead(idea.getIdea_id(),user_id));
			ideaList.add(i);
		}
		return ideaList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadAdminIdeaList(IdeaInfo ideaInfo) {
		List list = ideaDao.LoadIdeaList(ideaInfo);
		Iterator it1 = list.iterator();
		List ideaList = new ArrayList();
		Global global = new Global();
		while (it1.hasNext()) {
			Idea idea = (Idea) it1.next();
			IdeaInfo i = new IdeaInfo();
			i.setId(global.RegisterID("Idea", idea.getIdea_id()));
			i.setIdea_id(idea.getIdea_id());
			i.setIdea_title(idea.getIdea_title());
			i.setIdea_content(idea.getIdea_content());
			i.setCreate_time(idea.getCreate_time());
			if(idea.getCreate_user_id()>0){
				i.setCreate_user_id(idea.getCreate_user_id());
				i.setCreate_user_name(userDao.LoadNameById(idea.getCreate_user_id()));
			}
			i.setIs_anonymous(idea.getIs_anonymous());
			i.setBy_audit(idea.getBy_audit());
			i.setHasAttach(ideaDao.HasAttach(idea.getIdea_id()));
			i.setHasChoose(ideaDao.HasChoose(idea.getIdea_id(), ideaInfo.getUser_id()));
			i.setCntGood(idea.getGood());
			i.setCntBad(idea.getBad());
			ideaList.add(i);
		}
		return ideaList;
	}

	public Idea LoadIdeaById(int id) {
		return ideaDao.LoadIdeaById(id);
	}
	
	public String LoadUserNameById(int id) {
		return userDao.LoadNameById(id);
	}
	
	public Boolean HasAttach(int id){
		return ideaDao.HasAttach(id);
	}
	
	public Boolean HasChoose(int id,int user_id){
		return ideaDao.HasChoose(id,user_id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void Save(Idea idea) {
		int oldIdea = 0;
		if(idea.getIdea_id()>0){
			oldIdea = 1;
		}
		int is_Audit = sChildDao.LoadConfigByFieldName("IdeaAudit");
		if(is_Audit == 0){
			idea.setBy_audit(1);
		}
		ideaDao.Save(idea);
		Locale local = new Locale("zh","CN");
		ResourceBundle bundle = ResourceBundle.getBundle("message",local);
		if(idea.getCreate_user_id() >0){
			HistoryDao.SaveUserHistory(idea.getCreate_user_id(),3,idea.getIdea_id(),1, bundle.getString("idea.create"));
		}
		if(oldIdea>0){
			return;
		}
		if(is_Audit==0){
			List userList = userDao.LoadMsgConfigByFieldName("idea_send");
			if(userList.size()<=0){
				return;
			}
			
			Msg msg = new Msg();
			msg.setSrc_type(6);//创意消息
			msg.setSrc_id(idea.getIdea_id());
			msg.setMsg_title(idea.getIdea_title());
			msg.setMsg_content(idea.getIdea_content());
			msg.setCreate_user_id(idea.getCreate_user_id());
			msg.setCreate_time(new Date(System.currentTimeMillis()));
			msgDao.Save(msg);
			Iterator it1 = userList.iterator();
			while (it1.hasNext()) {
				Integer uId = (Integer) it1.next();
				msgDao.SaveUsers(msg.getMsg_id(), uId);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public List LoadIdeaFiles(int id) {
		return ideaDao.LoadIdeaFiles(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public void SaveVoteByUser(int id, int user_id, int choose) {
		int is_Audit = sChildDao.LoadConfigByFieldName("IdeaAudit");
		Idea idea = ideaDao.LoadIdeaById(id);
		if(is_Audit>0){
			if (idea.getBy_audit() > 0) {
				if(ideaDao.HasChoose(id, user_id)){
					if(choose == 1){
						int cnt = idea.getGood();
						cnt++;
						idea.setGood(cnt);
						ideaDao.Save(idea);
					}else if(choose == 2){
						int cnt = idea.getBad();
						cnt++;
						idea.setBad(cnt);
						ideaDao.Save(idea);
					}
				}
			}
		}else{
			if(ideaDao.HasChoose(id, user_id)){
				if(choose == 1){
					int cnt = idea.getGood();
					cnt++;
					idea.setGood(cnt);
					ideaDao.Save(idea);
				}else if(choose == 2){
					int cnt = idea.getBad();
					cnt++;
					idea.setBad(cnt);
					ideaDao.Save(idea);
				}
			}
		}
		
		ideaDao.SaveVoteByUser(id, user_id);
	}

	@Override
	public Boolean CheckCanOperation(int id, int user_id) {
		BigInteger cnt = ideaDao.CheckCanOperation(id, user_id);
		if (cnt.intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void Delete(int id) {
		ideaDao.Delete(id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void UpdateByAuditIdea(int id) {
		ideaDao.UpdateByAuditIdea(id);
		Idea idea = ideaDao.LoadIdeaById(id);
		List userList = userDao.LoadMsgConfigByFieldName("idea_send");
		if(userList.size()<=0){
			return;
		}
		Msg msg = new Msg();
		msg.setSrc_type(6);//创意消息
		msg.setSrc_id(id);
		msg.setMsg_title(idea.getIdea_title());
		msg.setMsg_content(idea.getIdea_content());
		msg.setCreate_user_id(idea.getCreate_user_id());
		msg.setCreate_time(new Date(System.currentTimeMillis()));
		msgDao.Save(msg);
		Iterator it1 = userList.iterator();
		while (it1.hasNext()) {
			Integer uId = (Integer) it1.next();
			msgDao.SaveUsers(msg.getMsg_id(), uId);
		}
	}

	public void UpdateReadStatus(int id, int user_id) {
		int is_read = ideaDao.CheckIsRead(id, user_id);
		if(is_read == 0){
			int msg_id = ideaDao.LoadMsgIDByIdeaId(id);
			ideaDao.UpdateMsgReadStatus(msg_id, user_id);
		}
	}

}
