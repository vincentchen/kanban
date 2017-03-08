package com.idealism.kanban.util;

import java.math.BigInteger;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.idealism.kanban.vo.PrivilegeInfo;
import com.opensymphony.xwork2.ActionContext;

public class PrivilegeUtil {

	// 权限字符串转换为二进制，如15转为1111
	public String PriStringToDec(String pri) {
		return Integer.toBinaryString(Integer.parseInt(pri));
	}

	// 权限字符串转换为二进制，如15转为1111
	public Object PriObjToDec(String pri) {
		String priString = String.format("%04d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(pri))));
		PrivilegeInfo privilegeInfo = new PrivilegeInfo();
		privilegeInfo.setADD(priString.substring(0, 1));
		privilegeInfo.setDEL(priString.substring(1, 2));
		privilegeInfo.setWRITE(priString.substring(2, 3));
		privilegeInfo.setREAD(priString.substring(3, 4));
		return privilegeInfo;
	}

	// 数组转换为权限字符串，如1111转为15
	public String PriDecToString(String pri) {
		BigInteger src = new BigInteger(pri, 2);
		String newString = String.format("%02d", Integer.parseInt(src.toString()));  
		return newString;  
	}

	// 根据传入参数获取权限
	public String GetPriByDef(String PriDef) {
		return ActionContext.getContext().getSession().get(PriDef).toString();
	}

	// 根据传入参数获取详细权限,注：Position参数分别为1=增，2=删，3=改，4=查
	public int GetPriWithDetail(String PriDef, int Position) {
		HttpSession s = ServletActionContext.getRequest().getSession();
		if (s.getAttribute(PriDef) != null && s.getAttribute(PriDef) != "") {
			HttpSession session = ServletActionContext.getRequest().getSession();
			PrivilegeInfo priInfo = (PrivilegeInfo) session.getAttribute(PriDef);
			int pri = 0;
			switch(Position){
				case 1:
					pri = Integer.parseInt(priInfo.getADD());
					break;
				case 2:
					pri = Integer.parseInt(priInfo.getDEL());
					break;
				case 3:
					pri = Integer.parseInt(priInfo.getWRITE());
					break;
				case 4:
					pri = Integer.parseInt(priInfo.getREAD());
					break;
			}
			return pri; 
		} else {
			return 0;
		}
	}

}