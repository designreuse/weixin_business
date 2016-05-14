package com.cht.emm.dao;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.UnionEntityGetPK;
import com.cht.emm.model.id.UserRole;

public interface UserRoleDao extends IBaseDao<UserRole, String>,
		UnionEntityGetPK<String, String, String> {

}
