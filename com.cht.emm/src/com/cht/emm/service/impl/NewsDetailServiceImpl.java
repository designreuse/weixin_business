package com.cht.emm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.NewsDetail;
import com.cht.emm.service.NewsDetailService;


@Service
public class NewsDetailServiceImpl extends BaseService<NewsDetail, String>
		implements NewsDetailService {

	@Resource(name = "newsDetailDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<NewsDetail, String> baseDao) {
		this.baseDao = baseDao;
	}

}
