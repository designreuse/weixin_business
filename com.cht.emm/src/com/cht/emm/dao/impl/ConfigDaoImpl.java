package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.ConfigDao;
import com.cht.emm.model.Config;

@Repository
public class ConfigDaoImpl extends BaseHibernateDaoUnDeletabke<Config, String>
		implements ConfigDao {

}
