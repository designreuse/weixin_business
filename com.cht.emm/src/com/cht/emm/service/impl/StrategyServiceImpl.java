package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.Strategy;
import com.cht.emm.service.StrategyService;
import com.cht.emm.vo.StrategyVO;


@Service
public class StrategyServiceImpl extends BaseService<Strategy, String>
		implements StrategyService {

	@Resource(name = "strategyDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<Strategy, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<StrategyVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length) {
		List<Strategy> strategies = this.baseDao.listAll(whereClause, orderby,
				pn, length);
		List<StrategyVO> vos = new ArrayList<StrategyVO>();
		for (Strategy strategy : strategies) {
			StrategyVO vo = new StrategyVO();
			vo.fromStrategy(strategy);
			vos.add(vo);
		}
		return vos;
	}

}
