/**
 * @Title: RemoteClassServiceImpl.java
 * @Package: nari.mip.backstage.service.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-2 下午2:54:18
 * @Version: 1.0
 */
package com.cht.emm.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.pagination.PageUtil;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.RemoteClass;
import com.cht.emm.service.RemoteClassService;
import com.cht.emm.util.objectcopier.RemoteClassCopier;
import com.cht.emm.vo.RemoteClassVO;


/**
 * @Class: RemoteClassServiceImpl
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Service("remoteClassService")
public class RemoteClassServiceImpl extends BaseService<RemoteClass, String> implements
		RemoteClassService {

	@Resource(name="remoteClassDao")
	@Override
	public void setBaseDao(IBaseDao<RemoteClass, String> baseDao) {
		// TODO Auto-generated method stub
		this.baseDao = baseDao;
	}

	@Override
	public Page<RemoteClassVO> queryForPage(int count ,
			String conditionQuery, String orderList, int pageNum, Integer pageSize) {

		List<RemoteClass> configs = this.baseDao.listAll(conditionQuery,orderList,  pageNum,   pageSize);
		Page<RemoteClassVO> page = PageUtil.getPage(count, pageNum, RemoteClassCopier.copy(configs), pageSize);
		return page;
	}

	@Override
	public RemoteClassVO getById(String id) {
		// TODO Auto-generated method stub
		return RemoteClassCopier.copy(baseDao.get(id));
	}

	 

}
