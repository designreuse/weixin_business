package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.ApplicationVersion;
import com.cht.emm.service.ApplicationVersionService;
import com.cht.emm.vo.ApplicationVO;

import nariis.pi3000.framework.utility.StringUtil;

@Service
public class ApplicationVersionServiceImpl extends
		BaseService<ApplicationVersion, String> implements
		ApplicationVersionService {

	@Resource(name = "applicationVersionDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<ApplicationVersion, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ApplicationVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length, HttpServletRequest request) {
		List<ApplicationVersion> appVersions = this.baseDao.listAll(
				whereClause, orderby, pn, length);
		List<ApplicationVO> vos = new ArrayList<ApplicationVO>();
		String prePath = StringUtil.substringBefore(request.getRequestURL()
				.toString(), "rest/app/appVersions");
		for (ApplicationVersion appVersion : appVersions) {
			ApplicationVO vo = new ApplicationVO();
			vo.setVersion_name(appVersion.getVersion_name());
			vo.setUpdateDesc(appVersion.getUpdateDesc());
			String suffixPath = StringUtil.substringAfterLast(
					appVersion.getUrl(), "uploads");
			vo.setUrl(prePath + "uploads" + StringUtil.replace(suffixPath, "\\", "/"));
			vos.add(vo);
		}
		return vos;
	}
}
