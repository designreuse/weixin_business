package com.cht.emm.dao;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.model.Token;

public interface TokenDao extends IBaseDao<Token, String> {

	public abstract Token getTokenByUserAndDevice(String uname, String devid);

}
