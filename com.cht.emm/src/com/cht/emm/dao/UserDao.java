package com.cht.emm.dao;

import java.util.List;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.model.User;


public interface UserDao extends IBaseDao<User, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nari.mip.backstage.dao.impl.User#getUsersByName(java.lang.String)
	 */
	public abstract List<User> getUsersByName(String username);

	public abstract List<User> excludedList(String type, String id);

	public boolean isAdmin(String userId);
}