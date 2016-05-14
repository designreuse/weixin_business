/**
 * @Title: EMMService.java
 * @Package: nari.mip.backstage.util.emm
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-1-12 下午2:13:28
 * @Version: 1.0
 */
package com.cht.emm.util.emm;

import com.alibaba.fastjson.JSONObject;
import com.cht.emm.model.Group;
import com.cht.emm.util.Response;
import com.cht.emm.util.emm.entity.RopServiceEntity;
import com.cht.emm.util.emm.util.HttpConn;
import com.cht.emm.vo.UserVO;


/**
 * @Class: EMMService
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
public class EMMService {

	private String serverUrl = "http://119.254.111.223:6001/thirdpartaccess";
	private String appKey = "EPM";
	private String secret = "FHuma025";
//	private String method = "mobileark.adduser";
	private String v = "1.0";
	private String format = "json";
	private boolean used = false;

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * @param serverUrl
	 *            the serverUrl to set
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * @param appKey
	 *            the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret
	 *            the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}
	 
	/**
	 * @return the v
	 */
	public String getV() {
		return v;
	}

	/**
	 * @param v
	 *            the v to set
	 */
	public void setV(String v) {
		this.v = v;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the used
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * @param used
	 *            the used to set
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}

	/**
	 * @Name: addOrg
	 * @Decription: 新增组织机构
	 * @Time: 2015-1-12 下午2:47:00
	 * @param groupVO
	 * @return Response
	 */
	public RopServiceEntity getEntity(){
		RopServiceEntity entity = new RopServiceEntity();
		entity.setAppKey(appKey);
		entity.setFormat(format);
		entity.setV(v);
		return entity;
	}
	
	public Response addOrg(Group groupVO) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("memo", groupVO.getGroupDesc());
		entity.put("orgName", groupVO.getGroupName());
		entity.put("orgCode", groupVO.getGroupName());
		entity.setMethod("mobileark.addorg");
		entity.sign(secret);
		try {
			String id = HttpConn.getConnResp(serverUrl, entity.toString());
			result.setSuccessful(true);
			result.setResultValue(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: updateOrg
	 * @Decription: 更新组织结构
	 * @Time: 2015-1-12 下午2:47:29
	 * @param groupVO
	 * @return Response
	 */
	public Response updateOrg(Group groupVO) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("memo", groupVO.getGroupDesc());
		entity.put("orgName", groupVO.getGroupName());
		entity.put("assignedLicenseNum", "-1");
		entity.put("orgUuid",groupVO.getId());
		entity.setMethod("mobileark.modifyorg");
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			//resultCode
			JSONObject object = JSONObject.parseObject(reStr);
			if(object.getInteger("resultCode")==0){
				result.setSuccessful(true);
			}
			// result.setResultValue(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: removeOrg
	 * @Decription: 删除组织结构
	 * @Time: 2015-1-12 下午2:49:40
	 * @param groupVO
	 * @return Response
	 */
	public Response removeOrg(String  gid) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.setMethod("mobileark.delorg");
		entity.put("orgUuid", gid);
		entity.sign(secret);
		try {
			String id = HttpConn.getConnResp(serverUrl, entity.toString());
			result.setSuccessful(true);
			result.setResultValue(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: addDep
	 * @Decription: 新增部门
	 * @Time: 2015-1-12 下午2:50:28
	 * @param groupVO
	 * @return Response
	 */
	public Response addDep(Group groupVO,String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("memo", groupVO.getGroupDesc());
		entity.put("depName", groupVO.getGroupName());
		Group paGroupVO = groupVO.getParentGroup();
		if(paGroupVO!=null ){
			if(paGroupVO.getGroupType()==Group.GROUP_TYPE.ORG.getType()){
//				entity.put("parentDepUuid", null);
			}else {
//				if(!orgUUID.equals(paGroupVO.getId())){
					entity.put("parentDepUuid", paGroupVO.getId());
//				}
			}
		}
		entity.put("orgUuid", orgUUID);
		entity.setMethod("mobileark.adddepartment");
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			String id = object.getString("depUuid");
			if(id!=null){
				result.setSuccessful(true);
				result.setResultValue(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: updateDep
	 * @Decription: 更新部门
	 * @Time: 2015-1-12 下午2:50:59
	 * @param groupVO
	 * @return Response
	 */
	public Response updateDep(Group groupVO,String orgUuid) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("memo", groupVO.getGroupDesc());
		entity.put("depUuid", groupVO.getId());
		entity.put("depName", groupVO.getGroupName());
		entity.put("orgUuid",orgUuid);
		entity.setMethod("mobileark.modifydepartment");
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			//resultCode
			JSONObject object = JSONObject.parseObject(reStr);
			if(object.getInteger("resultCode")==0){
				result.setSuccessful(true);
			}
			// result.setResultValue(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: removeDep
	 * @Decription: 删除部门
	 * @Time: 2015-1-12 下午2:51:21
	 * @param groupVO
	 * @return Response
	 */
	public Response removeDep(String  gid, String orgUUID) {
		
		Response result = new Response();
		if(!isUsed()){
			result.setSuccessful(true);
			return result;
		}
		RopServiceEntity entity = getEntity();
		entity.put("depUuid", gid);
		entity.setMethod("mobileark.deldepartment");
		entity.put("orgUuid", orgUUID);
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			if(object.getInteger("resultCode")==0){
				result.setSuccessful(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}

	/**
	 * @Name: addUser
	 * @Decription: 新增用户
	 * @Time: 2015-1-12 下午2:53:45
	 * @param user
	 * @return Response
	 */
	public Response addUser(UserVO user,String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("loginId", user.getUsername());
		entity.put("loginPassword", user.getPassword());
		entity.put("userName", user.getUserAlias());
		entity.put("emailAddress", user.getEmail());
		entity.put("phoneNumber", user.getMobile());
		entity.put("depUuid", user.getGroupIds().get(0));
		entity.setMethod("mobileark.adduser");
		entity.put("orgUuid", orgUUID);
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			String id = object.getString("userUuid");
			if(id!=null){
				result.setSuccessful(true);
				result.setResultValue(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: updateUser
	 * @Decription: 更新用户
	 * @Time: 2015-1-12 下午2:54:34
	 * @param user
	 * @return Response
	 */
	public Response updateUser(UserVO user,String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("userUuid", user.getId());
		entity.put("depUuid",user.getGroupIds().get(0));
		entity.put("loginPassword", user.getPassword());
		entity.put("userName", user.getUserAlias());
		entity.put("emailAddress", user.getEmail());
		entity.put("phoneNumber", user.getMobile());
		entity.put("loginId", user.getUsername());
		entity.setMethod("mobileark.modifyuser");
		entity.put("orgUuid",  orgUUID);
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			String id = object.getString("userUuid");
			if(id!=null){
				result.setSuccessful(true);
				result.setResultValue(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: removeUser
	 * @Decription: 删除用户
	 * @Time: 2015-1-12 下午2:54:50
	 * @param user
	 * @return Response
	 */
	public Response removeUser(String id,String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("userUuid", id);
		entity.setMethod("mobileark.deluser");
		entity.put("orgUuid", orgUUID);
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			if(object.getInteger("resultCode")==0){
				result.setSuccessful(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Response addOrgMaster(UserVO user, String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("loginId", user.getUsername());
		entity.put("loginPassword", user.getPassword());
		entity.put("adminName", user.getUserAlias());
		entity.put("emailAddress", user.getEmail());
		entity.put("phoneNumber", user.getMobile());
		entity.setMethod("mobileark.addadmin");
		entity.put("orgUuid", orgUUID);
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			String id = object.getString("userUuid");
			if(id!=null){
				result.setSuccessful(true);
				result.setResultValue(id);
			}else{
				result.setResultMessage(object.getString("subErrors.message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: updateUser
	 * @Decription: 更新用户
	 * @Time: 2015-1-12 下午2:54:34
	 * @param user
	 * @return Response
	 */
	public Response updateOrgAdmin(UserVO user,String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("adminUuid", user.getId());
		entity.put("loginPassword", user.getPassword());
		entity.put("adminName", user.getUserAlias());
		entity.put("emailAddress", user.getEmail());
		entity.put("phoneNumber", user.getMobile());
		entity.put("loginId", user.getUsername());
		entity.setMethod("mobileark.modifyadmin");
		entity.put("orgUuid",  orgUUID);
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			String id = object.getString("userUuid");
			if(id!=null){
				result.setSuccessful(true);
				result.setResultValue(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: removeUser
	 * @Decription: 删除用户
	 * @Time: 2015-1-12 下午2:54:50
	 * @param user
	 * @return Response
	 */
	public Response removeOrgAdmin(String id,String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("adminUuid", id);
		entity.setMethod("mobileark.deladmin");
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			if(object.getInteger("resultCode")==0){
				result.setSuccessful(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	public Response addDepMaster(UserVO user, String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("loginId", user.getUsername());
		entity.put("loginPassword", user.getPassword());
		entity.put("adminName", user.getUserAlias());
		entity.put("emailAddress", user.getEmail());
		entity.put("phoneNumber", user.getMobile());
		entity.put("depUuid", user.getGroupIds().get(0));
		entity.setMethod("mobileark.adddepadmin");
		entity.put("orgUuid", orgUUID);
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			String id = object.getString("userUuid");
			if(id!=null){
				result.setSuccessful(true);
				result.setResultValue(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * @Name: updateUser
	 * @Decription: 更新用户
	 * @Time: 2015-1-12 下午2:54:34
	 * @param user
	 * @return Response
	 */
	public Response updateDepAdmin(UserVO user,String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("adminUuid", user.getId());
		entity.put("depUuid",user.getGroupIds().get(0));
		entity.put("loginPassword", user.getPassword());
		entity.put("adminName", user.getUserAlias());
		entity.put("emailAddress", user.getEmail());
		entity.put("phoneNumber", user.getMobile());
		entity.setMethod("mobileark.modifydepadmin");
		entity.put("orgUuid",  orgUUID);
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			String id = object.getString("userUuid");
			if(id!=null){
				result.setSuccessful(true);
				result.setResultValue(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Name: removeUser
	 * @Decription: 删除用户
	 * @Time: 2015-1-12 下午2:54:50
	 * @param user
	 * @return Response
	 */
	public Response removeDepAdmin(String id,String orgUUID) {
		Response result = new Response();
		RopServiceEntity entity = getEntity();
		entity.put("adminUuid", id);
		entity.setMethod("mobileark.deldepadmin");
		entity.sign(secret);
		try {
			String reStr = HttpConn.getConnResp(serverUrl, entity.toString());
			JSONObject object = JSONObject.parseObject(reStr);
			if(object.getInteger("resultCode")==0){
				result.setSuccessful(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
