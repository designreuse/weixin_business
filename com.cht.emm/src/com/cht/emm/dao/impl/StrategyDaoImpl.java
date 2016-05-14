package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.StrategyDao;
import com.cht.emm.model.Strategy;

@Repository
public class StrategyDaoImpl extends
		BaseHibernateDaoUnDeletabke<Strategy, String> implements StrategyDao {

}
