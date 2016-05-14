package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.ApplicationVersionDao;
import com.cht.emm.model.ApplicationVersion;

@Repository
public class ApplicationVersionDaoImpl extends
		BaseHibernateDaoUnDeletabke<ApplicationVersion, String> implements
		ApplicationVersionDao {

}
