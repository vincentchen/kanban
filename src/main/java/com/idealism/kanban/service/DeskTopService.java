package com.idealism.kanban.service;

import java.util.List;

import com.idealism.kanban.vo.DeskTopInfo;

public interface DeskTopService {

	@SuppressWarnings("rawtypes")
	public abstract List LoadToDo(DeskTopInfo deskTopInfo);

	@SuppressWarnings("rawtypes")
	public abstract List LoadDoingByUserID(DeskTopInfo deskTopInfo);

	@SuppressWarnings("rawtypes")
	public abstract List LoadProByUserID(DeskTopInfo deskTopInfo);

	@SuppressWarnings("rawtypes")
	public abstract List LoadIdeaExcludeUserID(DeskTopInfo deskTopInfo);

	@SuppressWarnings("rawtypes")
	public abstract List LoadHelpExcludeUserID(DeskTopInfo deskTopInfo);

	@SuppressWarnings("rawtypes")
	public abstract List LoadMsgByUserID(DeskTopInfo deskTopInfo);
}