package com.cht.emm.dao.impl;

import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.ApplicationAuthorizationDao;
import com.cht.emm.model.id.ApplicationAuthorization;

@Repository
public class ApplicationAuthorizationDaoImpl extends
		BaseHibernateDao<ApplicationAuthorization, String> implements
		ApplicationAuthorizationDao {

}
