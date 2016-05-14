package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.ApplicationDeployDao;
import com.cht.emm.model.id.ApplicationDeploy;

@Repository
public class ApplicationDeployDaoImpl extends
		BaseHibernateDao<ApplicationDeploy, String> implements
		ApplicationDeployDao {

}
