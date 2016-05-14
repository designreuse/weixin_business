package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Resource;
import com.cht.emm.model.User;
import com.cht.emm.model.id.UserDevice;
import com.cht.emm.vo.RoleVO;
import com.cht.emm.vo.UserVO;


/**
 * @description
 * @author 张凯
 * @date 2014-8-16
 */
public interface UserService extends IBaseService<User, String> {

	/* 根据角色名获取所有资源 */
	public List<Resource> findResourcesByRoleId(String roleName);

	/* 根据用户名获取用户的角色 */
	public List<RoleVO> findUserRolesByUsername(String userName);

	/* 根据用户名获取用户信息 */
	public User loadUserByUserName(String username);

	public List<UserVO> getAllUsers();

	public UserVO getUserById(String id);

	public UserVO saveUserRole(String userId, String roleId);

	public UserVO saveUserGroup(String userId, String groupIds);

	public UserVO removeUserGroup(String userId, String groupIds);

	public UserVO removeUserRole(String userId, String roleIds);

	public UserVO saveUser(String id, String username, String password,
			String groups, String roles, String userAlias, String mobile,
			String email, Integer sex);

	public List<UserVO> selectUser(String type, String id);

	public User checkUser(String username, String password);

	public Page<UserVO> queryForPage(Integer count, ConditionQuery query,
			OrderBy orderBy, int pn, int pageSize);

	/**
	 * @Title: queryForPage
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param queryAll
	 * @param whereClause
	 * @param orderby
	 * @param pn
	 * @param length
	 * @return
	 * @return Page<UserVO> 返回类型
	 * @throws
	 */
	public Page<UserVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length);

	/**
	 * @Title: getDevices
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return
	 * @return List<String> 返回类型
	 * @throws
	 */
	public List<UserDevice> getDevices(String id);
	
	
	public boolean isAdmin(String userId);

}
