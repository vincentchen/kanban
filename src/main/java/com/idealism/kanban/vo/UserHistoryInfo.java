package com.idealism.kanban.vo;

import com.idealism.kanban.util.Page;

public class UserHistoryInfo extends Page {
	private String historyTypeName;
	private String historyContent;
	private String historyLogTime;

	public String getHistoryTypeName() {
		return historyTypeName;
	}

	public void setHistoryTypeName(String historyTypeName) {
		this.historyTypeName = historyTypeName;
	}

	public String getHistoryContent() {
		return historyContent;
	}

	public void setHistoryContent(String historyContent) {
		this.historyContent = historyContent;
	}

	public String getHistoryLogTime() {
		return historyLogTime;
	}

	public void setHistoryLogTime(String historyLogTime) {
		this.historyLogTime = historyLogTime;
	}

}