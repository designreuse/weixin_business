/**
 * @Title: RemoteDaoImpl.java
 * @Package: nari.mip.backstage.dao.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-2 下午2:45:45
 * @Version: 1.0
 */
package com.cht.emm.dao.impl;

import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.RemoteClassDao;
import com.cht.emm.model.RemoteClass;


/**
 * @Class: RemoteDaoImpl
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Repository("remoteClassDao")
public class RemoteDaoImpl extends BaseHibernateDao<RemoteClass, String> implements RemoteClassDao {

}
