package com.cht.emm.controller.authorities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;



import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.controller.BaseController;
import com.cht.emm.controller.util.CodeMap;
import com.cht.emm.dao.AuthorityDao;
import com.cht.emm.dao.ResourceAuthDao;
import com.cht.emm.model.Authority;
import com.cht.emm.model.Device;
import com.cht.emm.model.Group;
import com.cht.emm.model.MenuItem;
import com.cht.emm.model.Role;
import com.cht.emm.model.User;
import com.cht.emm.model.UserDetail;
import com.cht.emm.model.id.ResourceAuth;
import com.cht.emm.rpc.RPCService;
import com.cht.emm.security.MySecurityMetadataSource;
import com.cht.emm.security.OpPackage;
import com.cht.emm.service.AuthorityService;
import com.cht.emm.service.GroupService;
import com.cht.emm.service.MenuItemService;
import com.cht.emm.service.ResourceAuthService;
import com.cht.emm.service.ResourceService;
import com.cht.emm.service.RoleResourcePermissionService;
import com.cht.emm.service.RoleService;
import com.cht.emm.service.SecurityOpService;
import com.cht.emm.service.ThirdPartConfigService;
import com.cht.emm.service.UserGroupService;
import com.cht.emm.service.UserRoleService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.ByteTool;
import com.cht.emm.util.ColumnProperty;
import com.cht.emm.util.KeyValue;
import com.cht.emm.util.MemCache;
import com.cht.emm.util.NodeTools;
import com.cht.emm.util.PageInfo;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.util.ResourceUrlManager;
import com.cht.emm.util.Response;
import com.cht.emm.util.StringUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.db19.ParameterUtil;
import com.cht.emm.util.emm.EMMService;
import com.cht.emm.util.objectcopier.GroupCopier;
import com.cht.emm.util.objectcopier.ResourceCopier;
import com.cht.emm.util.objectcopier.UserCopier;
import com.cht.emm.vo.AuthVO;
import com.cht.emm.vo.GroupVO;
import com.cht.emm.vo.MenuItemVO;
import com.cht.emm.vo.NodeInst;
import com.cht.emm.vo.NodeLazy;
import com.cht.emm.vo.ResourceAuthVO;
import com.cht.emm.vo.ResourceOpsVO;
import com.cht.emm.vo.ResourceVO;
import com.cht.emm.vo.RoleOpsVO;
import com.cht.emm.vo.RoleVO;
import com.cht.emm.vo.UserVO;

