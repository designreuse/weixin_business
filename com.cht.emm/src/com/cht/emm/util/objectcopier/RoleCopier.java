package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cht.emm.model.Authority;
import com.cht.emm.model.Role;
import com.cht.emm.model.id.ResourceAuth;
import com.cht.emm.model.id.RoleResourcePermission;
import com.cht.emm.model.id.UserRole;
import com.cht.emm.util.ByteTool;
import com.cht.emm.vo.ResourceAuthVO;
import com.cht.emm.vo.ResourceOpsVO;
import com.cht.emm.vo.RoleVO;


public class RoleCopier {

	public static List<RoleVO> copy(Set<UserRole> userRoles) {
		// TODO Auto-generated method stub
		if(userRoles ==null ){
			return null;
		}
		List<RoleVO> copies = new ArrayList<RoleVO>();
		for (UserRole userrole : userRoles) {
			
			Role role= userrole.getRole();
			RoleVO copy =singelCopy(role);
			copies.add(copy);
		}
		return copies;
	}

	public static RoleVO copy(Role role,List<Authority> auths){
		if(role ==null ){
			return null;
		}
		RoleVO copy = singelCopy(role);
		copy.setUsers(UserCopier.copy(role.getUserRoles()));
		List<ResourceOpsVO> resources = new ArrayList<ResourceOpsVO>();
		for (RoleResourcePermission permission : role.getResourcePermissions()) {
			ResourceOpsVO resource =  getResourceOps(permission);
			resources.add(resource);
		}
		copy.setResources(resources);
		
		return copy;
	}

	public static RoleVO singelCopy(Role role){
		if(role ==null ){
			return null;
		}
		RoleVO copy = new RoleVO();
		copy.setId(role.getId());
		copy.setRoleName(role.getRoleName());
		copy.setRoleDesc(role.getRoleDesc());
		copy.setUserNum(role.getUserRoles().size());
		copy.setIsSystem(role.getIsSystem());
		copy.setUserType(role.getUserType());
		List<String> usertypes = new ArrayList<String>();
		for(int i= 1;i<=4;i++){
			if((copy.getUserType() & (2<<(i-1)))>0){
				switch (i) {
				case 1:
					usertypes.add("系统管理员");
					break;
				case 2:
					usertypes.add("机构管理员");
					break;
				case 3:
					usertypes.add("部门管理员");
					break;
				case 4:
					usertypes.add("普通员工");
					break;
				default:
					break;
				}
			}
		}
		copy.setUserTypes(usertypes);
		return copy;
	}
	public static List<RoleVO> copy(List<Role> listAll) {
		// TODO Auto-generated method stub
		if(listAll ==null){
			return null;
		}
		List<RoleVO> copies = new ArrayList<RoleVO>();
		for (Role role : listAll) {
			RoleVO copy = singelCopy(role);
			copies.add(copy);
		}
		return copies;
	}
	public static ResourceOpsVO getResourceOps(RoleResourcePermission rrp){
		ResourceOpsVO  ops = new  ResourceOpsVO();
		Integer permission = rrp.getPermission();
		Set<ResourceAuth> resourceAuths = rrp.getResource().getResourceAuths();
		ops.setResource(ResourceCopier.singleCopy(rrp.getResource()));
		if(resourceAuths!=null && resourceAuths.size()>0){
			List<ResourceAuthVO> vra = new ArrayList<ResourceAuthVO>();
				for (ResourceAuth resourceAuth : resourceAuths) {
					ResourceAuthVO ra = new ResourceAuthVO();
					  ra.setAuth(AuthorityCopier.copy(resourceAuth.getAuth()));
					  ra.setSubUri(resourceAuth.getSubUri());
					if(ByteTool.valid(resourceAuth.getAuth().getLocIndex(), permission)){
						ra.setSelected(true); 
						
					}
					 vra.add(ra);
			}
				ops.setResourceAuths(vra);
		}
		
		return ops;
	}
	
	
	
}
