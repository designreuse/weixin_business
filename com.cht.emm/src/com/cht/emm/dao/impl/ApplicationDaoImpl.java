package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.ApplicationDao;
import com.cht.emm.model.Application;

@Repository
public class ApplicationDaoImpl extends BaseHibernateDaoUnDeletabke<Application, String>
		implements ApplicationDao {

}
