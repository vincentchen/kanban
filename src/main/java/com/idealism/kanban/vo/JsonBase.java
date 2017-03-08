package com.idealism.kanban.vo;

public class JsonBase {
	private Object Content;
	private String Error;

	public Object getContent() {
		return Content;
	}

	public void setContent(Object content) {
		Content = content;
	}

	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}
}