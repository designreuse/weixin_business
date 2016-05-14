package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.impl.ApplicationTypeAppDaoImpl;
import com.cht.emm.model.ApplicationType;
import com.cht.emm.model.id.ApplicationTypeApp;
import com.cht.emm.service.ApplicationTypeService;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.vo.ApplicationTypeVO;

import nariis.pi3000.framework.utility.StringUtil;

@Service
public class ApplicationTypeServiceImpl extends
		BaseService<ApplicationType, String> implements ApplicationTypeService {

	@Resource(name = "applicationTypeAppDaoImpl")
	private ApplicationTypeAppDaoImpl applicationTypeAppDaoImpl;

	@Resource(name = "applicationTypeDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<ApplicationType, String> baseDao) {
		this.baseDao = baseDao;
	}

	public List<ApplicationTypeVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length) {
		List<ApplicationType> appTypes = this.baseDao.listAll(whereClause,
				orderby, pn, length);
		List<ApplicationTypeVO> vos = new ArrayList<ApplicationTypeVO>();
		for (ApplicationType appType : appTypes) {
			ApplicationTypeVO vo = new ApplicationTypeVO();
			vo.setId(appType.getId());
			vo.setName(appType.getName());
			vo.setDescription(appType.getDescription() == null ? "" : appType
					.getDescription());
			vo.setCount(appType.getAppTypeApps().size());
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public void deleteAppType(String[] ids) {
		List<ApplicationType> appTypes = this.listByIds(ids);
		for (ApplicationType appType : appTypes) {
			if ("0".equals(appType.getId())) {
				continue;
			}
			// 删除该应用分类和应用的所有关联记录
			for (ApplicationTypeApp appTypeApp : appType.getAppTypeApps()) {

				// 若删除后该应用未与任何其它分类有关联，则将其转移到“默认分类”下
				if (applicationTypeAppDaoImpl.countAll(" where app.id='"
						+ appTypeApp.getApp().getId() + "' ") == 1) {
					ApplicationTypeApp newAppTypeApp = new ApplicationTypeApp();
					newAppTypeApp.setId(UUIDGen.getUUID());
					newAppTypeApp.setApp(appTypeApp.getApp());
					newAppTypeApp.setType(this.get("0"));
					applicationTypeAppDaoImpl.save(newAppTypeApp);
				}
				appTypeApp.delete();
			}
			super.delete(appType.getId());
		}
	}

	@Override
	public boolean checkAppTypeNameExist(String name, String id) {
		StringBuilder sb = new StringBuilder();
		sb.append(" where name='").append(name).append("' ");
		if (StringUtil.isNotEmpty(id)) {
			sb.append(" and id!='").append(id).append("'");
		}
		return this.baseDao.countAll(sb.toString()) == 0;
	}

}
