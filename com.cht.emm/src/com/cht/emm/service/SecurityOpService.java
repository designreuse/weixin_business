/**
 * @Title: SecurityOpService.java
 * @Package: nari.mip.backstage.service
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-4 下午3:15:37
 * @Version: 1.0
 */
package com.cht.emm.service;

import java.util.List;

import com.cht.emm.security.OpPackage;


/**
 * @Class: SecurityOpService
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public interface SecurityOpService {

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
	
	
	/**
	 * @Name: updateResource
	 * @Decription: 使用新的url更新resource
	 * @Time: 2015-2-5 下午1:55:02
	 * @param resourceId
	 * @param newUrl
	 * @return List<OpPackage>
	 */
	public List<OpPackage> updateResource(String resourceId,String newUrl);
	
	
	
	/**
	 * @Name: updateResourceAuth
	 * @Decription: 更新resource和auth之间的关联对security的影响
	 * @Time: 2015-2-4 下午3:21:07
	 * @return List<OpPackage>
	 */
	public List<OpPackage> updateResourceAuth(String resourceId,String authId, String newUrl);
	
	/**
	 * @Name: addResourceAuth
	 * @Decription: 新增一个resource auth
	 * @Time: 2015-2-5 下午2:19:08
	 * @param resourceId
	 * @param authId
	 * @param url
	 * @return OpPackage
	 */
	public OpPackage addResourceAuth(String resourceId,String authId, String url);
	/**
	 * @Name: addRoleResource
	 * @Decription: 关联role和resource对security的影响
	 * @Time: 2015-2-4 下午3:21:54
	 * @return List<OpPackage>
	 */
	public OpPackage addRoleResource(String roleId,String[] resourceId);
	
	/**
	 * @Name: addResourceRole
	 * @Decription:关联 resource 和 role关联
	 * @Time: 2015-2-4 下午3:37:29
	 * @param resourceId
	 * @param roleId
	 * @return OpPackage   需要添加的url -roles内容
	 */
	public OpPackage addResourceRole(String resourceId,String[] roleId);
	
	/**
	 * @Name: deleteRoleResource
	 * @Decription: 解绑 role和resource
	 * @Time: 2015-2-4 下午3:38:42
	 * @param roleId
	 * @param resourceId
	 * @return OpPackage
	 */
	public OpPackage deleteRoleResource(String roleId,String resourceId);
	
	/**
	 * @Name: deleteResourceAuth
	 * @Decription: 解绑
	 * @Time: 2015-2-4 下午3:42:52
	 * @param resourceId
	 * @param authId
	 * @return OpPackage
	 */
	public OpPackage deleteResourceAuth(String resourceId,String authId);
	
	
	/**
	 * @Name: deleteRole
	 * @Decription: 删除role 对security的影响 
	 * @Time: 2015-2-4 下午3:27:05
	 * @return OpPackage
	 */
	public OpPackage deleteRole(String roleId);
	
	/**
	 * @Name: deleteResource
	 * @Decription: 删除资源对security的影响
	 * @Time: 2015-2-4 下午3:27:35
	 * @return List<OpPackage>
	 */
	public OpPackage deleteResource(String resourceId);
	
}
