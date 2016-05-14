package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Strategy;
import com.cht.emm.vo.StrategyVO;


public interface StrategyService extends IBaseService<Strategy, String> {

	public List<StrategyVO> queryForPage(int countFilter,
			String conditionQuery, String orderList, int i, Integer length);

}
