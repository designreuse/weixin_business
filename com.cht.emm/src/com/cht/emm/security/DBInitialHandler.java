package com.cht.emm.security;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.model.ApplicationType;
import com.cht.emm.model.Authority;
import com.cht.emm.model.Config;
import com.cht.emm.model.Group;
import com.cht.emm.model.MenuItem;
import com.cht.emm.model.PlatFormAgent;
import com.cht.emm.model.Resource;
import com.cht.emm.model.Role;
import com.cht.emm.model.Strategy;
import com.cht.emm.model.User;
import com.cht.emm.model.UserDetail;
import com.cht.emm.model.id.ResourceAuth;
import com.cht.emm.model.id.RoleResourcePermission;
import com.cht.emm.model.id.UserRole;
import com.cht.emm.service.ApplicationTypeService;
import com.cht.emm.service.AuthorityService;
import com.cht.emm.service.ConfigService;
import com.cht.emm.service.GroupService;
import com.cht.emm.service.MenuItemService;
import com.cht.emm.service.PlatFormAgentService;
import com.cht.emm.service.ResourceAuthService;
import com.cht.emm.service.ResourceService;
import com.cht.emm.service.RoleResourcePermissionService;
import com.cht.emm.service.RoleService;
import com.cht.emm.service.StrategyService;
import com.cht.emm.service.UserRoleService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.ByteTool;
import com.cht.emm.util.FileUtil;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.zxing.Code2Generator;



public class DBInitialHandler {

	@Autowired
	private ApplicationTypeService applicationTypeServiceImpl;
	@Autowired
	private ConfigService configServiceImpl;
	@Autowired
	private StrategyService strategyServiceImpl;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private MenuItemService menuItemService;
	@Autowired
	private ResourceAuthService resourceAuthService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleResourcePermissionService roleResourcePermissionService;
	
	@Autowired
	private UserService userService;
	
	private PropertiesReader propertiesReader;

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private PlatFormAgentService platFormAgentService;
	
	private Map<String, Role> roleMap = new HashMap<String, Role>();
	private Map<String, Resource> resourceMap = new HashMap<String, Resource>();
	private Map<String, User> userMap = new HashMap<String, User>();

	private Integer basePermission =-1;
	
	List<Authority> allAuths = new ArrayList<Authority>();
	List<Authority> baseAuths = new ArrayList<Authority>();
	List<Resource> baseResources = new ArrayList<Resource>();
	List<Integer> locIndex = new ArrayList<Integer>();
	List<Integer> baseLocIndex = new ArrayList<Integer>();
	
	public void setPropertiesReader(PropertiesReader propertiesReader) {

		this.propertiesReader = propertiesReader;
	}

	public void init() {


		initUser();
		initRole();
		initGroup();
		initAuthority();
		initResourceAndMenu();
		initResourceAuth();
	
		initResourceRoleAuth();
		initThirdpart();
		initAppConfig();
//		initPlatformClient();
		
	}

	private void initPlatformClient() {

		// 获取平台初始化客户端的URL
		String clientUrl = propertiesReader.getString("webRoot") + "/"
				+ propertiesReader.getString("clientUrl");

		// 生成二维码图片到指定路径
		String code2Path = FileUtil.getWebRootPath()+  propertiesReader.getString("targetFile");
		// 二维码图片宽、高
		int width = 190;
		int height = 190;
		Code2Generator.generaterCode2File(clientUrl, FileUtil.changeSeperator(code2Path), width, height);

		// 获取默认客户端的版本信息，并写入数据库

	}

	private void initAppConfig() {

		ApplicationType appType = this.applicationTypeServiceImpl.get("0");
		if (appType == null) {
			appType = new ApplicationType();
			appType.setId("0");
			appType.setName("默认分类");
			appType.setDescription("包含所有未分类的应用，无法删除");
			this.applicationTypeServiceImpl.save(appType);
		}

		Config config = this.configServiceImpl.get("0");
		if (config == null) {
			config = new Config();
			config.setId("0");
			config.setName("默认配置");
			config.setDesc("设备默认配置，无法删除");
			config.setContent("{\"nari.mip.console.model.SecurityConfig\":{\"data_encrypt\":1}}");
			config.setCreator(new User(ConstantId.USER_ADMIN_ID));
			config.setTime(new Timestamp(System.currentTimeMillis()));
			this.configServiceImpl.save(config);
		}

		Strategy strategy = this.strategyServiceImpl.get("0");
		if (strategy == null) {
			strategy = new Strategy();
			strategy.setId("0");
			strategy.setName("默认策略");
			strategy.setDesc("设备默认策略，无法删除");
			strategy.setContent("{\"appList\":-1,\"operation\":1,\"osVersion\":-1,\"breakout\":1,\"condition\":0,\"encrypt\":-1,\"password\":-1}");
			strategy.setCreator(new User(ConstantId.USER_ADMIN_ID));
			strategy.setTime(new Timestamp(System.currentTimeMillis()));
			this.strategyServiceImpl.save(strategy);
		}
	}

	/**
	 * 向数据库写入系统内置资源、角色、权限
	 */
	
	private  void initUser(){
		/**
		 * 初始化用户
		 */

	
		User admin = userService.get(ConstantId.USER_ADMIN_ID);
		
		if( admin == null){
			 admin = new User(ConstantId.USER_ADMIN_ID,
					propertiesReader.getString("admin_account"),
					propertiesReader.getString("admin_password"));
			admin.setUserType(User.UserType.ORG_MASTER.getType());
			admin.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
			//admin.setThirdPartConfig(tpConfig);
			admin.save();
			UserDetail detail = new UserDetail();
			// detail.setId(user.getId());
			detail.setCreateTime(new Timestamp(new Date().getTime()));
			detail.setMobile("15850513195");
			detail.setEmail("op@gmail.com");
			detail.setSex(1);
			detail.setUser(admin);
			detail.setUserAlias("系统管理员");
			detail.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
			detail.save();
		} 
		userMap.put(ConstantId.USER_ADMIN_ID, admin);
		 
		

		
	}
	
