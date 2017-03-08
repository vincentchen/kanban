package com.idealism.kanban.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idealism.kanban.dao.MsgDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.model.Msg;
import com.idealism.kanban.service.MsgService;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.MsgInfo;

@Service("MsgService")
public class MsgServiceImpl implements MsgService {
	@Autowired
	@Qualifier("MsgDao")
	private MsgDao msgDao;

	@Autowired
	@Qualifier("UserDao")
	private UserDao userDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadMsgList(MsgInfo msgInfo) {
		List msgList = msgDao.LoadMsgList(msgInfo);
		Iterator it1 = msgList.iterator();
		ArrayList list = new ArrayList();
		DefineConfig defineConfig = new DefineConfig();
		Global global = new Global();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		while (it1.hasNext()) {
			Msg msg = (Msg) it1.next();
			MsgInfo info = new MsgInfo();
			info.setId(global.RegisterID("Msg", msg.getMsg_id()));
			info.setMsg_id(msg.getMsg_id());
			info.setSrc_type(msg.getSrc_type());
			info.setSrc_id(msg.getSrc_id());
			info.setMsg_title(msg.getMsg_title());
			info.setMsg_content(msg.getMsg_content());
			info.setCreate_time(msg.getCreate_time());
			info.setCreate_user_id(msg.getCreate_user_id());
			info.setAttach_flag(msg.getAttach_flag());
			if(msgInfo.getUser_id()>0){
				info.setIs_read(msgDao.LoadMsgIsRead(msg.getMsg_id(), msgInfo.getUser_id()));
			}else {
				info.setIs_read(1);
			}

			info.setSrc_type_name(defineConfig.GetMsgTypeName(msg.getSrc_type()));
			info.setCreate_user_name(userDao.LoadNameById(msg.getCreate_user_id()));
			info.setCreate_time_str(format.format(msg.getCreate_time()));
			list.add(info);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void Save(Msg msg, List userlist) {
		try {
			msgDao.Save(msg);
			Iterator it1 = userlist.iterator();
			while (it1.hasNext()) {
				Integer user_id = (Integer) it1.next();
				msgDao.SaveUsers(msg.getMsg_id(), user_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void SaveUsers(int msg_id, List userList) {
		Iterator it1 = userList.iterator();
		while (it1.hasNext()) {
			Integer user_id = (Integer) it1.next();
			msgDao.SaveUsers(msg_id, user_id);
		}
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	public void DeleteUser(int user_id,List msgIDs) {
		try {
			Iterator it = msgIDs.iterator();
			while(it.hasNext()){
				Integer msg_id = (Integer) it.next();
				msgDao.DeleteUser(user_id,msg_id);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public MsgInfo LoadMsgById(int msg_id) {
		MsgInfo info = new MsgInfo();
		DefineConfig defineConfig = new DefineConfig();
		Msg msg = msgDao.LoadMsgById(msg_id);
		info.setMsg_id(msg.getMsg_id());
		info.setSrc_type(msg.getSrc_type());
		info.setSrc_id(msg.getSrc_id());
		info.setMsg_title(msg.getMsg_title());
		info.setMsg_content(msg.getMsg_content());
		info.setCreate_time(msg.getCreate_time());
		info.setCreate_user_id(msg.getCreate_user_id());
		info.setAttach_flag(msg.getAttach_flag());

		String urlAction = defineConfig.GetMsgUrl(msg.getSrc_type());
		Global global = new Global();
		String url;
		if(info.getSrc_id()>0){
			url = urlAction+"View?id="+global.RegisterID(urlAction, msg.getSrc_id());
		}else {
			url = urlAction+"View?id="+global.RegisterID(urlAction, msg.getMsg_id());
		}
		info.setSrc_url(url);
		info.setSrc_type_name(defineConfig.GetMsgTypeName(msg.getSrc_type()));
		info.setCreate_user_name(userDao.LoadNameById(msg.getCreate_user_id()));
		return info;
	}

	@Override
	public void UpdateRead(int msg_id, int user_id) {
		msgDao.UpdateRead(msg_id,user_id);
	}

}
