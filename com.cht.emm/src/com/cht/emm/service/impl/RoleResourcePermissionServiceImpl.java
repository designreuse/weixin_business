/**
 * 
 */
package com.cht.emm.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.RoleResourcePermissionDao;
import com.cht.emm.model.id.RoleResourcePermission;
import com.cht.emm.service.RoleResourcePermissionService;


/**
 * @author zhang-kai
 *
 */
@Service("roleResourcePermissionService")
public class RoleResourcePermissionServiceImpl extends BaseService<RoleResourcePermission, String> implements
		RoleResourcePermissionService {

	@Resource (name="roleResourcePermissionDao")
	@Override
	public void setBaseDao(IBaseDao<RoleResourcePermission, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void deleteUnionEntityA2B(String pk1, String[] pk2s) {
		for (String spk : pk2s) {
			String pk = ((RoleResourcePermissionDao) baseDao).getPK(pk1,spk);
			if(pk!=null){
				delete(pk);
			}
		}
	}

	@Override
	public void deleteUnionEntityB2A(String pk1, String[] pk2s) {
		for (String spk : pk2s) {
			String pk = ((RoleResourcePermissionDao) baseDao).getPK(spk,pk1);
			if(pk!=null){
				delete(pk);
			}
		}
	}

	@Override
	public String getPK(String roleId, String resourceId) {
		// TODO Auto-generated method stub
		return ((RoleResourcePermissionDao)baseDao).getPK(roleId, resourceId);
	}
 

}
