package com.cht.emm.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.cht.emm.model.Resource;
import com.cht.emm.service.ResourceAuthService;
import com.cht.emm.service.RoleService;
import com.cht.emm.service.UserService;
import com.cht.emm.vo.RoleVO;

/**
 * @description 资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问
 * @author cht
 */
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	private UserService userService;
	private RoleService roleService;
	private ResourceAuthService resourceAuthService;
	private DBInitialHandler dbInitialHandler;
	/* 保存资源和权限的对应关系 key-资源url value-权限 */
	private Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private AntPathMatcher urlMatcher = new AntPathMatcher();

	public MySecurityMetadataSource(UserService userService,
			RoleService roleService, ResourceAuthService resourceAuthService,
			DBInitialHandler dbInitialHandler) {

		this.userService = userService;
		this.roleService = roleService;
		this.resourceAuthService = resourceAuthService;
		this.dbInitialHandler = dbInitialHandler;

		// 重新实例化一个对象，用于刷新内存资源
		singleton = this;

		// 初始化数据库
		this.dbInitialHandler.init();

		loadResourcesDefine();
	}

	private static MySecurityMetadataSource singleton = null;

	public static MySecurityMetadataSource singleton() {

		return singleton;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 重新加载资源
	 */
	public void reloadResources() {

		loadResourcesDefine();
	}

	private void loadResourcesDefine() {

		// 校验
		if (userService == null || roleService == null)
			return;

		resourceMap = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();

		System.out
				.println("MySecurityMetadataSource.loadResourcesDefine()--------------开始加载资源列表数据--------");

		List<RoleVO> roles = roleService.getAVRoles();
		for (RoleVO role : roles) {

			List<Resource> resources = userService.findResourcesByRoleId(role
					.getId());
			for (Resource resource : resources) {
				Collection<ConfigAttribute> configAttributes = null;
				ConfigAttribute configAttribute = new SecurityConfig(
						role.getRoleName());

				if (resourceMap.containsKey(resource.getUri())) {
					configAttributes = resourceMap.get(resource.getUri());
					configAttributes.add(configAttribute);
				} else {
					configAttributes = new ArrayList<ConfigAttribute>();
					configAttributes.add(configAttribute);
					resourceMap.put(resource.getUri(), configAttributes);
				}

				// 添加权限路径
				List<String> resAuthList = resourceAuthService
						.getAuthUrlsByResourceId(resource.getId());

				for (String resAuth : resAuthList) {

					if (resourceMap.containsKey(resAuth)) {
						configAttributes = resourceMap.get(resAuth);
						configAttributes.add(configAttribute);
					} else {
						configAttributes = new ArrayList<ConfigAttribute>();
						configAttributes.add(configAttribute);
						resourceMap.put(resAuth, configAttributes);
					}
				}
			}
		}
	}

	/*
	 * 根据请求的资源地址，获取它所拥有的权限
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object obj)
			throws IllegalArgumentException {

		Collection<ConfigAttribute> returnCollection = new ArrayList<ConfigAttribute>();

		// 获取请求的url地址
		String url = ((FilterInvocation) obj).getRequestUrl();
		if (url.indexOf('?') != -1) {

			url = url.substring(0, url.indexOf('?'));
		}
		System.out
				.println("MySecurityMetadataSource:getAttributes()---------------请求地址为："
						+ url);
		Iterator<String> it = resourceMap.keySet().iterator();
		while (it.hasNext()) {
			String _url = it.next();
			if (_url.indexOf('?') != -1) {
				_url = _url.substring(0, _url.indexOf('?'));
			}
			// if(urlMatcher.match(_url, url))
			// return resourceMap.get(_url);

			if (urlMatcher.match(_url, url)) {

				Collection<ConfigAttribute> roles = resourceMap.get(_url);
				for (ConfigAttribute ca : roles) {

					returnCollection.add(ca);
				}
			}
		}

		// 如果没有匹配的，则默认赋于ROLE_NO_USER
		if (returnCollection.size() == 0)
			returnCollection.add(new SecurityConfig("ROLE_NO_USER"));

		return returnCollection;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		System.out
				.println("MySecurityMetadataSource.supports()---------------------");
		return true;
	}

	// 以下定义资源和角色的互操作，避免重启服务

	/**
	 * 删除资源，检查是否有关联的角色，如果有则去掉关联关系
	 * 
	 * @param resUrl
	 * @return
	 */
	public boolean removeResources(String[] resUrls, String[][] roleNames) {

		boolean flag = false;
		for (int i = 0; i < resUrls.length; i++) {
			if (resourceMap.containsKey(resUrls[i])) {

				// resourceMap.remove(resUrls[i]);

				if (roleNames[i] != null && roleNames[i].length > 0)
					this.unrelateResourceRole(resUrls[i], roleNames[i]);

				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 更新资源
	 * 
	 * @param srcUrl
	 * @param newUrl
	 * @return
	 */
	public boolean updateResource(String srcUrl, String newUrl) {

		if (resourceMap.containsKey(srcUrl)) {

			Collection<ConfigAttribute> value = resourceMap.remove(srcUrl);
			resourceMap.put(newUrl, value);
			return true;
		}

		return false;
	}

	/**
	 * 删除角色，检查是否有关联的资源如果有则去掉关联
	 * 
	 * @param roleName
	 * @return
	 */
	public boolean removeRoles(String[] roleNames) {

		boolean flag = false;

		for (int i = 0; i < roleNames.length; i++) {

			for (Entry<String, Collection<ConfigAttribute>> entry : resourceMap
					.entrySet()) {

				boolean delFlag = false;
				ConfigAttribute destCa = null;
				for (ConfigAttribute ca : entry.getValue()) {

					if (ca.getAttribute().equals(roleNames[i])) {
						
						destCa = ca;
						delFlag = true;
						flag = true;
						break;
					}
				}

				if (delFlag) {

					entry.getValue().remove(destCa);
				}

			}
		}
		return flag;
	}

	/**
	 * 更新角色名称，检查是否有关联的资源，如果有更新角色名称
	 * 
	 * @param srcRoleName
	 * @param newRoleName
	 * @return
	 */
	public boolean updateRole(String srcRoleName, String newRoleName) {

		boolean flag = false;
		for (Entry<String, Collection<ConfigAttribute>> entry : resourceMap
				.entrySet()) {

			boolean delFlag = false;
			ConfigAttribute destCa = null;
			for (ConfigAttribute ca : entry.getValue()) {

				if (ca.getAttribute().equals(srcRoleName)) {

					destCa = ca;
					delFlag = true;
					flag = true;
					break;
				}
			}

			if (delFlag) {

				entry.getValue().remove(destCa);
				entry.getValue().add(new SecurityConfig(newRoleName));
			}

		}

		return flag;
	}

	/**
	 * 关联角色资源
	 * 
	 * @param roleName
	 *            角色名称
	 * @param resUrls
	 *            资源路径
	 * @return
	 */
	public boolean relateRoleResource(String roleName, String[] resUrls) {

		// 检查是否有关联的资源
		for (int i = 0; i < resUrls.length; i++) {

			// 已存在资源
			if (resourceMap.containsKey(resUrls[i])) {

				Collection<ConfigAttribute> cas = resourceMap.get(resUrls[i]);

				boolean flag = false;
				for (ConfigAttribute ca : cas) {

					if (ca.getAttribute().equals(roleName)) {

						flag = true;
						break;
					}
				}

				if (!flag)
					cas.add(new SecurityConfig(roleName));

			} else {

				Collection<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();
				cas.add(new SecurityConfig(roleName));
				resourceMap.put(resUrls[i], cas);
			}
		}

		return true;
	}

	/**
	 * 解除角色资源关联
	 * 
	 * @param roleName
	 *            角色名称
	 * @param resUrls
	 *            资源路径
	 * @return
	 */
	public boolean unrelateRoleResource(String roleName, String[] resUrls) {
		boolean ret = false;
		// 检查是否有关联的资源
		for (int i = 0; i < resUrls.length; i++) {

			// 已存在资源
			if (resourceMap.containsKey(resUrls[i])) {

				Collection<ConfigAttribute> cas = resourceMap.get(resUrls[i]);

				boolean flag = false;
				ConfigAttribute destCa = null;
				for (ConfigAttribute ca : cas) {

					if (ca.getAttribute().equals(roleName)) {

						destCa = ca;
						flag = true;
						ret = true;
						break;
					}
				}

				if (flag)
					cas.remove(destCa);

			}
		}

		return ret;
	}

	/**
	 * 关联资源角色
	 * 
	 * @param resUrl
	 *            资源路径
	 * @param roleNames
	 *            角色名称
	 * @return
	 */
	public boolean relateResourceRole(String resUrl, String[] roleNames) {

		if (resourceMap.containsKey(resUrl)) {

			Collection<ConfigAttribute> cas = resourceMap.get(resUrl);

			for (int i = 0; i < roleNames.length; i++) {

				boolean flag = false;
				for (ConfigAttribute ca : cas) {

					if (ca.getAttribute().equals(roleNames[i])) {

						flag = true;
						break;
					}
				}

				if (!flag)
					cas.add(new SecurityConfig(roleNames[i]));
			}

		} else {

			Collection<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();
			for (int i = 0; i < roleNames.length; i++) {
				cas.add(new SecurityConfig(roleNames[i]));
			}
			resourceMap.put(resUrl, cas);
		}

		return true;
	}

	/**
	 * 解除资源角色关联
	 * 
	 * @param resUrl
	 *            资源路径
	 * @param roleNames
	 *            角色名称
	 * @return
	 */
	public boolean unrelateResourceRole(String resUrl, String[] roleNames) {

		boolean ret = false;

		if (resourceMap.containsKey(resUrl)) {

			Collection<ConfigAttribute> cas = resourceMap.get(resUrl);

			for (int i = 0; i < roleNames.length; i++) {

				boolean flag = false;
				ConfigAttribute destCa = null;
				for (ConfigAttribute ca : cas) {

					if (ca.getAttribute().equals(roleNames[i])) {

						destCa = ca;
						ret = true;
						flag = true;
						break;
					}
				}

				if (flag)
					cas.remove(destCa);
			}

		}

		return ret;
	}

	public void resourceRoleOp(OpPackage opPackage) {
		if (opPackage.getType() == OpPackage.OP.ADD.getType()) {
			List<OpItem> items = opPackage.toItems();
			for (OpItem opItem : items) {
				relateResourceRole(
						opItem.getKey(),
						opItem.getValues().toArray(
								new String[opItem.getValues().size()]));
			}
		} else if (opPackage.getType() == OpPackage.OP.REMOVE.getType()) {
			List<OpItem> items = opPackage.toItems();
			for (OpItem opItem : items) {
				unrelateResourceRole(opItem.getKey(), opItem.getValues()
						.toArray(new String[opItem.getValues().size()]));
			}
		}
	}

	public static void main(String[] args) {

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {

			list.add(i);
		}

		for (int i = list.size() - 1; i >= 0; i--)
		// for(Integer i:list)
		{

			if (i % 2 == 0) {

				list.remove(i);
				// list.add(i+100);
			}

			// System.out.println(i);
		}

		for (Integer i : list) {

			System.out.println(i);
		}
	}

}
