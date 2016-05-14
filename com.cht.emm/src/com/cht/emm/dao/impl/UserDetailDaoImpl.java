/**   
* @Title: UserDetailDaoImpl.java 
* @Package nari.mip.backstage.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
* @date 2014-9-23 上午11:15:32 
* @version V1.0   
*/
package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.UserDetailDao;
import com.cht.emm.model.UserDetail;


/** 
 * @ClassName: UserDetailDaoImpl 
 * @Description: 实现 UserDetailDao
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
 * @date 2014-9-23 上午11:15:32 
 *  
 */
@Repository("userDetailDao")
public class UserDetailDaoImpl extends BaseHibernateDaoUnDeletabke<UserDetail,String> implements UserDetailDao {

	 

}
