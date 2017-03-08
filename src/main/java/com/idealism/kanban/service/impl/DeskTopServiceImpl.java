package com.idealism.kanban.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.idealism.kanban.dao.DeskTopDao;
import com.idealism.kanban.dao.UserDao;
import com.idealism.kanban.model.Help;
import com.idealism.kanban.service.DeskTopService;
import com.idealism.kanban.util.DefineConfig;
import com.idealism.kanban.vo.DeskTopInfo;
import com.idealism.kanban.vo.HelpInfo;
import com.idealism.kanban.vo.MsgInfo;

@Service("DeskTopService")
public class DeskTopServiceImpl implements DeskTopService {
	@Autowired
	@Qualifier("DeskTopDao")
	private DeskTopDao deskTopDao;

	@Autowired
	@Qualifier("UserDao")
	private UserDao userDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idealism.kanban.service.impl.DeskTopService#LoadToDo(int)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List LoadToDo(DeskTopInfo deskTopInfo) {
		return deskTopDao.LoadToDo(deskTopInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idealism.kanban.service.impl.DeskTopService#LoadDoingByUserID(int)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List LoadDoingByUserID(DeskTopInfo deskTopInfo) {
		return deskTopDao.LoadDoingByUserID(deskTopInfo);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadProByUserID(DeskTopInfo deskTopInfo) {
		List list = deskTopDao.LoadProByUserID(deskTopInfo);
		HashMap<Integer, HashMap> map = new HashMap<Integer, HashMap>();
		Iterator it1 = list.iterator();
		List idList = new ArrayList();
		
		List proList = new ArrayList();
		
		while(it1.hasNext()){
			Object[] proObjects = (Object[]) it1.next();
			idList.add(proObjects[0]);
			HashMap<String, Object> detailMap = new HashMap<String, Object>();
			detailMap.put("ID", proObjects[0]);
			detailMap.put("NAME", proObjects[1]);
			detailMap.put("TODO", 0);
			detailMap.put("DOING", 0);
			detailMap.put("DONE", 0);
			map.put((Integer) proObjects[0], detailMap);
		}
		
		if(idList.size()>0){
			List statusList = deskTopDao.LoatProTaskStatus(idList);
			if(statusList.size()>0){
				Iterator statusiIterator = statusList.iterator();
				while (statusiIterator.hasNext()) {
					Object[] type = (Object[]) statusiIterator.next();
					HashMap<String, Object> detailHashMap = (HashMap<String, Object>)map.get(type[0]);
					if(type[1].equals(0)){
						int i = (Integer) detailHashMap.get("TODO");
						i++;
						detailHashMap.put("TODO", i);
					}
					if(type[1].equals(100)){
						int i = (Integer) detailHashMap.get("DOING");
						i++;
						detailHashMap.put("DOING", i);
					}
					if(type[1].equals(200) || type[1].equals(300)){
						int i = (Integer) detailHashMap.get("DONE");
						i++;
						detailHashMap.put("DONE", i);
					}
					map.put((Integer) type[0], detailHashMap);
				}
			}
			
			for(Integer o : map.keySet()){ 
				HashMap<String, Object> d = (HashMap<String, Object>)map.get(o);
				proList.add(d);
			}
		}

		return proList;
	}

	@SuppressWarnings("rawtypes")
	public List LoadIdeaExcludeUserID(DeskTopInfo deskTopInfo) {
		return deskTopDao.LoadIdeaExcludeUserID(deskTopInfo);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadHelpExcludeUserID(DeskTopInfo deskTopInfo) {
		List helpList = deskTopDao.LoadHelpExcludeUserID(deskTopInfo);
		Iterator it1 = helpList.iterator();
		ArrayList list = new ArrayList();
		while (it1.hasNext()) {
			Help help = (Help) it1.next();
			HelpInfo helpInfo = new HelpInfo();
			helpInfo.setRequest_id(help.getRequest_id());
			helpInfo.setSeek_help_user_id(help.getHelp_user_id());
			helpInfo.setHelp_title(help.getHelp_title());
			helpInfo.setHelp_content(help.getHelp_content());
			helpInfo.setHelp_user_id(help.getHelp_user_id());
			helpInfo.setCreate_time(help.getCreate_time());
			helpInfo.setSolve_time(help.getSolve_time());
			
			helpInfo.setSeek_help_user_name(userDao.LoadNameById(help.getSeek_help_user_id()));
			helpInfo.setHelp_user_name(userDao.LoadNameById(help.getHelp_user_id()));
			list.add(helpInfo);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LoadMsgByUserID(DeskTopInfo deskTopInfo) {
		List msgList = deskTopDao.LoadMsgByUserID(deskTopInfo);
		Iterator it1 = msgList.iterator();
		ArrayList list = new ArrayList();
		DefineConfig defineConfig = new DefineConfig();
		while(it1.hasNext()){
			MsgInfo msgInfo = new MsgInfo();
			Object[] msgObjects = (Object[]) it1.next();
			msgInfo.setMsg_id((Integer) msgObjects[0]);
			msgInfo.setSrc_type((Integer) msgObjects[1]);
			msgInfo.setSrc_id((Integer) msgObjects[2]);
			msgInfo.setMsg_title((String) msgObjects[3]);
			msgInfo.setMsg_content((String) msgObjects[4]);
			msgInfo.setCreate_time((Date) msgObjects[5]);
			msgInfo.setCreate_user_id((Integer) msgObjects[6]);
			msgInfo.setAttach_flag((Integer) msgObjects[7]);
			msgInfo.setIs_read((Integer) msgObjects[8]);
			
			msgInfo.setSrc_type_name(defineConfig.GetMsgTypeName((Integer) msgObjects[1]));
			msgInfo.setSrc_url(defineConfig.GetMsgUrl((Integer) msgObjects[1]));
			msgInfo.setCreate_user_name(userDao.LoadNameById((Integer) msgObjects[6]));
			list.add(msgInfo);
		}
		return list;
	}
}
