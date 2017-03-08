package com.idealism.kanban.action.interceptor;

import java.io.File;

import com.idealism.kanban.util.GetSystemPath;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class InstallInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		String lockPath = new GetSystemPath().GetWebRootPath() + "data/install.lock";
		File filename = new File(lockPath);
		
		if (!filename.exists()) {
			return "install";
		}
		
		return invocation.invoke();
	}

}