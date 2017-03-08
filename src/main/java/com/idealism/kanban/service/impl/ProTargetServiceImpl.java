package com.idealism.kanban.service.impl;

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

import com.idealism.kanban.dao.MsgDao;
import com.idealism.kanban.dao.ProTargetDao;
import com.idealism.kanban.dao.ProjectHistoryDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.dao.UserHistoryDao;
import com.idealism.kanban.model.Msg;
import com.idealism.kanban.model.ProTarget;
import com.idealism.kanban.model.ProTask;
import com.idealism.kanban.service.ProTargetService;
import com.idealism.kanban.vo.ProTaskInfo;

@Service("ProTargetService")
public class ProTargetServiceImpl implements ProTargetService {
	Locale local = new Locale("zh","CN");
	ResourceBundle bundle = ResourceBundle.getBundle("message",local);
	
	@Autowired
	@Qualifier("ProTargetDao")
	private ProTargetDao proTargetDao;

	@Autowired
	@Qualifier("UserDao")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("MsgDao")
	private MsgDao msgDao;
	
	@Autowired
	@Qualifier("UserHistoryDao")
	private UserHistoryDao HistoryDao;
	
	@Autowired
	@Qualifier("ProjectHistoryDao")
	private ProjectHistoryDao projectHistoryDao;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadTask(int id,String type){
		List taskList = null;
		if(type.equals("TODO")){
			taskList = proTargetDao.LoadTaskTodo(id);
		}
		if(type.equals("DOING")){
			taskList = proTargetDao.LoadTaskDoing(id);
		}
		if(type.equals("DONE")){
			taskList = proTargetDao.LoadTaskDone(id);
		}
		if(taskList.size()>0){
			List list = new ArrayList();
			Iterator taskIt = taskList.iterator();
			while (taskIt.hasNext()) {
				ProTaskInfo proTaskInfo = new ProTaskInfo();
				ProTask proTask = (ProTask) taskIt.next();
				proTaskInfo.setTask_id(proTask.getTask_id());
				proTaskInfo.setTarget_id(proTask.getTarget_id());
				proTaskInfo.setTask_des(proTask.getTask_des());
				proTaskInfo.setCreate_time(proTask.getCreate_time());
				proTaskInfo.setStart_time(proTask.getStart_time());
				proTaskInfo.setEnd_time(proTask.getEnd_time());
				proTaskInfo.setStatus_value(proTask.getStatus_value());
				proTaskInfo.setCreate_user_id(proTask.getCreate_user_id());
				proTaskInfo.setCreate_user_name(userDao.LoadNameById(proTask.getCreate_user_id()));
				proTaskInfo.setReceive_task_user(proTask.getReceive_task_user());
				proTaskInfo.setReceive_task_user_name(userDao.LoadNameById(proTask.getReceive_task_user()));
				proTaskInfo.setEstimated_hour(proTask.getEstimated_hour());
				proTaskInfo.setUrgent_degree(proTask.getUrgent_degree());
				proTaskInfo.setStart_task_time(proTask.getStart_task_time());
				proTaskInfo.setEnd_task_time(proTask.getEnd_task_time());
				list.add(proTaskInfo);
			}
			return list;
		}
		return null;
	}

