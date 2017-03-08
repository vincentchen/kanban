package com.idealism.kanban.service;

import java.util.Date;
import java.util.List;

import com.idealism.kanban.model.Help;
import com.idealism.kanban.vo.HelpInfo;

public interface HelpService {
	@SuppressWarnings("rawtypes")
	public abstract List LoadHelpNotMy(HelpInfo helpInfo);
	
	@SuppressWarnings("rawtypes")
	public abstract List LoadHelpWithMy(HelpInfo helpInfo);

	public abstract Help LoadHelpById(int id);

	public abstract void Save(Help help);

	@SuppressWarnings("rawtypes")
	public abstract List LoadHelpFiles(int id);

	public abstract Boolean CheckCanDelete(int id, int user_id);

	public abstract void Delete(int id);

	public abstract void UpdateAccept(int id, int user_id);

	public abstract Boolean CheckCanAccept(int id);

	public abstract Boolean CheckCanRefuse(int id, int user_id);

	public abstract void UpdateFinish(int id, Date date);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAllHelp(HelpInfo helpInfo);

	@SuppressWarnings("rawtypes")
	public abstract List LoadAllUserByHelpCnt(HelpInfo helpInfo);

	public abstract HelpInfo LoadHelpInfoById(int id);
}
