package com.cht.emm.controller.apps;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import nariis.pi3000.framework.utility.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.controller.BaseController;
import com.cht.emm.model.ApplicationType;
import com.cht.emm.model.ApplicationVersion;
import com.cht.emm.model.User;
import com.cht.emm.service.ApplicationAuthorizationService;
import com.cht.emm.service.ApplicationDeployService;
import com.cht.emm.service.ApplicationScoreService;
import com.cht.emm.service.ApplicationService;
import com.cht.emm.service.ApplicationTypeService;
import com.cht.emm.service.ApplicationVersionService;
import com.cht.emm.service.GroupService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.ColumnProperty;
import com.cht.emm.util.KeyValue;
import com.cht.emm.util.PageInfo;
import com.cht.emm.util.Response;
import com.cht.emm.util.db19.ParameterUtil;
import com.cht.emm.vo.ApplicationAuthorizationVO;
import com.cht.emm.vo.ApplicationDeployVO;
import com.cht.emm.vo.ApplicationScoreVO;
import com.cht.emm.vo.ApplicationTypeVO;
import com.cht.emm.vo.ApplicationVO;
import com.cht.emm.vo.GroupTreeNode;
import com.cht.emm.vo.UserVO;

/**
 * 应用管理相关rest服务
 * 
 * @author luoyupan
 * 
 */
@Controller
public class AppsServiceController extends BaseController {
	/**
	 * 应用管理服务
	 */
	@Resource(name = "applicationServiceImpl")
	private ApplicationService applicationServiceImpl;
	/**
	 * 应用分类管理服务
	 */
	@Resource(name = "applicationTypeServiceImpl")
	private ApplicationTypeService applicationTypeServiceImpl;
	/**
	 * 应用评分记录管理服务
	 */
	@Resource(name = "applicationScoreServiceImpl")
	private ApplicationScoreService applicationScoreServiceImpl;
	/**
	 * 应用部署记录管理服务
	 */
	@Resource(name = "applicationDeployServiceImpl")
	private ApplicationDeployService applicationDeployServiceImpl;
	/**
	 * 应用授权管理服务
	 */
	@Resource(name = "applicationAuthorizationServiceImpl")
	private ApplicationAuthorizationService applicationAuthorizationServiceImpl;
	/**
	 * 应用版本管理服务
	 */
	@Resource(name = "applicationVersionServiceImpl")
	private ApplicationVersionService applicationVersionServiceImpl;

	// 用户管理服务
	@Resource(name = "userService")
	private UserService userService;

	// 群组管理服务
	@Resource(name = "groupService")
	GroupService groupService;

	/**
	 * 获取所有应用对象
	 * 
	 * @return 应用对象列表
	 */
	@RequestMapping(value = "rest/app/all", method = RequestMethod.GET)
	@ResponseBody
	public Response listAll() {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(applicationServiceImpl.listAllApps());
		return res;
	}

	/**
	 * 创建或更新应用
	 * 
	 * @param appVo
	 *            应用对象
	 * @return
	 */
	@RequestMapping(value = "rest/app/add", method = RequestMethod.POST)
	@ResponseBody
	public Response saveOrUpdate(@RequestBody ApplicationVO appVo) {
		Response res = new Response();
		try {
			applicationServiceImpl.saveOrUpdate(appVo);
			res.setSuccessful(true);
		} catch (Exception e) {
			res.setSuccessful(false);
			res.setResultMessage(e.getMessage());
		}
		return res;
	}

