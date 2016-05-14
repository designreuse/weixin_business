package com.cht.emm.dao;

import java.util.List;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.model.Resource;
import com.cht.emm.model.id.RoleResourcePermission;


public interface ResourceDao extends IBaseDao<Resource, String> {

	public abstract RoleResourcePermission getRoleResource(String roleId,
			String resourceId);

	public abstract List<Resource> selectResource(String type, String id);

}