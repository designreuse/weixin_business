package com.cht.emm.service;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.common.service.UnionEntityDeleteTrait;
import com.cht.emm.model.id.UserRole;
import com.cht.emm.vo.RoleVO;
import com.cht.emm.vo.UserVO;

public interface UserRoleService extends IBaseService<UserRole, String>,
		UnionEntityDeleteTrait<String, String> {

	public UserVO addUserRole(String userId, String roleIds);

	public RoleVO removeRoleUser(String roleId, String userIds);

	public RoleVO addRoleUser(String roleId, String userIds);

	UserVO updateUserRole(String userId, String roleIds);

	public String getPK(String userId,String roleId);
}
