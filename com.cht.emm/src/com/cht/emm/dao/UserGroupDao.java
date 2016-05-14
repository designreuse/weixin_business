package com.cht.emm.dao;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.UnionEntityGetPK;
import com.cht.emm.model.id.UserGroup;

public interface UserGroupDao extends IBaseDao<UserGroup, String>,
		UnionEntityGetPK<String, String, String> {

}