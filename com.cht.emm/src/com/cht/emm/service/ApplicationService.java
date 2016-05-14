package com.cht.emm.service;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Application;
import com.cht.emm.model.ApplicationType;
import com.cht.emm.model.ApplicationVersion;
import com.cht.emm.vo.ApplicationDetailVO;
import com.cht.emm.vo.ApplicationScoreVO;
import com.cht.emm.vo.ApplicationScoreVO1;
import com.cht.emm.vo.ApplicationTypeVO;
import com.cht.emm.vo.ApplicationVO;
import com.cht.emm.vo.ApplicationVO1;
import com.cht.emm.vo.ApplicationVO3;


public interface ApplicationService extends IBaseService<Application, String> {
	public List<ApplicationVO> listAllApps();

	public void saveOrUpdate(ApplicationVO appVo) throws Exception;

	public ApplicationVersion getAppVersion(String id);

	public List<ApplicationVO1> getAppsOfUser(String username, String deviceID,
			HttpServletRequest request);

	public void addScore(int score, String app_id, String comment,
			String username);

	public void deleteApp(String[] ids);

	public void disableApp(String id);

	public void enableApp(String id);

	public ApplicationDetailVO getApplicationDetailVO(String id,
			HttpServletRequest request);

	public void addAppType(ApplicationType type) throws Exception;

	public List<ApplicationTypeVO> listAppTypes();

	public ApplicationScoreVO getScore(String app_id, String username);

	public ApplicationVO3 loadAppDetail(String app_id,
			HttpServletRequest request);

	public List<ApplicationScoreVO1> getMoreAppScores(String app_id,
			Timestamp timestamp);

	public List<ApplicationVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length, HttpServletRequest request);

	public void upgradeApp(ApplicationVO appVo) throws Exception;

	public void saveAppDeploy(String userName, String deviceId, String id);
}
