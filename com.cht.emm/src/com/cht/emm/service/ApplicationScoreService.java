package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.id.ApplicationScore;
import com.cht.emm.vo.ApplicationScoreVO;


public interface ApplicationScoreService extends
		IBaseService<ApplicationScore, String> {

	public List<ApplicationScoreVO> queryForPage(int count, String queryAll,
			String whereClause, String orderby, int pn, Integer length);

}