@Controller
public class AuthoritiesServiceController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AuthoritiesServiceController.class);

	@Resource
	EMMService emmService;
	@Resource
	UserService userService;

	@Resource
	ResourceService resourceService;

	@Resource
	AuthorityDao authorityDao;
	@Resource
	RoleService roleService;

	@Resource
	ResourceAuthDao resourceAuthDao;
	@Resource
	GroupService groupService;
	@Resource
	AuthorityService authorityService;
	// DeviceService deviceService;

	@Resource
	UserGroupService userGroupService;

	@Resource
	UserRoleService userRoleService;

	@Resource
	RoleResourcePermissionService roleResourcePermissionService;

	@Resource
	ResourceAuthService resourceAuthService;

	@Resource(name = "rpcService")
	RPCService rpcService;

	@Resource(name = "menuItemService")
	MenuItemService menuItemService;

	@Resource
	PropertiesReader propertiesReader;

	@Resource
	MemCache memCaches;

	@Resource
	SecurityOpService securityOpService;
	
	@Resource(name="thirdPartConfigService")
	ThirdPartConfigService thirdPartConfigService;

	@RequestMapping(value = "/rest/user/checkUserNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validUserNameExist(String username) {
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.loadUserByUserName(username);
		if (user == null) {
			result = true;
		}
		map.put("valid", result);
		return map;
	}

	@RequestMapping(value = "/rest/group/checkGroupNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validGroupNameExist(String id, String groupName) {
		boolean result = false;
		Group parent = groupService.get(id).getParentGroup();

		result = groupService.checkSubName(id,
				parent == null ? null : parent.getId(), groupName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("valid", result);
		return map;
	}

	@RequestMapping(value = "/rest/role/checkRoleNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validRoleNameExist(String roleName) {
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("roleName", roleName));
		List<Role> roles = roleService.listAll(query, null, -1, -1);
		if (roles == null || roles.size() == 0) {
			result = true;
		}
		map.put("valid", result);
		return map;
	}

	@RequestMapping(value = "/rest/resource/checkResourceNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validResourceNameExist(String resourceName) {
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("name", resourceName));
		List<com.cht.emm.model.Resource> resources = resourceService
				.listAll(query, null, -1, -1);
		if (resources == null || resources.size() == 0) {
			result = true;
		}
		map.put("valid", result);
		return map;
	}

	@RequestMapping(value = "/rest/auth/checkAuthNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validAuthNameExist(String name) {
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("name", name));
		List<Authority> auths = authorityService.listAll(query, null, -1, -1);
		if (auths == null || auths.size() == 0) {
			result = true;
		}
		map.put("valid", result);
		return map;
	}

	@RequestMapping(value = "/rest/user/all", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllUsers() {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UserVO> users = userService.getAllUsers();
		resultMap.put("userlist", users);
		resultMap.put("status", CodeMap.SUCCESS);
		return resultMap;
	}

	@RequestMapping(value = "/rest/user/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserPages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		/**
		 * 此处type表明 用户分页显示的使用场景 group: 在分配组用户时使用，需要进行组内已有用户的过滤，以防重复添加 role:
		 * 在给角色分配用户时使用，需要排除role本身拥有的用户
		 */
		String type = request.getParameter("type");
		String id = request.getParameter("id");

		String exlcudesql = null;
		if ("role".equals(type)) {
			exlcudesql = "id not in (select ur.user.id from UserRole ur where ur.role.id='"
					+ id + "' )";
		} else if ("group".equals(type)) {
			exlcudesql = "id not in (select ug.user.id from UserGroup ug where ug.group.id= '"
					+ id + "' )";
		} else {
			exlcudesql = "";
		}
		if (search != null && !"".equals(search)) {

			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}
					/**
					 * 由于基本列表中不需要显示用户所在组合角色，故过滤掉了 userrole usergroup的关联
					 * 但是此处增减了电话号码的显示,所以需要和mobile匹配
					 */
					if (StringUtil.isNullOrBlank(type)) {
						if ("mobile".equals(columnProperty.getColName())) {
							condition.append(" detail.mobile like '%" + search
									+ "%' ");
						}
					} else {
						if ("groups".equals(columnProperty.getColName())) {

							condition
									.append(" id in ( select ug.user.id from UserGroup  ug where ug.group.groupName like '%"
											+ search + "%' ) ");
						}

						if ("roles".equals(columnProperty.getColName())) {

							condition
									.append(" id in ( select ur.user.id from UserRole  ur where ur.role.roleName like '%"
											+ search + "%'  ) ");
						}

					}
					/**
					 * 几个需要进行匹配的字段，因为vo和do的差异，需要对vo字段进行适当的调整
					 */
					if ("userAlias".equals(columnProperty.getColName())) {
						condition.append(" detail.userAlias like '%" + search
								+ "%' ");
					}

					if ("username".equals(columnProperty.getColName())) {
						condition.append("  username like '%" + search + "%'");
					}
				}
			}

		}

		String conditionQuery = "";
		String orderList = "";

		if (!StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql)) {
			conditionQuery = " where ( " + condition.toString() + ") and  ( "
					+ exlcudesql + " )";
		} else if ((StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql) || (!StringUtil
				.isNullOrBlank(condition.toString()) && StringUtil
				.isNullOrBlank(exlcudesql)))) {
			conditionQuery = " where ("
					+ (StringUtil.isNullOrBlank(condition.toString()) ? exlcudesql
							: condition.toString()) + " ) ";
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		/**
		 * 此处排序和search处理的方式相同，由于vo和do的差异，需要做适当的调整
		 */
		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (!"".equals(orderBy.toString())) {
				orderBy.append(",");
			}
			String colName = columns.get(keyValue.getKey()).getColName();
			if ("username".equals(colName)) {
				orderBy.append(" username " + keyValue.getValue().toLowerCase());
			}

			if ("userAlias".equals(colName)) {
				orderBy.append(" detail.userAlias "
						+ keyValue.getValue().toLowerCase());
			}

			if ("mobile".equals(colName)) {
				orderBy.append(" detail.mobile "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (!"".equals(orderBy.toString())) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = userService.countAll(conditionQuery);
		int countAll = userService.countAll();
		Page<UserVO> users = userService.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", users.getItems());
		return resultMap;
	}

	@RequestMapping(value = "/rest/group/nodes", method = RequestMethod.POST)
	@ResponseBody
	public NodeInst getGroupNodes() {
		GroupVO vo = groupService.getTopGroup();
		NodeInst node = NodeTools.toNodeInst(vo);
		node.setRoot(true);
		node.getState().put("opened", true);
		return node;
	}

	@RequestMapping(value = "/rest/group/get", method = RequestMethod.POST)
	@ResponseBody
	public Response getGroup(String id) {
		GroupVO vo = groupService.getById(id);
		Response response = new Response();
		response.setSuccessful(true);
		response.setResultValue(vo);
		return response;
	}

	@RequestMapping(value = "/rest/group/all", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllGroups() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<GroupVO> groups = groupService.getAllGroups();
		resultMap.put("groupList", groups);
		resultMap.put("status", CodeMap.SUCCESS);
		return resultMap;
	}

	@RequestMapping(value = "/rest/group/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllGroupPages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		List<Criterion> criterions = new ArrayList<Criterion>();
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		String conditionQuery = "";

		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String exlcudesql = null;
		if ("user".equals(type)) {
			exlcudesql = "id not in (select u.group.id from UserGroup u where u.user.id='"
					+ id + "')";
		} else if ("group".equals(type)) {
			exlcudesql = "id not in (select g.id from Group g where (g.id = '"
					+ id + "' or g.parentGroup.id= '" + id
					+ "' ) or g.parentGroup.id != null) ";
		} else {
			exlcudesql = "";
		}

		if (search != null && !"".equals(search)) {
			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					criterions.add(Restrictions.like(
							columnProperty.getColName(), search,
							MatchMode.ANYWHERE));
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}

					condition.append(columnProperty.getColName() + " like '%"
							+ search + "%' ");
				}

			}

		}

		if (!StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql)) {
			conditionQuery = " where ( " + condition.toString() + ") and  ( "
					+ exlcudesql + " )";
		} else if ((StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql) || (!StringUtil
				.isNullOrBlank(condition.toString()) && StringUtil
				.isNullOrBlank(exlcudesql)))) {
			conditionQuery = " where "
					+ (StringUtil.isNullOrBlank(condition.toString()) ? exlcudesql
							: condition.toString());
		}

		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		// StringBuilder orderBy = new StringBuilder();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (!"".equals(orderBy.toString())) {
				orderBy.append(",");
			}
			String colName = columns.get(keyValue.getKey()).getColName();
			if ("username".equals(colName)) {
				orderBy.append(" username " + keyValue.getValue().toLowerCase());
			}

		}
		String orderList = "";
		if (!"".equals(orderBy.toString())) {
			orderList = " order by " + orderBy.toString();
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = groupService.countAll(conditionQuery);
		int countAll = groupService.countAll();
		Page<GroupVO> users = groupService.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", users.getItems());
		return resultMap;

	}

	@RequestMapping(value = "/rest/role/all", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllRoles() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<RoleVO> roles = roleService.getAllRoles();
		resultMap.put("roleList", roles);
		resultMap.put("status", CodeMap.SUCCESS);
		return resultMap;
	}

	@RequestMapping(value = "/rest/role/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllRolePages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);

		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String exlcudesql = null;
		/*
		 * 用户选择角色时 ，需要排除已选的角色，免得重复选择
		 */
		if ("user".equals(type)) {
			exlcudesql = "id not in (select ur.role.id from UserRole ur where ur.user.id='"
					+ id + "' or ur.role.isSystem =1 ) ";
		} else if ("resource".equals(type)) {
			exlcudesql = "id not in (select rr.role.id from RoleResourcePermission rr where rr.resource.id='"
					+ id + "' or rr.role.isSystem =1)";
		} else {
			exlcudesql = " isSystem != 1 ";
		}
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		String conditionQuery = "";
		StringBuilder orderBy = new StringBuilder();
		/*
		 * 查询条件，需和页面配合决定哪些字段在查询的范围之内
		 */
		if (search != null && !"".equals(search)) {
			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}
					condition.append(columnProperty.getColName() + " like '"
							+ search + "' ");
				}
			}
		}
		/*
		 * 拼凑 where 条件语句
		 */
		if (!StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql)) {
			conditionQuery = " where ( " + condition.toString() + ") and  ( "
					+ exlcudesql + " ) ";
		} else if ((StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql) || (!StringUtil
				.isNullOrBlank(condition.toString()) && StringUtil
				.isNullOrBlank(exlcudesql)))) {
			conditionQuery = " where "
					+ (StringUtil.isNullOrBlank(condition.toString()) ? exlcudesql
							: condition.toString());
		}

		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		/*
		 * 拼凑order 排序语句
		 */
		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (!"".equals(orderBy.toString())) {
				orderBy.append(",");
			}
			String colName = columns.get(keyValue.getKey()).getColName();
			orderBy.append(" " + colName + " "
					+ keyValue.getValue().toLowerCase());
		}

		String orderList = "";
		if (!StringUtil.isNullOrBlank(orderBy.toString())) {
			orderList = " order by " + orderBy.toString() + " ";
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = roleService.countAll(conditionQuery);
		// int countAll = roleService.countAll();
		Page<RoleVO> roles = roleService.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countFilter);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", roles.getItems());
		return resultMap;
	}

	@RequestMapping(value = "/rest/resource/all", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllResource() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ResourceVO> resourceList = resourceService.getResources();
		resultMap.put("resourceList", resourceList);
		resultMap.put("status", CodeMap.SUCCESS);
		return resultMap;
	}

	@RequestMapping(value = "/rest/resource/select", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAVResource(String id, String type) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ResourceVO> resourceList = resourceService
				.selectResource(type, id);
		resultMap.put("resourceList", resourceList);
		resultMap.put("status", CodeMap.SUCCESS);
		return resultMap;
	}

	@RequestMapping(value = "/rest/resource/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllResourcePages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (search != null && !"".equals(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}
					if ("resourceAuths".equals(colnName)) {
						condition
								.append(" id in ( select ra.resource.id from ResourceAuth  ra where ra.auth.name like '%"
										+ search + "%' ) ");
					} else if ("roles".equals(colnName)) {
						condition
								.append(" id in ( select rr.resource.id from RoleResourcePermission  rr where rr.role.roleName like '%"
										+ search + "%'  ) ");
					} else if ("name".equals(colnName)) {
						condition.append("  name like '%" + search + "%'");
					} else if ("uri".equals(colnName)) {
						condition.append("  uri like '%" + search + "%'");
					}
				}
			}

		}
		/*
		 * 排除方案
		 */
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String exlcudesql = null;
		if ("role".equals(type)) {
			exlcudesql = "id not in (select rr.resource.id from RoleResourcePermission rr where rr.role.id='"
					+ id + "')";
		} else {
			exlcudesql = "";
		}

		String conditionQuery = "";
		String orderList = "";
		if (!StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql)) {
			conditionQuery = " where ( " + condition.toString() + ") and  ( "
					+ exlcudesql + " )";
		} else if ((StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql) || (!StringUtil
				.isNullOrBlank(condition.toString()) && StringUtil
				.isNullOrBlank(exlcudesql)))) {
			conditionQuery = " where "
					+ (StringUtil.isNullOrBlank(condition.toString()) ? exlcudesql
							: condition.toString());
		}
		if(StringUtil.isNullOrBlank(conditionQuery)){
			conditionQuery = " where isSystem!=1 ";
		}else {
			conditionQuery += " and isSystem!=1 ";
		}
		
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (!"".equals(orderBy.toString())) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("name".equals(colName) || "uri".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (!"".equals(orderBy.toString())) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = resourceService.countAll(conditionQuery);
		int countAll = countFilter;
		Page<ResourceVO> resources = resourceService.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", resources.getItems());
		return resultMap;

	}

	@RequestMapping(value = "/rest/userrole/add", method = RequestMethod.POST)
	@ResponseBody
	public Response saveUserRole(String userId, String roleIds) {
		UserVO user = userRoleService.addUserRole(userId, roleIds);

		Response res = new Response();
		if (user != null) {
			res.setSuccessful(true);
			res.setResultValue(user.getRoles());
		}

		return res;
	}

	@RequestMapping(value = "/rest/userrole/remove", method = RequestMethod.POST)
	@ResponseBody
	public Response removeUserRole(String userId, String roleIds) {
		UserVO user = userService.removeUserRole(userId, roleIds);
		Response res = new Response();
		if (user != null) {
			res.setSuccessful(true);
		}

		return res;
	}

	@RequestMapping(value = "/rest/roleuser/remove", method = RequestMethod.POST)
	@ResponseBody
	public Response removeRoleUser(String roleId, String userIds) {
		RoleVO role = userRoleService.removeRoleUser(roleId, userIds);
		Response res = new Response();
		if (role != null) {
			res.setSuccessful(true);
		}

		return res;
	}

	@RequestMapping(value = "/rest/usergroup/add", method = RequestMethod.POST)
	@ResponseBody
	public Response saveUserGroup(String userId, String groupIds) {
		UserVO user = userGroupService.addUserGroup(userId, groupIds);
		Response res = new Response();
		if (user != null) {
			res.setSuccessful(true);
			String[] groups = groupIds.split(",");
			List<String> userIds = new ArrayList<String>();
			userIds.add(userId);
			for (String groupId : groups) {
				rpcService.changeUserOrganization(groupId, userIds);
			}
		}
		res.setResultValue(user.getGroups());
		return res;
	}

	@RequestMapping(value = "/rest/groupuser/add", method = RequestMethod.POST)
	@ResponseBody
	public Response saveGroupUser(String groupId, String userIds) {
		GroupVO group = userGroupService.addGroupUser(groupId, userIds);
		Response res = new Response();
		if (group != null) {
			res.setSuccessful(true);
			List<String> ids = new ArrayList<String>();
			String[] users = userIds.split(",");
			for (String id : users) {
				ids.add(id);
			}

			{
				// 通知
				rpcService.changeUserOrganization(groupId, ids);

			}

		}
		res.setResultValue(group.getUsers());
		return res;
	}

	@RequestMapping(value = "/rest/group/saveSubGroup", method = RequestMethod.GET)
	@ResponseBody
	public Response saveSubGroup(String groupId, String subIds) {
		GroupVO group = groupService.saveSubGroup(groupId, subIds);
		Response res = new Response();
		if (group != null) {
			res.setSuccessful(true);
			res.setResultValue(group.getSubGroups());

			{
				// 通知
				List<GroupVO> groups = group.getSubGroups();
				rpcService.addOrUpdateOrganization1(groups);

			}

		}

		return res;
	}

	@RequestMapping(value = "/rest/group/delSubGroup", method = RequestMethod.GET)
	@ResponseBody
	public Response deleteSubGroup(String groupId, String subIds) {
		Response res = new Response();
		try {
			List<Group> groups = groupService.listByIds(subIds.split(","));
			for (Group group : groups) {
				group.setParentGroup(null);
				group.update();
			}
			res.setSuccessful(true);
			{
				// 通知
				rpcService.addOrUpdateOrganization2(groups);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	@RequestMapping(value = "/rest/roleuser/save", method = RequestMethod.POST)
	@ResponseBody
	public Response saveRoleUser(String userIds, String roleId) {
		RoleVO role = userRoleService.addRoleUser(roleId, userIds);
		Response res = new Response();
		if (role != null) {
			res.setResultValue(role.getUsers());
			res.setSuccessful(true);
		}
		return res;
	}

	@RequestMapping(value = "/rest/usergroup/remove", method = RequestMethod.POST)
	@ResponseBody
	public Response removeUserGroup(String userId, String groupIds) {
		UserVO user = userService.removeUserGroup(userId, groupIds);
		Response res = new Response();
		if (user != null) {
			res.setSuccessful(true);

			// String[] groups =groupIds.split(",");
			// List<String> userIds = new ArrayList<String>();
			// userIds.add(userId);
			// for (String groupId : groups) {
			//
			// rpcService.changeUserOrganization(groupId, userIds);
			// }

		}
		return res;
	}

	@RequestMapping(value = "/rest/groupuser/remove", method = RequestMethod.POST)
	@ResponseBody
	public Response removeGroupUser(String groupId, String userIds) {
		GroupVO group = userGroupService.removeGroupUser(groupId, userIds);
		Response res = new Response();
		if (group != null) {
			res.setSuccessful(true);
		}
		return res;
	}

	@RequestMapping(value = "rest/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response getUserInfoByID(String id) {
		Response response = new Response();
		return response;
	}

	/**
	 * @Name: addResource
	 * @Decription: 新增资源
	 * @Time: 2015-1-15 上午10:33:30
	 * @param resourceName
	 * @param resourceType
	 * @param resourceUri
	 * @param resourceAuth
	 * @param isItem
	 * @return Response
	 */
	@RequestMapping(value = "/rest/resource/save", method = RequestMethod.POST)
	@ResponseBody
	public Response addResource(String resourceName, Integer resourceType,
			String resourceUri, String resourceAuth, Integer isItem) {

		Response response = new Response();
		boolean isSuccess = false;
		try {
			com.cht.emm.model.Resource resource = new com.cht.emm.model.Resource();
			resource.setId(UUIDGen.getUUID());
			resource.setName(resourceName);
			resource.setType(resourceType);
			resource.setUri(resourceUri);
			resource.setIsItem(isItem);
			resource.save();
			String[] auth_id = resourceAuth.split(",");
			List<Authority> auths = authorityService.listByIds(auth_id);

			List<Integer> locIndex = new ArrayList<Integer>();
			for (Authority authority : auths) {
				locIndex.add(authority.getLocIndex());
				ResourceAuth ra = new ResourceAuth();
				ra.setAuth(authority);
				ra.setResource(resource);
				ra.setId(UUIDGen.getUUID());
				ra.setSubUri(resource.getUri().replace("*", "") + "suburi");
				ra.save();
				if (resource.getResourceAuths() == null) {
					resource.setResourceAuths(new HashSet<ResourceAuth>());
				}
				resource.getResourceAuths().add(ra);
			}
			resource.setPermission(ByteTool.sum(locIndex));
			resource.update();
			response.setResultValue(ResourceCopier.copy(resource, auths));
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		response.setSuccessful(isSuccess);
		return response;
	}

	/**
	 * @Name: editResource
	 * @Decription: 保存资源
	 * @Time: 2015-1-15 上午10:33:01
	 * @param id
	 * @param resourceName
	 * @param resourceType
	 * @param resourceUri
	 * @param resourceAuth
	 * @param isItem
	 * @return Response
	 */
	@RequestMapping(value = "/rest/resource/edit", method = RequestMethod.POST)
	@ResponseBody
	public Response editResource(String id, String resourceName,
			Integer resourceType, String resourceUri, String resourceAuth,
			Integer isItem) {

		Response response = new Response();
		boolean isSuccess = true;
		try {
			resourceService.saveResourceAndAuth(id, resourceAuth, null);
			com.cht.emm.model.Resource resource = resourceService
					.get(id);
			String srcResUrl = resource.getUri();
			resource.setName(resourceName);
			resource.setType(resourceType);
			resource.setUri(resourceUri);
			resource.setIsItem(isItem);

			resource.update();

			// 缓存同步
			MySecurityMetadataSource.singleton().updateResource(srcResUrl,
					resourceUri);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		response.setSuccessful(isSuccess);
		response.setResultValue(resourceService.getVMById(id));
		return response;
	}

	/**
	 * 资源相关的操作
	 */

	/**
	 * 保存资源对于角色的权限
	 * 
	 * @param resourceId
	 * @param roleId
	 * @param authId
	 * @return
	 */
	@RequestMapping(value = "/rest/resourcerole/save", method = RequestMethod.POST)
	@ResponseBody
	public Response saveRoleResource(String resourceId, String roleId,
			String authId) {
		Response response = new Response();
		boolean successful = true;
		try {
			resourceService.saveResourceRole(resourceId, roleId, authId);

		} catch (Exception e) {
			e.printStackTrace();
			successful = false;
		}
		response.setSuccessful(successful);
		return response;
	}

	@RequestMapping(value = "/rest/auths/all", method = RequestMethod.GET)
	@ResponseBody
	public Response getAllAuths() {
		Response response = new Response();
		List<AuthVO> auths = resourceService.getAllAuths();
		response.setSuccessful(true);
		response.setResultValue(auths);
		return response;
	}

	@RequestMapping(value = "/rest/auths/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAuthPages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		List<Criterion> criterions = new ArrayList<Criterion>();
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		String conditionQuery = "";
		String exlcudesql = null;
		if (search != null && !"".equals(search)) {
			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					criterions.add(Restrictions.like(
							columnProperty.getColName(), search,
							MatchMode.ANYWHERE));
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}

					condition.append(columnProperty.getColName() + " like '%"
							+ search + "%' ");
				}

			}

		}

		if (!StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql)) {
			conditionQuery = " where ( " + condition.toString() + ") and  ( "
					+ exlcudesql + " )";
		} else if ((StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql) || (!StringUtil
				.isNullOrBlank(condition.toString()) && StringUtil
				.isNullOrBlank(exlcudesql)))) {
			conditionQuery = " where "
					+ (StringUtil.isNullOrBlank(condition.toString()) ? exlcudesql
							: condition.toString());
		}

		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (!"".equals(orderBy.toString())) {
				orderBy.append(",");
			}
			String colName = columns.get(keyValue.getKey()).getColName();
			if ("name".equals(colName)) {
				orderBy.append(" name " + keyValue.getValue().toLowerCase());
			}

			if ("descp".equals(colName)) {
				orderBy.append(" descp " + keyValue.getValue().toLowerCase());
			}

			if ("locIndex".equals(colName)) {
				orderBy.append(" locIndex " + keyValue.getValue().toLowerCase());
			}
			if ("showIndex".equals(colName)) {
				orderBy.append(" showIndex "
						+ keyValue.getValue().toLowerCase());
			}
		}
		String orderList = "";
		if (!"".equals(orderBy.toString())) {
			orderList = " order by " + orderBy.toString();
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = authorityService.countAll(conditionQuery);
		int countAll = authorityService.countAll();
		Page<AuthVO> auths = authorityService.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", auths.getItems());
		return resultMap;

	}

	@RequestMapping(value = "/rest/auth/saveOrupdate", method = RequestMethod.POST)
	@ResponseBody
	public Response saveOrUpdateAuth(String id, String name, String descp,
			Integer showIndex) {
		Response response = new Response();
		Authority auth = null;
		if (StringUtil.isNullOrBlank(id)) {
			auth = new Authority();

			int max = authorityService.getMaxIndex() + 1;
			auth.setLocIndex(max);
			auth.setShowIndex(max);
		} else {
			auth = authorityService.get(id);
		}
		auth.setDescp(descp);
		auth.setName(name);
		if (showIndex != auth.getShowIndex()) {
			authorityService.updateShowIndex(showIndex, auth.getShowIndex());
			auth.setShowIndex(showIndex);
		}
		if (StringUtil.isNullOrBlank(id)) {
			auth.setId(UUIDGen.getUUID());
			auth.save();
		} else {
			auth.update();
		}
		response.setSuccessful(true);
		return response;
	}

	@RequestMapping(value = "/rest/resourceauth/save", method = RequestMethod.POST)
	@ResponseBody
	public Response saveResourceAuth(String resourceId, String authIds,
			String uris) {
		Response response = new Response();
		ResourceVO resource = resourceService.saveResourceAndAuth(resourceId,
				authIds, uris);
		if (resource != null) {
			response.setSuccessful(true);
		}
		return response;
	}

	@RequestMapping(value = "/rest/resourcerole/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addResourceRole(String resourceId, String roleIds) {
		Response response = new Response();
		boolean successful = false;
		try {
			List<RoleOpsVO> added = new ArrayList<RoleOpsVO>();
			List<RoleOpsVO> result = resourceService.addResourceRole(
					resourceId, roleIds);
			List<String> roleNames = new ArrayList<String>();
			List<String> urls = new ArrayList<String>();
			urls.add(resourceService.get(resourceId).getUri());

			for (RoleOpsVO roleOpsVO : result) {
				if (roleIds.indexOf(roleOpsVO.getRole().getId()) >= 0) {
					added.add(roleOpsVO);
					roleNames.add(roleOpsVO.getRole().getRoleName());
					List<ResourceAuthVO> resourceAuths = roleOpsVO
							.getResourceAuth();
					if (resourceAuths != null && resourceAuths.size() > 0) {
						for (ResourceAuthVO resourceAuth : resourceAuths) {
							if (!urls.contains(resourceAuth.getSubUri())) {
								urls.add(resourceAuth.getSubUri());
							}

						}
					}

				}
			}
			// 缓存在业务层实现
			LOGGER.info(" urls :" + urls + " , roles :" + roleNames);
			for (String url : urls) {
				MySecurityMetadataSource.singleton().relateResourceRole(url,
						roleNames.toArray(new String[roleNames.size()]));
			}

			response.setResultValue(added);
			successful = true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		response.setSuccessful(successful);
		return response;
	}

	@RequestMapping(value = "/rest/roleresource/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addRoleResource(String roleId, String resourceIds) {
		Response response = new Response();
		boolean successful = true;
		try {

			RoleVO role = resourceService.addRoleResource(resourceIds, roleId);
			List<ResourceOpsVO> added = new ArrayList<ResourceOpsVO>();
			List<ResourceOpsVO> all = role.getResources();
			List<String> resUrls = new ArrayList<String>();
			for (ResourceOpsVO resourceOpsVO : all) {
				if (resourceIds.indexOf(resourceOpsVO.getResource().getId()) >= 0) {
					added.add(resourceOpsVO);
					List<ResourceAuthVO> resourceAuths = resourceOpsVO
							.getResourceAuths();
					if (resourceAuths != null && resourceAuths.size() > 0) {
						for (ResourceAuthVO resourceAuth : resourceAuths) {
							String subUrl = resourceAuth.getSubUri();
							if (!resUrls.contains(subUrl)) {
								resUrls.add(subUrl);
							}
						}
					}
					resUrls.add(resourceOpsVO.getResource().getUri());
				}
			}
			String roleName = role.getRoleName();
			LOGGER.info("roleName: " + roleName + ", urls: " + resUrls);

			// 缓存同步
			MySecurityMetadataSource.singleton().relateRoleResource(roleName,
					resUrls.toArray(new String[resUrls.size()]));
			response.setResultValue(added);
		} catch (Exception e) {
			e.printStackTrace();
			successful = false;
		}
		response.setSuccessful(successful);
		return response;
	}

	@RequestMapping(value = "/rest/roleresource/remove", method = RequestMethod.POST)
	@ResponseBody
	public Response removeRoleResource(String roleId, String resourceId) {

		RoleVO role = roleService.getById(roleId);
		String roleName = role.getRoleName();
		ResourceVO resource = resourceService.getVMById(resourceId);
		List<ResourceAuthVO> resourceAuths = resource.getResourceAuths();
		List<String> urls = new ArrayList<String>();
		if (resourceAuths != null && resourceAuths.size() > 0) {
			for (ResourceAuthVO resourceAuth : resourceAuths) {
				String subUrl = resourceAuth.getSubUri();
				if (!urls.contains(subUrl)) {
					urls.add(subUrl);
				}
			}

		}
		LOGGER.info("release role resource ");
		LOGGER.info("roleName :" + roleName + "  , urls : " + urls);

		MySecurityMetadataSource.singleton().unrelateRoleResource(roleName,
				urls.toArray(new String[urls.size()]));
		boolean result = resourceService.removeRoleResource(roleId, resourceId);
		Response res = new Response();

		res.setSuccessful(result);

		return res;
	}

	@RequestMapping(value = "/rest/user/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addNewUser(String username, String password, String groups,
			String roles, String userAlias, String mobile, String email,
			Integer sex, Integer userType) {
		Response response = new Response();

		User user = new User();
		user.setStatus(1);
		user.setUsername(username);
		user.setPassword(password);
		user.setUserType(userType);

		UserDetail detail = new UserDetail();
		detail.setEmail(email);
		detail.setMobile(mobile);
		detail.setSex(sex);
		detail.setUserAlias(userAlias);
		user.setDetail(detail);
		detail.setCreateTime(new Timestamp(new Date().getTime()));
		boolean notifiedPassed = false;
		{
			// 与emm同步代码
			if (emmService.isUsed()) {
				UserVO userVO = UserCopier.singleCopy(user);
				String[] groupId = groups.split(",");
				List<String> groupIds = StringUtil.getList(groupId);
				userVO.setGroupIds(groupIds);
				Response result = new Response();
				GroupVO top = groupService.getTopGroup();
				// 根据不同的类型新增同的组织架构
				if (userType == User.UserType.ORG_MASTER.getType()){
					result.setSuccessful(true);
//					result = emmService.addOrgMaster(userVO,top.getId());
				} else if ( userType == User.UserType.DEPART_MASTER.getType()) {
					result.setSuccessful(true);
//					result = emmService.addDepMaster(userVO,top.getId());
				} else if (userType == User.UserType.COMMON.getType()) {
					result = emmService.addUser(userVO, top.getId());
				}
				if (result.isSuccessful()) {
					notifiedPassed = true;
				}
				// 如果成功，将得到的id赋予该用户
				if (notifiedPassed) {
					user.setId((String) result.getResultValue());
					user.getDetail().setId(user.getId());
				}

			} else {
				// 如果未使用emm服务，则自己产生id
				user.setId(UUIDGen.getUUID());
				user.getDetail().setId(user.getId());
				notifiedPassed = true;
			}
		}

		if (notifiedPassed) {
			userService.save(user);
			user.getDetail().setUser(user);
			user.getDetail().save();
			userGroupService.addUserGroup(user.getId(), groups);
			if (userType == User.UserType.DEPART_MASTER.getType()
					|| userType == User.UserType.COMMON.getType()) {
				if (StringUtil.isNullOrBlank(roles)) {
					roles = "user";
				} else {
					roles += ",user";
				}
			} else {
				if (StringUtil.isNullOrBlank(roles)) {
					roles = "admin";
				} else {
					roles += ",admin";
				}
			}
			userRoleService.addUserRole(user.getId(), roles);
			response.setSuccessful(true);
			// response.setResultValue(user);
		}

		/*
		 * { // 通知 List<RPCUser> users = new ArrayList<RPCUser>(); RPCUser
		 * rpcUser = new RPCUser();
		 * rpcUser.setOrg_id(user.getGroups().get(0).getId());
		 * rpcUser.setUser_id(user.getUsername());
		 * rpcUser.setPassword(user.getPassword());
		 * rpcUser.setReal_name(userAlias); rpcUser.setStatus(user.getStatus()
		 * == 0 ? 1 : 0); users.add(rpcUser); rpcService.addORUpdateUser(users);
		 * if (!StringUtil.isNullOrBlank(groups)) { String groupId =
		 * groups.split(",")[0]; List<String> userIds = new ArrayList<String>();
		 * userIds.add(user.getId()); rpcService.changeUserOrganization(groupId,
		 * userIds); } }
		 */

		return response;
	}

	@RequestMapping(value = "/rest/user/edit", method = RequestMethod.POST)
	@ResponseBody
	public Response editUser(String id, String password, String groups,Integer userType,
			String roles, String userAlias, String mobile, String email,
			Integer sex) {
		Response response = new Response();
		User user = userService.get(id);
		if (!user.getPassword().equals(password)) {
			user.setPassword(password);
		}
		user.getDetail().setUserAlias(userAlias);
		user.getDetail().setEmail(email);
		user.getDetail().setMobile(mobile);
		user.getDetail().setSex(sex);
		user.setUserType(userType);
		UserVO userVO = UserCopier.singleCopy(user);
		String[] groupId = groups.split(",");
		List<String> groupIds = StringUtil.getList(groupId);

		userVO.setGroupIds(groupIds);
		boolean notifiedPassed = false;
		{
			if (emmService.isUsed()) {
				Response result = null;
				GroupVO top = groupService.getTopGroup();
				// 根据不同的类型新增同的组织架构
				if (userType == User.UserType.ORG_MASTER.getType()
						|| userType == User.UserType.DEPART_MASTER.getType()) {

				} else if (userType == User.UserType.COMMON.getType()) {
					result = emmService.updateUser(userVO, top.getId());
					if (result.isSuccessful()) {
						notifiedPassed = true;
					}
				}
			} else {
				notifiedPassed = true;
			}
		}
		if (notifiedPassed) {
			userService.update(user);
			user.getDetail().update();
			userGroupService.addUserGroup(user.getId(), groups);
			if (userType == User.UserType.DEPART_MASTER.getType()
					|| userType == User.UserType.COMMON.getType()) {
				if (StringUtil.isNullOrBlank(roles)) {
					roles = "user";
				} else {
					roles += ",user";
				}
			} else {
				if (StringUtil.isNullOrBlank(roles)) {
					roles = "admin";
				} else {
					roles += ",admin";
				}
			}
			userRoleService.updateUserRole(user.getId(), roles);
			response.setSuccessful(true);
			// response.setResultValue(user);
		}

		// UserVO user = userService.saveUser(id, username, password, groups,
		// roles, userAlias, mobile, email, sex);
		/*
		 * { // 通知 List<RPCUser> users = new ArrayList<RPCUser>(); RPCUser
		 * rpcUser = new RPCUser(); rpcUser.setOrg_id(user.getId());
		 * rpcUser.setPassword(user.getPassword());
		 * rpcUser.setReal_name(userAlias); rpcUser.setStatus(user.getStatus());
		 * rpcService.addORUpdateUser(users); if
		 * (!StringUtil.isNullOrBlank(groups)) { String groupId =
		 * groups.split(",")[0]; List<String> userIds = new ArrayList<String>();
		 * userIds.add(user.getId()); rpcService.changeUserOrganization(groupId,
		 * userIds); } }
		 */

		// response.setResultValue(user);
		return response;
	}

	@RequestMapping(value="/rest/role/userType",method = RequestMethod.POST)
	@ResponseBody
	public Response getRolesByUserType(Integer userType){
		Response response = new Response();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.gt(" bitand(userType,"+(1<<( userType -1 ))+") ", 0));
		List<RoleVO> roles = roleService.selectRole(userType);
		response.setResultValue(roles);
		response.setSuccessful(true);
		return response;
	}
	
	
	@RequestMapping(value = "/rest/role/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addNewRole(String roleName,String userTypes, String roleDesc) {
		Response response = new Response();
		RoleVO role = roleService.addRole(roleName, roleDesc);
		Role role2 = roleService.get(role.getId());
		List<Integer> types = new ArrayList<Integer>();
		String[] types2 = userTypes.split(",");
		for (String type : types2) {
			if(!StringUtil.isNullOrBlank(type)){
				types.add(Integer.parseInt(type));
			}
		}
		role2.setUserType(ByteTool.sum(types));
		role2.update();
		if (role != null) {
			response.setResultValue(role);
			response.setSuccessful(true);
		}
		return response;
	}

	@RequestMapping(value = "/rest/role/edit", method = RequestMethod.POST)
	@ResponseBody
	public Response editRole(String id, String roleName, String roleDesc,String userTypes) {
		Response response = new Response();
		Role role = roleService.get(id);
		String srcRoleName = role.getRoleName();
		String[] types2 = userTypes.split(",");
		List<Integer> types = new ArrayList<Integer>();
		for (String type : types2) {
			if(!StringUtil.isNullOrBlank(type)){
				types.add(Integer.parseInt(type));
			}
		}
		role.setUserType(ByteTool.sum(types));
		role.setRoleDesc(roleDesc);
		role.setRoleName(roleName);
		role.update();
		// 缓存同步
		MySecurityMetadataSource.singleton().updateRole(srcRoleName, roleName);
		response.setSuccessful(true);
		response.setResultValue(roleService.getById(id));
		return response;
	}

	@RequestMapping(value = "/rest/userdevice/save", method = RequestMethod.GET)
	@ResponseBody
	public Response saveUserDevice(String userId, String deviceId,
			String deviceName, String deviceType, String deviceOp) {
		Response response = new Response();
		Device device = new Device();
		device.setId(deviceId);
		// device.setOs(deviceOp);
		// device.set
		return response;

	}

	@RequestMapping(value = "/rest/group/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addNewGroup(String groupName, String desc, String parentId) {
		Response response = new Response();
		Group group = new Group();

		group.setGroupName(groupName);
		group.setGroupDesc(desc);
		group.setStatus(1);
		if (!StringUtil.isNullOrBlank(parentId)) {
			GroupVO parent = groupService.getById(parentId);
			int length = parent.getSubGroups() == null ? 0 : parent
					.getSubGroups().size();
			if (length != 0) {
				group.setGroupName(groupName + "_" + length);
			}
			group.setParentGroup(groupService.get(parentId));
			group.setGroupType(Group.GROUP_TYPE.DEP.getType());
		} else {
			group.setGroupType(Group.GROUP_TYPE.ORG.getType());
		}
		boolean notifiedPassed = false;
		// 如果emm服务启用
		if (emmService.isUsed()) {
			Response result = null;
			// 根据不同的类型新增同的组织架构
			if (group.getGroupType() == Group.GROUP_TYPE.ORG.getType()) {
				result = emmService.addOrg(group);
				if (result.isSuccessful()) {
					notifiedPassed = true;
				}
			} else if (group.getGroupType() == Group.GROUP_TYPE.DEP.getType()) {
				GroupVO top = groupService.getTopGroup();
				result = emmService.addDep(group, top.getId());
				if (result.isSuccessful()) {
					notifiedPassed = true;
				}
			}
			// 如果成功，将得到的id赋予该组
			if (notifiedPassed) {
				group.setId((String) result.getResultValue());
			}
			// else if(group.getGroupType() == Group.GROUP_TYPE.VG.getType()) {
			// result = emmService.(group);
			// if(result.isSuccessful()){
			// notifiedPassed = true;
			// }
			// }
		} else {
			// 如果未使用emm服务，则自己产生id
			group.setId(UUIDGen.getUUID());
			notifiedPassed = true;
		}
		// 如果获得了id
		if (notifiedPassed) {
			groupService.save(group);

			// if(group.getParentGroup()!=null){
			// Group parentGroup =
			// groupService.get(group.getParentGroup().getId());
			// parentGroup.getSubGroups().add(group);
			// groupService.update(parentGroup);
			// }

		}

		response.setSuccessful(true);
		if (group != null) {
			response.setResultValue(GroupCopier.singleCopy(group));
		}

		// {
		// // 通知
		// List<Group> groups = new ArrayList<Group>();
		// groups.add(group);
		//
		// rpcService.addOrUpdateOrganization2(groups);
		// }

		return response;
	}

	@RequestMapping(value = "/rest/group/rename", method = RequestMethod.POST)
	@ResponseBody
	public Response renameGroup(String id, String groupName) {
		Response response = new Response();
		Group group = groupService.get(id);
		Group parentGroup = group.getParentGroup();
		boolean exists = groupService.checkSubName(id,parentGroup.getId(),
				groupName);
		if (exists) {
			group.setGroupName(groupName);
			boolean notifiedPassed = false;
			{

				if (emmService.isUsed()) {
					Response result = null;
					GroupVO top = groupService.getTopGroup();
					if (group.getGroupType() == Group.GROUP_TYPE.ORG.getType()) {
						result = emmService.updateOrg(group);
						if (result.isSuccessful()) {
							notifiedPassed = true;
						}

					} else if (group.getGroupType() == Group.GROUP_TYPE.DEP
							.getType()) {
						result = emmService.updateDep(group, top.getId());
						if (result.isSuccessful()) {
							notifiedPassed = true;
						}
					}

				} else {
					notifiedPassed = true;
				}

			}
			if (notifiedPassed) {
				groupService.update(group);
				response.setSuccessful(true);
			}
		}
		/*
		 * // 通知服务 { List<Group> groups = new ArrayList<Group>();
		 * groups.add(group); rpcService.addOrUpdateOrganization2(groups); }
		 */
		// response.setResultValue(groupService.getById(id));
		return response;
	}

	@RequestMapping(value = "/rest/group/edit", method = RequestMethod.GET)
	@ResponseBody
	public Response editGroup(String id, String groupName, String desc) {
		Response response = new Response();
		Group group = groupService.get(id);
		group.setGroupName(groupName);
		group.setGroupDesc(desc);
		boolean notifiedPassed = false;

		{

			if (emmService.isUsed()) {
				Response result = null;
				GroupVO top = groupService.getTopGroup();
				if (group.getGroupType() == Group.GROUP_TYPE.ORG.getType()) {
					result = emmService.updateOrg(group);
					if (result.isSuccessful()) {
						notifiedPassed = true;
					}

				} else if (group.getGroupType() == Group.GROUP_TYPE.DEP
						.getType()) {
					result = emmService.updateDep(group, top.getId());
					if (result.isSuccessful()) {
						notifiedPassed = true;
					}
				}

			} else {
				notifiedPassed = true;
			}

		}
		if (notifiedPassed) {
			group.update();
			response.setSuccessful(true);
		}
		/*
		 * // 通知服务 { List<Group> groups = new ArrayList<Group>();
		 * groups.add(group); rpcService.addOrUpdateOrganization2(groups); }
		 */
		// response.setResultValue(groupService.getById(id));
		return response;
	}

	@RequestMapping(value = "rest/deleteEntity", method = RequestMethod.GET)
	@ResponseBody
	public Response deleteEntity(String type, String ids) {
		Response response = new Response();
		GroupVO top = groupService.getTopGroup();
		if (type != null) {
			if ("user".equals(type)) {
				// List<String> ids_ = new ArrayList<String>();
				String id[] = ids.split(",");
				for (String userId : id) {
					User user = userService.get(userId);

					boolean notifiedPassed = false;
					if (emmService.isUsed()) {
						if (user.getUserType() == User.UserType.COMMON
								.getType()) {
							Response result = emmService.removeUser(userId,
									top.getId());
							if (result.isSuccessful()) {
								notifiedPassed = true;
							}
						}
					} else {
						notifiedPassed = true;
					}
					if (notifiedPassed) {
						userService.deleteObject(user);
//						user.delete();
					}

				}
			}
			if ("group".equals(type)) {
				String id[] = ids.split(",");
				List<String> allGroupIds = new ArrayList<String>();
				for (String groupId : id) {

					List<String> part = groupService
							.getSubGroupListIDs(groupId);
					for (String string : part) {
						if (!allGroupIds.contains(string)) {
							allGroupIds.add(string);
						}
					}
				}
				for (String nid : allGroupIds) {
					Group group = groupService.get(nid);
					boolean notifiedPassed = false;
					if (emmService.isUsed()) {
						if (group.getGroupType() == Group.GROUP_TYPE.ORG
								.getType()) {
							Response result = emmService.removeOrg(nid);
							if (result.isSuccessful()) {
								notifiedPassed = true;

							}
						} else if (group.getGroupType() == Group.GROUP_TYPE.DEP
								.getType()) {
							Response result = emmService.removeDep(nid,
									top.getId());
							if (result.isSuccessful()) {
								notifiedPassed = true;
							}
						}
					} else {
						notifiedPassed = true;
					}
					if (notifiedPassed) {
						group.delete();
					}

				}
				// groupService.delete(ids.split(","));
				// for (String string : id) {
				// ids_.add(string);
				// }
				// rpcService.deleteOrganization(ids_);
			}
			if ("role".equals(type)) {
				roleService.delete(ids.split(","));
				// 缓存同步
				List<String> roleNames = new ArrayList<String>();
				for (String id : ids.split(",")) {
					roleNames.add(roleService.get(id).getRoleName());
				}
				MySecurityMetadataSource.singleton().removeRoles(
						roleNames.toArray(new String[roleNames.size()]));
			}
			if ("resource".equals(type)) {
				/**
				 * 删除资源需要的做的事情 1.删除相关的RoleResourcePermission 2.更新security中的缓存
				 * 3.删除对应的menuItem
				 */
				String[] idArray = ids.split(",");
				resourceService.releaseAuth(idArray);
				/**
				 * 删除所有的resource
				 */
				resourceService.delete(idArray);

			}
			
			
			if("thirdpart".equals(type)){
				thirdPartConfigService.deleteThirdPart(ids.split(","));
			}
			// if ("auth".equals(type)) {
			// resourceService.delete(ids.split(","));
			// }
			response.setSuccessful(true);
		}

		return response;
	}

	public void deleteCircled(Group group) {

		if (group.getSubGroups() != null) {
			for (Group sub : group.getSubGroups()) {
				deleteCircled(sub);
			}
		}
		group.delete();
	}

	@RequestMapping(value = "/rest/deleteUnionEntity", method = RequestMethod.GET)
	@ResponseBody
	public Response deleteUnionEntity(String type, String pid, String sids) {
		Response response = new Response();
		if (type != null) {
			if ("usergroup".equals(type)) {
				userGroupService.deleteUnionEntityA2B(pid, sids.split(","));
			}
			if ("groupuser".equals(type)) {
				userGroupService.deleteUnionEntityB2A(pid, sids.split(","));
			}
			if ("roleuser".equals(type)) {
				userRoleService.deleteUnionEntityB2A(pid, sids.split(","));
			}
			if ("userrole".equals(type)) {
				userRoleService.deleteUnionEntityA2B(pid, sids.split(","));
			}
			if ("resourceauth".equals(type)) {
				resourceAuthService.deleteUnionEntityA2B(pid, sids.split(","));
			}

			if ("roleresource".equals(type)) {

				// 缓存同步
				// List<String> resUrls = new ArrayList<String>();
				for (String id : sids.split(",")) {
					OpPackage package1 = securityOpService.deleteRoleResource(
							pid, id);
					MySecurityMetadataSource.singleton().resourceRoleOp(
							package1);
				}
				roleResourcePermissionService.deleteUnionEntityA2B(pid,
						sids.split(","));
			}
			if ("resourcerole".equals(type)) {
				roleResourcePermissionService.deleteUnionEntityB2A(pid,
						sids.split(","));
				// 缓存同步
				List<String> roleNames = new ArrayList<String>();
				for (String id : sids.split(",")) {
					roleNames.add(roleService.get(id).getRoleName());
				}
				MySecurityMetadataSource.singleton().unrelateResourceRole(
						resourceService.get(pid).getUri(),
						roleNames.toArray(new String[roleNames.size()]));
			}
			response.setSuccessful(true);
		}
		return response;
	}

	/**
	 * 当资源定义改变时重新加载内存资源，此方法消耗比较高，不建议使用
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/refreshMemoryResource", method = RequestMethod.GET)
	@ResponseBody
	public Response reloadResource() {

		Response response = new Response();

		// 重新加载内存资源
		MySecurityMetadataSource.singleton().reloadResources();

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 内存同步，删除资源
	 * 
	 * @param resUrl
	 *            资源路径
	 * @return
	 */
	@RequestMapping(value = "rest/syncmemory/removeResource", method = RequestMethod.POST)
	@ResponseBody
	public Response removeResources(String[] resUrls, String[][] roleNames) {

		Response response = new Response();

		MySecurityMetadataSource.singleton()
				.removeResources(resUrls, roleNames);

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 内存同步，更新资源
	 * 
	 * @param srcResUrl
	 *            原资源路径
	 * @param destResUrl
	 *            目标资源路径
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/syncmemory/updateResource", method = RequestMethod.POST)
	@ResponseBody
	public Response updateResource(String srcResUrl, String destResUrl) {

		Response response = new Response();

		MySecurityMetadataSource.singleton().updateResource(srcResUrl,
				destResUrl);

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 
	 * 内存同步，删除角色
	 * 
	 * @param roleName
	 *            角色名称
	 * @return
	 */
	@RequestMapping(value = "rest/syncmemory/removeRole", method = RequestMethod.POST)
	@ResponseBody
	public Response removeRoles(String[] roleNames) {

		Response response = new Response();

		MySecurityMetadataSource.singleton().removeRoles(roleNames);

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 内存同步，更新角色
	 * 
	 * @param srcRoleName
	 *            原角色名称
	 * @param destRoleName
	 *            新角色名称
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/syncmemory/updateRole", method = RequestMethod.POST)
	@ResponseBody
	public Response updateRole(String srcRoleName, String destRoleName) {

		Response response = new Response();

		MySecurityMetadataSource.singleton().updateRole(srcRoleName,
				destRoleName);

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 内存同步，添加资源角色的关联
	 * 
	 * @param resUrl
	 *            资源路径
	 * @param roleNames
	 *            角色名称集合
	 * @return
	 */
	@RequestMapping(value = "rest/syncmemory/relateResourceRole", method = RequestMethod.POST)
	@ResponseBody
	public Response relateResourceRole(String resUrl, String[] roleNames) {

		Response response = new Response();

		MySecurityMetadataSource.singleton().relateResourceRole(resUrl,
				roleNames);

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 内存同步，解除资源角色的关联
	 * 
	 * @param resUrl
	 *            资源路径
	 * @param roleNames
	 *            角色名称集合
	 * @return
	 */
	@RequestMapping(value = "rest/syncmemory/unrelateResourceRole", method = RequestMethod.POST)
	@ResponseBody
	public Response unrelateResourceRole(String resUrl, String[] roleNames) {

		Response response = new Response();

		MySecurityMetadataSource.singleton().unrelateResourceRole(resUrl,
				roleNames);

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 内存同步，添加角色资源的管理
	 * 
	 * @param roleName
	 *            角色名称
	 * @param resUrls
	 *            资源路径
	 * @return
	 */
	@RequestMapping(value = "rest/syncmemory/relateRoleResource", method = RequestMethod.POST)
	@ResponseBody
	public Response relateRoleResource(String roleName, String[] resUrls) {

		Response response = new Response();

		MySecurityMetadataSource.singleton().relateRoleResource(roleName,
				resUrls);

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 内存同步，解除角色和资源的管理
	 * 
	 * @param roleName
	 *            角色名称
	 * @param resUrls
	 *            资源路径集合
	 * @return
	 */
	@RequestMapping(value = "rest/syncmemory/unrelateRoleResource", method = RequestMethod.POST)
	@ResponseBody
	public Response unrelateRoleResource(String roleName, String[] resUrls) {

		Response response = new Response();

		MySecurityMetadataSource.singleton().unrelateRoleResource(roleName,
				resUrls);

		response.setSuccessful(true);

		return response;
	}

	/**
	 * 分页功能的 用户选择
	 * 
	 * @return
	 */
	@RequestMapping(value = "rest/selectUser/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectUser(HttpServletRequest request) {

		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (search != null && !"".equals(search)) {

			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}
					if ("userAlias".equals(columnProperty.getColName())) {
						condition.append(" detail.userAlias like '%" + search
								+ "%' ");
					}

					if ("mobile".equals(columnProperty.getColName())) {
						condition.append(" detail.mobile like '%" + search
								+ "%' ");
					}
					if ("username".equals(columnProperty.getColName())) {
						condition.append("  username like '%" + search + "%'");
					}
				}
			}

		}
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String exlcudesql = null;
		if (!StringUtil.isNullOrBlank(id) && !StringUtil.isNullOrBlank(type)) {

			if ("role".equals(type)) {
				exlcudesql = "select ur.user.id from UserRole ur where ur.role.id=?";
			} else if ("group".equals(type)) {
				exlcudesql = "select ug.user.id from UserGroup ug where ug.group.id=?";
			} else {
				exlcudesql = "";
			}
		}

		String conditionQuery = "";
		String orderList = "";
		if (!StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(exlcudesql)) {
			conditionQuery = " where  (" + condition.toString()
					+ ") and id not in (" + exlcudesql + ")";
		} else if ((StringUtil.isNullOrBlank(condition.toString()) && !StringUtil
				.isNullOrBlank(exlcudesql))
				|| (!StringUtil.isNullOrBlank(condition.toString()) && StringUtil
						.isNullOrBlank(exlcudesql))) {
			conditionQuery = "where "
					+ (StringUtil.isNullOrBlank(condition.toString()) ? exlcudesql
							: condition.toString());
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (!"".equals(orderBy.toString())) {
				orderBy.append(",");
			}
			String colName = columns.get(keyValue.getKey()).getColName();
			if ("username".equals(colName)) {
				orderBy.append(" username " + keyValue.getValue().toLowerCase());
			}

			if ("userAlias".equals(colName)) {
				orderBy.append(" detail.userAlias "
						+ keyValue.getValue().toLowerCase());
			}

			if ("mobile".equals(colName)) {
				orderBy.append(" detail.mobile "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (!"".equals(orderBy.toString())) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = userService.countAll(conditionQuery);
		int countAll = userService.countAll();
		Page<UserVO> users = userService.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", users.getItems());
		return resultMap;
	}

	/**
	 * menu相关内容的后台调用 1. content的修改 、删除、新增等操作 2. contextmenu 的排序 3. load子菜单内容
	 */

	@RequestMapping(value = "/rest/menu/get", method = RequestMethod.POST)
	@ResponseBody
	public Response getItem(String id) {
		Response response = new Response();
		// 虚拟顶级顶点
		MenuItemVO vo = null;
		if (StringUtil.filterId(id) == null) {
			vo = new MenuItemVO();
			vo.setId("#");
			vo.setIndex(0);
			vo.setRoot(true);

		} else {
			vo = menuItemService.getVO(id);
		}
		vo.setMax_index(menuItemService.getMaxIndex(vo.getParentId()));
		response.setSuccessful(true);
		response.setResultValue(vo);
		return response;
	}

	@RequestMapping(value = "/rest/menu/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addMenu(String title, String parentId,
			String type) {
		Response response = new Response();
		MenuItemVO vo = new MenuItemVO();
		vo.setTitle(title);
		vo.setParentId(parentId);
		if ("leaf".equals(type)) {
			vo.setLeaf(true);
			vo.setBeforeTitle(propertiesReader.getString("before_title_leaf"));
			vo.setLinkClass(propertiesReader.getString("link_class_leaf"));

		} else {
			vo.setBeforeTitle(propertiesReader.getString("before_title_node"));
			vo.setAfterTitle(propertiesReader.getString("after_title_node"));
			vo.setLinkClass(propertiesReader.getString("link_class_node"));
			vo.setClassName(propertiesReader.getString("menu_class"));
			vo.setTitleClass(propertiesReader.getString("title_class"));
		}
		vo.setIndex(menuItemService.getMaxIndex(parentId));
		Map<String, String> result = new HashMap<String, String>();

		try {
			MenuItem item = menuItemService.save(vo);
			response.setSuccessful(true);
			result.put("id", item.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/rest/menu/update", method = RequestMethod.POST)
	@ResponseBody
	public Response updateMenu(String id, String title, int index,
			boolean isLeaf, String className, String titleClass,
			String beforeTitle, String afterTitle, String trigger, String url,
			String parentId, String resourceId, String linkClass) {
		Response response = new Response();
		try {
			MenuItem item = menuItemService.get(id);
			String preParentId = null;
			if ("#".equals(parentId) || "-1".equals(parentId)) {
				parentId = null;
			}
			if (item.getParentItem() != null) {
				preParentId = item.getParentItem().getId();

			}
			/**
			 * 判断parentId 是否发生变化
			 * */
			boolean changed = false;
			if (preParentId == null) {
				if (parentId != null) {
					changed = true;
				}
			} else {
				if (!preParentId.equals(parentId)) {
					changed = true;
				}
			}

			if (changed) {
				menuItemService.updateIndex(preParentId, index,
						item.getIndex(), -1);
				menuItemService
						.updateIndex(parentId, index, item.getIndex(), 1);
				if (parentId != null) {
					MenuItem parent = menuItemService.get(parentId);
					item.setLayer(parent.getLayer() + 1);
				} else {
					item.setLayer(0);
				}
			} else {
				if (index != item.getIndex())
					menuItemService.updateIndex(parentId, index,
							item.getIndex(), 1);
			}

			item.setClassName(className);
			item.setIndex(index);
			item.setIsLeaf(isLeaf ? 1 : 0);
			item.setTitleClass(titleClass);
			item.setBeforeTitle(beforeTitle);
			item.setAfterTitle(afterTitle);
			item.setTrigger(trigger);
			item.setLinkClass(linkClass);
			item.setUrl(url);
			if (StringUtil.isNullOrBlank(parentId)) {
				item.setParentItem(null);
			} else {
				item.setParentItem(menuItemService.get(parentId));
			}
			if (item.getIsLeaf() == 1) {
				if (!StringUtil.isNullOrBlank(resourceId)) {

					item.setResource(resourceService.get(resourceId));
					menuItemService.update(item);
				}
			}
			menuItemService.update(item);
			response.setSuccessful(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/rest/menu/list", method = RequestMethod.POST)
	@ResponseBody
	public List<NodeLazy> listMenu(String parentId) {
		MenuItemVO parent = null;
		List<NodeLazy> result = null;

		if ("#".equals(parentId)) {
			parent = new MenuItemVO();
			parent.setTitle("根菜单");
			parent.setRoot(true);
			parent.setId("-1");
			parent.setMax_index(menuItemService.getMaxIndex(null));
			List<MenuItemVO> list = menuItemService.getMenuItems(null);
			parent.setSubItems(list);
			parent.setIsSystem(0);
			result = new ArrayList<NodeLazy>();
			result.add(NodeTools.toNode(parent));

		} else {
			List<MenuItemVO> list = null;
			if ("-1".equals(parentId)) {
				list = menuItemService.getMenuItems(null);
			} else {
				list = menuItemService.getMenuItems(parentId);
			}
			result = NodeTools.toNodes(list);
		}
		return result;
	}

	@RequestMapping(value = "/rest/menu/del", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteMenu(String id) {
		Response response = new Response();
		try {
			MenuItemVO item = menuItemService.getVO(id);
			menuItemService.updateIndex(item.getParentId(), item.getIndex(),
					-1, -1);
			menuItemService.delete(id);
			response.setSuccessful(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@RequestMapping(value = "/rest/menu/rename", method = RequestMethod.POST)
	@ResponseBody
	public boolean renameMenu(String id, String title) {
		try {
			MenuItem item = menuItemService.get(id);
			item.setTitle(title);
			item.update();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@RequestMapping(value = "/rest/menu/checkNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkMenuNameExists(String id, String menu_name) {
		boolean result = false;
		MenuItem menuItem = menuItemService.get(id);
		ConditionQuery query = new ConditionQuery();
		//顶层的menuitem
		if(menuItem.getParentItem()==null){
			query.add(Restrictions.and(Restrictions.eq("title", menu_name),Restrictions.isNull("parentItem.id"),Restrictions.ne("id", id)));
		}else {
			query.add(Restrictions.and(Restrictions.eq("title", menu_name),Restrictions.eq("parentItem.id", menuItem.getParentItem().getId()),Restrictions.ne("id", id)));
		}
		List<MenuItem> list = menuItemService.listAll(query, null, -1, -1);
		if(list ==null || list.size() ==0){
			result =true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("valid", result);
		return map;
	}
	
	
	@RequestMapping(value = "/rest/menu/move", method = RequestMethod.POST)
	@ResponseBody
	public boolean MoveMenu(String id, String parentId) {
		try {
			MenuItem item = menuItemService.get(id);
			if (StringUtil.filterId(parentId) == null) {
				item.setParentItem(null);
				item.setLayer(0);
			} else {
				MenuItem parent = menuItemService.get(parentId);
				item.setParentItem(parent);
				item.setLayer(parent.getLayer() + 1);
			}
			item.update();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Name: listMenuByRole
	 * @Decription: 生成角色对应的菜单视图
	 * @Time: 2015-1-15 上午10:49:47
	 * @param roleId
	 * @return NodeInst
	 */
	@RequestMapping(value = "/rest/menu/nodes/roleIds", method = RequestMethod.POST)
	@ResponseBody
	public NodeInst listMenuByRole(String roleIds) {
		NodeInst root = new NodeInst();
		root.setText("菜单");
		root.setType("node");
		root.setRoot(true);
		root.setIcon("jstree-folder");
		root.setId("-1");
		Map<String, Boolean> state = new HashMap<String, Boolean>();
		state.put("disabled", true);
		state.put("opened", true);
		root.setState(state);
		if (!StringUtil.isNullOrBlank(roleIds)) {
			List<NodeInst> children = new ArrayList<NodeInst>();
			List<MenuItemVO> list = menuItemService.getRoleMenus(roleIds);
			if (list != null) {
				Collections.sort(list);
				for (MenuItemVO menuItemVO : list) {
					children.add(NodeTools.toNodeInst(menuItemVO));
				}
				root.setChildren(children);
			}

		}

		return root;
	}

	@RequestMapping(value = "/rest/menu/menus", method = RequestMethod.POST)
	@ResponseBody
	public Response listMenus(String userId) {
		Response response = new Response();
		if (userId != null) {
			boolean isAdmin = userService.isAdmin(userId);
			List<MenuItemVO> list = null;
			if (isAdmin) {
				list = menuItemService.getMenuItems(null);
			} else {
				list = menuItemService.getUserMenus(userId);
			}
			response.setResultValue(list);
			response.setSuccessful(true);
		}

		return response;
	}

	@RequestMapping(value = "/rest/group/depart/nodes", method = RequestMethod.POST)
	@ResponseBody
	public NodeInst groupDepartNodes(String groupId) throws Exception {

		GroupVO group = groupService.getDepartStruct(groupId);
		NodeInst node = NodeTools.toNodeInstOrg(group);
		node.setType("node");
		node.setIcon("jstree-folder");
		node.setRoot(true);
		node.getState().put("opened", true);
		return node;
	}

	@RequestMapping(value = "/rest/group/depart/users/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDepartUser(HttpServletRequest request) {

		String groupId = request.getParameter("groupId");
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		List<String> groupids = groupService.getSubGroupListIDs(groupId);
		StringBuilder sb = new StringBuilder();
		for (String gid : groupids) {
			if (!StringUtil.isNullOrBlank(sb.toString())) {
				sb.append(",");
			}

			sb.append("'" + gid + "'");
		}
		String incudesql = " id in (select user.id from UserGroup  where group.id in ("
				+ sb.toString() + ")) ";

		if (search != null && !"".equals(search)) {

			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}

					/**
					 * 只查询和显示基本信息，组、角色之类的不在考虑范围内
					 */
					if ("mobile".equals(columnProperty.getColName())) {
						condition.append(" detail.mobile like '%" + search
								+ "%' ");
					}

					if ("userAlias".equals(columnProperty.getColName())) {
						condition.append(" detail.userAlias like '%" + search
								+ "%' ");
					}

					if ("username".equals(columnProperty.getColName())) {
						condition.append("  username like '%" + search + "%'");
					}
				}
			}

		}

		String conditionQuery = "";
		String orderList = "";

		if (!StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(incudesql)) {
			conditionQuery = " where ( " + condition.toString() + ") and  ( "
					+ incudesql + " )";
		} else if ((StringUtil.isNullOrBlank(condition.toString())
				&& !StringUtil.isNullOrBlank(incudesql) || (!StringUtil
				.isNullOrBlank(condition.toString()) && StringUtil
				.isNullOrBlank(incudesql)))) {
			conditionQuery = " where ("
					+ (StringUtil.isNullOrBlank(condition.toString()) ? incudesql
							: condition.toString()) + " ) ";
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (!"".equals(orderBy.toString())) {
				orderBy.append(",");
			}
			String colName = columns.get(keyValue.getKey()).getColName();
			if ("username".equals(colName)) {
				orderBy.append(" username " + keyValue.getValue().toLowerCase());
			}

			if ("userAlias".equals(colName)) {
				orderBy.append(" detail.userAlias "
						+ keyValue.getValue().toLowerCase());
			}

			if ("mobile".equals(colName)) {
				orderBy.append(" detail.mobile "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (!"".equals(orderBy.toString())) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = userService.countAll(conditionQuery);
		// int countAll = userService.countAll();

		Page<UserVO> users = userService.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countFilter);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", users.getItems());
		return resultMap;

	}

	@RequestMapping(value = "/rest/service/list", method = RequestMethod.POST)
	@ResponseBody
	public Response listService(String search) throws Exception {
		Response response = new Response();
		@SuppressWarnings("unchecked")
		Map<String, List<String>> services = memCaches.getCache("services");
		List<String> result = new ArrayList<String>();
		List<String> values = null;
		if (services.get("RequestMapping@value") == null) {
			synchronized (services) {
				values = new ArrayList<String>();
				services.put("RequestMapping@value", values);
				values.addAll(ResourceUrlManager.getSingleton()
						.getAllResourceUrl());
			}
		} else {
			values = services.get("RequestMapping@value");
		}
		for (String v : values) {
			if (v.startsWith(search)) {
				result.add(v);
			}
		}

		response.setSuccessful(true);
		response.setResultValue(result);
		return response;
	}
	@RequestMapping(value = "/rest/user/valid", method = RequestMethod.POST)
	@ResponseBody
	public Response userValid(String userName,String password,String others) throws Exception {
		Response response = new Response();
		User user = userService.loadUserByUserName(userName);
		if(user!=null){
			if(user.getPassword().equals(password)){
				response.setSuccessful(true);
			}
		}
		 
		return response;
	}

}