	private void initGroup(){
		/**
		 * 初始化 组织机构
		 */
		Group group1 =groupService.get(ConstantId.GROUP_TOPORG_ID) ;
		if(group1== null){
			group1 = new Group();
			group1.setGroupName("默认机构");
			group1.setGroupDesc("默认组织机构");
			group1.setGroupType(Group.GROUP_TYPE.ORG.getType());
			group1.setIsSystem(Group.IS_SYSTEM_TYPE_UNDELETE);
			group1.setId(ConstantId.GROUP_TOPORG_ID);
			group1.save();
		}
		

		/**
		 * 初始化 第三方平台映射组的挂载点 不可删除，不能创建子组（子组的创建/删除交由第三方创建进行管理）
		 */
		Group thirdPartTopGroup = groupService.get(ConstantId.GROUP_THIRD_PART_ID);
		if( thirdPartTopGroup == null){
			thirdPartTopGroup = new Group();
			thirdPartTopGroup.setId(ConstantId.GROUP_THIRD_PART_ID);
			thirdPartTopGroup.setGroupName("默认部门");
			thirdPartTopGroup.setGroupDesc("所有未指明部门的账号挂在在该组之下");
			thirdPartTopGroup.setGroupType(Group.GROUP_TYPE.DEP.getType());
			thirdPartTopGroup.setThirdPartType(Group.GROUP_THIRDPART_TYPE.TOP
					.getType());
			thirdPartTopGroup.setParentGroup(group1);
			thirdPartTopGroup.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
			thirdPartTopGroup.save();
		}
	}
	
	private void initRole(){
		int userTypeValSuper = 2 << (User.UserType.SUPER_USER.getType() - 2);
		int userTypeValOrg = 2 << (User.UserType.ORG_MASTER.getType() - 2);
		int userTypeValDep = 2 << (User.UserType.DEPART_MASTER.getType() - 2);
		int userTypeValCommon = 2 << (User.UserType.COMMON.getType() - 2);
		Role r_a = roleService.get(ConstantId.ROLE_ADMIN_ID);
		if(r_a == null){
			r_a= new Role(ConstantId.ROLE_ADMIN_ID, "ROLE_ADMIN");
			r_a.setRoleDesc("系统管理员");
			r_a.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNSEEN);
			r_a.save();
		}
		roleMap.put(ConstantId.ROLE_ADMIN_ID, r_a);
		

