package com.idealism.kanban.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.idealism.kanban.model.Help;
import com.idealism.kanban.vo.HelpInfo;


public interface HelpDao {
	@SuppressWarnings("rawtypes")
	public abstract List LoadHelpNotMy(HelpInfo helpInfo);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadHelpWithMy(HelpInfo helpInfo);
	
	public abstract Boolean HasAttach(int id);

	public abstract Help LoadHelpById(int id);

	public abstract void Save(Help help);

	@SuppressWarnings("rawtypes")
	public abstract List LoadHelpFiles(int id);

	public abstract BigInteger CheckCanDelete(int id, int user_id);

	public abstract void Delete(int id);

	public abstract void UpdateAccept(int id, int user_id);

	public abstract BigInteger CheckCanAccept(int id);

	public abstract BigInteger CheckCanRefuse(int id, int user_id);

	public abstract void UpdateFinish(int id, Date date);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAllHelp(HelpInfo helpInfo);

	public abstract int LoadCntBySeekHelpUser(int user_id, HelpInfo info);

	public abstract int LoadCntByHelper(int user_id, HelpInfo info);

	public abstract int CheckIsRead(int id, int user_id);

	public abstract int LoadMsgIDByHelpId(int id);

	public abstract void UpdateMsgReadStatus(int msg_id, int user_id);

}
