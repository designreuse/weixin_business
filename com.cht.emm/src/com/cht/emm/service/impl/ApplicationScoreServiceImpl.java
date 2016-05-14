package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.id.ApplicationScore;
import com.cht.emm.service.ApplicationScoreService;
import com.cht.emm.util.TimestampUtil;
import com.cht.emm.vo.ApplicationScoreVO;


@Service
public class ApplicationScoreServiceImpl extends
		BaseService<ApplicationScore, String> implements
		ApplicationScoreService {

	@Resource(name = "applicationScoreDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<ApplicationScore, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ApplicationScoreVO> queryForPage(int count, String queryAll,
			String whereClause, String orderby, int pn, Integer length) {
		List<ApplicationScore> appScores = this.baseDao.listAll(queryAll
				+ whereClause + orderby, pn, length);
		List<ApplicationScoreVO> vos = new ArrayList<ApplicationScoreVO>();
		for (ApplicationScore appScore : appScores) {
			ApplicationScoreVO vo = new ApplicationScoreVO();
			vo.setComment(appScore.getComment());
			vo.setScore(appScore.getScore());
			vo.setTime(TimestampUtil.toString(appScore.getTime()));
			try {
				vo.setUser(appScore.getUser().getUsername());
			} catch (Exception e) {
				vo.setUser("");
			}
			vos.add(vo);
		}
		return vos;
	}

}
