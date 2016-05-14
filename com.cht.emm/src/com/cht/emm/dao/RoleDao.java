package com.cht.emm.dao;

import java.util.List;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.model.Resource;
import com.cht.emm.model.Role;


public interface RoleDao extends IBaseDao<Role, String> {

	public abstract List<Resource> findResourceByRoleId(String roleId);

	public abstract List<Role> excludedList(String excluded, String excludedId);

}