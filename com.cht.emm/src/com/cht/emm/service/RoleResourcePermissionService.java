/**
 * 
 */
package com.cht.emm.service;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.common.service.UnionEntityDeleteTrait;
import com.cht.emm.model.id.RoleResourcePermission;

/**
 * @author zhang-kai
 * 
 */
public interface RoleResourcePermissionService extends
		IBaseService<RoleResourcePermission, String>,
		UnionEntityDeleteTrait<String, String> {
	public String getPK(String roleId,String resourceId);
}
