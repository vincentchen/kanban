package com.idealism.kanban.action.interceptor;

import java.lang.reflect.Method;

import com.idealism.kanban.util.ConfigPrivilege;
import com.idealism.kanban.util.PrivilegeUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class PrivilegeInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionProxy actionProxy = invocation.getProxy();
		// 获取目标Action的字节码
		Class<? extends Object> actionClass = actionProxy.getAction().getClass();
		// 获取要执行的目标Action的方法名称
		String methodName = actionProxy.getMethod();
		// 获取字节码中的指定方法对象
		Method method = actionClass.getMethod(methodName);
		// 获取目标方法上的ConfigPrivilege注解
		ConfigPrivilege configPrivilege = method.getAnnotation(ConfigPrivilege.class);
		if (configPrivilege == null) {
			return invocation.invoke();
		}
		// 获取注解的privilegeName属性值
		String privilegeName = configPrivilege.privilegeName();

		if (privilegeName.equals("") || privilegeName == null) {
			return invocation.invoke();
		}
		// 获取注解的privilegeValue属性值
		int privilegeValue = Integer.parseInt(configPrivilege.privilegeValue());
		// 使用公共权限工具
		PrivilegeUtil priutil = new PrivilegeUtil();
		int hasPri = priutil.GetPriWithDetail(privilegeName, privilegeValue);
		if (hasPri == 1) {
			return invocation.invoke();
		}
		if(method.getReturnType().equals(Void.TYPE)){
			return "noPriByAjax";
		}else{
			return "noPrivilege";
		}
		
	}
}
