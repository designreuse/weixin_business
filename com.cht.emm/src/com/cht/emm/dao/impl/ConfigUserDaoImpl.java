package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.ConfigUserDao;
import com.cht.emm.model.id.ConfigUser;

@Repository
public class ConfigUserDaoImpl extends BaseHibernateDao<ConfigUser, String>
		implements ConfigUserDao {

}
