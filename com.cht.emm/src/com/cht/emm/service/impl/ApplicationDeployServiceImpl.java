package com.cht.emm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.IntegerFetcher;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.id.ApplicationDeploy;
import com.cht.emm.service.ApplicationDeployService;
import com.cht.emm.util.TimestampUtil;
import com.cht.emm.vo.ApplicationDeployVO;
import com.cht.emm.vo.CounterVO;

@Service
public class ApplicationDeployServiceImpl extends
		BaseService<ApplicationDeploy, String> implements
		ApplicationDeployService {

	@Resource(name = "applicationDeployDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<ApplicationDeploy, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ApplicationDeployVO> queryForPage(int count, String queryAll,
			String whereClause, String orderby, int pn, Integer length) {
		List<ApplicationDeploy> appDeploys = this.baseDao.listAll(queryAll
				+ whereClause + orderby, pn, length);
		List<ApplicationDeployVO> vos = new ArrayList<ApplicationDeployVO>();
		for (ApplicationDeploy appDeploy : appDeploys) {
			ApplicationDeployVO vo = new ApplicationDeployVO();
			vo.setStatus(appDeploy.getStatus());
			vo.setDevicename(appDeploy.getDevice().getName());
			vo.setUsername(appDeploy.getUser().getUsername());
			vo.setAppname(appDeploy.getApp().getName());
			vo.setTime(TimestampUtil.toString(appDeploy.getTime()));
			vo.setVersion(appDeploy.getApp().getVersion_name());
			vos.add(vo);
		}
		return vos;
	}

	/**
	* <p>Title: getCounterVOs</p> 
	* <p>Description: </p> 
	* @param hsql
	* @param query
	* @param orderBy
	* @param groupBy
	* @param maxNum
	* @return 
	* @see com.cht.emm.service.ApplicationDeployService#getCounterVOs(java.lang.String, com.cht.emm.common.dao.util.ConditionQuery, com.cht.emm.common.dao.util.OrderBy, java.util.List, java.lang.Integer) 
	*/
	@Override
	public List<CounterVO> getCounterVOs(String hsql, String query,
			String orderBy, String groupBy, Integer maxNum) {
		
		return baseDao.getCounters(hsql, query, orderBy, groupBy, maxNum);
	}

	/**
	* <p>Title: getAppDownLoadWithPeriod</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.cht.emm.service.ApplicationDeployService#getAppDownLoadWithPeriod() 
	*/
	@Override
	public Map<String, Integer> getAppDownLoadWithPeriod() {
		// TODO Auto-generated method stub
		long  time  = getStartTime();
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("day", countApp(1, time));
		map.put("week", countApp(1, time));
		map.put("month", countApp(3, time));
		
		return map;
	}
	private  Integer countApp(int type, long time) {
		Timestamp starTimestamp = null;

		switch (type) {
		case 1:
			starTimestamp = new Timestamp(time);
			break;

		case 2:
			starTimestamp = new Timestamp(time - 604800000);
			break;
		case 3:
			starTimestamp = new Timestamp((time / 1000 - 2592000) * 1000);
			break;
		default:
			break;
		}
		Integer total = 0;
		Session session = baseDao.getSession();
		Query query = session.createQuery("select count(*) from ApplicationDeploy where time >=?");
		 query.setTimestamp(0, starTimestamp);
		 total = IntegerFetcher.getIntVal(query.uniqueResult());
		return total;
	}

	private  Long getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}
}
