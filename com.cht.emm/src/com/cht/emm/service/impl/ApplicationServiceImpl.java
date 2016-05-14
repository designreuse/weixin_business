package com.cht.emm.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import nariis.pi3000.framework.json.JSONException;
import nariis.pi3000.framework.json.JSONObject;
import nariis.pi3000.framework.utility.StringUtil;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.impl.ApplicationAuthorizationDaoImpl;
import com.cht.emm.dao.impl.ApplicationDaoImpl;
import com.cht.emm.dao.impl.ApplicationDeployDaoImpl;
import com.cht.emm.dao.impl.ApplicationScoreDaoImpl;
import com.cht.emm.dao.impl.ApplicationTypeAppDaoImpl;
import com.cht.emm.dao.impl.ApplicationTypeDaoImpl;
import com.cht.emm.dao.impl.ApplicationVersionDaoImpl;
import com.cht.emm.dao.impl.ConfigDaoImpl;
import com.cht.emm.dao.impl.UserDaoImpl;
import com.cht.emm.model.Application;
import com.cht.emm.model.ApplicationType;
import com.cht.emm.model.ApplicationVersion;
import com.cht.emm.model.Config;
import com.cht.emm.model.Device;
import com.cht.emm.model.Group;
import com.cht.emm.model.User;
import com.cht.emm.model.id.ApplicationAuthorization;
import com.cht.emm.model.id.ApplicationDeploy;
import com.cht.emm.model.id.ApplicationScore;
import com.cht.emm.model.id.ApplicationTypeApp;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.service.ApplicationService;
import com.cht.emm.util.DeviceOrderConstants;
import com.cht.emm.util.TimestampUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.vo.ApplicationDetailVO;
import com.cht.emm.vo.ApplicationScoreVO;
import com.cht.emm.vo.ApplicationScoreVO1;
import com.cht.emm.vo.ApplicationTypeVO;
import com.cht.emm.vo.ApplicationVO;
import com.cht.emm.vo.ApplicationVO1;
import com.cht.emm.vo.ApplicationVO3;

/**
 * 应用管理服务
 * 
 * @author luoyupan
 * 
 */