	@Override
	public ProTarget LoadProTargetById(int id) {
		return proTargetDao.LoadProTargetById(id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void Save(ProTarget protarget) {
		int oldIdea = 0;
		if(protarget.getTarget_id()>0){
			oldIdea = 1;
		}
		proTargetDao.Save(protarget);
		
		String content = "";
		if(oldIdea==0){
			content += bundle.getString("target.create")+" : "+protarget.getTarget_name();
		}else {
			content += bundle.getString("target.edited")+" : "+protarget.getTarget_name();
		}
		projectHistoryDao.SaveHistory(protarget.getPro_id(), protarget.getTarget_id(), 0, protarget.getTarget_status(), content, new Date(System.currentTimeMillis()));
		
		if(oldIdea == 0){
			HistoryDao.SaveUserHistory(protarget.getCreate_user_id(), 5,protarget.getTarget_id(), 1, bundle.getString("target.create"));
		}else{
			HistoryDao.SaveUserHistory(protarget.getCreate_user_id(), 5,protarget.getTarget_id(), 3, bundle.getString("target.edited"));
		}
		List userList = proTargetDao.LoadRelProUser(protarget.getPro_id());
		for(int i=0;i<userList.size();i++){
			int uId = (Integer) userList.get(i);
			int hasProSend = userDao.LoadMsgConfig("pro_send", uId);
			if(hasProSend==0){
				int hasTargetSend = userDao.LoadMsgConfig("protarget_send", uId);
				if(hasTargetSend==0){
					userList.remove(i);
				}else {
					List attention = proTargetDao.LoadAttention(protarget.getTarget_id(), uId);
					if(attention.size()<=0){
						userList.remove(i);
					}
				}
			}
		}
		if(oldIdea>0 && userList.size()>0){
			
			Msg msg = new Msg();
			msg.setSrc_type(9);//任务目标消息
			msg.setSrc_id(protarget.getTarget_id());
			msg.setMsg_title(protarget.getTarget_name()+bundle.getString("msg.edit"));
			msg.setMsg_content(protarget.getTarget_des());
			msg.setCreate_user_id(protarget.getCreate_user_id());
			msg.setCreate_time(new Date(System.currentTimeMillis()));
			msgDao.Save(msg);
			Iterator it1 = userList.iterator();
			while (it1.hasNext()) {
				Integer uId = (Integer) it1.next();
				msgDao.SaveUsers(msg.getMsg_id(), uId);
			}
		}else {
			Msg msg = new Msg();
			msg.setSrc_type(9);//任务目标消息
			msg.setSrc_id(protarget.getTarget_id());
			msg.setMsg_title(bundle.getString("add")+bundle.getString("pro_tatget")+" "+protarget.getTarget_name());
			msg.setMsg_content(protarget.getTarget_des());
			msg.setCreate_user_id(protarget.getCreate_user_id());
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
	public List LoadTargetFiles(int id) {
		return proTargetDao.LoadProFiles(id);
	}

	@SuppressWarnings("rawtypes")
	public List LoadTaskGraphList(int id) {
		return proTargetDao.LoadTaskGraphList(id);
	}

	@SuppressWarnings("rawtypes")
	public List LoadCurrentMonth(int id) {
		return proTargetDao.LoadCurrentMonth(id);
	}

	@SuppressWarnings("rawtypes")
	public List SearchProjectHistory(Date history_min_time, Date history_max_time,int id) {
		return proTargetDao.SearchProjectHistory(history_min_time,history_max_time,id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void SaveAttention(int user_id, int id) {
		List list = proTargetDao.LoadAttention(user_id, id);
		if(list.size()>0){
			proTargetDao.DeleteAttention(user_id, id);
		}else {
			proTargetDao.InsertAttention(user_id, id);
		}
	}

	@SuppressWarnings("rawtypes")
	public List LoadAttentionProtarget(int user_id, int target_id) {
		return proTargetDao.LoadAttention(target_id,user_id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void UpdateTargetStatus(ProTarget protarget) {
		proTargetDao.Save(protarget);
		
		projectHistoryDao.SaveHistory(protarget.getPro_id(), protarget.getTarget_id(), 0, protarget.getTarget_status(), bundle.getString("target.edited")+" : "+protarget.getTarget_name(), new Date(System.currentTimeMillis()));
		
		List userList = proTargetDao.LoadRelProUser(protarget.getPro_id());
		for(int i=0;i<userList.size();i++){
			int uId = (Integer) userList.get(i);
			int hasProSend = userDao.LoadMsgConfig("pro_send", uId);
			if(hasProSend==0){
				int hasTargetSend = userDao.LoadMsgConfig("protarget_send", uId);
				if(hasTargetSend==0){
					userList.remove(i);
				}else {
					List attention = proTargetDao.LoadAttention(protarget.getTarget_id(), uId);
					if(attention.size()<=0){
						userList.remove(i);
					}
				}
			}
		}

		if(protarget.getTarget_status() == 200){
			HistoryDao.SaveUserHistory(protarget.getCreate_user_id(), 5,protarget.getTarget_id(), 2, bundle.getString("target.finish"));
		}
		
		Msg msg = new Msg();
		msg.setSrc_type(9);//任务目标消息
		msg.setSrc_id(protarget.getTarget_id());
		String title = protarget.getTarget_name()+bundle.getString("msg.change_status");
		if(protarget.getTarget_status() == 100){
			title += " "+bundle.getString("target.status.doing");
		}
		if(protarget.getTarget_status() == 200){
			title += " "+bundle.getString("target.status.done");
		}
		msg.setMsg_title(title);
		msg.setMsg_content(protarget.getTarget_des());
		msg.setCreate_user_id(protarget.getCreate_user_id());
		msg.setCreate_time(new Date(System.currentTimeMillis()));
		msgDao.Save(msg);
		Iterator it1 = userList.iterator();
		while (it1.hasNext()) {
			Integer uId = (Integer) it1.next();
			msgDao.SaveUsers(msg.getMsg_id(), uId);
		}
	}

	@Override
	public int checkCanOperation(int id, int user_id) {
		return proTargetDao.checkCanOperation(id,user_id);
	}
}
