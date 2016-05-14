package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.ApplicationType;
import com.cht.emm.vo.ApplicationTypeVO;


public interface ApplicationTypeService extends
		IBaseService<ApplicationType, String> {

	public List<ApplicationTypeVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length);

	public void deleteAppType(String[] ids);

	public boolean checkAppTypeNameExist(String name, String id);
}
