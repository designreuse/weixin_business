package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.ApplicationTypeAppDao;
import com.cht.emm.model.id.ApplicationTypeApp;

@Repository
public class ApplicationTypeAppDaoImpl extends
		BaseHibernateDao<ApplicationTypeApp, String> implements
		ApplicationTypeAppDao {

}
