/**   
* @Title: RPCService.java 
* @Package nari.mip.backstage.rpc 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
* @date 2014-9-29 上午11:17:06 
* @version V1.0   
*/
package com.cht.emm.rpc;

import java.util.List;
import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.cht.emm.model.Group;
import com.cht.emm.rpc.model.RPCGroup;
import com.cht.emm.rpc.model.RPCUser;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.vo.GroupVO;
import com.cht.emm.vo.UserVO;




/** 
 * @ClassName: RPCService 
 * @Description: 远程调用工具类
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
 * @date 2014-9-29 上午11:17:06 
 *  
 */
@Service("rpcService")
public class RPCService {
	
	@Resource
	PropertiesReader propertiesReader;
	
	private static final String ADD_OR_UPDATE_GROUP = "add_or_update_org";
	private static final String ADD_OR_UPDATE_USER = "add_or_update_user";
	private static final String DEL_ORG ="del_org";
	private static final String DEL_USER ="del_user";
	private static final String SWITCH_ORG = "switch_org";
	
	/**
	* @Title: addOrUpdateOrganization 
	* @Description: 新增或更新组
	* @param groups
	* @return  
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addOrUpdateOrganization1(List<GroupVO> groups){
		
		return false;
		/*
		List<RPCGroup> rpcGroups = new ArrayList<RPCGroup>();
		for (GroupVO group : groups) {
			 RPCGroup g = new RPCGroup();
			 g.setId(group.getId());
			 g.setOrg_desc(group.getGroupDesc());
			 g.setOrg_name(group.getGroupName());
			 g.setStatus(group.getStatus()==null?1:group.getStatus());
			 g.setParent_id(group.getParentGroup()==null?null:group.getParentGroup().getId());
			 rpcGroups.add(g);
			 
		}
		return addOrUpdateOrganization(rpcGroups);
		*/
	}
	
	
	public boolean addOrUpdateOrganization(List<RPCGroup> groups){
		return false;
		/**
		
		boolean result =true; 
		String body = RestClient.postJSON(propertiesReader.getString("RPC_BASE_URL")+ADD_OR_UPDATE_GROUP,  JSONArray.fromObject(groups).toString());
		JSONObject status = JSONObject.fromObject(body);
		if(status.containsKey("code")&&status.getInt("code")==0){
			result = true;
		}
		return result;
		*/
	}
	
	
	
	
	
	/**
	* @Title: addORUpdateUser 
	* @Description: 新增或更新用户 
	* @param user
	* @return  
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addORUpdateUser(List<RPCUser> user){
		
		return false;
		/*
		boolean result =false;
		JSONArray json = JSONArray.fromObject(user);
		System.out.println(propertiesReader.getString("RPC_BASE_URL")+ADD_OR_UPDATE_USER);
		String body = RestClient.postJSON(propertiesReader.getString("RPC_BASE_URL")+ADD_OR_UPDATE_USER,  json.toString());
		JSONObject status = JSONObject.fromObject(body);
		if(status.containsKey("code")&&status.getInt("code")==0){
			result = true;
		}
		
		return result;
		*/
	}
	
	
	public  boolean addORUpdateUser2(List<UserVO> users){
		 
		return false;
		/*
		List<RPCUser> users2 = new ArrayList<RPCUser>();
		for (UserVO user2 : users) {
			RPCUser rpcUser = new RPCUser();
			rpcUser.setUser_id(user2.getUsername());
			rpcUser.setReal_name(user2.getUserAlias());
			rpcUser.setPassword(user2.getPassword());
			rpcUser.setStatus(user2.getStatus());
			List<GroupVO> groups = user2.getGroups();
			if(groups!=null){
				rpcUser.setOrg_id(groups.get(0).getId());
			}
			users.add(user2);
		}
		 return addORUpdateUser(users2);
		 */
	}
	/**
	* @Title: deleteOrganization 
	* @Description: 删除用户组
	* @param ids
	* @return  
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean deleteOrganization(List<String> ids){
		return false;
		/*
		boolean result =false;
		JSONArray array = JSONArray.fromObject(ids);
		String body = RestClient.postJSON(propertiesReader.getString("RPC_BASE_URL")+DEL_ORG,  array.toString());
		JSONObject status = JSONObject.fromObject(body);
		if(status.containsKey("code")&&status.getInt("code")==0){
			result = true;
		}
		return result;
		*/
	}
	
	
	/**
	* @Title: deleteUser 
	* @Description: 删除用户
	* @param ids
	* @return  
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean deleteUser(List<String> ids){
		return false;
		/*
		boolean result =false;
		JSONArray array = JSONArray.fromObject(ids);
		String body = RestClient.postJSON(propertiesReader.getString("RPC_BASE_URL")+DEL_USER,  array.toString());
		JSONObject status = JSONObject.fromObject(body);
		if(status.containsKey("code")&&status.getInt("code")==0){
			result = true;
		}
		return result;
		*/
	}
	
	
	/**
	* @Title: changeUserOrganization 
	* @Description:  更改用户所在的组 
	* @param groupId
	* @param userIds
	* @return  
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean changeUserOrganization(String groupId,List<String > userIds){
		return false;
		/*
		boolean result =false;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("org_id", groupId);
		params.put("user_ids", userIds);
		JSONObject json = JSONObject.fromObject(params);
		String body = RestClient.postJSON(propertiesReader.getString("RPC_BASE_URL")+SWITCH_ORG,  json.toString());
		JSONObject status = JSONObject.fromObject(body);
		if(status.containsKey("code")&&status.getInt("code")==0){
			result = true;
		}
		return result;
		*/
	}


	/** 
	* @Title: addOrUpdateOrganization 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param groups  
	* @return void    返回类型 
	* @throws 
	*/
	public boolean addOrUpdateOrganization2(List<Group> groups) {
		// TODO Auto-generated method stub
		return false;
		/*
		List<RPCGroup> rpcGroups = new ArrayList<RPCGroup>();
		for (Group group : groups) {
			 RPCGroup g = new RPCGroup();
			 g.setId(group.getId());
			 g.setOrg_desc(group.getGroupDesc());
			 g.setOrg_name(group.getGroupName());
			 g.setStatus(group.getStatus());
			 g.setParent_id(group.getParentGroup()==null?null:group.getParentGroup().getId());
			 rpcGroups.add(g);
			 
		}
		return addOrUpdateOrganization(rpcGroups);
		*/
	}
}
