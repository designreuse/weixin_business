package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.NewsDao;
import com.cht.emm.model.News;

@Repository
public class NewsDaoImpl extends BaseHibernateDaoUnDeletabke<News, String>
		implements NewsDao {

}
