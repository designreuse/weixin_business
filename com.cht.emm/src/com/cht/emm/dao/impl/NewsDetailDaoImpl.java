package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.NewsDetailDao;
import com.cht.emm.model.NewsDetail;

@Repository
public class NewsDetailDaoImpl extends
		BaseHibernateDaoUnDeletabke<NewsDetail, String> implements
		NewsDetailDao {

}
