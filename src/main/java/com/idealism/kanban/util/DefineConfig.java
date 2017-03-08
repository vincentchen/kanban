package com.idealism.kanban.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class DefineConfig extends ActionSupport {
	private HashMap<Integer, String> HistoryType = new HashMap<Integer, String>();
	private HashMap<Integer, String> HistoryStatus = new HashMap<Integer, String>();
	private HashMap<String, Integer> ConfigText = new HashMap<String, Integer>();
	private HashMap<Integer, String> msgType = new HashMap<Integer, String>();
	private HashMap<Integer, String> msgUrl = new HashMap<Integer, String>();
	private HashMap<Integer, Integer> proStatusProcess = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> targetStatusProcess = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> taskStatusProcess = new HashMap<Integer, Integer>();
	private HashMap<Integer, String> statusName = new HashMap<Integer, String>();
	
	public HashMap<Integer, String> getHistoryType() {
		return HistoryType;
	}

	public void setHistoryType(HashMap<Integer, String> historyType) {
		HistoryType = historyType;
	}

	@SuppressWarnings("rawtypes")
	public String getConfigTextString() {
		Iterator iter = ConfigText.entrySet().iterator();
		String text = "";
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			text += entry.getKey() + ":" + entry.getValue() + ";";
		}
		return text;
	}

	public void setConfigText(HashMap<String, Integer> configText) {
		ConfigText = configText;
	}

	public void setConfigTextString(String configText) {
		String[] ss = configText.split(";");
		for (String config : ss) {
			String[] detail = config.split(":");
			UpdateConfigText(detail[0], Integer.parseInt(detail[1]));
		}
	}

	public HashMap<String, Integer> getConfigText() {
		return ConfigText;
	}

	public DefineConfig() {
		HistoryType.put(1, getText("history.type1"));
		HistoryType.put(2, getText("history.type2"));
		HistoryType.put(3, getText("history.type3"));
		HistoryType.put(4, getText("history.type4"));
		HistoryType.put(5, getText("history.type5"));
		HistoryType.put(6, getText("history.type6"));
		
		HistoryStatus.put(0, getText("unknown"));
		HistoryStatus.put(1, getText("add"));
		HistoryStatus.put(2, getText("edit"));
		HistoryStatus.put(3, getText("finish"));
		HistoryStatus.put(4, getText("cancel"));
		
		ConfigText.put("TODO", 5);
		ConfigText.put("DOING", 5);
		ConfigText.put("PRO", 5);
		ConfigText.put("CHAT", 5);
		ConfigText.put("HELP", 5);
		ConfigText.put("FLOW", 5);
		ConfigText.put("IDEA", 5);
		ConfigText.put("MSG", 5);
		
		msgType.put(0, getText("msg.src_type.unknown"));
		msgType.put(1, getText("msg.src_type.sys"));
		msgType.put(2, getText("msg.src_type.pro"));
		msgType.put(3, getText("msg.src_type.flow"));
		msgType.put(4, getText("msg.src_type.user"));
		msgType.put(5, getText("msg.src_type.broadcast"));
		msgType.put(6, getText("msg.src_type.idea"));
		msgType.put(7, getText("msg.src_type.help"));
		msgType.put(8, getText("msg.src_type.chat"));
		msgType.put(9, getText("msg.src_type.protarget"));
		msgType.put(10, getText("msg.src_type.protask"));
		
		msgUrl.put(1, "Msg");
		msgUrl.put(2, "Project");
		msgUrl.put(3, "Flow");
		msgUrl.put(4, "Msg");
		msgUrl.put(5, "Msg");
		msgUrl.put(6, "Idea");
		msgUrl.put(7, "Help");
		msgUrl.put(8, "Chat");
		msgUrl.put(9, "ProTarget");
		msgUrl.put(10, "ProTask");
		
		proStatusProcess.put(0, 100);
		proStatusProcess.put(100, 200);
		proStatusProcess.put(200, 100);
		
		targetStatusProcess.put(0, 100);
		targetStatusProcess.put(100, 200);
		targetStatusProcess.put(200, 100);
		
		taskStatusProcess.put(0, 100);
		taskStatusProcess.put(100, 200);
		taskStatusProcess.put(100, 300);
		
		statusName.put(0, getText("protask.status.todo"));
		statusName.put(100, getText("protask.status.doing"));
		statusName.put(200, getText("protask.status.done"));
		statusName.put(300, getText("protask.status.cancel"));
		
	}
	
	public String GetStatusName(Integer status){
		return statusName.get(status);
	}
	
	public Integer GetProStatusNextProcess(Integer status){
		return proStatusProcess.get(status);
	}
	
	public Integer GetTargetStatusNextProcess(Integer status){
		return targetStatusProcess.get(status);
	}
	
	public Integer GetTaskStatusNextProcess(Integer status){
		return taskStatusProcess.get(status);
	}
	
	public String GetMsgTypeName(int type){
		return msgType.get(type);
	}
	
	public HashMap<Integer, String> GetMsgTypeMap(){
		return msgType;
	}
	
	public String GetMsgUrl(int type){
		return msgUrl.get(type);
	}

	public void UpdateConfigText(String type, int val) {
		ConfigText.put(type, val);
	}

	public Integer GetConfigValue(String type) {
		return ConfigText.get(type);
	}

	public String UserHistoryType(int type) {
		return HistoryType.get(type);
	}
	
	public String UserHistoryStatus(int status){
		return HistoryStatus.get(status);
	}
}