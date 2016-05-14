package com.cht.emm.service;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.common.service.UnionEntityDeleteTrait;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.vo.GroupVO;
import com.cht.emm.vo.UserVO;

public interface UserGroupService extends IBaseService<UserGroup, String>,
		UnionEntityDeleteTrait<String, String> {

	public UserVO addUserGroup(String userId, String groupIds);

	public GroupVO addGroupUser(String groupId, String userIds);

	public GroupVO removeGroupUser(String groupId, String userIds);

}
