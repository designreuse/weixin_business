package com.cht.emm.dao;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.UnionEntityGetPK;
import com.cht.emm.model.id.RoleResourcePermission;

public interface RoleResourcePermissionDao extends
		IBaseDao<RoleResourcePermission, String>,
		UnionEntityGetPK<String, String, String> {

}
