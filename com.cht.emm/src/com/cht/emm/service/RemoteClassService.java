/**
 * @Title: RemoteClassService.java
 * @Package: nari.mip.backstage.service
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-2 下午2:38:53
 * @Version: 1.0
 */
package com.cht.emm.service;

import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.RemoteClass;
import com.cht.emm.vo.RemoteClassVO;

/**
 * @Class: RemoteClassService
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public interface RemoteClassService extends IBaseService<RemoteClass, String> {

	/**
	 * @Name: queryForPage
	 * @Decription: 
	 * @Time: 2015-4-2 下午4:43:04
	 * @param countFilter
	 * @param query
	 * @param orderList
	 * @param pageNum
	 * @param pageSize
	 * @return Page<RemoteClassVO>
	 */
	Page<RemoteClassVO> queryForPage(int countFilter, String query,
			String orderList, int pageNum, Integer pageSize);

	/**
	 * @Name: getById
	 * @Decription: 
	 * @Time: 2015-4-2 下午5:24:25
	 * @param id
	 * @return RemoteClassVO
	 */
	RemoteClassVO getById(String id);
}
