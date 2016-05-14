package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.ApplicationScoreDao;
import com.cht.emm.model.id.ApplicationScore;

@Repository
public class ApplicationScoreDaoImpl extends
		BaseHibernateDao<ApplicationScore, String> implements
		ApplicationScoreDao {

}
