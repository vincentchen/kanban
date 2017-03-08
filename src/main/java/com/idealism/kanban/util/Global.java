package com.idealism.kanban.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

@Service("Global")
public class Global {
	@SuppressWarnings("unchecked")
	public Integer RegisterID(String keyName, Integer id) {
		HttpSession session = ServletActionContext.getRequest().getSession();

		if (session.getAttribute("globalIDs") == null) {
			session.setAttribute("globalIDs", new HashMap<String, HashMap<String, Integer>>());
		}

		HashMap<String, HashMap<Integer, Integer>> globalIDs = (HashMap<String, HashMap<Integer, Integer>>) session.getAttribute("globalIDs");
		if (globalIDs.get(keyName) == null) {
			globalIDs.put(keyName, new HashMap<Integer, Integer>());
		}

		Random random = new Random();
		int seqNo = random.nextInt(99999999);

		HashMap<Integer, Integer> values = globalIDs.get(keyName);
		values.put(seqNo, id);

		globalIDs.put(keyName, values);

		session.setAttribute("globalIDs", globalIDs);

		return seqNo;
	}

	@SuppressWarnings("unchecked")
	public Integer GetRegID(String keyName, int seqNo) {
		HttpSession session = ServletActionContext.getRequest().getSession();

		if (session.getAttribute("globalIDs") == null) {
			return 0;
		}

		HashMap<String, HashMap<Integer, Integer>> globalIDs = (HashMap<String, HashMap<Integer, Integer>>) session.getAttribute("globalIDs");
		if (globalIDs.get(keyName) == null) {
			return 0;
		}
		if (globalIDs.get(keyName).get(seqNo) == null) {
			return 0;
		} else {
			return globalIDs.get(keyName).get(seqNo);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List GetRegIDs(String keyName, List seqNos) {
		HttpSession session = ServletActionContext.getRequest().getSession();

		if (session.getAttribute("globalIDs") == null) {
			return null;
		}

		HashMap<String, HashMap<Integer, Integer>> globalIDs = (HashMap<String, HashMap<Integer, Integer>>) session.getAttribute("globalIDs");
		if (globalIDs.get(keyName) == null) {
			return null;
		}
		Set set = new HashSet();
		List newList = new ArrayList();
		Iterator it = seqNos.iterator();
		while (it.hasNext()) {
			String element = (String) it.next();
			Integer id = globalIDs.get(keyName).get(Integer.parseInt(element));
			if (id > 0) {
				if (set.add(id)) {
					newList.add(id);
				}
			}
		}
		return newList;
	}
	
	public boolean getDayIsToday(Date dt){
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		String todayStr = sdf.format(today);
		String dtStr = sdf.format(dt);
		if(todayStr.equals(dtStr)){
			return true;
		}else {
			return false; 
		}
	}
}
