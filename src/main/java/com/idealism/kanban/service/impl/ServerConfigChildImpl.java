package com.idealism.kanban.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idealism.kanban.dao.ServerConfigChildDao;
import com.idealism.kanban.service.ServerConfigChild;

@Service("ServerConfigChild")
public class ServerConfigChildImpl implements ServerConfigChild{
	@Autowired
	@Qualifier("ServerConfigChildDao")
	private ServerConfigChildDao sChildDao;
	
	public int LoadConfigByFieldName(String field_name) {
		return sChildDao.LoadConfigByFieldName(field_name);
	}

	@SuppressWarnings("rawtypes")
	public List LoadAllPlugin() {
		return sChildDao.LoadAllPlugin();
	}

	@Transactional(rollbackFor = Exception.class)
	public void SavePlugin(String[] arr) throws Exception {
		int len = arr.length;
		for(int i=0;i<len;i++){
			String[] congfigStrings = arr[i].split(":");
			int val = 0;
			if(congfigStrings[2].equals("true")){
				val = 1;
			}
			int row = sChildDao.SavePlugin(congfigStrings[0],congfigStrings[1],val);
			if(row<=0){
				throw new Exception("");
			}
		}
	}

}
