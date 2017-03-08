package com.idealism.kanban.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.StrutsStatics;

import com.idealism.kanban.vo.JsonBase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AjaxActionBase extends ActionSupport {
	private JsonBase jsonBase = new JsonBase();

	public void SetJsonBaseContent(Object content) {
		jsonBase.setContent(content);
	}

	//直接返回字符串
	public void SetJsonBaseError(String error) {
		jsonBase.setError(error);
	}

	//返回国际化编码转换之后的字符串
	public void SetJsonBaseErrorText(String error) {
		jsonBase.setError(getText(error));
	}

	public void Error(String error) throws IOException {
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setCharacterEncoding("utf-8");

		PrintWriter out = null;
		out = response.getWriter();
		out.print(error);
		out.flush();
		out.close();
	}

	public void ErrorText(String error) throws IOException {
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setCharacterEncoding("utf-8");

		PrintWriter out = null;
		out = response.getWriter();
		out.print(getText(error));
		out.flush();
		out.close();
	}

	public void CallBackJSON() throws IOException {
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setCharacterEncoding("utf-8");

		PrintWriter out = null;
		out = response.getWriter();

		out.print(JSONArray.fromObject(jsonBase));
		out.flush();
		out.close();
	}

	public void Success() throws IOException {
		Error("ok");
	}
}