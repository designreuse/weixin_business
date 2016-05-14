package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.ApplicationTypeDao;
import com.cht.emm.model.ApplicationType;

@Repository
public class ApplicationTypeDaoImpl extends
		BaseHibernateDaoUnDeletabke<ApplicationType, String> implements
		ApplicationTypeDao {

}
