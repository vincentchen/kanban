package com.idealism.kanban.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//让注解保存到程序运行阶段
@Retention(RetentionPolicy.RUNTIME)
// 当前注解只能应用在方法上
@Target(ElementType.METHOD)
public @interface ConfigPrivilege {
	/**
	 * 设置权限名称(使用define名称)
	 * 
	 * @return 权限名称
	 */
	public String privilegeName();

	/*
	 * 设置权限值(参数分别为1=增，2=删，3=改，4=查)
	 * 
	 * @return 权限值
	 */
	public String privilegeValue();
}
