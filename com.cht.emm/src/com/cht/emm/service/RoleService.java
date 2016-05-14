package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Role;
import com.cht.emm.vo.RoleVO;


public interface RoleService extends IBaseService<Role, String> {

	public abstract RoleVO getById(String id);

	/**
	 * @Title: getAllRoles
	 * @Description: 返回全部的角色
	 * @return
	 * @return List<RoleVO> 返回类型
	 * @throws
	 */
	public abstract List<RoleVO> getAllRoles();

	public abstract List<RoleVO> getAVRoles();
	/**
	 * @Title: selectRoles
	 * @Description: 按照一定要求查询角色
	 * @param excluded
	 * @param excludedId
	 * @return
	 * @return List<RoleVO> 返回类型
	 * @throws
	 */
	public abstract List<RoleVO> selectRoles(String excluded, String excludedId);

	/**
	 * @Name: selectRole
	 * @Decription: 
	 * @Time: 2015-2-13 上午10:31:23
	 * @param query
	 * @param orderBy
	 * @param pn
	 * @param pageSize
	 * @return List<RoleVO>
	 */
	public abstract List<RoleVO> selectRole( Integer userType);
	/**
	 * @Title: addRole
	 * @Description: 新增
	 * @param roleName
	 * @param desc
	 * @return
	 * @return RoleVO 返回类型
	 * @throws
	 */
	public abstract RoleVO addRole(String roleName, String desc);

	/**
	 * @Title: saveUserRole
	 * @Description: 保存角色-用户关联关系
	 * @param roleId
	 * @param userIds
	 * @return
	 * @return RoleVO 返回类型
	 * @throws
	 */
	public abstract RoleVO saveUserRole(String roleId, String userIds);

	/**
	 * @Title: queryForPage
	 * @Description: 分页查询，标准格式
	 * @param countFilter
	 * @param conditionQuery
	 * @param orderBy
	 * @param i
	 * @param length
	 * @return
	 * @return Page<RoleVO> 返回类型
	 * @throws
	 */
	public abstract Page<RoleVO> queryForPage(int countFilter,
			ConditionQuery conditionQuery, OrderBy orderBy, int i,
			Integer length);

	/**
	 * @Title: queryForPage
	 * @Description: 分页查询，非标准格式
	 * @param count
	 * @param queryAll
	 * @param whereClause
	 * @param orderby
	 * @param pn
	 * @param length
	 * @return
	 * @return Page<GroupVO> 返回类型
	 * @throws
	 */
	public Page<RoleVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length);
}
