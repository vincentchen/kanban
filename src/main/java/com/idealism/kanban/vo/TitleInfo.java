package com.idealism.kanban.vo;

import java.io.Serializable;

public class TitleInfo implements Serializable {
	private int MsgCnt;
	private int ApprovalCnt;
	private int IdeaCnt;
	private int ProCnt;
	private int HelpCnt;
	private int ChatCnt;

	public TitleInfo() {
		MsgCnt = 0;
		ApprovalCnt = 0;
		IdeaCnt = 0;
		ProCnt = 0;
		HelpCnt = 0;
		ChatCnt = 0;
	}

	public int getMsgCnt() {
		return MsgCnt;
	}

	public void setMsgCnt(int msgCnt) {
		MsgCnt = msgCnt;
	}

	public int getApprovalCnt() {
		return ApprovalCnt;
	}

	public void setApprovalCnt(int approvalCnt) {
		ApprovalCnt = approvalCnt;
	}

	public int getIdeaCnt() {
		return IdeaCnt;
	}

	public void setIdeaCnt(int ideaCnt) {
		IdeaCnt = ideaCnt;
	}

	public int getProCnt() {
		return ProCnt;
	}

	public void setProCnt(int proCnt) {
		ProCnt = proCnt;
	}

	public int getHelpCnt() {
		return HelpCnt;
	}

	public void setHelpCnt(int helpCnt) {
		HelpCnt = helpCnt;
	}

	public int getChatCnt() {
		return ChatCnt;
	}

	public void setChatCnt(int chatCnt) {
		ChatCnt = chatCnt;
	}
}