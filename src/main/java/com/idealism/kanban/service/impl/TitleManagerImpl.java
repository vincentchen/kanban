package com.idealism.kanban.service.impl;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.idealism.kanban.dao.TitleDao;
import com.idealism.kanban.service.TitleManager;
import com.idealism.kanban.vo.TitleInfo;

@Service("TitleManager")
public class TitleManagerImpl implements TitleManager {
	@Autowired
	@Qualifier("TitleDao")
	private TitleDao titleDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idealism.kanban.service.TitleManager#UserTitleCnt()
	 */
	@SuppressWarnings("rawtypes")
	public TitleInfo UserTitleCnt(int user_id) {
		List list = titleDao.LoadCntByUserWhereNoRead(user_id);
		Iterator<?> it = list.iterator();
		TitleInfo titleInfo = new TitleInfo();
		while (it.hasNext()) {
			Object[] msg = (Object[]) it.next();
			switch ((Integer)msg[0]) {
			case 1:
			case 4:
			case 5:
			case 9:
			case 10:
				titleInfo.setMsgCnt(((BigInteger)msg[1]).intValue());
				break;
			case 3:
				titleInfo.setApprovalCnt(((BigInteger)msg[1]).intValue());
				break;
			case 6:
				titleInfo.setIdeaCnt(((BigInteger)msg[1]).intValue());
				break;
			case 2:
				titleInfo.setProCnt(((BigInteger)msg[1]).intValue());
				break;
			case 7:
				titleInfo.setHelpCnt(((BigInteger)msg[1]).intValue());
				break;
			case 8:
				titleInfo.setChatCnt(((BigInteger)msg[1]).intValue());
				break;
			}
		}
		return titleInfo;
	}
}