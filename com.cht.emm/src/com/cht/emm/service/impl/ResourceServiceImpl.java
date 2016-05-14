package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.pagination.PageUtil;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.AuthorityDao;
import com.cht.emm.dao.ResourceDao;
import com.cht.emm.dao.RoleDao;
import com.cht.emm.dao.RoleResourcePermissionDao;
import com.cht.emm.model.MenuItem;
import com.cht.emm.model.Resource;
import com.cht.emm.model.Role;
import com.cht.emm.model.id.ResourceAuth;
import com.cht.emm.model.id.RoleResourcePermission;
import com.cht.emm.security.MySecurityMetadataSource;
import com.cht.emm.service.ResourceService;
import com.cht.emm.util.ByteTool;
import com.cht.emm.util.StringUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.objectcopier.AuthorityCopier;
import com.cht.emm.util.objectcopier.ResourceCopier;
import com.cht.emm.util.objectcopier.RoleCopier;
import com.cht.emm.vo.AuthVO;
import com.cht.emm.vo.ResourceVO;
import com.cht.emm.vo.RoleOpsVO;
import com.cht.emm.vo.RoleVO;


@Service("resourceService")
public class ResourceServiceImpl extends BaseService<Resource, String>
		implements ResourceService {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ResourceServiceImpl.class);

	@javax.annotation.Resource
	private AuthorityDao authorityDAO;
	@javax.annotation.Resource
	private RoleDao roleDao;
	@javax.annotation.Resource
	RoleResourcePermissionDao roleResourcePermissionDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	
	
	@Override
	public List<ResourceVO> getResources() {
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.ne("isSystem", 1));
		return ResourceCopier.copy(baseDao.listAll(query,null,-1,-1), authorityDAO.listAll());
	}

	@Override
	public List<AuthVO> getAllAuths() {
		return AuthorityCopier.copy(authorityDAO.listAll());
	}

	@Override
	public Resource getById(String resource_id) {
		return baseDao.get(resource_id);
	}

	@Override
	public List<AuthVO> getAuths(String[] auth_id) {
		return AuthorityCopier.copy(authorityDAO.listByIds(auth_id));
	}

	@Override
	public ResourceVO getVMById(String resource_id) {
		return ResourceCopier.copy(baseDao.get(resource_id),
				authorityDAO.listAll());
	}

	@Override
	public void delete(String[] ids) {
		List<Resource> resources = baseDao.listByIds(ids);
		for (Resource resource : resources) {
			// 删除资源和权限关联
			Set<ResourceAuth> auths = resource.getResourceAuths();
			if (auths != null) {
				for (ResourceAuth resourceAuth : auths) {
					resourceAuth.delete();
				}
			}
			Set<RoleResourcePermission> roles = resource
					.getResourcePermissions();
			if (roles != null) {
				for (RoleResourcePermission role : roles) {
					role.delete();
				}
			}
		}
		super.delete(ids);
	}

	@Override
	public ResourceVO saveResourceAndAuth(String resourceId, String authIds,
			String uris) {
		com.cht.emm.model.Resource resource = baseDao.get(resourceId);
		Set<ResourceAuth> resourceAuths = resource.getResourceAuths();

		List<String> ids = new ArrayList<String>();
		String[] id_s = null;
		if (!StringUtil.isNullOrBlank(authIds)) {
			id_s = authIds.split(",");
		}

		List<String> uri_s = new ArrayList<String>();
		if (uris == null) {
			int length = id_s == null ? 0 : id_s.length;
			for (int i = 0; i < length; i++) {
				uri_s.add(resource.getUri() + "/sub");
			}

		} else {
			String[] urivalues = uris.split(",");
			for (String uri : urivalues) {
				uri_s.add(uri);
			}
		}

		List<Integer> permissions = new ArrayList<Integer>();
		Map<String, String> auth_uri = new HashMap<String, String>();
		int i = 0;
		if (id_s != null) {
			for (String string : id_s) {

				ids.add(string);
				auth_uri.put(string, uri_s.get(i));
				i++;
			}
		}
		List<ResourceAuth> removed = new ArrayList<ResourceAuth>();
		if (resourceAuths != null) {
			i = 0;
			Map<String, List<String>> removedUrl = new HashMap<String, List<String>>();
			
			for (ResourceAuth resourceAuth : resourceAuths) {
				
				String id = resourceAuth.getAuth().getId();
				// authid 不再 保存的auth之内，移除
				if (authIds.indexOf(id) < 0) {
					removed.add(resourceAuth);
//					allPackages.add(securityOpService.deleteResourceAuth(resourceId, resourceAuth.getId()));
					//此处只做更新Permission的操作，其他的统统过滤掉
					
					/**
					 * 删除ResourceAuth时，需要进行以下操作需 1. 更新必要的RoleResourcePermission
					 * 2. 找到该ResourceAuth相关的Role, 更新security中的相关信息
					 * 
					 */
					String url = resourceAuth.getSubUri();
					if(removedUrl.get(url) ==null){
						removedUrl.put(url, new ArrayList<String>());
					}
					String[] roleName = null;
					// 更新所有的permission
					String whereClause = " where bitand(permission,"
							+ (1<<(resourceAuth.getAuth().getLocIndex() - 1))
							+ ") >0 and resource.id='"
							+ resourceAuth.getResource().getId() + "'";
					List<RoleResourcePermission> rrps = roleResourcePermissionDao
							.listAll(whereClause, "", -1, -1);
					if (rrps != null) {
						roleName = new String[rrps.size()];
						int j = 0;
						for (RoleResourcePermission rrp : rrps) {
							roleName[j++] = rrp.getRole().getRoleName();
							removedUrl.get(url).add(rrp.getRole().getRoleName());
							rrp.setPermission(rrp.getPermission()
									- (int) Math.round(Math.pow(2, resourceAuth
											.getAuth().getLocIndex() - 1)));
						}
						
						 
					}
					Iterator<Entry<String, List<String>>> it = removedUrl.entrySet().iterator();
					while(it.hasNext()){
						Entry<String, List<String>> entry = it.next();
						MySecurityMetadataSource.singleton().unrelateResourceRole(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
					}
					
					resourceAuth.delete();
				} else {
					// 已存在的resourceAuth，更新即可
					ids.remove(id);
					if (uris != null){
						String newUrl = auth_uri.get(id);
						if(!resourceAuth.getSubUri().equals(newUrl)){
							
							// 如果发生url更新，做相应的处理
							MySecurityMetadataSource.singleton().updateResource(resourceAuth.getSubUri(), newUrl);
							LOGGER.info("更新资源地址： "+resourceAuth.getSubUri()+"->"+newUrl);
							resourceAuth.setSubUri(newUrl);
							resourceAuth.update();
						}
							 
					}
					permissions.add(resourceAuth.getAuth().getLocIndex());
				}
				i++;
			}
		}
		if (ids.size() > 0) {
			for (String string : ids) {
				ResourceAuth ra = new ResourceAuth();
				ra.setId(UUIDGen.getUUID());
				ra.setResource(resource);
				ra.setAuth(authorityDAO.get(string));
				ra.setSubUri(auth_uri.get(string));
				permissions.add(ra.getAuth().getLocIndex());
				ra.save();
				resource.getResourceAuths().add(ra);
				
			}
		}

		resource.getResourceAuths().removeAll(removed);
		resource.setPermission(ByteTool.sum(permissions));
		resource.update();
		
		return ResourceCopier.copy(resource, authorityDAO.listAll());
	}

	@Override
	public void saveResourceRole(String resourceId, String roleId, String authId) {
		com.cht.emm.model.Resource resource = baseDao.get(resourceId);
		String unionPK = roleResourcePermissionDao.getPK(roleId, resourceId);
		RoleResourcePermission roleResourcePermission = roleResourcePermissionDao
				.get(unionPK);

		Set<ResourceAuth> auths = resource.getResourceAuths();
		List<Integer> authIndexs = new ArrayList<Integer>();
		String roleName = roleResourcePermission.getRole().getRoleName();
		String url = null;
		for (ResourceAuth resourceAuth : auths) {
			// 判断该resourceAuth是否在原先的roleResource中
			LOGGER.info("permission:"
					+ roleResourcePermission.getPermission()
					+ "  loc: "
					+ resourceAuth.getAuth().getLocIndex()
					+ " result "
					+ (1<<(resourceAuth.getAuth()
							.getLocIndex() - 1)));

			boolean exits = ((roleResourcePermission.getPermission() & (1<<(resourceAuth.getAuth()
					.getLocIndex() - 1))) > 0);
			LOGGER.info("exist : " + exits);

			if (authId.indexOf(resourceAuth.getAuth().getId()) >= 0) {
				// 选中
				authIndexs.add(resourceAuth.getAuth().getLocIndex());

				if (!exits) {
					// 原先不存在关联，为新增

					url = resourceAuth.getSubUri();
					LOGGER.info("新增 getSubUri: " + url + " roleName: "
							+ roleName);
					MySecurityMetadataSource.singleton().relateResourceRole(
							url, new String[] { roleName });
				}

			} else {
				// 未选中
				if (exits) {
					// 原先存在关联，为删除
					url = resourceAuth.getSubUri();
					LOGGER.info("删除getSubUri: " + url + " roleName: "
							+ roleName);
					MySecurityMetadataSource.singleton().unrelateResourceRole(
							url, new String[] { roleName });
				}
			}

		}
		roleResourcePermission.setPermission(ByteTool.sum(authIndexs));
		roleResourcePermission.update();

		/*
		 * Set<RoleResourcePermission> roleResourcePermissions =
		 * resource.getResourcePermissions(); for (RoleResourcePermission
		 * roleResourcePermission : roleResourcePermissions) { if
		 * (roleResourcePermission.getRole().getId().equals(roleId)) {
		 * Set<ResourceAuth> auths = resource.getResourceAuths(); List<Integer>
		 * authIndexs = new ArrayList<Integer>(); for (ResourceAuth resourceAuth
		 * : auths) {
		 * 
		 * if(authId.indexOf(resourceAuth.getAuth().getId())>=0){
		 * 
		 * authIndexs.add(resourceAuth.getAuth().getLocIndex());
		 * 
		 * }else{
		 * 
		 * } } roleResourcePermission.setPermission(ByteTool.sum(authIndexs));
		 * roleResourcePermission.update(); break; } }
		 */
		resource.update();
	}

	@Override
	public List<RoleOpsVO> addResourceRole(String resourceId, String roleIds) {
		Resource resource = baseDao.get(resourceId);
		String[] ids = roleIds.split(",");
		for (String id : ids) {
			Role role = roleDao.get(id);
			RoleResourcePermission rr = new RoleResourcePermission();
			rr.setId(UUIDGen.getUUID());
			rr.setRole(role);
			rr.setResource(resource);
			rr.setPermission(resource.getPermission());
			rr.save();
			if (resource.getResourcePermissions() == null) {
				resource.setResourcePermissions(new HashSet<RoleResourcePermission>());
			}
			resource.getResourcePermissions().add(rr);
		}
		return ResourceCopier.getViewRole(resource);
	}

	@Override
	public boolean removeRoleResource(String roleId, String resourceId) {
		RoleResourcePermission rrp = ((ResourceDao) baseDao).getRoleResource(
				roleId, resourceId);
		rrp.delete();
		return true;
	}

	@Override
	public RoleVO addRoleResource(String resourceIds, String roleId) {
		Role role = roleDao.get(roleId);
		String[] ids = resourceIds.split(",");
		for (String id : ids) {
			System.out.println(id);
			Resource resource = baseDao.get(id);
			RoleResourcePermission rr = new RoleResourcePermission();
			rr.setId(UUIDGen.getUUID());
			rr.setRole(role);
			rr.setResource(resource);
			System.out.println(resource.getName());
			rr.setPermission(resource.getPermission());
			rr.save();
			if (role.getResourcePermissions() == null) {
				role.setResourcePermissions(new HashSet<RoleResourcePermission>());
			}
			role.getResourcePermissions().add(rr);

		}
		return RoleCopier.copy(role, authorityDAO.listAll());
	}

	@Override
	public List<ResourceVO> selectResource(String type, String id) {
		List<Resource> all = ((ResourceDao) baseDao).selectResource(type, id);
		List<ResourceVO> result = null;
		if (all != null) {
			result = new ArrayList<ResourceVO>();
			for (Resource resource : all) {
				result.add(ResourceCopier.singleCopy(resource));
			}
		}
		return result;
	}

	@Override
	@javax.annotation.Resource(name = "resourceDao")
	public void setBaseDao(IBaseDao<Resource, String> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * <p>
	 * Title: queryForPage
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param count
	 * @param string
	 * @param conditionQuery
	 * @param orderList
	 * @param i
	 * @param length
	 * @return
	 * @see com.cht.emm.service.ResourceService#queryForPage(int,
	 *      java.lang.String, java.lang.String, java.lang.String, int,
	 *      java.lang.Integer)
	 */
	@Override
	public Page<ResourceVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length) {
		List<Resource> resources = this.baseDao.listAll(whereClause, orderby,
				pn, length);
		Page<ResourceVO> page = PageUtil.getPage(count, pn,
				ResourceCopier.copy(resources, authorityDAO.listAll()), length);
		return page;
	}

	@Override
	public void releaseAuth(String[] ids) {
		Map<String, List<String>> allMap = new HashMap<String, List<String>>();
		for (int i = 0; i < ids.length; i++) {
			com.cht.emm.model.Resource resource = resourceDao
					.get(ids[i]);
			ConditionQuery query = new ConditionQuery();
			query.add(Restrictions.eq("resource.id", ids[i]));
			List<RoleResourcePermission> roleResrcs = roleResourcePermissionDao
					.listAll(query, null, -1, -1);
			String resourceUrl = resource.getUri();
			if (roleResrcs.size() > 0) {

				// 处理资源subUrl中role的访问权限，如果定义资源非常好的
				// 话，直接删除以资源路径开头的所有url就行了，^~^
				Set<ResourceAuth> auths = resource.getResourceAuths();
				for (RoleResourcePermission rrp : roleResrcs) {
					// 删除resource url对应的url;
					String roleName = rrp.getRole().getRoleName();
					if (!getListFromMap(resourceUrl, allMap).contains(roleName)) {
						allMap.get(resourceUrl).add(roleName);
					}
					for (ResourceAuth resourceAuth : auths) {
						// 判断 Role与 ResourceAuth是否有关联，有的话，则在对应的subUri中
						// 删除role(资源的subUrl可能重复，开始没有约定好，^~^)
						if ((rrp.getPermission() & (1<<(resourceAuth.getAuth().getLocIndex()-1))) > 0) {
							if (!getListFromMap(resourceAuth.getSubUri(),
									allMap).contains(roleName)) {
								allMap.get(resourceAuth.getSubUri()).add(
										roleName);
							}
						}
					}

				}

			}
			/**
			 * 删除item
			 */
			if (resource.getIsItem() == 1) {
				MenuItem item = resource.getItem();
				if (item != null) {
					item.delete();
				}

			}

		}
		int size = allMap.size();
		String[] resUrls = new String[size];

		String[][] roleNames = new String[size][];
		Iterator<Entry<String, List<String>>> iterator = allMap.entrySet()
				.iterator();
		int j = 0;
		while (iterator.hasNext()) {
			Entry<String, List<String>> entry = iterator.next();
			resUrls[j] = entry.getKey();
			roleNames[j] = new String[entry.getValue().size()];
			int k = 0;
			for (String roleName : entry.getValue()) {
				roleNames[j][k++] = roleName;
			}

			j++;
		}
		MySecurityMetadataSource.singleton()
				.removeResources(resUrls, roleNames);

	}

	private List<String> getListFromMap(String key,
			Map<String, List<String>> map) {
		synchronized (map) {
			if (map.get(key) == null) {
				map.put(key, new ArrayList<String>());
			}
			return map.get(key);
		}
	}
}
