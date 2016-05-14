package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.NotificationMODao;
import com.cht.emm.model.NotificationMO;

@Repository
public class NotificationMODaoImpl extends
		BaseHibernateDao<NotificationMO, String> implements NotificationMODao {

}
