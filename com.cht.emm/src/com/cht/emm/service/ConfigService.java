package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Config;
import com.cht.emm.vo.ConfigVO;


public interface ConfigService extends IBaseService<Config, String> {

	public List<ConfigVO> queryForPage(int countFilter, String conditionQuery,
			String orderList, int i, Integer length);

}
