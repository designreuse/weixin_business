/**
 * @Title: OFService.java
 * @Package: nari.mip.backstage.rpc
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-9 下午3:54:56
 * @Version: 1.0
 */
package com.cht.emm.rpc;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.model.User;
import com.cht.emm.util.PropertiesReader;

import nari.mip.core.exception.MobileRuntimeException;
import nari.mip.core.util.JsonUtil;
import nari.mip.core.web.rpc.IRestClient;
import nari.mip.core.web.rpc.RestClient;
import nari.mip.core.web.rpc.RestResult;
import nari.mip.msg.openfire.user.MipUser;
import nari.mip.msg.openfire.user.MipUserAction;

/**
 * @Class: OFService
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */

@Service("ofService")
public class OFService {
	@Resource(name = "propertiesReader")
	PropertiesReader propertiesReader;

	private boolean inUse =true;
	/**
	 * @return the inUse
	 */
	public boolean isInUse() {
		return inUse;
	}

	/**
	 * @param inUse the inUse to set
	 */
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public boolean addUser(User user) throws URISyntaxException {
//		if(!inUse){
//			return true;
//		}
//		boolean result = false;
//		IRestClient restClient = RestClient.createDefault(propertiesReader
//				.getString("of_user_url"));
//		// "http://127.0.0.1:9090/plugins/mipmsg/user"
//		MipUser user2 = new MipUser();
//		user2.setUserName(user.getUsername());
//		user2.setPassword(user.getPassword());
//		user2.setEmail(user.getDetail().getEmail());
//		user2.setAlias(user.getDetail().getUserAlias());
//
//		MipUserAction action = new MipUserAction();
//		action.setAction(MipUserAction.ACTION_ADD);
//
//		List<MipUser> users = new ArrayList<MipUser>();
//		users.add(user2);
//
//		action.setMipUsers(users);
//
//		Map<String, String> parames = new HashMap<String, String>();
//		parames.put(MipUserAction.KEY, JsonUtil.toJSONString(action));
//
//		RestResult response = restClient.post("", parames);
//		if (response.isSuccessful()) {
//			result = true;
//			System.out.println(result);
//		} else {
//			System.out.println(response.toString());
//			throw new MobileRuntimeException(response.getText());
//		}
//		return result;
		
		return true;
	}

	public boolean delUser(User user) throws URISyntaxException {
//		if(!inUse){
//			return true;
//		}
//		boolean result = false;
//		IRestClient restClient = RestClient.createDefault(propertiesReader
//				.getString("of_user_url"));
//
//		MipUser user2 = new MipUser();
//		user2.setUserName(user.getUsername());
//
//		MipUserAction action = new MipUserAction();
//		action.setAction(MipUserAction.ACTION_DEL);
//
//		List<MipUser> users = new ArrayList<MipUser>();
//
//		users.add(user2);
//
//		action.setMipUsers(users);
//
//		Map<String, String> parames = new HashMap<String, String>();
//		parames.put(MipUserAction.KEY, JsonUtil.toJSONString(action));
//
//		RestResult response = restClient.post("", parames);
//		if (response.isSuccessful()) {
//			result = true;
//			System.out.println(result);
//		} else {
//			System.out.println(response.toString());
//			throw new MobileRuntimeException(response.getText());
//		}
//		return result;
		return true;
	}

	public boolean editUser(User user) throws URISyntaxException {
//		if(!inUse){
//			return true;
//		}
//		boolean result = false;
//		IRestClient restClient = RestClient.createDefault(propertiesReader
//				.getString("of_user_url"));
//
//		MipUser user2 = new MipUser();
//		user2.setUserName(user.getUsername());
//		user2.setPassword(user.getPassword());
//		user2.setEmail(user.getDetail().getEmail());
//		user2.setAlias(user.getDetail().getUserAlias());
//
//		MipUserAction action = new MipUserAction();
//		action.setAction(MipUserAction.ACTION_EDIT);
//
//		List<MipUser> users = new ArrayList<MipUser>();
//		users.add(user2);
//
//		action.setMipUsers(users);
//
//		Map<String, String> parames = new HashMap<String, String>();
//		parames.put(MipUserAction.KEY, JsonUtil.toJSONString(action));
//
//		RestResult response = restClient.post("", parames);
//		if (response.isSuccessful()) {
//			result = true;
//			System.out.println(result);
//		} else {
//			System.out.println(response.toString());
//			throw new MobileRuntimeException(response.getText());
//		}
//
//		return result;
		return true;

	}
}
