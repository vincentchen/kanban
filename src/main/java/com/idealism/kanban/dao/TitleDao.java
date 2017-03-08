package com.idealism.kanban.dao;

import java.util.List;

public interface TitleDao {

	@SuppressWarnings("rawtypes")
	public abstract List LoadCntByUserWhereNoRead(int user_id);

	public abstract int LoadMsgCntByUserWhereNoRead(int user_id);

	public abstract int LoadFlowCntByUserWhereNoRead(int user_id);

	public abstract int LoadIdeaCntByUserWhereNoRead(int user_id);

	public abstract int LoadProCntByUserWhereNoRead(int user_id);

	public abstract int LoadHelpCntByUserWhereNoRead(int user_id);

	public abstract int LoadChatCntByUserWhereNoRead(int user_id);
}