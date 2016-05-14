package com.cht.emm.service;

import java.util.List;
import java.util.Map;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.id.ApplicationDeploy;
import com.cht.emm.vo.ApplicationDeployVO;


public interface ApplicationDeployService extends
		IBaseService<ApplicationDeploy, String> {

	public List<ApplicationDeployVO> queryForPage(int count, String queryAll,
			String whereClause, String orderby, int pn, Integer length);

	public Map<String, Integer> getAppDownLoadWithPeriod();
}
