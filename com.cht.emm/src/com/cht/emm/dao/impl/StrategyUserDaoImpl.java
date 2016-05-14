package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.StrategyUserDao;
import com.cht.emm.model.id.StrategyUser;

@Repository
public class StrategyUserDaoImpl extends BaseHibernateDao<StrategyUser, String>
		implements StrategyUserDao {

}
