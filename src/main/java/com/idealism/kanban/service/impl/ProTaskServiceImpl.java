package com.idealism.kanban.service.impl;

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
import com.idealism.kanban.dao.ProTaskDao;
import com.idealism.kanban.dao.ProjectHistoryDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.dao.UserHistoryDao;
import com.idealism.kanban.model.Msg;
import com.idealism.kanban.model.ProTarget;
import com.idealism.kanban.model.ProTask;
import com.idealism.kanban.model.Users;
import com.idealism.kanban.service.ProTaskService;
import com.idealism.kanban.vo.ProTaskInfo;

@Service("ProTaskService")
public class ProTaskServiceImpl implements ProTaskService {
	Locale local = new Locale("zh", "CN");
	ResourceBundle bundle = ResourceBundle.getBundle("message", local);
	
	@Autowired
	@Qualifier("ProTaskDao")
	private ProTaskDao proTaskDao;

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

	@Override
	public Boolean CheckCanReceive(int id, int user_id) {
		return proTaskDao.CheckCanReceive(id, user_id);
	}

	@Override
	public ProTask LoadProTaskById(int id) {
		return proTaskDao.LoadProTaskById(id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public int UpdateReceive(int task_id, int user_id) {
		Boolean checkCanReceive = proTaskDao.CheckCanReceive(task_id, user_id);
		if(!checkCanReceive){
			return 0;
		}
		
		int row = proTaskDao.UpdateReceive(task_id, user_id);
		
		ProTask proTask = proTaskDao.LoadProTaskById(task_id);
		
		ProTarget protarget = proTaskDao.LoadProTargetByTaskID(task_id);
		Users users = userDao.LoadUserByID(user_id);
		String content = users.getUser_name()+" "+bundle.getString("task.received")+" : #"+proTask.getTask_id();
		projectHistoryDao.SaveHistory(protarget.getPro_id(), protarget.getTarget_id(), task_id, proTask.getStatus_value(), content, new Date(System.currentTimeMillis()));
		
		HistoryDao.SaveUserHistory(user_id, 6,task_id, 1, bundle.getString("task.received"));
		
		List userList = proTaskDao.LoadRelProUser(task_id);
		for (int i = 0; i < userList.size(); i++) {
			int uId = (Integer) userList.get(i);
			int hasProSend = userDao.LoadMsgConfig("pro_send", uId);
			if (hasProSend == 0) {
				int hasTargetSend = userDao
						.LoadMsgConfig("protarget_send", uId);
				if (hasTargetSend == 0) {
					userList.remove(i);
				} else {
					List attention = proTaskDao.LoadAttention(task_id, uId);
					if (attention.size() <= 0) {
						userList.remove(i);
					}
				}
			}
		}

		Msg msg = new Msg();
		msg.setSrc_type(10);// 任务目标消息
		msg.setSrc_id(task_id);
		String user_name = userDao.LoadNameById(user_id);
		String title = user_name + " " + bundle.getString("msg.receive_task")
				+ " #" + task_id;
		msg.setMsg_title(title);
		msg.setMsg_content(proTask.getTask_des());
		msg.setCreate_user_id(user_id);
		msg.setCreate_time(new Date(System.currentTimeMillis()));
		msgDao.Save(msg);
		Iterator it1 = userList.iterator();
		while (it1.hasNext()) {
			Integer uId = (Integer) it1.next();
			msgDao.SaveUsers(msg.getMsg_id(), uId);
		}
		return row;
	}

	@Transactional(rollbackFor = Exception.class)
	public int UpdateBlock(int task_id, int user_id) {
		Boolean checkCanBlock = proTaskDao.CheckCanOperation(task_id,user_id);
		if(!checkCanBlock){
			return 0;
		}
		
		int row = proTaskDao.UpdateBlock(task_id, user_id);
		
		ProTask proTask = proTaskDao.LoadProTaskById(task_id);
		
		ProTarget protarget = proTaskDao.LoadProTargetByTaskID(task_id);
		Users users = userDao.LoadUserByID(user_id);
		String content = users.getUser_name()+" "+bundle.getString("task.canceled")+" : #"+proTask.getTask_id();
		projectHistoryDao.SaveHistory(protarget.getPro_id(), protarget.getTarget_id(), task_id, proTask.getStatus_value(), content, new Date(System.currentTimeMillis()));
		

		HistoryDao.SaveUserHistory(user_id, 6,task_id, 4, bundle.getString("task.canceled"));
		
		Msg msg = new Msg();
		msg.setSrc_type(10);// 任务目标消息
		msg.setSrc_id(task_id);
		String user_name = userDao.LoadNameById(user_id);
		String title = user_name + " " + bundle.getString("msg.block_task")
				+ " #" + task_id;
		msg.setMsg_title(title);
		msg.setMsg_content(proTask.getTask_des());
		msg.setCreate_user_id(user_id);
		msg.setCreate_time(new Date(System.currentTimeMillis()));
		msgDao.Save(msg);
		msgDao.SaveUsers(msg.getMsg_id(), proTask.getCreate_user_id());
		return row;
	}

	@Transactional(rollbackFor = Exception.class)
	public int UpdateFinish(int task_id, int user_id) {
		Boolean checkCanBlock = proTaskDao.CheckCanOperation(task_id,user_id);
		if(!checkCanBlock){
			return 0;
		}
		
		int row = proTaskDao.UpdateFinish(task_id, user_id);
		ProTask proTask = proTaskDao.LoadProTaskById(task_id);
		
		ProTarget protarget = proTaskDao.LoadProTargetByTaskID(task_id);
		Users users = userDao.LoadUserByID(user_id);
		String content = users.getUser_name()+" "+bundle.getString("task.finished")+" : #"+proTask.getTask_id();
		projectHistoryDao.SaveHistory(protarget.getPro_id(), protarget.getTarget_id(), task_id, proTask.getStatus_value(), content, new Date(System.currentTimeMillis()));
		
		
		HistoryDao.SaveUserHistory(user_id, 6,task_id, 2, bundle.getString("task.finish"));
		
		Msg msg = new Msg();
		msg.setSrc_type(10);// 任务目标消息
		msg.setSrc_id(task_id);
		String user_name = userDao.LoadNameById(user_id);
		String title = user_name + " " + bundle.getString("msg.finish_task")
				+ " #" + task_id;
		msg.setMsg_title(title);
		msg.setMsg_content(proTask.getTask_des());
		msg.setCreate_user_id(user_id);
		msg.setCreate_time(new Date(System.currentTimeMillis()));
		msgDao.Save(msg);
		msgDao.SaveUsers(msg.getMsg_id(), proTask.getCreate_user_id());
		return row;
	}

	@Override
	public Boolean CheckCanOperation(int task_id, int user_id) {
		return proTaskDao.CheckCanOperation(task_id, user_id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void Save(ProTask proTask) {
		int oldTask = 0;
		if(proTask.getTask_id()>0){
			oldTask = 1;
		}
		proTaskDao.Save(proTask);
		
		ProTarget protarget = proTaskDao.LoadProTargetByTaskID(proTask.getTask_id());
		Users users = userDao.LoadUserByID(proTask.getCreate_user_id());
		String content = "";
		
		if(oldTask == 0){
			content += users.getUser_name()+" "+bundle.getString("task.create")+" : #"+proTask.getTask_id();
		}else {
			content += users.getUser_name()+" "+bundle.getString("task.edited")+" : #"+proTask.getTask_id();
		}
		projectHistoryDao.SaveHistory(protarget.getPro_id(), protarget.getTarget_id(), proTask.getTask_id(), proTask.getStatus_value(), content, new Date(System.currentTimeMillis()));
		
		if(oldTask == 0){
			
			HistoryDao.SaveUserHistory(proTask.getCreate_user_id(), 6,proTask.getTask_id(), 1, bundle.getString("task.create"));
		}else{
			HistoryDao.SaveUserHistory(proTask.getCreate_user_id(), 6,proTask.getTask_id(), 3, bundle.getString("task.edited"));
		}
		
		List userList = proTaskDao.LoadRelProUser(proTask.getTask_id());
		for(int i=0;i<userList.size();i++){
			int uId = (Integer) userList.get(i);
			int hasProSend = userDao.LoadMsgConfig("pro_send", uId);
			if(hasProSend==0){
				int hasTargetSend = userDao.LoadMsgConfig("protarget_send", uId);
				if(hasTargetSend==0){
					userList.remove(i);
				}else {
					List attention = proTaskDao.LoadAttention(proTask.getTask_id(), uId);
					if(attention.size()<=0){
						userList.remove(i);
					}
				}
			}
		}
		Msg msg = new Msg();
		msg.setSrc_type(10);// 任务目标消息
		msg.setSrc_id(proTask.getTask_id());
		String user_name = userDao.LoadNameById(proTask.getCreate_user_id());
		String title = "";
		if(oldTask>0 && userList.size()>0){
			title = user_name + " " + bundle.getString("msg.edit_task") + " #" + proTask.getTask_id();
		}else {
			title = user_name + " " + bundle.getString("msg.edit_task") + " #" + proTask.getTask_id();
		}
		msg.setMsg_title(title);
		msg.setMsg_content(proTask.getTask_des());
		msg.setCreate_user_id(proTask.getCreate_user_id());
		msg.setCreate_time(new Date(System.currentTimeMillis()));
		msgDao.Save(msg);
		Iterator it1 = userList.iterator();
		while (it1.hasNext()) {
			Integer uId = (Integer) it1.next();
			msgDao.SaveUsers(msg.getMsg_id(), uId);
		}
	}

	@SuppressWarnings("rawtypes")
	public List LoadTaskFiles(int id) {
		return proTaskDao.LoadTaskFiles(id);
	}

	@SuppressWarnings("rawtypes")
	public List LoadRelUsersByTargetId(int target_id) {
		return proTaskDao.LoadRelUsersByTargetId(target_id);
	}

	@Override
	public ProTaskInfo LoadProTaskByIdWithInfo(int id) {
		ProTask proTask = proTaskDao.LoadProTaskById(id);
		ProTaskInfo info = new ProTaskInfo();
		info.setTask_id(proTask.getTask_id());
		info.setTarget_id(proTask.getTarget_id());
		info.setTask_des(proTask.getTask_des());
		info.setCreate_time(proTask.getCreate_time());
		info.setStart_time(proTask.getStart_time());
		info.setEnd_time(proTask.getEnd_time());
		info.setStatus_value(proTask.getStatus_value());
		info.setCreate_user_id(proTask.getCreate_user_id());
		info.setCreate_user_name(userDao.LoadNameById(proTask.getCreate_user_id()));
		info.setReceive_task_user(proTask.getReceive_task_user());
		info.setReceive_task_user_name(userDao.LoadNameById(proTask.getReceive_task_user()));
		info.setEstimated_hour(proTask.getEstimated_hour());
		info.setUrgent_degree(proTask.getUrgent_degree());
		info.setStart_task_time(proTask.getStart_task_time());
		info.setEnd_task_time(proTask.getEnd_task_time());
		return info;
	}

}
