package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.Config;
import com.cht.emm.service.ConfigService;
import com.cht.emm.vo.ConfigVO;


@Service
public class ConfigServiceImpl extends BaseService<Config, String> implements
		ConfigService {

	@Resource(name = "configDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<Config, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ConfigVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length) {
		List<Config> configs = this.baseDao.listAll(whereClause, orderby, pn,
				length);
		List<ConfigVO> vos = new ArrayList<ConfigVO>();
		for (Config config : configs) {
			ConfigVO vo = new ConfigVO();
			vo.fromConfig(config);
			vos.add(vo);
		}
		return vos;
	}

}
