package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cht.emm.model.Authority;
import com.cht.emm.model.Resource;
import com.cht.emm.model.id.ResourceAuth;
import com.cht.emm.model.id.RoleResourcePermission;
import com.cht.emm.util.ByteTool;
import com.cht.emm.vo.AuthVO;
import com.cht.emm.vo.ResourceAuthVO;
import com.cht.emm.vo.ResourceVO;
import com.cht.emm.vo.RoleOpsVO;


public class ResourceCopier {
	public static List<ResourceVO> copy(List<Resource> resources,
			List<Authority> allauths) {
		if (resources == null) {
			return null;
		}
		List<ResourceVO> copies = new ArrayList<ResourceVO>();
		for (Resource resource : resources) {
			ResourceVO copy = singleCopy(resource);
			copy.setResourceAuths(getViewResourceAuths(resource));
			copy.setRoles(getSingleViewRole(resource));
			copies.add(copy);
		}
		return copies;
	}
 
	public static ResourceVO singleCopy(Resource resource) {
		if (resource == null) {
			return null;
		}
		ResourceVO copy = new ResourceVO();
		copy.setId(resource.getId());
		copy.setName(resource.getName());
		copy.setPermission(resource.getPermission());
		copy.setType(resource.getType());
		copy.setUri(resource.getUri());
		copy.setIsItem(resource.getIsItem());
		copy.setIsSystem(resource.getIsSystem());

		return copy;
	}

	public static ResourceVO copy(Resource resource, List<Authority> allauths) {
		if (resource == null) {
			return null;
		}
		ResourceVO copy = singleCopy(resource);
		copy.setRoles(getViewRole(resource));
		copy.setResourceAuths(getViewResourceAuths(resource));
		if (copy.getResourceAuths() != null) {
			List<String> authIds = new ArrayList<String>();
			for (ResourceAuthVO auth : copy.getResourceAuths()) {
				authIds.add(auth.getAuth().getId());
			}
			copy.setAuthIds(authIds);
		}
		return copy;
	}

	public static List<AuthVO> getViewAuths(Resource resource,
			List<Authority> auths) {
		if (resource == null) {
			return null;
		}
		if (resource.getPermission() != null && resource.getPermission() > 0) {
			List<AuthVO> viewAuths = new ArrayList<AuthVO>();
			for (Authority auth : auths) {
				if (ByteTool
						.valid(auth.getLocIndex(), resource.getPermission())) {
					viewAuths.add(AuthorityCopier.copy(auth));
				}
			}
			return viewAuths;
		}
		return null;
	}

	public static List<ResourceAuthVO> getViewResourceAuths(Resource resource) {
		if (resource == null) {
			return null;
		}
		List<ResourceAuthVO> vra = null;
		Set<ResourceAuth> resourceAuths = resource.getResourceAuths();
		if (resourceAuths != null && resourceAuths.size() > 0) {
			vra = new ArrayList<ResourceAuthVO>();
			for (ResourceAuth resourceAuth : resourceAuths) {
				ResourceAuthVO ra = new ResourceAuthVO();
				ra.setAuth(AuthorityCopier.copy(resourceAuth.getAuth()));
				ra.setSubUri(resourceAuth.getSubUri());
				ra.setId(resourceAuth.getId());
				vra.add(ra);
			}
		}
		return vra;
	}

	public static ResourceAuthVO getResourceAuthVO(ResourceAuth resourceAuth) {
		if (resourceAuth == null) {
			return null;
		}
		ResourceAuthVO ra = new ResourceAuthVO();
		ra.setAuth(AuthorityCopier.copy(resourceAuth.getAuth()));
		ra.setSubUri(resourceAuth.getSubUri());
		ra.setId(resourceAuth.getId());
		return ra;
	}

	public static ResourceAuthVO copy(ResourceAuth resourceAuth){
		ResourceAuthVO copy = getResourceAuthVO(resourceAuth);
		if(copy != null){
			copy.setResource(singleCopy(resourceAuth.getResource()));
		}
		return copy;
	}
	
	public static List<RoleOpsVO> getViewRole(Resource resource) {
		if (resource == null) {
			return null;
		}
		Set<RoleResourcePermission> permissions = resource
				.getResourcePermissions();
		if (permissions != null && permissions.size() > 0) {
			List<RoleOpsVO> viewRoles = new ArrayList<RoleOpsVO>();
			for (RoleResourcePermission rp : permissions) {
				RoleOpsVO vrole = new RoleOpsVO();
				vrole.setId(rp.getId());
				vrole.setRole(RoleCopier.singelCopy(rp.getRole()));
				Set<ResourceAuth> resourceAuths = resource.getResourceAuths();
				if (resourceAuths != null && resourceAuths.size() > 0) {
					List<ResourceAuthVO> vra = new ArrayList<ResourceAuthVO>();
					for (ResourceAuth resourceAuth : resourceAuths) {
						ResourceAuthVO ra = new ResourceAuthVO();
						ra.setAuth(AuthorityCopier.copy(resourceAuth.getAuth()));
						ra.setSubUri(resourceAuth.getSubUri());

						if (ByteTool.valid(
								resourceAuth.getAuth().getLocIndex(),
								rp.getPermission())) {
							ra.setSelected(true);
						}
						vra.add(ra);
					}
					vrole.setResourceAuth(vra);
				}
				viewRoles.add(vrole);
			}
			return viewRoles;
		}
		return null;
	}

	public static List<RoleOpsVO> getSingleViewRole(Resource resource) {
		Set<RoleResourcePermission> permissions = resource
				.getResourcePermissions();
		if (permissions != null && permissions.size() > 0) {
			List<RoleOpsVO> viewRoles = new ArrayList<RoleOpsVO>();
			for (RoleResourcePermission rp : permissions) {
				RoleOpsVO vrole = new RoleOpsVO();
				vrole.setId(rp.getId());
				vrole.setRole(RoleCopier.singelCopy(rp.getRole()));
				viewRoles.add(vrole);
			}
			return viewRoles;
		}
		return null;
	}

}
