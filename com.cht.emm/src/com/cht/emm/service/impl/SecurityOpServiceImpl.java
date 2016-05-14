/**
 * @Title: SecurityOpServiceImpl.java
 * @Package: nari.mip.backstage.service.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-4 下午3:48:00
 * @Version: 1.0
 */
package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cht.emm.dao.ResourceAuthDao;
import com.cht.emm.dao.ResourceDao;
import com.cht.emm.dao.RoleDao;
import com.cht.emm.dao.RoleResourcePermissionDao;
import com.cht.emm.model.Resource;
import com.cht.emm.model.Role;
import com.cht.emm.model.id.ResourceAuth;
import com.cht.emm.model.id.RoleResourcePermission;
import com.cht.emm.security.OpItem;
import com.cht.emm.security.OpPackage;
import com.cht.emm.service.SecurityOpService;


/**
 * @Class: SecurityOpServiceImpl
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
@Service("securityOpService")
public class SecurityOpServiceImpl implements SecurityOpService {
	public static enum ITEM_TYPE {
		byRole(1), byUrl(2);
		private int type;

		private ITEM_TYPE(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	@javax.annotation.Resource
	RoleDao roleDao;

	@javax.annotation.Resource
	ResourceDao resourceDao;

	@javax.annotation.Resource
	ResourceAuthDao resourceAuthDao;

	@javax.annotation.Resource
	RoleResourcePermissionDao roleResourcePermissionDao;
	
	
	@Override
	public List<OpPackage> updateResource(String resourceId, String newUrl) {
		List<OpPackage> packages = new ArrayList<OpPackage>();
		OpItem remove = new OpItem();
		remove.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
		
		OpItem add = new OpItem();
		add.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
		add.setKey(newUrl);
		
		Resource resource = resourceDao.get(resourceId);
		remove.setKey(resource.getUri());
		Set<RoleResourcePermission> rrps = resource.getResourcePermissions();
		List<String> roles = new ArrayList<String>();
		if(rrps !=null){
			for (RoleResourcePermission rrp : rrps) {
				roles.add(rrp.getRole().getRoleName());
			}
		}
		
		add.setValues(roles);
		remove.setValues(roles);
		
		OpPackage packageadd = new OpPackage();
		packageadd.setType(OpPackage.OP.ADD.getType());
		packageadd.Wrap(add);
		packages.add(packageadd);
		
		OpPackage packageRemove = new OpPackage();
		packageRemove.setType(OpPackage.OP.REMOVE.getType());
		packageRemove.Wrap(remove);
		packages.add(packageRemove);
		return packages;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.service.SecurityOpService#updateResourceAuth(java.
	 * lang.String, java.lang.String[], java.lang.String[])
	 */
	@Override
	public List<OpPackage> updateResourceAuth(String resourceId,
			String authId, String newUrl) {
		List<OpPackage> packages = new ArrayList<OpPackage>();
		
		String unionPK = resourceAuthDao.getPK(resourceId, authId);
		ResourceAuth resourceAuth = resourceAuthDao.get(unionPK);
		String url = resourceAuth.getSubUri();
		
		
		List<String> roles = new ArrayList<String>();
		int locIndex = resourceAuth.getAuth().getLocIndex();
		int permissionValue = 1 <<(locIndex-1);
		List<RoleResourcePermission> resourcePermissions = roleResourcePermissionDao.listAll("where resource.id ='"+resourceId+"' and bitand(permission,"+ permissionValue +") > 0 ", null, -1, -1);
		for (RoleResourcePermission roleResourcePermission : resourcePermissions) {
			roles.add(roleResourcePermission.getRole().getRoleName());
		}
		
		OpItem item = new OpItem();
		item.setKey(url);
		item.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
		item.setValues(roles);
		OpPackage package1 = new OpPackage();
		package1.setType(OpPackage.OP.REMOVE.getType());
		package1.Wrap(item);
		packages.add(package1);
		
		OpItem addItem = new OpItem();
		addItem.setKey(newUrl);
		addItem.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
		addItem.setValues(roles);
		OpPackage package2 = new OpPackage();
		package2.setType(OpPackage.OP.ADD.getType());
		package2.Wrap(addItem);
		packages.add(package2);
		return packages;
	}

	@Override
	public OpPackage addResourceAuth(String resourceId, String authId,
			String url) {
		String unionPK = resourceAuthDao.getPK(resourceId, authId);
		ResourceAuth resourceAuth = resourceAuthDao.get(unionPK);
		 
		List<String> roles = new ArrayList<String>();
		int locIndex = resourceAuth.getAuth().getLocIndex();
		int permissionValue = 1 <<(locIndex-1);
		List<RoleResourcePermission> resourcePermissions = roleResourcePermissionDao.listAll("where resource.id ='"+resourceId+"' and bitand(permission,"+ permissionValue +") > 0 ", null, -1, -1);
		for (RoleResourcePermission roleResourcePermission : resourcePermissions) {
			roles.add(roleResourcePermission.getRole().getRoleName());
		}
		OpItem item = new OpItem();
		item.setKey(url);
		item.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
		item.setValues(roles);
		OpPackage package1 = new OpPackage();
		package1.setType(OpPackage.OP.ADD.getType());
		package1.Wrap(item);
		return package1;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.service.SecurityOpService#addRoleResource(java.lang
	 * .String, java.lang.String[])
	 */
	@Override
	public OpPackage addRoleResource(String roleId, String[] resourceId) {
		List<Resource> resources = resourceDao.listByIds(resourceId);
		OpItem item = new OpItem();
		item.setType(ITEM_TYPE.byRole.getType());
		Role role = roleDao.get(roleId);
		item.setKey(role.getRoleName());
		for (Resource resource : resources) {
			item.getValues().add(resource.getUri());
			Set<ResourceAuth> resourceAuths = resource.getResourceAuths();
			for (ResourceAuth resourceAuth : resourceAuths) {
				String subUrl = resourceAuth.getSubUri();
				if (subUrl != null && !item.getValues().contains(subUrl)) {
					item.getValues().add(subUrl);
				}
			}

		}

		OpPackage pkg = new OpPackage();
		pkg.setType(OpPackage.OP.ADD.getType());
		return pkg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.service.SecurityOpService#addResourceRole(java.lang
	 * .String, java.lang.String[])
	 */
	@Override
	public OpPackage addResourceRole(String resourceId, String[] roleId) {
		Resource resource = resourceDao.get(resourceId);
		List<String> urls = new ArrayList<String>();
		urls.add(resource.getUri());
		Set<ResourceAuth> resourceAuths = resource.getResourceAuths();
		if (resourceAuths != null) {
			for (ResourceAuth resourceAuth : resourceAuths) {
				urls.add(resourceAuth.getSubUri());
			}
		}
		List<Role> roles = roleDao.listByIds(roleId);
		List<String> rolesList = new ArrayList<String>();
		for (Role role : roles) {
			String roleName = role.getRoleName();
			if (rolesList.contains(roleName)) {
				rolesList.add(roleName);
			}
		}
		OpPackage package1 = new OpPackage();
		package1.setType(OpPackage.OP.ADD.getType());
		for (String url : urls) {
			OpItem item = new OpItem();
			item.setKey(url);
			item.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
			item.setValues(rolesList);
			package1.Wrap(item);
		}
		
		return package1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.service.SecurityOpService#deleteRoleResource(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public OpPackage deleteRoleResource(String roleId, String resourceId) {
		String unionKey = roleResourcePermissionDao.getPK(roleId, resourceId);
		RoleResourcePermission rrp =roleResourcePermissionDao.get(unionKey);
		
		Resource resource = resourceDao.get(resourceId);
		
		List<String> urls = new ArrayList<String>();
		urls.add(resource.getUri());
		Set<ResourceAuth> resourceAuths = resource.getResourceAuths();
		if (resourceAuths != null) {
			for (ResourceAuth resourceAuth : resourceAuths) {
				if((rrp.getPermission()&(1<<(resourceAuth.getAuth().getLocIndex()-1))) >0 )
				urls.add(resourceAuth.getSubUri());
			}
		}
		Role role = roleDao.get(roleId);
		List<String> rolesList = new ArrayList<String>();
		rolesList.add(role.getRoleName());
		OpPackage package1 = new OpPackage();
		package1.setType(OpPackage.OP.REMOVE.getType());
		for (String url : urls) {
			OpItem item = new OpItem();
			item.setKey(url);
			item.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
			item.setValues(rolesList);
			package1.Wrap(item);
		}
		
		return package1;
		
		 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.service.SecurityOpService#deleteResourceAuth(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public OpPackage deleteResourceAuth(String resourceId, String authId) {
		String unionPK = resourceAuthDao.getPK(resourceId, authId);
		ResourceAuth resourceAuth = resourceAuthDao.get(unionPK);
		String url = resourceAuth.getSubUri();
		List<String> roles = new ArrayList<String>();
		int locIndex = resourceAuth.getAuth().getLocIndex();
		int permissionValue = 1 <<(locIndex-1);
		List<RoleResourcePermission> resourcePermissions = roleResourcePermissionDao.listAll("where resource.id ='"+resourceId+"' and bitand(permission,"+ permissionValue +") > 0 ", null, -1, -1);
		for (RoleResourcePermission roleResourcePermission : resourcePermissions) {
			roles.add(roleResourcePermission.getRole().getRoleName());
		}
		OpItem item = new OpItem();
		item.setKey(url);
		item.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
		item.setValues(roles);
		OpPackage package1 = new OpPackage();
		package1.setType(OpPackage.OP.REMOVE.getType());
		package1.Wrap(item);
		return package1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.service.SecurityOpService#deleteRole(java.lang.String)
	 */
	@Override
	public OpPackage deleteRole(String roleId) {
		Role role = roleDao.get(roleId);
		String roleName = role.getRoleName();
		List<String> urls = new ArrayList<String>();
		Set<RoleResourcePermission> roleResourcePermissions = role.getResourcePermissions();
		if(roleResourcePermissions!=null){
			for (RoleResourcePermission roleResourcePermission : roleResourcePermissions) {
				Resource resource = roleResourcePermission.getResource();
				urls.add(resource.getUri());
				List<ResourceAuth> resourceAuths = resourceAuthDao.listAll(" where resource.id = '"+resource.getId()+"' and bitand(pow(auth.locIndex),"+roleResourcePermission.getPermission()+") > 0", null , -1, -1);
				if(resourceAuths!= null){
					for (ResourceAuth resourceAuth : resourceAuths) {
						urls.add(resourceAuth.getSubUri());
					}
				}
			}
		}
		
		OpItem item = new OpItem();
		item.setKey(roleName);
		item.setType(SecurityOpService.ITEM_TYPE.byRole.getType());
		item.setValues(urls);
		OpPackage package1 = new OpPackage();
		package1.setType(OpPackage.OP.REMOVE.getType());
		package1.Wrap(item);
		return package1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.service.SecurityOpService#deleteResource(java.lang
	 * .String)
	 */
	@Override
	public OpPackage deleteResource(String resourceId) {
		OpPackage package1 = new OpPackage();
		package1.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
		Resource resource =  resourceDao.get(resourceId);
		String url = resource.getUri();
		List<String> urls =new ArrayList<String>();
		urls.add(url);
		
		/**
		 * 查找该资源下的下级url
		 */
		
		Set<ResourceAuth> resourceAuths = resource.getResourceAuths();
		if(resourceAuths!=null){
			for (ResourceAuth resourceAuth : resourceAuths) {
				urls.add(resourceAuth.getSubUri());
			}
		}
				
		/**
		 * 找到关联的所有的role
		 */
		List<String> roles = new ArrayList<String>();
		Set<RoleResourcePermission> roleResourcePermissions = resource.getResourcePermissions();
		if(roleResourcePermissions!=null){
			for (RoleResourcePermission roleResourcePermission : roleResourcePermissions) {
				roles.add(roleResourcePermission.getRole().getRoleName());
			}
		}
		for (String u  : urls) {
			OpItem  item = new OpItem();
			item.setKey(u);
			item.setType(SecurityOpService.ITEM_TYPE.byUrl.getType());
			item.setValues(roles);
			package1.Wrap(item);
		}
		return package1;
	}
}
