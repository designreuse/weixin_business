package com.cht.emm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.ApplicationVersion;
import com.cht.emm.vo.ApplicationVO;


public interface ApplicationVersionService extends
		IBaseService<ApplicationVersion, String> {

	List<ApplicationVO> queryForPage(int countFilter, String conditionQuery,
			String orderList, int i, Integer length, HttpServletRequest request);

}