	/**
	 * 取消应用相关文件上传
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 */
	@RequestMapping(value = "rest/app/cancel", method = RequestMethod.POST)
	@ResponseBody
	public Response cancelUploads(@RequestParam String path) {
		Response res = new Response();
		// 删除文件所在目录
		File file = new File(path);
		if (file.exists()) {
			if (file.delete()) {
				file.getParentFile().delete();
			}
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 获取应用详情
	 * 
	 * @param id
	 *            应用ID
	 * @return 应用对象
	 */
	@RequestMapping(value = "rest/app/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response getAppDetail(@PathVariable String id,
			HttpServletRequest request) {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(applicationServiceImpl.getApplicationDetailVO(id,
				request));
		return res;
	}

	/**
	 * 删除应用
	 * 
	 * @param ids
	 *            待删除的应用ID，用","分开
	 * @return
	 */
	@RequestMapping(value = "rest/app/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteApp(@RequestParam String ids) {
		Response res = new Response();
		applicationServiceImpl.deleteApp(ids.split(","));
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 停用应用
	 * 
	 * @param ids
	 *            待停用的应用ID，用","分开
	 * @return
	 */
	@RequestMapping(value = "rest/app/disable", method = RequestMethod.POST)
	@ResponseBody
	public Response disableApp(@RequestParam String ids) {
		Response res = new Response();
		// 逐个停用应用
		for (String id : ids.split(",")) {
			applicationServiceImpl.disableApp(id);
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 启用应用
	 * 
	 * @param ids
	 *            待启用的应用ID，用","分开
	 * @return
	 */
	@RequestMapping(value = "rest/app/enable", method = RequestMethod.POST)
	@ResponseBody
	public Response enableApp(@RequestParam String ids) {
		Response res = new Response();
		// 逐个启用应用
		for (String id : ids.split(",")) {
			applicationServiceImpl.enableApp(id);
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 下载应用
	 * 
	 * @param id
	 *            应用ID
	 * @param request
	 *            http请求对象
	 * 
	 * @return 应用下载url
	 */
	@RequestMapping(value = "rest/app/download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response getDownloadUrl(@PathVariable String id,
			HttpServletRequest request) {
		Response res = new Response();
		res.setSuccessful(false);
		ApplicationVersion appVersion = applicationServiceImpl
				.getAppVersion(id);
		if (appVersion != null) {
			String prePath = StringUtil.substringBefore(request.getRequestURL()
					.toString(), "rest/app/download");
			String suffixPath = StringUtil.substringAfterLast(
					appVersion.getUrl(), "uploads");
			res.setSuccessful(true);
			res.setResultValue(prePath + "uploads" + suffixPath);
		}
		return res;
	}

	/**
	 * 创建或更新应用分类
	 * 
	 * @param type
	 *            应用分类对象
	 * @return
	 */
	@RequestMapping(value = "rest/apptype/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addAppType(@RequestBody ApplicationType type) {
		Response res = new Response();

		try {
			applicationServiceImpl.addAppType(type);
		} catch (Exception e) {
			res.setSuccessful(false);
			res.setResultMessage(e.getMessage());
			return res;
		}
		res.setSuccessful(true);
		res.setResultValue(type.getId());
		return res;
	}

	/**
	 * 删除应用分类
	 * 
	 * @param ids
	 *            待删除的应用分类ID，用","分开
	 * @return
	 */
	@RequestMapping(value = "rest/apptype/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteAppType(@RequestParam String ids) {
		Response res = new Response();
		applicationTypeServiceImpl.deleteAppType(ids.split(","));
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 获取所有应用分类对象
	 * 
	 * @return 应用分类对象列表
	 */
	@RequestMapping(value = "rest/apptype/all", method = RequestMethod.GET)
	@ResponseBody
	public Response listAppTypes() {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(applicationServiceImpl.listAppTypes());
		return res;
	}

	/**
	 * 分页加载应用分类对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/apptype/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAppTypePages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%'");
					} else if ("description".equals(colnName)) {
						condition
								.append(" description like '%" + search + "%'");
					}
				}
			}

		}

		String conditionQuery = "";
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery = " where " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("name".equals(colName) || "description".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = applicationTypeServiceImpl.countAll(conditionQuery);
		int countAll = applicationTypeServiceImpl.countAll();
		List<ApplicationTypeVO> appTypes = applicationTypeServiceImpl
				.queryForPage(countFilter, conditionQuery, orderList,
						pageInfo.getStart() / pageInfo.getLength() + 1,
						pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", appTypes);
		return resultMap;
	}

	/**
	 * 获取指定应用分类
	 * 
	 * @param id
	 *            应用分类ID
	 * @return 应用分类对象
	 */
	@RequestMapping(value = "rest/apptype/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response loadAppType(@PathVariable String id) {
		Response res = new Response();
		res.setSuccessful(true);
		ApplicationType appType = applicationTypeServiceImpl.get(id);
		ApplicationTypeVO vo = new ApplicationTypeVO();
		vo.setId(appType.getId());
		vo.setName(appType.getName());
		vo.setDescription(appType.getDescription() == null ? "" : appType
				.getDescription());
		res.setResultValue(vo);
		return res;
	}

	/**
	 * 分页加载应用分类下包含的应用对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/apptype/apps/pages/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAppTypeAppPages(@PathVariable String id,
			HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = " where id in (select ata.app.id from ApplicationTypeApp ata where ata.type.id='"
				+ id + "')";
		
		String orderList = "";
		String searcheQuery = conditionQuery;
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
//		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();
//
//		StringBuilder orderBy = new StringBuilder();
//
//		for (KeyValue<Integer, String> keyValue : columnOrder) {
//			if (orderBy.length() > 0) {
//				orderBy.append(",");
//			}
//
//			String colName = columns.get(keyValue.getKey()).getColName();
//			if ("name".equals(colName)) {
//				orderBy.append(" " + colName + " "
//						+ keyValue.getValue().toLowerCase());
//			}
//
//		}
//		if (orderBy.length() > 0) {
//			orderList = " order by " + orderBy.toString();
//		}
		
		orderList = " order by download_count desc";
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = applicationServiceImpl.countAll(conditionQuery);
		int countAll = applicationServiceImpl.countAll(searcheQuery);
		List<ApplicationVO> apps = applicationServiceImpl.queryForPage(
				countFilter, "from Application "+conditionQuery, orderList, pageInfo.getStart()
						/ pageInfo.getLength() + 1, pageInfo.getLength(),
				request);
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", apps);
		return resultMap;
	}

	/**
	 * 分页加载应用对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/app/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAppPages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);

		String userId = request.getParameter("userId");
		UserVO user = userService.getUserById(userId);
		// 获得该用户下载的app
		
		String groupIds = "";
		String baseQuery = "select distinct app from Application app";
		String countQuery = " select count(distinct app) from Application app";
		String whereClauseGroup ="";
		if (user.getUserType() == User.UserType.DEPART_MASTER.getType()
				|| user.getUserType() == User.UserType.COMMON.getType()) {
			String groupId = null;
			if (user.getGroups() != null) {
				groupId = user.getGroups().get(0).getId();
			}
			if(user.getUserType() == User.UserType.COMMON.getType()){
				groupIds = "'"+groupId+"'";
			}else {
				List<String> ids = groupService.getSubGroupListIDs(groupId);
				for (String id : ids) {
					if(StringUtil.isNotBlank(groupIds) ){
						groupIds +=",";
					}
					groupIds += "'"+id+"'";
				}
			}
		} else if (user.getUserType() == User.UserType.ORG_MASTER.getType()) {
			//groupIds = groupService.getTopGroup().getId();
		}
		
		if(StringUtil.isBlank(groupIds)){
			baseQuery += "";
		}else{
			baseQuery += "  inner join  app.appAuths  appauth " ;
			countQuery+="   inner join  app.appAuths  appauth ";
			whereClauseGroup =" where appauth.group.id in ("+groupIds+")";
		}

		
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {
			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" app.name like '%" + search + "%' ");
					}
				}
			}
		}

		 
		String orderList = "";
		if (condition.length() > 0) {
			if(StringUtil.isBlank(whereClauseGroup)){
				whereClauseGroup = " where " + condition.toString()  ;
			}else {
				whereClauseGroup += " and " + condition.toString() ;
			}
		} //+ " and app.deleted =0 "
		if(StringUtil.isBlank(whereClauseGroup)){
			whereClauseGroup = " where " +  " app.deleted =0 " ;
		}else {
			whereClauseGroup += " and " + " app.deleted =0 " ;
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("name".equals(colName)) {
				orderBy.append(" app." + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = applicationServiceImpl.countAll(countQuery,whereClauseGroup);
		int countAll = applicationServiceImpl.countAll();
		List<ApplicationVO> apps = applicationServiceImpl.queryForPage(
				countFilter, baseQuery+whereClauseGroup, orderList, pageInfo.getStart()
						/ pageInfo.getLength() + 1, pageInfo.getLength(),
				request);
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", apps);
		return resultMap;
	}

	/**
	 * 分页加载应用的评分对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/app/appScores/pages/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAppScorePages(@PathVariable String id,
			HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("comment".equals(colnName)) {
						condition.append(" comment like '%" + search + "%'");
					} else if ("user".equals(colnName)) {
						condition.append(" user like '%" + search + "%'");
					}
				}
			}

		}

		String conditionQuery = " where app.id='" + id + "'";
		String searchQuery = conditionQuery;
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("score".equals(colName) || "time".equals(colName)
					|| "user".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = applicationScoreServiceImpl.countAll(conditionQuery);
		int countAll = applicationScoreServiceImpl.countAll(searchQuery);
		List<ApplicationScoreVO> appScores = applicationScoreServiceImpl
				.queryForPage(countFilter, " from ApplicationScore ",
						conditionQuery, orderList, pageInfo.getStart()
								/ pageInfo.getLength() + 1,
						pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", appScores);
		return resultMap;
	}

	/**
	 * 分页加载应用的部署对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/app/appDeploys/pages/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAppDeployPages(@PathVariable String id,
			HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("devicename".equals(colnName)) {
						condition.append(" device.name like '%" + search
								+ "%' ");
					} else if ("username".equals(colnName)) {
						condition.append(" user like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = " where app.id='" + id + "'";
		String searchQuery = conditionQuery;
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("status".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			} else if ("username".equals(colName)) {
				orderBy.append(" user " + keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = applicationDeployServiceImpl.countAll(conditionQuery);
		int countAll = applicationDeployServiceImpl.countAll(searchQuery);
		List<ApplicationDeployVO> appDeploys = applicationDeployServiceImpl
				.queryForPage(countFilter, " from ApplicationDeploy ",
						conditionQuery, orderList, pageInfo.getStart()
								/ pageInfo.getLength() + 1,
						pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", appDeploys);
		return resultMap;
	}

	/**
	 * 分页加载应用的版本对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/app/appVersions/pages/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAppVersionPages(@PathVariable String id,
			HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					if (condition.length() > 0) {
						condition.append(" or ");
					}
				}
			}

		}

		String conditionQuery = " where app.id='" + id + "'";
		String searchQuery = conditionQuery;
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("version_name".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = applicationVersionServiceImpl
				.countAll(conditionQuery);
		int countAll = applicationVersionServiceImpl.countAll(searchQuery);
		List<ApplicationVO> apps = applicationVersionServiceImpl.queryForPage(
				countFilter, conditionQuery, orderList, pageInfo.getStart()
						/ pageInfo.getLength() + 1, pageInfo.getLength(),
				request);
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", apps);
		return resultMap;
	}

	@RequestMapping(value = "/rest/apptype/checkNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAppTypeNameExist(String name, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("valid",
				applicationTypeServiceImpl.checkAppTypeNameExist(name, id));
		return map;
	}

	/**
	 * 创建或更新应用授权
	 * 
	 * @param appAuth
	 *            应用授权对象
	 * @return
	 */
	@RequestMapping(value = "rest/appauth/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addAppAuth(@RequestBody ApplicationAuthorizationVO appAuth) {
		Response res = new Response();

		try {
			String[] groups = StringUtil.split(appAuth.getGroup_id(), ",");
			for (String group : groups) {
				appAuth.setGroup_id(group);
				applicationAuthorizationServiceImpl.saveOrUpdate(appAuth
						.toAppAuth());
			}
		} catch (Exception e) {
			res.setSuccessful(false);
			res.setResultMessage(e.getMessage());
			return res;
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 删除应用授权
	 * 
	 * @param id
	 *            待删除的应用授权ID
	 * @return
	 */
	@RequestMapping(value = "rest/appauth/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteAppAuth(@RequestParam String id) {
		Response res = new Response();
		applicationAuthorizationServiceImpl.delete(id);
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 获取指定应用的应用授权对象
	 * 
	 * @return 应用授权对象列表
	 */
	@RequestMapping(value = "rest/appauth/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listAppAuths(@PathVariable String id,
			HttpServletRequest request) {
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countAll = applicationAuthorizationServiceImpl
				.countAll(" where app.id='" + id + "'");
		List<ApplicationAuthorizationVO> vos = applicationAuthorizationServiceImpl
				.listAppAuths(id, pageInfo.getStart() / pageInfo.getLength()
						+ 1, pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countAll);
		resultMap.put("aaData", vos);
		return resultMap;
	}

	@RequestMapping(value = "rest/appauth/grouptree", method = RequestMethod.GET)
	@ResponseBody
	public List<GroupTreeNode> getGroupTree(@RequestParam String id) {
		return applicationAuthorizationServiceImpl.getGroupTree(id);
	}

	/**
	 * 升级应用
	 * 
	 * @param appVo
	 *            应用对象
	 * @return
	 */
	@RequestMapping(value = "rest/app/upgrade", method = RequestMethod.POST)
	@ResponseBody
	public Response upgradeApp(@RequestBody ApplicationVO appVo) {
		Response res = new Response();
		try {
			applicationServiceImpl.upgradeApp(appVo);
			res.setSuccessful(true);
		} catch (Exception e) {
			res.setSuccessful(false);
			res.setResultMessage(e.getMessage());
		}
		return res;
	}
}
