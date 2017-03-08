package com.idealism.kanban.service.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.idealism.kanban.dao.MsgDao;
import com.idealism.kanban.dao.ProjectDao;
import com.idealism.kanban.dao.ProjectHistoryDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.dao.UserHistoryDao;
import com.idealism.kanban.model.Msg;
import com.idealism.kanban.model.ProTarget;
import com.idealism.kanban.model.Project;
import com.idealism.kanban.service.ProjectService;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.ProjectInfo;

@Service("ProjectService")
public class ProjectServiceImpl implements ProjectService {
	Locale local = new Locale("zh","CN");
	ResourceBundle bundle = ResourceBundle.getBundle("message",local);
	
	@Autowired
	@Qualifier("ProjectDao")
	private ProjectDao projectDao;
	
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
	public Project LoadProById(int id) {
		return projectDao.LoadProByID(id);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List LoadProTargetUnFinishById(int id, int attention, int user_id){
		List proTargetList = projectDao.LoadProTargetUnFinishById(id);
		Iterator it1 = proTargetList.iterator();
		List targetList = new ArrayList();
		while(it1.hasNext()){
			List list = new ArrayList();
			ProTarget target = (ProTarget) it1.next();
			list.add(target.getTarget_id());
			list.add(target.getPro_id());
			list.add(target.getTarget_name());
			list.add(target.getTarget_des());
			list.add(target.getStart_target_time());
			list.add(target.getEnd_target_time());
			list.add(target.getEstimated_day());
			list.add(target.getTarget_status());
			list.add(0);//Task-TODO
			list.add(0);//Task-DOING
			list.add(0);//Task-DONE
			List taskList = projectDao.LoadProTaskById(target.getTarget_id());
			Iterator itTask = taskList.iterator();
			while (itTask.hasNext()) {
				Object[] task = (Object[]) itTask.next();
				if(task[0].equals(0)){//判断todo
					list.set(8, task[1]);
				}
				if(task[0].equals(100)){//判断DOING
					list.set(9, task[1]);
				}
				if(task[0].equals(200) || task[0].equals(300)){//判断DONE和CANCEL
					int cnt = (Integer)list.get(10);
					BigInteger thiscnt = (BigInteger)task[1];
					list.set(10, cnt+thiscnt.intValue());
				}
			}
			list.add(0);
			if(attention>0){
				List attentionList = projectDao.LoadAttentionProtarget(target.getTarget_id(),user_id);
				if(attentionList.size()>0){
					list.set(11, 1);
				}
			}
			targetList.add(list);
		}
		return targetList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List LoadProTargetFinishedById(int id, int attention, int user_id) {
		List proTargetList = projectDao.LoadProTargetFinishedById(id);
		Iterator it1 = proTargetList.iterator();
		List targetList = new ArrayList();
		while(it1.hasNext()){
			List list = new ArrayList();
			ProTarget target = (ProTarget) it1.next();
			list.add(target.getTarget_id());
			list.add(target.getPro_id());
			list.add(target.getTarget_name());
			list.add(target.getTarget_des());
			list.add(target.getStart_target_time());
			list.add(target.getEnd_target_time());
			list.add(target.getEstimated_day());
			list.add(target.getTarget_status());
			list.add(0);//Task-TODO
			list.add(0);//Task-DOING
			list.add(0);//Task-DONE
			List taskList = projectDao.LoadProTaskById(target.getTarget_id());
			Iterator itTask = taskList.iterator();
			while (itTask.hasNext()) {
				Object[] task = (Object[]) itTask.next();
				if(task[0].equals(0)){//判断todo
					list.set(8, task[1]);
				}
				if(task[0].equals(100)){//判断DOING
					list.set(9, task[1]);
				}
				if(task[0].equals(200) || task[0].equals(300)){//判断DONE和CANCEL
					int cnt = (Integer)list.get(10);
					BigInteger thiscnt = (BigInteger)task[1];
					list.set(10, cnt+thiscnt.intValue());
				}
			}
			targetList.add(list);
		}
		return targetList;
	}

	
	@SuppressWarnings("rawtypes")
	public List LoadProFiles(int id) {
		return projectDao.LoadProFiles(id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadProject(ProjectInfo projectInfo) {
		List proList = projectDao.LoadProject(projectInfo);
		Iterator it1 = proList.iterator();
		ArrayList list = new ArrayList();
		Global global = new Global();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		while (it1.hasNext()) {
			Project project = (Project) it1.next();
			ProjectInfo pInfo = new ProjectInfo();
			pInfo.setId(global.RegisterID("Project", project.getPro_id()));
			pInfo.setPro_id(project.getPro_id());
			pInfo.setPro_name(project.getPro_name());
			pInfo.setPro_des(project.getPro_des());
			pInfo.setCreate_time(project.getCreate_time());
			pInfo.setStart_time(project.getStart_time());
			pInfo.setEnd_time(project.getEnd_time());
			pInfo.setStart_pro_time(project.getStart_pro_time());
			pInfo.setEnd_pro_time(project.getEnd_pro_time());
			pInfo.setEstimated_day(project.getEstimated_day());
			pInfo.setPro_status(project.getPro_status());
			pInfo.setCreate_user_id(project.getCreate_user_id());
			
			pInfo.setCreate_user_name(userDao.LoadNameById(project.getCreate_user_id()));
			pInfo.setCreate_time_str(format.format(project.getCreate_time()));
			list.add(pInfo);
		}
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	public void SaveInfo(ProjectInfo projectInfo) {
		Project project = new Project();
		project.setPro_name(projectInfo.getPro_name());
		project.setPro_des(projectInfo.getPro_des());
		project.setCreate_time(new Date(System.currentTimeMillis()));
		project.setStart_pro_time(projectInfo.getStart_pro_time());
		project.setEnd_pro_time(projectInfo.getEnd_pro_time());
		project.setEstimated_day(projectInfo.getEstimated_day());
		project.setCreate_user_id(projectInfo.getCreate_user_id());
		if(projectInfo.getImm_start()>0){
			project.setStart_pro_time(new Date(System.currentTimeMillis()));
			project.setStart_time(new Date(System.currentTimeMillis()));
			project.setPro_status(100);
		}
		project.setIs_open(projectInfo.getIs_open());
		projectDao.Save(project);
		
		if(project.getPro_id()>0){
			projectDao.InsertRelUsers(project.getPro_id(), projectInfo.getCreate_user_id());
		}
		
		String content = bundle.getString("pro.create")+" : "+ project.getPro_name();
		projectHistoryDao.SaveHistory(project.getPro_id(), 0, 0, project.getPro_status(), content, new Date(System.currentTimeMillis()));
		
		projectInfo.setPro_id(project.getPro_id());
		
		HistoryDao.SaveUserHistory(projectInfo.getCreate_user_id(),4,projectInfo.getPro_id(),1, bundle.getString("pro.create"));
	}

	@Transactional(rollbackFor = Exception.class)
	public void Save(Project project) {
		projectDao.Save(project);
		String content = bundle.getString("pro.edit")+" : "+ project.getPro_name();
		projectHistoryDao.SaveHistory(project.getPro_id(), 0, 0, project.getPro_status(), content, new Date(System.currentTimeMillis()));
		
		HistoryDao.SaveUserHistory(project.getCreate_user_id(),4,project.getPro_id(),3, bundle.getString("pro.edit"));
	}

	@Transactional(rollbackFor = Exception.class)
	public void DeleteProject(int id) {
		projectDao.DeleteProRelUsers(id);
		projectDao.DeleteProRelFiles(id);
		projectDao.DeleteProject(id);
	}

	@SuppressWarnings("rawtypes")
	public List LoadProUsers(int id) {
		return projectDao.LoadProUsers(id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void SaveRelUsers(int id, List userlist) {
		try {
			Iterator it1 = userlist.iterator();
			int addMsg = 0;
			int msg_id = 0;
			while (it1.hasNext()) {
				Integer user_id = (Integer) it1.next();
				Boolean hasUser = projectDao.CheckRelUsers(id,user_id);
				if(!hasUser){
					projectDao.InsertRelUsers(id, user_id);
					int hasSend = userDao.LoadMsgConfig("pro_send", user_id);
					if(hasSend>0){
						if(addMsg == 0){
							Project project = projectDao.LoadProByID(id);
							Msg msg = new Msg();
							msg.setSrc_type(2);//创意消息
							msg.setSrc_id(id);
							HttpSession session = ServletActionContext.getRequest().getSession();
							int my_id = (Integer) session.getAttribute("user_id");
							String title = userDao.LoadNameById(my_id)+" "+bundle.getString("msg.rel_pro_user")+" "+project.getPro_name();
							msg.setMsg_title(title);
							msg.setMsg_content(project.getPro_des());
							msg.setCreate_user_id(user_id);
							msg.setCreate_time(new Date(System.currentTimeMillis()));
							msgDao.Save(msg);
							msg_id = msg.getMsg_id();
							addMsg = 1;
							msgDao.SaveUsers(msg_id, user_id);
						}else {
							msgDao.SaveUsers(msg_id, user_id);
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void DeleteRelUsers(int id, int user_id) {
		projectDao.DeleteRelUsers(id,user_id);
	}

	
	@SuppressWarnings("rawtypes")
	public List LoadTargetGraphList(int id) {
		return projectDao.LoadTargetGraphByProID(id);
	}

	@SuppressWarnings("rawtypes")
	public List LoadHistoryByProID(int id) {
		return projectDao.LoadHistoryByProID(id);
	}

	@SuppressWarnings("rawtypes")
	public List SearchProjectHistory(Date history_min_time, Date history_max_time, int id) {
		return projectDao.SearchProjectHistory(history_min_time,history_max_time,id);
	}

	@SuppressWarnings("rawtypes")
	public List LoadAll() {
		return projectDao.LoadAll();
	}

	@Transactional(rollbackFor = Exception.class)
	public void UpdateStatus(Project project) {
		projectDao.Save(project);
		String content = bundle.getString("pro.edit")+" : "+ project.getPro_name();
		projectHistoryDao.SaveHistory(project.getPro_id(), 0, 0, project.getPro_status(), content, new Date(System.currentTimeMillis()));
		
		if(project.getPro_status() == 200){
			HistoryDao.SaveUserHistory(project.getCreate_user_id(),4,project.getPro_id(),2, bundle.getString("pro.finish"));
		}
	}
	
}
