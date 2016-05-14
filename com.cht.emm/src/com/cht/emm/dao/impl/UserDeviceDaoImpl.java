package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.UserDeviceDao;
import com.cht.emm.model.id.UserDevice;

@Repository
public class UserDeviceDaoImpl extends BaseHibernateDao<UserDevice, String>
		implements UserDeviceDao {

}
