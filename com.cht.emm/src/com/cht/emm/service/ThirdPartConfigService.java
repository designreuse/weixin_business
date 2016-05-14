/**
 * @Title: ThirdPartConfigService.java
 * @Package: nari.mip.backstage.service
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-23 下午3:15:58
 * @Version: 1.0
 */
package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.ThirdPartConfig;
import com.cht.emm.vo.ThirdPartConfigVO;


/**
 * @Class: ThirdPartConfigService
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 第三方服务的服务接口
 */
public interface ThirdPartConfigService extends
		IBaseService<ThirdPartConfig,String> {
	
	/**
	 * @Name: checkThirdPartName
	 * @Decription:检查第三方接入名称是否存在
	 * @Time: 2015-3-24 上午11:27:15
	 * @param name
	 * @return Boolean
	 */
	Boolean checkThirdPartName(String name);

	/**
	 * @Name: queryForPage
	 * @Decription: 分页查询
	 * @Time: 2015-3-24 上午11:27:41
	 * @param countFilter
	 * @param conditionQuery
	 * @param orderList
	 * @param i
	 * @param length
	 * @return Page<GroupVO>
	 */
	Page<ThirdPartConfigVO> queryForPage(int countFilter, String conditionQuery,
			String orderList, int i, Integer length);

	/**
	 * @Name: getAllList
	 * @Decription: 返回可用的所有第三方接入列表
	 * @Time: 2015-3-24 上午11:27:57
	 * @return List<ThirdPartConfigVO>
	 */
	List<ThirdPartConfigVO> getAllList();

	/**
	 * @Name: getById
	 * @Decription: 获取config的VO
	 * @Time: 2015-3-27 下午3:15:29
	 * @param id
	 * @return ThirdPartConfigVO
	 */
	ThirdPartConfigVO getById(String id);

	/**
	 * @Name: releaseThirdPart
	 * @Decription: 删除第三方应用，删除组合用户
	 * @Time: 2015-3-30 上午9:51:30
	 * @param ids void
	 */
	void deleteThirdPart(String[] ids);
	
	
	
}