		Role u_r = roleService.get(ConstantId.ROLE_USER_ID);
		if(u_r == null){
			u_r = new Role(ConstantId.ROLE_USER_ID, "ROLE_USER");
			u_r.setRoleDesc("一般用户");
			u_r.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNSEEN);
			u_r.save();
		}
		roleMap.put(ConstantId.ROLE_USER_ID, u_r);
		
		
		
		Role minRole =roleService.get(ConstantId.ROLE_MONITOR_ID);
		if(minRole == null ){
			minRole = new Role(ConstantId.ROLE_MONITOR_ID, "监控分析管理员");
			minRole.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
			minRole.setUserType(userTypeValOrg + userTypeValSuper);
			minRole.save();
			
		}
		roleMap.put(ConstantId.ROLE_MONITOR_ID, minRole);
		
		Role appRole = roleService.get(ConstantId.ROLE_APP_ID);
		if(appRole == null ){
			appRole = new Role(ConstantId.ROLE_APP_ID, "应用管理员");
			appRole.setUserType(userTypeValOrg);
			appRole.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
			appRole.save();
		}
		roleMap.put(ConstantId.ROLE_APP_ID, appRole);
		
		Role newsRole = roleService.get(ConstantId.ROLE_NEWS_ID);
		if(newsRole == null){
			newsRole = new Role(ConstantId.ROLE_NEWS_ID, "资讯管理员");
			newsRole.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
			newsRole.setUserType(userTypeValOrg);
			newsRole.save();
		}
		roleMap.put(ConstantId.ROLE_NEWS_ID, newsRole);
		
		Role authRole = roleService.get(ConstantId.ROLE_AUTH_ID);
		if(authRole == null){
			authRole = new Role(ConstantId.ROLE_AUTH_ID, "权限管理员");
			authRole.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
			authRole.setUserType(userTypeValOrg);
			authRole.save();
		}
		roleMap.put(ConstantId.ROLE_AUTH_ID, authRole);
		
		Role departManager = roleService.get(ConstantId.ROLE_DEPART_ID);
		if(departManager == null){
			departManager = new Role();
			departManager.setId(ConstantId.ROLE_DEPART_ID);
			departManager.setRoleName("部门管理员");
			departManager.setUserType(userTypeValDep);
			departManager.setIsSystem(Role.IS_SYSTEM_TYPE_UNDELETE);
			departManager.setDeletable(false);
			departManager.save();
		}
		roleMap.put(ConstantId.ROLE_DEPART_ID, departManager);
		
		
		Role comonUser = roleService.get(ConstantId.ROLE_COMMON_ID);
		if(comonUser == null){
			comonUser = new Role();
			comonUser.setId(ConstantId.ROLE_COMMON_ID);
			comonUser.setUserType(userTypeValCommon);
			comonUser.setIsSystem(Role.IS_SYSTEM_TYPE_UNDELETE);
			comonUser.setRoleName("普通员工");
			comonUser.save();
		}
		roleMap.put(ConstantId.ROLE_COMMON_ID, comonUser);
		
		Role agentManager = roleService.get(ConstantId.ROLE_PLATFORM_ID);
		if(agentManager == null){
			agentManager = new Role(ConstantId.ROLE_PLATFORM_ID, "系统配置管理员");
			agentManager.setUserType(userTypeValOrg);
			agentManager.setIsSystem(Role.IS_SYSTEM_TYPE_UNDELETE);
			agentManager.save();
		}
		roleMap.put(ConstantId.ROLE_PLATFORM_ID, agentManager);
		
		
	}
	
	private void initThirdpart(){
		
		
		
		
	}
	
	private void initAuthority(){
		/**
		 * 初始化 权限
		 */
		
		// 新增权限
		Authority authAdd =authorityService.get(ConstantId.AUTH_ADD_ID);
		if(authAdd == null ){
			authAdd = new Authority();
			authAdd.setLocIndex(1);
			authAdd.setDeleted(0);
			authAdd.setId(ConstantId.AUTH_ADD_ID);
			authAdd.setShowIndex(1);
			authAdd.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
			authAdd.setDescp("新增");
			authAdd.setName("新增");
			this.authorityService.save(authAdd);
			allAuths.add(authAdd);
			baseAuths.add(authAdd);
			locIndex.add(authAdd.getLocIndex());
			baseLocIndex.add(authAdd.getLocIndex());
		}
		

		// 访问权限
		Authority authAccess =authorityService.get(ConstantId.AUTH_ACC_ID);
		if(authAccess == null){
			authAccess = new Authority();
			authAccess.setLocIndex(2);
			authAccess.setDeleted(0);
			authAccess.setId(ConstantId.AUTH_ACC_ID);
			authAccess.setShowIndex(2);
			authAccess.setIsSystem(1);
			authAccess.setDescp("访问");
			authAccess.setName("访问");
			this.authorityService.save(authAccess);
			allAuths.add(authAccess);
			baseAuths.add(authAccess);
			locIndex.add(authAccess.getLocIndex());
			baseLocIndex.add(authAccess.getLocIndex());
		}
		

		// 修改权限
		Authority authModi =authorityService.get(ConstantId.AUTH_MODI_ID);
		if(authModi == null){
			authModi = new Authority();
			authModi.setLocIndex(3);
			authModi.setDeleted(0);
			authModi.setId(ConstantId.AUTH_MODI_ID);
			authModi.setShowIndex(3);
			authModi.setDescp("修改");
			authModi.setIsSystem(1);
			authModi.setName("修改");
			this.authorityService.save(authModi);
			allAuths.add(authModi);
			baseAuths.add(authModi);
			locIndex.add(authModi.getLocIndex());
			baseLocIndex.add(authModi.getLocIndex());
		}
		
		// 删除权限
		Authority authDel =authorityService.get(ConstantId.AUTH_DEL_ID);
		if(authDel == null){
			authDel = new Authority();
			authDel.setLocIndex(4);
			authDel.setDeleted(0);
			authDel.setId(ConstantId.AUTH_DEL_ID);
			authDel.setShowIndex(4);
			authDel.setDescp("删除");
			authDel.setName("删除");
			authDel.setIsSystem(1);
			this.authorityService.save(authDel);
			allAuths.add(authDel);
			baseAuths.add(authDel);
			locIndex.add(authDel.getLocIndex());
			baseLocIndex.add(authDel.getLocIndex());
		}
		

		// 上传 权限
		Authority authUpload =authorityService.get(ConstantId.AUTH_UPLOAD_ID);
		if(authUpload == null){
			authUpload = new Authority();
			authUpload.setLocIndex(5);
			authUpload.setDeleted(0);
			authUpload.setId(ConstantId.AUTH_UPLOAD_ID);
			authUpload.setShowIndex(5);
			authUpload.setDescp("上传");
			authUpload.setName("上传");
			authUpload.setIsSystem(1);
			this.authorityService.save(authUpload);
			allAuths.add(authUpload);
			locIndex.add(authUpload.getLocIndex());
		}
		

		// 下载权限
		Authority authDownload =authorityService.get(ConstantId.AUTH_DOWNLOAD_ID);
		if(authDownload == null){
			authDownload = new Authority();
			authDownload.setLocIndex(6);
			authDownload.setDeleted(0);
			authDownload.setId(ConstantId.AUTH_DOWNLOAD_ID);
			authDownload.setIsSystem(1);
			authDownload.setShowIndex(6);
			authDownload.setDescp("下载");
			authDownload.setName("下载");
			this.authorityService.save(authDownload);
			allAuths.add(authDownload);
			locIndex.add(authDownload.getLocIndex());
		}
		
		basePermission = ByteTool.sum(baseLocIndex);
	}
	private void initResourceAndMenu(){
		/**
		 * 创建admin的访问权限
		 */
		Resource resourceAll = resourceService.get(ConstantId.RESOURCE_ALL_ID);
		if(resourceAll == null ){
			resourceAll = new Resource(ConstantId.RESOURCE_ALL_ID, "restAll");
			resourceAll.setUri("/**");
			resourceAll.setDeleted(0);
			resourceAll.setIsSystem(Resource.IS_SYSTEM_TYPE_UNSEEN);
			resourceAll.setType(Resource.ResourceType.URL.getType());
			this.resourceService.save(resourceAll);
		}
		resourceMap.put(ConstantId.RESOURCE_ALL_ID, resourceAll);

		/**
		 * 创建user的访问权限
		 */
		Resource resourceUser =resourceService.get(ConstantId.RESOURCE_USER_URL_ID);
		if(resourceUser == null){
			resourceUser = new Resource(ConstantId.RESOURCE_USER_URL_ID, "user");
			resourceUser.setUri("/user/**");
			resourceUser.setDeleted(0);
			resourceUser.setIsSystem(Resource.IS_SYSTEM_TYPE_UNSEEN);
			resourceUser.setType(Resource.ResourceType.URL.getType());
			this.resourceService.save(resourceUser);
		}
		resourceMap.put(ConstantId.RESOURCE_USER_URL_ID, resourceUser);

		/**
		 * 菜单项： 监控中心
		 */
		MenuItem monCenterMenu = menuItemService.get(ConstantId.MENU_MONITOR_ID);
		if(monCenterMenu == null ){
			monCenterMenu = wrapMenu(WRAP_NODE);
			monCenterMenu.setTitle("监控中心");
			monCenterMenu.setId(ConstantId.MENU_MONITOR_ID);
			monCenterMenu.setIndex(0);
			monCenterMenu.setIsSystem(MenuItem.IS_SYSTEM_TYPE_UNDELETE);
			monCenterMenu.setBeforeTitle("<i class=\"fa fa-video-camera\"></i>");
			monCenterMenu.setLayer(0);
			monCenterMenu.save();
		}
	

		/**
		 * 信息中心
		 */
		Resource monitor = resourceService.get(ConstantId.RESOURCE_INFO_CENTER_ID);
		if(monitor == null ){
			monitor = new Resource(ConstantId.RESOURCE_INFO_CENTER_ID, "信息中心");
			monitor.setDeleted(0);
			monitor.setIsItem(1);
			monitor.setPermission(basePermission);
			monitor.setType(Resource.ResourceType.URL.getType());
			monitor.setIsSystem(MenuItem.IS_SYSTEM_TYPE_UNDELETE);
			monitor.setDeletable(false);
			monitor.setUri("/console/monitor_info_center");
			this.resourceService.save(monitor);
			
		}
		baseResources.add(monitor);
		resourceMap.put(ConstantId.RESOURCE_INFO_CENTER_ID, monitor);

		
		MenuItem inforCenterMenu = menuItemService.get(ConstantId.MENU_INFO_CENTER_ID); 
		if(inforCenterMenu == null){
			inforCenterMenu = wrapMenu(WRAP_LEAF);
			inforCenterMenu.setId(ConstantId.MENU_INFO_CENTER_ID);
			inforCenterMenu.setIndex(0);
			inforCenterMenu.setTitle("信息中心");
			inforCenterMenu.setUrl(monitor.getUri());
			inforCenterMenu.setIsSystem(MenuItem.IS_SYSTEM_TYPE_UNDELETE);
			inforCenterMenu.setParentItem(monCenterMenu);
			inforCenterMenu.setLayer(1);
			inforCenterMenu.setResource(monitor);
			inforCenterMenu.save();
		}
		
		/**
		 * 应用分类
		 */
		Resource appType =  resourceService.get(ConstantId.RESOURCE_APP_TYPE_ID);
		if(appType == null){
			appType = new Resource(ConstantId.RESOURCE_APP_TYPE_ID, "应用分类");
			appType.setDeleted(0);
			appType.setIsItem(1);
			appType.setIsSystem(1);
			appType.setPermission(basePermission);
			appType.setType(Resource.ResourceType.URL.getType());
			appType.setDeletable(false);
			appType.setUri("/console/apps_type");
			appType.save();
		}
		resourceMap.put(ConstantId.RESOURCE_APP_TYPE_ID, appType);
		baseResources.add(appType);

		/**
		 * 应用相关
		 */
		MenuItem appManage = menuItemService.get(ConstantId.MENU_APP_MANAGE_ID);
		if(appManage == null ){
			appManage = wrapMenu(WRAP_NODE);
			appManage.setId(ConstantId.MENU_APP_MANAGE_ID);
			appManage.setTitle("应用管理");
			appManage.setBeforeTitle("<i class=\"fa fa-th\"></i>");
			appManage.setIndex(2);
			appManage.setLayer(0);
			appManage.save();
		}
		
		
		MenuItem appTypeItem =menuItemService.get(ConstantId.MENU_APP_TYPE_ID);
		if(appTypeItem == null ){
			appTypeItem = wrapMenu(WRAP_LEAF);
			appTypeItem.setId(ConstantId.MENU_APP_TYPE_ID);
			appTypeItem.setIndex(0);
			appTypeItem.setTitle("应用分类");
			appTypeItem.setUrl(appType.getUri());
			appTypeItem.setParentItem(appManage);
			appTypeItem.setResource(appType);
			appTypeItem.setLayer(1);
			appTypeItem.save();
		}
		
		/**
		 * 应用列表
		 */
		Resource appList = resourceService.get(ConstantId.RESOURCE_APP_ID); 
		if(appList == null ){
			appList= new Resource(ConstantId.RESOURCE_APP_ID , "应用");
			appList.setDeleted(0);
			appList.setIsItem(1);
			appList.setIsSystem(1);
			appList.setPermission(basePermission);
			appList.setType(Resource.ResourceType.URL.getType());
			appList.setDeletable(false);
			appList.setUri("/console/apps");
			appList.save();
		}
		
		resourceMap.put(ConstantId.RESOURCE_APP_ID, appList);
		baseResources.add(appList);

		MenuItem appItem =  menuItemService.get(ConstantId.MENU_APP_LIST_ID);
		if(appItem == null){
			appItem = wrapMenu(WRAP_LEAF);
			appItem.setId(ConstantId.MENU_APP_LIST_ID);
			appItem.setIndex(1);
			appItem.setTitle("应用列表");
			appItem.setUrl(appList.getUri());
			appItem.setParentItem(appManage);
			appItem.setResource(appList);
			appItem.setLayer(1);
			appItem.save();
		}
		

		/**
		 * 咨询信息
		 */
		MenuItem newsManageItem = menuItemService.get(ConstantId.MENU_NEWS_MANAGE_ID);
		if(newsManageItem == null){
			newsManageItem = wrapMenu(WRAP_NODE);
			newsManageItem.setId( ConstantId.MENU_NEWS_MANAGE_ID);
			newsManageItem.setBeforeTitle("<i class=\"fa fa-coffee\"></i>");
			newsManageItem.setTitle("资讯管理");
			newsManageItem.setIndex(3);
			newsManageItem.setIsSystem(MenuItem.IS_SYSTEM_TYPE_UNDELETE);
			newsManageItem.setLayer(0);
			newsManageItem.save();
		}
		
		/**
		 * 资讯列表
		 */
		Resource newsList =  resourceService.get(ConstantId.RESOURCE_NEWS_ID); 
		if( newsList == null ){
			newsList = new Resource(ConstantId.RESOURCE_NEWS_ID, "资讯");
			newsList.setDeleted(0);
			newsList.setIsItem(1);
			newsList.setPermission(basePermission);
			newsList.setType(Resource.ResourceType.URL.getType());
			newsList.setDeletable(false);
			newsList.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			newsList.setUri("/console/news");
			newsList.save();
		}
		resourceMap.put(ConstantId.RESOURCE_NEWS_ID, newsList);
		baseResources.add(newsList);

		MenuItem newsListMenu = menuItemService.get(ConstantId.MENU_NEWS_LIST_ID);
		if(newsListMenu == null ){
			newsListMenu = wrapMenu(WRAP_LEAF);
			newsListMenu.setId(ConstantId.MENU_NEWS_LIST_ID);
			newsListMenu.setTitle("资讯列表");
			newsListMenu.setUrl(newsList.getUri());
			newsListMenu.setIndex(0);
			newsListMenu.setIsSystem(MenuItem.IS_SYSTEM_TYPE_UNDELETE);
			newsListMenu.setParentItem(newsManageItem);
			newsListMenu.setLayer(1);
			newsListMenu.setResource(newsList);
			newsListMenu.save();
		}
		
		/**
		 * 权限管理
		 */
		MenuItem authManageMenu =menuItemService.get(ConstantId.MENU_AUTHORITY_ID);
		if(authManageMenu == null){
			authManageMenu = wrapMenu(WRAP_NODE);
			authManageMenu.setId(ConstantId.MENU_AUTHORITY_ID);
			authManageMenu.setBeforeTitle("<i class=\"fa fa-user\"></i>");
			authManageMenu.setTitle("权限管理");
			authManageMenu.setIndex(4);
			authManageMenu.setIsSystem(MenuItem.IS_SYSTEM_TYPE_UNDELETE);
			authManageMenu.setLayer(0);
			authManageMenu.save();
		}
		
		/**
		 * 员工
		 */
		Resource user =  resourceService.get(ConstantId.RESOURCE_USER_ID);
		if(user == null){
			user= new Resource(ConstantId.RESOURCE_USER_ID, "用户");
			user.setIsItem(1);
			user.setPermission(basePermission);
			user.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			user.setType(Resource.ResourceType.URL.getType());
			user.setDeletable(false);
			user.setUri("/console/user/list");
			user.save();
		}
		resourceMap.put(ConstantId.RESOURCE_USER_ID, user);
		baseResources.add(user);

		MenuItem userMenu = menuItemService.get(ConstantId.MENU_MEMBER_ID);
		if(userMenu == null ){
			userMenu = wrapMenu(WRAP_LEAF);
			userMenu.setId(ConstantId.MENU_MEMBER_ID);
			userMenu.setTitle("员工管理");
			userMenu.setUrl(user.getUri());
			userMenu.setIndex(1);
			userMenu.setIsLeaf(1);
			userMenu.setParentItem(authManageMenu);
			userMenu.setResource(user);
			userMenu.setLayer(1);
			userMenu.save();
		}
		
		/**
		 * 菜单
		 */
		Resource menu = resourceService.get(ConstantId.RESOURCE_MENU_ID);
		if(menu == null ){
			menu = new Resource(ConstantId.RESOURCE_MENU_ID, "菜单");
			menu.setDeleted(0);
			menu.setIsItem(1);
			menu.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			menu.setPermission(basePermission);
			menu.setType(Resource.ResourceType.URL.getType());
			menu.setDeletable(false);
			menu.setUri("/console/menu/list");
			menu.save();
		}
		resourceMap.put(ConstantId.RESOURCE_MENU_ID, menu);
		baseResources.add(menu);

		MenuItem menuMenu = menuItemService.get(ConstantId.MENU_MENU_ID);
		if(menuMenu == null ){
			menuMenu = wrapMenu(WRAP_LEAF);
			menuMenu.setId(ConstantId.MENU_MENU_ID);
			menuMenu.setTitle("菜单管理");
			menuMenu.setUrl(menu.getUri());
			menuMenu.setIndex(2);
			menuMenu.setIsSystem(MenuItem.IS_SYSTEM_TYPE_UNDELETE);
			menuMenu.setParentItem(authManageMenu);
			menuMenu.setLayer(1);
			menuMenu.setIsLeaf(1);
			menuMenu.setResource(menu);
			menuMenu.save();
		}
		
		/**
		 * 组织结构
		 */
		Resource group = resourceService.get(ConstantId.RESOURCE_GROUP_ID);
		if( group == null ){
			group = new Resource(ConstantId.RESOURCE_GROUP_ID, "组织架构");
			group.setDeleted(0);
			group.setIsItem(1);
			group.setIsSystem(1);
			group.setPermission(basePermission);
			group.setType(Resource.ResourceType.URL.getType());
			group.setDeletable(false);
			group.setUri("/console/group/list");
			group.save();
		}
		resourceMap.put(ConstantId.RESOURCE_GROUP_ID, group);
		baseResources.add(group);

		MenuItem groupMenu = menuItemService.get(ConstantId.MENU_GROUP_ID);
		if(groupMenu == null) {
			groupMenu = wrapMenu(WRAP_LEAF);
			groupMenu.setTitle("组织架构");
			groupMenu.setId(ConstantId.MENU_GROUP_ID);
			groupMenu.setUrl(group.getUri());
			groupMenu.setIndex(0);
			groupMenu.setIsSystem(1);
			groupMenu.setIsLeaf(1);
			groupMenu.setParentItem(authManageMenu);
			groupMenu.setLayer(1);
			groupMenu.setResource(group);
			groupMenu.save();
		}
		
		/**
		 * 角色
		 */
		Resource role =resourceService.get(ConstantId.RESOURCE_ROLE_ID);
		if(role == null){
			role = new Resource(ConstantId.RESOURCE_ROLE_ID, "角色");
			role.setDeleted(0);
			role.setIsItem(1);
			role.setIsSystem(1);
			role.setPermission(basePermission);
			role.setType(Resource.ResourceType.URL.getType());
			role.setDeletable(false);
			role.setUri("/console/role/list");
			role.save();
		}

		resourceMap.put(ConstantId.RESOURCE_ROLE_ID, role);
		baseResources.add(role);

		MenuItem roleMenu = menuItemService.get(ConstantId.MENU_ROLE_ID);
		if(roleMenu == null ){
			roleMenu = wrapMenu(WRAP_LEAF);
			roleMenu.setTitle("角色管理");
			roleMenu.setId(ConstantId.MENU_ROLE_ID);
			roleMenu.setUrl(role.getUri());
			roleMenu.setIsSystem(1);
			roleMenu.setIsLeaf(1);
			roleMenu.setIndex(3);
			roleMenu.setLayer(1);
			roleMenu.setParentItem(authManageMenu);
			roleMenu.setResource(role);
			roleMenu.save();
		}
		
		/**
		 * 资源
		 **/
		Resource resource = resourceService.get(ConstantId.RESOURCE_RESOURCE_ID);
		if(resource == null){
			resource = new Resource(ConstantId.RESOURCE_RESOURCE_ID, "资源");
			resource.setDeleted(0);
			resource.setIsItem(1);
			resource.setPermission(basePermission);
			resource.setType(Resource.ResourceType.URL.getType());
			resource.setDeletable(false);
			resource.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			resource.setUri("/console/resource/list");
			resource.save();
		}
		resourceMap.put(ConstantId.RESOURCE_RESOURCE_ID, resource);
		baseResources.add(resource);

		MenuItem resourceMenu = menuItemService.get(ConstantId.MENU_RESOURCE_ID);
		if(resourceMenu == null){
			resourceMenu = wrapMenu(WRAP_LEAF);
			resourceMenu.setTitle("资源管理");
			resourceMenu.setId(ConstantId.MENU_RESOURCE_ID);
			resourceMenu.setUrl(resource.getUri());
			resourceMenu.setIndex(4);
			resourceMenu.setIsLeaf(1);
			resourceMenu.setParentItem(authManageMenu);
			resourceMenu.setResource(resource);
			resourceMenu.setIsSystem(MenuItem.IS_SYSTEM_TYPE_UNDELETE);
			resourceMenu.setLayer(1);
			resourceMenu.save();
		}
		
		/**
		 * 操作
		 */
		Resource auth = resourceService.get(ConstantId.RESOURCE_AUTH_ID);
		if(auth == null ){
			auth = new Resource(ConstantId.RESOURCE_AUTH_ID, "权限");
			auth.setDeleted(0);
			auth.setIsItem(0);
			auth.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			auth.setPermission(basePermission);
			auth.setType(Resource.ResourceType.URL.getType());
			auth.setDeletable(false);
			auth.setUri("/console/auth/list");
			auth.save();
		}
		resourceMap.put(ConstantId.RESOURCE_AUTH_ID, auth);
		baseResources.add(auth);

		/**
		 * 系统配置项
		 */
		Resource clientResource = resourceService.get(ConstantId.RESOURCE_PLATFORM_CLIENT_ID);
		if(clientResource == null ){
			clientResource = new Resource();
			clientResource.setId(ConstantId.RESOURCE_PLATFORM_CLIENT_ID);
			clientResource.setIsItem(1);
			clientResource.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			clientResource.setName("平台客户端");
			clientResource.setPermission(basePermission);
			clientResource.setType(Resource.ResourceType.URL.getType());
			clientResource.setUri("/console/platform_agent/history");
			clientResource.save();
		}
		resourceMap.put(ConstantId.RESOURCE_PLATFORM_CLIENT_ID, clientResource);
		baseResources.add(clientResource);

		MenuItem sysConfig = menuItemService.get(ConstantId.MENU_SYSCONFIG_ID);
		if(sysConfig == null ){
			sysConfig = wrapMenu(WRAP_NODE);
			sysConfig.setTitle("系统配置");
			sysConfig.setId(ConstantId.MENU_SYSCONFIG_ID);
			sysConfig.setBeforeTitle("<i class=\"fa fa-cog\"></i>");
			sysConfig.setIndex(5);
			sysConfig.setLayer(0);
			sysConfig.save();
		}
		
		MenuItem platformAgent = menuItemService.get(ConstantId.MENU_PLATFORM_CLIENT_ID);
		if(platformAgent == null) {
			platformAgent= wrapMenu(WRAP_LEAF);
			platformAgent.setTitle("平台管理");
			platformAgent.setId(ConstantId.MENU_PLATFORM_CLIENT_ID);
			platformAgent.setLayer(1);
			platformAgent.setIsLeaf(1);
			platformAgent.setIndex(0);
			platformAgent.setUrl(clientResource.getUri());
			platformAgent.setResource(clientResource);
			platformAgent.setParentItem(sysConfig);
			platformAgent.save();
		}
		
		/**
		 * 第三方
		 */
		Resource thirdPart = resourceService.get(ConstantId.RESOURCE_THIRD_PART_ID);
		if(thirdPart == null ){
			thirdPart = new Resource();
			thirdPart.setId(ConstantId.RESOURCE_THIRD_PART_ID);
			thirdPart.setIsItem(1);
			thirdPart.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			thirdPart.setName("三方接入");
			thirdPart.setPermission(basePermission);
			thirdPart.setType(Resource.ResourceType.URL.getType());
			thirdPart.setUri("/console/thirdpart/list");
			thirdPart.save();
		}
		resourceMap.put(ConstantId.RESOURCE_THIRD_PART_ID, thirdPart);
		baseResources.add(thirdPart);
		
		MenuItem thirdpartItem = menuItemService.get(ConstantId.MENU_THIRDPART_ID);
		if(thirdpartItem == null) {
			thirdpartItem= wrapMenu(WRAP_LEAF);
			thirdpartItem.setTitle("三方接入");
			thirdpartItem.setId(ConstantId.MENU_THIRDPART_ID);
			thirdpartItem.setLayer(1);
			thirdpartItem.setIsLeaf(1);
			thirdpartItem.setIndex(1);
			thirdpartItem.setUrl(thirdPart.getUri());
			thirdpartItem.setResource(thirdPart);
			thirdpartItem.setParentItem(sysConfig);
			thirdpartItem.save();
		}
		
		/**
		 * 系统配置项
		 */
		Resource parameter = resourceService.get(ConstantId.RESOURCE_PARAMETER_SETTING_ID);
		if(parameter == null ){
			parameter = new Resource();
			parameter.setId(ConstantId.RESOURCE_PARAMETER_SETTING_ID);
			parameter.setIsItem(1);
			parameter.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			parameter.setName("参数配置");
			parameter.setPermission(basePermission);
			parameter.setType(Resource.ResourceType.URL.getType());
			parameter.setUri("/console/sysconfig/params");
			parameter.save();
		}
		resourceMap.put(ConstantId.RESOURCE_PARAMETER_SETTING_ID, parameter);
		baseResources.add(parameter);
		
		MenuItem paramItem = menuItemService.get(ConstantId.MENU_PARAMETER_SETTING_ID);
		if(paramItem == null) {
			paramItem= wrapMenu(WRAP_LEAF);
			paramItem.setTitle("参数设置");
			paramItem.setId(ConstantId.MENU_PARAMETER_SETTING_ID);
			paramItem.setLayer(1);
			paramItem.setIsLeaf(1);
			paramItem.setIndex(2);
			paramItem.setUrl(parameter.getUri());
			paramItem.setResource(parameter);
			paramItem.setParentItem(sysConfig);
			paramItem.save();
		}
		
		
		
	}
	 
	private void initResourceAuth(){
		for (Resource resource2 : baseResources) {
			String resourceName = resource2.getName();
			for (Authority au : baseAuths) {
				if(resourceAuthService.getPK(resource2.getId(),au.getId())== null){
					ResourceAuth resourceauth = new ResourceAuth();
					resourceauth.setId(UUIDGen.getUUID());
					resourceauth.setAuth(au);
					Integer loc = au.getLocIndex();
					String subUrlString = null;
					switch (loc) {
					case 1:
						if ("用户".equals(resourceName)) {
							subUrlString = "/console/user/add";
						} else if ("资源".equals(resourceName)) {
							subUrlString = "/console/resource/add";
						} else if ("组织架构".equals(resourceName)) {
							subUrlString = "/console/group/add";
						} else if ("角色".equals(resourceName)) {
							subUrlString = "/console/role/add";
						} else if ("菜单".equals(resourceName)) {

						} else if ("权限".equals(resourceName)) {
							subUrlString = "/console/auth/add";
						} else if ("资讯".equals(resourceName)) {
							subUrlString = "/console/news_new";
						} else if ("应用分类".equals(resourceName)) {
							subUrlString = "/console/apps_type_new";
						} else if ("应用".equals(resourceName)) {
							subUrlString = "/console/apps_new";
						}
						break;
					case 2:
						if ("用户".equals(resourceName)) {
							subUrlString = "/console/user/info";
						} else if ("资源".equals(resourceName)) {
							subUrlString = "/console/resource/info";
						} else if ("组织架构".equals(resourceName)) {
							subUrlString = "/console/group/info";
						} else if ("角色".equals(resourceName)) {
							subUrlString = "/console/role/info";
						} else if ("菜单".equals(resourceName)) {

						} else if ("权限".equals(resourceName)) {
							subUrlString = "/console/auth/add";
						} else if ("资讯".equals(resourceName)) {
							subUrlString = "console/news_new";
						} else if ("应用分类".equals(resourceName)) {
							subUrlString = "/console/apps_type_new";
						} else if ("应用".equals(resourceName)) {
							subUrlString = "/console/apps_detail";
						}
						break;
					case 3:
						if ("用户".equals(resourceName)) {
							subUrlString = "/console/user/edit";
						} else if ("资源".equals(resourceName)) {
							subUrlString = "/console/resource/edit";
						} else if ("组织架构".equals(resourceName)) {
							subUrlString = "/console/group/edit";
						} else if ("角色".equals(resourceName)) {
							subUrlString = "/console/role/edit";
						} else if ("菜单".equals(resourceName)) {

						} else if ("权限".equals(resourceName)) {
							subUrlString = "/console/auth/edit";
						} else if ("资讯".equals(resourceName)) {
							subUrlString = "/console/news_new";
						} else if ("应用分类".equals(resourceName)) {
							subUrlString = "/console/apps_type_new";
						} else if ("应用".equals(resourceName)) {
							subUrlString = "/console/apps_new";
						}

						break;
					default:
						break;
					}
					if (subUrlString != null) {
						resourceauth.setSubUri(subUrlString);
						resourceauth.setResource(resource2);
						resourceauth.setIsSystem(1);
						resourceauth.save();
					}
					
				}
				

			}
		}

		// 设置资源的访问权限
		List<ResourceAuth> rrs = new ArrayList<ResourceAuth>();
		Resource resourceAll = resourceMap.get(ConstantId.RESOURCE_ALL_ID);
		for (Authority authority : allAuths) {
			ResourceAuth rr = new ResourceAuth();
			rr.setId(UUIDGen.getUUID());
			rr.setAuth(authority);
			rr.setResource( resourceAll);
			rr.setSubUri(resourceAll.getUri());
			rr.setIsSystem(1);
			rr.save();
			rrs.add(rr);
		}
		resourceAll.setResourceAuths(new HashSet<ResourceAuth>(rrs));

		resourceAll.setPermission(ByteTool.sum(locIndex));
		resourceAll.update();
	}
	
	private void initResourceRoleAuth() {
 
		/**
		 * 生成资源权限映射
		 */
		
		//

		/**
		 * role of admin
		 */
		Resource resourceAll = resourceMap.get(ConstantId.RESOURCE_ALL_ID);
		Role r_a = roleMap.get(ConstantId.ROLE_ADMIN_ID);
		Role u_r = roleMap.get(ConstantId.ROLE_USER_ID);
		 
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_ADMIN_ID, ConstantId.RESOURCE_ALL_ID)==null){
			RoleResourcePermission rrp = new RoleResourcePermission();
			rrp.setId(UUIDGen.getUUID());
			rrp.setRole(r_a);
			rrp.setResource(resourceAll);
			rrp.setPermission(resourceAll.getPermission());
			rrp.save();
		}
		

		/**
		 * role of user
		 */
		
		if(userRoleService.getPK(ConstantId.USER_ADMIN_ID, ConstantId.ROLE_ADMIN_ID) == null){
			UserRole ur = new UserRole();
			ur.setId(UUIDGen.getUUID());
			ur.setUser(userMap.get(ConstantId.USER_ADMIN_ID));
			ur.setRole(r_a);
			ur.save();
		}
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_USER_ID, ConstantId.RESOURCE_USER_URL_ID) == null){
			RoleResourcePermission rrp2 = new RoleResourcePermission();
			rrp2.setId(UUIDGen.getUUID());
			rrp2.setRole(u_r);
			rrp2.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			rrp2.setResource(resourceMap.get(ConstantId.RESOURCE_USER_URL_ID));
			rrp2.setPermission(resourceMap.get(ConstantId.RESOURCE_USER_ID).getPermission());
			rrp2.save();
		}

		/**
		 * 监控分析
		 */
		Resource monitor = resourceMap.get(ConstantId.RESOURCE_INFO_CENTER_ID);
		Role minRole = roleMap.get(ConstantId.ROLE_MONITOR_ID);
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_MONITOR_ID, ConstantId.RESOURCE_INFO_CENTER_ID) == null){
			RoleResourcePermission minRoleAdmin = new RoleResourcePermission();
			minRoleAdmin.setId(UUIDGen.getUUID());
			minRoleAdmin.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			minRoleAdmin.setResource(monitor);
			minRoleAdmin.setRole(minRole);
			minRoleAdmin.setPermission(monitor.getPermission());
			minRoleAdmin.save();
		}

		Role appRole = roleMap.get(ConstantId.ROLE_APP_ID);
		Resource appList = resourceMap.get(ConstantId.RESOURCE_APP_ID);
		Resource appType = resourceMap.get(ConstantId.RESOURCE_APP_TYPE_ID);
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_APP_ID, ConstantId.RESOURCE_APP_ID) == null){
			RoleResourcePermission appResourceRole = new RoleResourcePermission();
			appResourceRole.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			appResourceRole.setId(UUIDGen.getUUID());
			appResourceRole.setRole(appRole);
			appResourceRole.setResource(appList);
			appResourceRole.setPermission(basePermission);
			appResourceRole.save();
		}
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_APP_ID, ConstantId.RESOURCE_APP_TYPE_ID) == null){
			RoleResourcePermission appTypeResourceRole = new RoleResourcePermission();
			appTypeResourceRole.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			appTypeResourceRole.setId(UUIDGen.getUUID());
			appTypeResourceRole.setRole(appRole);
			appTypeResourceRole.setResource(appType);
			appTypeResourceRole.setPermission(basePermission);
			appTypeResourceRole.save();
		}
		
		/**
		 * 初始化新闻功能
		 */
		Role newsRole = roleMap.get(ConstantId.ROLE_NEWS_ID);
		Resource newsList =  resourceMap.get(ConstantId.RESOURCE_NEWS_ID);
		
		
		/**
		 * 初始化权限管理相关内容
		 */
		Role authRole = roleMap.get(ConstantId.ROLE_AUTH_ID);
		 
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_AUTH_ID, ConstantId.RESOURCE_GROUP_ID) == null){
			RoleResourcePermission groupPermission = new RoleResourcePermission();
			groupPermission.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			groupPermission.setId(UUIDGen.getUUID());
			groupPermission.setRole(authRole);
			groupPermission.setResource(resourceMap.get(ConstantId.RESOURCE_GROUP_ID));
			groupPermission.setPermission(basePermission);
			groupPermission.save();
		}
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_AUTH_ID, ConstantId.RESOURCE_USER_ID) == null){
			RoleResourcePermission userPermission = new RoleResourcePermission();
			userPermission.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			userPermission.setId(UUIDGen.getUUID());
			userPermission.setRole(authRole);
			userPermission.setResource(resourceMap.get(ConstantId.RESOURCE_USER_ID));
			userPermission.setPermission(basePermission);
			userPermission.save();
		}
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_AUTH_ID, ConstantId.RESOURCE_MENU_ID) == null){	
			RoleResourcePermission menuPermission = new RoleResourcePermission();
			menuPermission.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			menuPermission.setId(UUIDGen.getUUID());
			menuPermission.setRole(authRole);
			menuPermission.setResource(resourceMap.get(ConstantId.RESOURCE_MENU_ID));
			menuPermission.setPermission(basePermission);
			menuPermission.save();
		}	
	
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_AUTH_ID, ConstantId.RESOURCE_ROLE_ID) == null){
			RoleResourcePermission rolePermission = new RoleResourcePermission();
			rolePermission.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			rolePermission.setId(UUIDGen.getUUID());
			rolePermission.setRole(authRole);
			rolePermission.setResource(resourceMap.get(ConstantId.RESOURCE_ROLE_ID));
			rolePermission.setPermission(basePermission);
			rolePermission.save();
		}
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_AUTH_ID, ConstantId.RESOURCE_RESOURCE_ID) == null){
			RoleResourcePermission resourcePermission = new RoleResourcePermission();
			resourcePermission.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			resourcePermission.setId(UUIDGen.getUUID());
			resourcePermission.setRole(authRole);
			resourcePermission.setResource(resourceMap.get(ConstantId.RESOURCE_RESOURCE_ID));
			resourcePermission.setPermission(basePermission);
			resourcePermission.save();
		}
		/**
		 * 初始化和设备相关的内容
		 */
		Role departManager = roleMap.get(ConstantId.ROLE_DEPART_ID);
		
		/**
		 * 初始化  
		 */
		Role commonUser = roleMap.get(ConstantId.ROLE_COMMON_ID);
		User admin = userMap.get(ConstantId.USER_ADMIN_ID);
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_COMMON_ID, ConstantId.RESOURCE_APP_ID) == null){
			RoleResourcePermission appResourceRole = new RoleResourcePermission();
			appResourceRole.setIsSystem(Resource.IS_SYSTEM_TYPE_UNDELETE);
			appResourceRole.setId(UUIDGen.getUUID());
			appResourceRole.setRole(commonUser);
			appResourceRole.setResource(appList);
			appResourceRole.setPermission(basePermission);
			appResourceRole.save();
		}
		
		/**
		 * 生成agent内容
		 */
		if(platFormAgentService.get(ConstantId.PLATFORM_AGENT_ID) == null){
			PlatFormAgent agent = new PlatFormAgent();
			agent.setId(ConstantId.PLATFORM_AGENT_ID);
			agent.setCreator(admin);
			agent.setIconUrl(propertiesReader.getString("iconUrl"));
			agent.setOs(0);
			agent.setPackageName("nari.mip.console");
			agent.setUrl(propertiesReader.getString("clientUrl"));
			agent.setPath(propertiesReader.getString("clientPath"));
			agent.setVersionCode(100);
			agent.setVersionName("1.0");
			agent.setCreateTime(new Timestamp(new Date().getTime()));
			agent.save();
		}
		
		/**
		 * 初始化  配置项相关
		 */
		
		Role agentManager  = roleMap.get(ConstantId.ROLE_PLATFORM_ID);
		Resource clientResource = resourceMap.get(ConstantId.RESOURCE_PLATFORM_CLIENT_ID);

		if(roleResourcePermissionService.getPK(ConstantId.ROLE_PLATFORM_ID, ConstantId.RESOURCE_PLATFORM_CLIENT_ID)==null){
			RoleResourcePermission agentresourceRole = new RoleResourcePermission();
			agentresourceRole.setId(UUIDGen.getUUID());
			agentresourceRole.setResource(clientResource);
			agentresourceRole.setRole(agentManager);
			agentresourceRole.save();
		}
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_PLATFORM_ID, ConstantId.RESOURCE_PARAMETER_SETTING_ID)==null){
			RoleResourcePermission paremresourceRole = new RoleResourcePermission();
			paremresourceRole.setId(UUIDGen.getUUID());
			paremresourceRole.setResource(resourceMap.get(ConstantId.RESOURCE_PARAMETER_SETTING_ID));
			paremresourceRole.setRole(agentManager);
			paremresourceRole.save();
		}
		
		if(roleResourcePermissionService.getPK(ConstantId.ROLE_PLATFORM_ID, ConstantId.RESOURCE_THIRD_PART_ID)==null){
			RoleResourcePermission thirdpartResourceRole = new RoleResourcePermission();
			thirdpartResourceRole.setId(UUIDGen.getUUID());
			thirdpartResourceRole.setResource(resourceMap.get(ConstantId.RESOURCE_THIRD_PART_ID));
			thirdpartResourceRole.setRole(agentManager);
			thirdpartResourceRole.save();
		}
		
		
	}

	private MenuItem wrapMenu(int type) {
		MenuItem menu = new MenuItem();
//		menu.setId(UUIDGen.getUUID());
		if (type == WRAP_LEAF) {
			menu.setIsLeaf(1);
			menu.setBeforeTitle(propertiesReader.getString("before_title_leaf"));
			menu.setLinkClass(propertiesReader.getString("link_class_leaf"));
			menu.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
		} else {
			menu.setBeforeTitle(propertiesReader.getString("before_title_node"));
			menu.setAfterTitle(propertiesReader.getString("after_title_node"));
			menu.setLinkClass(propertiesReader.getString("link_class_node"));
			menu.setClassName(propertiesReader.getString("menu_class"));
			menu.setTitleClass(propertiesReader.getString("title_class"));
			menu.setIsSystem(AbstractModel.IS_SYSTEM_TYPE_UNDELETE);
		}

		return menu;
	}

	private static int WRAP_NODE = 1;
	private static int WRAP_LEAF = 2;
}