@Service
public class ApplicationServiceImpl extends BaseService<Application, String>
		implements ApplicationService {

	@Resource(name = "applicationDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<Application, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Resource(name = "applicationDaoImpl")
	private ApplicationDaoImpl applicationDaoImpl;

	@Resource(name = "applicationVersionDaoImpl")
	private ApplicationVersionDaoImpl applicationVersionDaoImpl;

	@Resource(name = "applicationScoreDaoImpl")
	private ApplicationScoreDaoImpl applicationScoreDaoImpl;

	@Resource(name = "applicationDeployDaoImpl")
	private ApplicationDeployDaoImpl applicationDeployDaoImpl;

	@Resource(name = "applicationTypeDaoImpl")
	private ApplicationTypeDaoImpl applicationTypeDaoImpl;

	@Resource(name = "applicationTypeAppDaoImpl")
	private ApplicationTypeAppDaoImpl applicationTypeAppDaoImpl;

	@Resource(name = "userDao")
	private UserDaoImpl userDaoImpl;

	@Resource(name = "configDaoImpl")
	private ConfigDaoImpl configDaoImpl;

	@Resource(name = "applicationAuthorizationDaoImpl")
	private ApplicationAuthorizationDaoImpl applicationAuthorizationDaoImpl;

	/**
	 * 返回应用列表
	 * 
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<ApplicationVO> listAllApps() {
		List<ApplicationVO> vos = new ArrayList<ApplicationVO>();
		for (Application app : this.baseDao.listAll()) {
			ApplicationVO vo = new ApplicationVO();
			vo.setId(app.getId());
			vo.setName(app.getName());
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * 新建或更新应用
	 * 
	 * @param appVo
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveOrUpdate(ApplicationVO appVo) throws Exception {
		// 若新建应用则设置ID
		if (appVo.getId() == null) {
			// 先判断是否已有相同应用存在
			if (applicationDaoImpl.countAll(" where pkg_name='"
					+ appVo.getPkg_name() + "'") > 0) {
				throw new Exception("应用已存在！");
			}
			appVo.setId(UUIDGen.getUUID());
		}
		// 若更新应用则保存原应用的快照URL
		ApplicationVersion oldVersion = null;
		String screenshot = "";
		Application oldApp = applicationDaoImpl.get(appVo.getId());
		if (oldApp != null) {
			for (ApplicationVersion version : oldApp.getAppVersions()) {
				if (version.getVersion_code() == oldApp.getVersion_code()) {
					oldVersion = version;
					break;
				}
			}
			screenshot = oldApp.getScreenshot();
		}
		// 更新应用和应用版本对象
		Application app = appVo.toApp(oldApp);
		applicationDaoImpl.saveOrUpdate(app);
		ApplicationVersion version = appVo.toAppVersion(oldVersion);
		version.setApp(app);
		applicationVersionDaoImpl.saveOrUpdate(version);
		// 更新应用和应用分类的关联记录
		List<ApplicationTypeApp> appCategorys = applicationTypeAppDaoImpl
				.getSession().createCriteria(ApplicationTypeApp.class)
				.add(Restrictions.eq("app.id", appVo.getId())).list();
		// 先删除所有应用和应用分类的关联记录
		for (ApplicationTypeApp appCategory : appCategorys) {
			applicationTypeAppDaoImpl.deleteObject(appCategory);
		}
		// 再新建应用和应用分类的关联记录
		String categorys = appVo.getCategory();
		if (StringUtil.isNotEmpty(categorys)) {
			String[] categoryList = StringUtil.split(categorys, ",");
			for (String category : categoryList) {
				ApplicationTypeApp appCategory = new ApplicationTypeApp();
				appCategory.setApp(app);
				appCategory.setType(applicationTypeDaoImpl.get(category));
				appCategory.setId(UUIDGen.getUUID());
				applicationTypeAppDaoImpl.save(appCategory);
			}
		}
		// 若更新应用则判断之前上传的文件是否需要删除
		if (oldVersion != null) {
			// 若应用的快照有变化则删除原应用快照文件
			if (StringUtil.isNotEmpty(screenshot)
					&& !StringUtil.equals(screenshot, appVo.getScreenshot())) {
				String prePath = StringUtil.substringBeforeLast(
						oldVersion.getUrl(), "uploads");
				String[] screenshots = screenshot.split(",");
				List<String> newScreenshots = new ArrayList<String>();
				if (StringUtil.isNotEmpty(appVo.getScreenshot())) {
					String[] screenshotsArray = appVo.getScreenshot()
							.split(",");
					for (String s : screenshotsArray) {
						newScreenshots.add(s);
					}
				}
				for (String screenshotUrl : screenshots) {
					if (!newScreenshots.contains(screenshotUrl)) {
						String suffixPath = StringUtil.substringAfterLast(
								screenshotUrl, "uploads");
						File file = new File(prePath + "uploads" + suffixPath);
						if (file.exists()) {
							if (file.delete()) {
								file = file.getParentFile();
								file.delete();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 获取指定应用的版本对象
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ApplicationVersion getAppVersion(String id) {
		Application app = applicationDaoImpl.get(id);
		if (app != null) {
			for (ApplicationVersion version : app.getAppVersions()) {
				if (version.getVersion_code() == app.getVersion_code()) {
					return version;
				}
			}
		}
		return null;
	}

	/**
	 * 返回应用列表到移动客户端
	 * 
	 * @param username
	 * 
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<ApplicationVO1> getAppsOfUser(String username, String deviceID,
			HttpServletRequest request) {
		// 根据应用授权返回用户的应用权限，从所属部门一直往上遍历至根部门
		List<String> permissionApps = new ArrayList<String>();
		List<String> unpermissionApps = new ArrayList<String>();
		List<User> users = userDaoImpl.getUsersByName(username);
		User user = null;
		if (users != null && users.size() > 0) {
			user = users.get(0);
			Set<UserGroup> userGroups = user.getUserGroups();
			if (userGroups.size() > 0) {
				UserGroup userGroup = (UserGroup) userGroups.toArray()[0];
				Group group = userGroup.getGroup();
				while (group != null) {
					ConditionQuery query = new ConditionQuery();
					query.add(Restrictions.eq("group.id", group.getId()));
					List<ApplicationAuthorization> appAuths = applicationAuthorizationDaoImpl
							.listAll(query, null, -1, -1);
					for (ApplicationAuthorization appAuth : appAuths) {
						// 应用至今未授权
						Application app = appAuth.getApp();
						if (app != null) {
							if (!permissionApps.contains(app.getId())
									&& !unpermissionApps.contains(app.getId())) {
								if (appAuth.getType() == 1) {
									permissionApps.add(app.getId());
								} else {
									unpermissionApps.add(app.getId());
								}
							}
						}
					}
					group = group.getParentGroup();
				}
			}
		}
		// 根据设备配置的黑白名单返回授权应用
		int app_list_type = 0;
		String app_list = null;
		List<String> configApps = new ArrayList<String>();
		if (user != null) {
			Config config = configDaoImpl.get("0");
			if (user.getConfigUsers().size() > 0) {
				config = user.getConfigUsers().get(0).getConfig();
			}
			try {
				JSONObject obj = new JSONObject(config.getContent());
				if (obj.has(DeviceOrderConstants.CONFIG_APP)) {
					JSONObject appConfig = obj
							.getJSONObject(DeviceOrderConstants.CONFIG_APP);
					app_list_type = appConfig
							.getInt(DeviceOrderConstants.CONFIG_APP_LIST_TYPE);
					app_list = appConfig
							.getString(DeviceOrderConstants.CONFIG_APPS);
					for (String id : StringUtil.split(app_list, ",")) {
						configApps.add(id);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		List<ApplicationVO1> vos = new ArrayList<ApplicationVO1>();
		// 获取所有应用对象，不包含平台应用
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.ne("pkg_name", "nari.mip.console"));
		List<Application> apps = applicationDaoImpl
				.listAll(query, null, -1, -1);
		String url = request.getRequestURL().toString();
		String pre = url.substring(0, url.indexOf("rest/mobile"));
		for (Application app : apps) {
			if (!permissionApps.contains(app.getId())) {
				continue;
			}
			if (StringUtil.isNotEmpty(app_list)) {
				if (app_list_type == DeviceOrderConstants.CONFIG_BLACK_APP_LIST) {
					if (configApps.contains(app.getId())) {
						continue;
					}
				} else if (app_list_type == DeviceOrderConstants.CONFIG_WHITE_APP_LIST) {
					if (!configApps.contains(app.getId())) {
						continue;
					}
				}
			}
			ApplicationVO1 vo = new ApplicationVO1();
			vo.fromApp(app);
			for (ApplicationVersion version : app.getAppVersions()) {
				if (version.getVersion_code() == app.getVersion_code()) {
					vo.setIcon(version.getIcon());
					break;
				}
			}
			String path = vo.getIcon();
			String suffix = path.substring(path.indexOf("uploads"));
			vo.setIcon(pre + suffix);
			StringBuilder sb = new StringBuilder();
			for (ApplicationTypeApp appTypeApp : app.getAppTypeApps()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(appTypeApp.getType().getName());
			}
			vo.setCategory(sb.toString());
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * 获取指定应用的VO对象
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private ApplicationVO getApplicationVO(String id, HttpServletRequest request) {
		ApplicationVO vo = new ApplicationVO();
		Application app = applicationDaoImpl.get(id);
		String url = request.getRequestURL().toString();
		String pre = url.substring(0, url.indexOf("rest/app"));
		if (app != null) {
			vo.fromApp(app);
			for (ApplicationVersion version : app.getAppVersions()) {
				if (version.getVersion_code() == app.getVersion_code()) {
					vo.fromAppVersion(version);
					break;
				}
			}
			StringBuilder sb = new StringBuilder();
			for (ApplicationTypeApp appTypeApp : app.getAppTypeApps()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(appTypeApp.getType().getName());
			}
			vo.setCategory(sb.toString());
			String path = vo.getIcon();
			String suffix = path.substring(path.indexOf("uploads"));
			vo.setIcon(pre + suffix);
			if (StringUtil.isNotEmpty(vo.getScreenshot())) {
				String[] screenshots = vo.getScreenshot().split(",");
				sb = new StringBuilder();
				for (String screenshot : screenshots) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					suffix = screenshot
							.substring(screenshot.indexOf("uploads"));
					sb.append(pre + suffix);
				}
				vo.setScreenshot(sb.toString());
			}
		}
		return vo;
	}

	/**
	 * 创建或更新应用评分记录
	 * 
	 * @param score
	 *            应用评分对象
	 * @param username
	 *            评分用户
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addScore(int score, String app_id, String comment,
			String username) {
		List<User> users = userDaoImpl.getUsersByName(username);
		User user = null;
		if (users != null && users.size() > 0) {
			user = users.get(0);
			// 查询用户对该应用是否已有评分记录
			List<ApplicationScore> scores = applicationScoreDaoImpl
					.getSession().createCriteria(ApplicationScore.class)
					.add(Restrictions.eq("app.id", app_id))
					.add(Restrictions.eq("user.id", user.getId())).list();
			// 计算应用的总分
			String hql = "select count(*),sum(a.score) from ApplicationScore a where a.app.id='"
					+ app_id + "'";
			List results = applicationScoreDaoImpl.getSession()
					.createQuery(hql).list();
			int countNum = 0;
			int scoreSum = 0;
			if (results.size() > 0) {
				Object[] obj = (Object[]) results.get(0);
				countNum = ((Long) obj[0]).intValue();
				if (countNum > 0) {
					scoreSum = ((Long) obj[1]).intValue();
				}
			}
			Application app = applicationDaoImpl.get(app_id);
			// 若用户对该应用已有评分记录，则从应用总分中扣去之前的评分；否则应用的评分次数加1
			if (scores.size() == 0) {
				app.setScore_count(++countNum);
			} else {
				app.setScore_count(countNum);
				scoreSum = scoreSum - scores.get(0).getScore();
			}
			// 计算应用的新平均分
			app.setScore((scoreSum + score) / app.getScore_count());
			// 更新应用详情对象
			applicationDaoImpl.saveOrUpdate(app);
			// 创建或更新应用评分记录
			if (scores.size() == 0) {
				ApplicationScore newScore = new ApplicationScore();
				newScore.setId(UUIDGen.getUUID());
				newScore.setTime(new Timestamp(System.currentTimeMillis()));
				newScore.setUser(user);
				newScore.setApp(new Application(app_id));
				newScore.setComment(comment);
				newScore.setScore(score);
				applicationScoreDaoImpl.save(newScore);
			} else {
				scores.get(0)
						.setTime(new Timestamp(System.currentTimeMillis()));
				scores.get(0).setScore(score);
				scores.get(0).setComment(comment);
				applicationScoreDaoImpl.saveOrUpdate(scores.get(0));
			}
		}
	}

	/**
	 * 删除指定应用
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteApp(String[] ids) {
		List<Application> apps = this.listByIds(ids);
		String prePath = null;
		String suffixPath = null;
		File file = null;
		for (Application app : apps) {
			// 删除应用的所有评分记录
			applicationScoreDaoImpl.deleteObjectList(app.getAppScores());
			// 删除应用的所有下载记录
			applicationDeployDaoImpl.deleteObjectList(app.getAppDeploys());
			// 删除应用和应用分类的关联记录
			applicationTypeAppDaoImpl.deleteObjectList(app.getAppTypeApps());
			// 删除应用的授权记录
			applicationAuthorizationDaoImpl.deleteObjectList(app.getAppAuths());
			// 删除应用所有版本的程序文件和图标文件
			for (ApplicationVersion version : app.getAppVersions()) {
				file = new File(version.getUrl());
				if (file.exists()) {
					if (file.delete()) {
						file = file.getParentFile();
						file.delete();
					}
				}
				prePath = StringUtil.substringBeforeLast(version.getUrl(),
						"uploads");
				suffixPath = StringUtil.substringAfterLast(version.getIcon(),
						"uploads");
				file = new File(prePath + "uploads" + suffixPath);
				if (file.exists()) {
					if (file.delete()) {
						file = file.getParentFile();
						file.delete();
					}
				}
				// 删除应用的版本记录
				applicationVersionDaoImpl.deleteObject(version);
			}
			// 删除应用的快照文件
			if (StringUtil.isNotEmpty(app.getScreenshot())) {
				String[] screenshots = app.getScreenshot().split(",");
				for (String screenshotUrl : screenshots) {
					suffixPath = StringUtil.substringAfterLast(screenshotUrl,
							"uploads");
					file = new File(prePath + "uploads" + suffixPath);
					if (file.exists()) {
						if (file.delete()) {
							file = file.getParentFile();
							file.delete();
						}
					}
				}
			}
			// 删除应用记录
			applicationDaoImpl.delete(app.getId());
		}
	}

	/**
	 * 禁用指定应用
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void disableApp(String id) {
		Application app = applicationDaoImpl.get(id);
		app.setStatus(1);
		applicationDaoImpl.saveOrUpdate(app);
	}

	/**
	 * 启用指定应用
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void enableApp(String id) {
		Application app = applicationDaoImpl.get(id);
		app.setStatus(0);
		applicationDaoImpl.saveOrUpdate(app);
	}

	/**
	 * 获取应用的详情VO，包含应用基本信息、应用详情、应用的所有评分记录、下载记录和版本记录
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ApplicationDetailVO getApplicationDetailVO(String id,
			HttpServletRequest request) {
		ApplicationDetailVO vo = new ApplicationDetailVO();
		vo.setApp(getApplicationVO(id, request));
		return vo;
	}

	/**
	 * 创建或更新应用分类
	 * 
	 * @param type
	 * @throws Exception
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addAppType(ApplicationType type) throws Exception {
		if (type.getId() == null) {
			type.setId(UUIDGen.getUUID());
		}
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("name", type.getName()));
		query.add(Restrictions.ne("id", type.getId()));
		List<ApplicationType> apptype = applicationTypeDaoImpl.listAll(query,
				null, -1, -1);
		if (apptype.size() > 0) {
			throw new Exception("应用分类不能重名");
		}
		applicationTypeDaoImpl.saveOrUpdate(type);
	}

	/**
	 * 返回应用分类列表
	 * 
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<ApplicationTypeVO> listAppTypes() {
		List<ApplicationTypeVO> vos = new ArrayList<ApplicationTypeVO>();
		List<ApplicationType> types = applicationTypeDaoImpl.listAll();
		for (ApplicationType type : types) {
			ApplicationTypeVO vo = new ApplicationTypeVO();
			vo.setId(type.getId());
			vo.setName(type.getName());
			vo.setDescription(type.getDescription() == null ? "" : type
					.getDescription());
			vo.setCount(type.getAppTypeApps().size());
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * 创建或更新应用下载记录
	 * 
	 * @param appDeploy
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveAppDeploy(String user_id, String device_id, String app_id) {
		ApplicationDeploy appDeploy = new ApplicationDeploy();
		List<User> users = userDaoImpl.getUsersByName(user_id);
		User user = null;
		if (users != null && users.size() > 0) {
			user = users.get(0);
			// 若用户在设备上曾下载过该应用,则更新应用下载时间
			List<ApplicationDeploy> appDeploys = applicationDeployDaoImpl
					.getSession().createCriteria(ApplicationDeploy.class)
					.add(Restrictions.eq("user.id", user.getId()))
					.add(Restrictions.eq("device.id", device_id))
					.add(Restrictions.eq("app.id", app_id)).list();
			if (appDeploys.size() > 0) {
				appDeploy = appDeploys.get(0);
			}
			// 否则新建一条下载记录
			else {
				appDeploy.setId(UUIDGen.getUUID());
				appDeploy.setApp(new Application(app_id));
				appDeploy.setDevice(new Device(device_id));
				appDeploy.setStatus(0);
				appDeploy.setUser(user);
			}
			appDeploy.setTime(new Timestamp(System.currentTimeMillis()));
			applicationDeployDaoImpl.saveOrUpdate(appDeploy);
		}
	}

	/**
	 * 获取指定用户对指定应用的评分记录
	 * 
	 * @param app_id
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ApplicationScoreVO getScore(String app_id, String username) {
		List<User> users = userDaoImpl.getUsersByName(username);
		User user = null;
		if (users != null && users.size() > 0) {
			user = users.get(0);
			List<ApplicationScore> scores = applicationScoreDaoImpl
					.getSession().createCriteria(ApplicationScore.class)
					.add(Restrictions.eq("app.id", app_id))
					.add(Restrictions.eq("user.id", user.getId())).list();
			if (scores.size() > 0) {
				ApplicationScoreVO vo = new ApplicationScoreVO();
				vo.fromAppScore(scores.get(0));
				return vo;
			}
		}
		return new ApplicationScoreVO();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ApplicationVO3 loadAppDetail(String app_id,
			HttpServletRequest request) {
		ApplicationVO3 vo = new ApplicationVO3();
		Application app = applicationDaoImpl.get(app_id);
		if (app != null) {
			vo.fromApp(app);
			String url = request.getRequestURL().toString();
			String pre = url.substring(0, url.indexOf("rest/mobile"));
			if (StringUtil.isNotEmpty(vo.getScreenshot())) {
				String[] screenshots = vo.getScreenshot().split(",");
				StringBuilder sb = new StringBuilder();
				for (String screenshot : screenshots) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					String suffix = screenshot.substring(screenshot
							.indexOf("uploads"));
					sb.append(pre + suffix);
				}
				vo.setScreenshot(sb.toString());
			}
			List<ApplicationScore> scores = applicationScoreDaoImpl
					.getSession().createCriteria(ApplicationScore.class)
					.add(Restrictions.eq("app.id", app_id))
					.addOrder(Order.desc("time")).list();
			List<ApplicationScoreVO1> vos = new ArrayList<ApplicationScoreVO1>();
			for (ApplicationScore score : scores) {
				if (vos.size() > 9) {
					break;
				}
				ApplicationScoreVO1 scoreVo = new ApplicationScoreVO1();
				scoreVo.fromAppScore(score);
				vos.add(scoreVo);
			}
			vo.setScores(vos);
		}
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<ApplicationScoreVO1> getMoreAppScores(String app_id,
			Timestamp timestamp) {
		List<ApplicationScore> scores = applicationScoreDaoImpl.getSession()
				.createCriteria(ApplicationScore.class)
				.add(Restrictions.eq("app.id", app_id))
				.add(Restrictions.lt("time", timestamp))
				.addOrder(Order.desc("time")).list();
		List<ApplicationScoreVO1> vos = new ArrayList<ApplicationScoreVO1>();
		for (ApplicationScore score : scores) {
			if (vos.size() > 9) {
				break;
			}
			ApplicationScoreVO1 scoreVo = new ApplicationScoreVO1();
			scoreVo.fromAppScore(score);
			vos.add(scoreVo);
		}
		return vos;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<ApplicationVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length, HttpServletRequest request) {
		List<Application> apps = this.baseDao.listHql(whereClause+orderby, pn,
				length);
		List<ApplicationVO> vos = new ArrayList<ApplicationVO>();
		String url = request.getRequestURL().toString();
		String pre = url.substring(0, url.indexOf("rest/app"));
		for (Application app : apps) {
			ApplicationVO vo = new ApplicationVO();
			vo.setId(app.getId());
			vo.setName(app.getName());
			vo.setOs(app.getOs() == 0 ? "ANDROID" : "IOS");
			vo.setVersion_name(app.getVersion_name());
			vo.setType(app.getType());
			vo.setPublisher(app.getUser().getDetail().getUserAlias());
			vo.setCreate(TimestampUtil.toString(app.getCreate()));
			vo.setPkg_name(app.getPkg_name());
			vo.setVersion_code(app.getVersion_code());
			vo.setDownload_count(app.getDownload_count());
			for (ApplicationVersion version : app.getAppVersions()) {
				if (version.getVersion_code() == app.getVersion_code()) {
					vo.setIcon(version.getIcon());
					break;
				}
			}
			String path = vo.getIcon();
			String suffix = path.substring(path.indexOf("uploads"));
			vo.setIcon(pre + suffix);
			vo.setStatus(app.getStatus());
			vo.setKind(app.getKind());
			vos.add(vo);
		}
		return vos;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void upgradeApp(ApplicationVO appVo) throws Exception {
		Application app = applicationDaoImpl.get(appVo.getId());
		if (app == null) {
			throw new Exception("应用不存在！");
		}
		if (!StringUtil.equals(app.getPkg_name(), appVo.getPkg_name())) {
			throw new Exception("应用程序ID不一致！");
		}
		if (app.getVersion_code() >= appVo.getVersion_code()) {
			throw new Exception("应用版本不能低于当前最新版本！");
		}
		app.setVersion_code(appVo.getVersion_code());
		app.setVersion_name(appVo.getVersion_name());
		app.setUser(new User(appVo.getPublisher()));
		app.setUpdate(new Timestamp(System.currentTimeMillis()));
		applicationDaoImpl.saveOrUpdate(app);
		applicationVersionDaoImpl.save(appVo.toAppVersion(null));
	}

}
