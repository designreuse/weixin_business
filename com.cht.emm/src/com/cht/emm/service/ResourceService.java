package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Resource;
import com.cht.emm.vo.AuthVO;
import com.cht.emm.vo.ResourceVO;
import com.cht.emm.vo.RoleOpsVO;
import com.cht.emm.vo.RoleVO;


public interface ResourceService extends IBaseService<Resource, String> {

	public abstract List<ResourceVO> getResources();

	public abstract List<AuthVO> getAllAuths();

	public abstract Resource getById(String resource_id);

	public abstract ResourceVO getVMById(String resource_id);

	public abstract List<AuthVO> getAuths(String[] auth_id);

	public abstract ResourceVO saveResourceAndAuth(String resourceId,
			String authIds, String uris);

	public abstract void saveResourceRole(String resourceId, String roleIds,
			String authIds);

	public abstract List<RoleOpsVO> addResourceRole(String resourceId,
			String roleIds);

	public abstract boolean removeRoleResource(String roleId, String resourceId);

	public abstract RoleVO addRoleResource(String resourceIds, String roleId);

	/**
	 * @Name: selectResource
	 * @Decription: 根据类型和Id选择合适的resource显示
	 * @Time: 2014-12-23 下午4:23:53
	 * @param type
	 * @param id
	 * @return List<ResourceVO>
	 */
	public abstract List<ResourceVO> selectResource(String type, String id);

	/**
	 * @Title: queryForPage
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param countFilter
	 * @param string
	 * @param conditionQuery
	 * @param orderList
	 * @param i
	 * @param length
	 * @return
	 * @return Page<ResourceVO> 返回类型
	 * @throws
	 */
	public abstract Page<ResourceVO> queryForPage(int count,
			String conditionQuery, String orderList, int i, Integer length);

	// public abstract
	
	public void releaseAuth(String[] ids);
}
