package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Group;
import com.cht.emm.vo.GroupVO;


public interface GroupService extends IBaseService<Group, String> {

	public abstract GroupVO getById(String id);

	public abstract List<GroupVO> getAllGroups();

	public List<GroupVO> selectGroups(String type, String id);

	/**
	 * @Name: getTopGroup
	 * @Decription: 查找最顶层的group
	 * @Time: 2015-1-12 下午4:39:58
	 * @return GroupVO
	 */
	public GroupVO getTopGroup();
	
	/**
	 * @Name: saveGroupUser
	 * @Decription: 保存用户和组
	 * @Time: 2015-3-24 上午10:43:02
	 * @param groupId
	 * @param userIds
	 * @return GroupVO
	 */
	public abstract GroupVO saveGroupUser(String groupId, String userIds);

	/**
	 * @Name: saveSubGroup
	 * @Decription: 保存子组
	 * @Time: 2015-3-24 上午10:43:20
	 * @param groupId
	 * @param subIds
	 * @return GroupVO
	 */
	public abstract GroupVO saveSubGroup(String groupId, String subIds);

	/**
	 * @Name: queryForPage
	 * @Decription: 按页查询
	 * @Time: 2015-3-24 上午10:43:37
	 * @param count
	 * @param whereClause
	 * @param orderby
	 * @param pn
	 * @param length
	 * @return Page<GroupVO>
	 */
	public Page<GroupVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length);

	/**
	 * @Name: queryForPage
	 * @Decription: 按页查询
	 * @Time: 2015-3-24 上午10:44:03
	 * @param count
	 * @param query
	 * @param orderBy
	 * @param pn
	 * @param pageSize
	 * @return Page<GroupVO>
	 */
	public Page<GroupVO> queryForPage(Integer count, ConditionQuery query,
			OrderBy orderBy, int pn, int pageSize);
	
	/**
	 * @Name: getSubGroupListIDs
	 * @Decription: 获得某个组下属的所有group Id
	 * @Time: 2015-1-16 下午2:26:33
	 * @param topId
	 * @return List<String>
	 */
	public List<String> getSubGroupListIDs(String topId);

	/**
	 * @Name: getDepartStruct
	 * @Decription: 获取部门结构
	 * @Time: 2015-3-24 上午10:44:20
	 * @param groupId
	 * @return GroupVO
	 */
	public GroupVO getDepartStruct(String groupId);

	/**
	 * @Name: checkSubName
	 * @Decription: 检查子组中是否有重名
	 * @Time: 2015-3-24 上午10:44:37
	 * @param id		子组id，不为空时，需排除原来的名称	
	 * @param parentId	父组id
	 * @param name		要检查的名称
	 * @return boolean	不存在返回true
	 */
	boolean checkSubName(String id, String parentId, String name);
	
	/**
	 * @Name: getThirdpartTopGroup
	 * @Decription: 获得第三方子组的顶级组，所有第三方接入的映射组 必须挂靠在该组下
	 * @Time: 2015-3-24 上午10:46:32
	 * @return Group
	 */
	public Group getThirdpartTopGroup();
	
}
