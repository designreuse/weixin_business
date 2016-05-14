package com.cht.emm.msg;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cht.emm.common.util.SpringContextUtil;
import com.cht.emm.util.PropertiesReader;

import nari.mip.core.util.JsonUtil;
import nari.mip.core.web.rpc.IRestClient;
import nari.mip.core.web.rpc.RestClient;
import nari.mip.core.web.rpc.RestResult;
import nari.mip.msg.openfire.msg.MipMsg;
import nari.mip.msg.openfire.user.MipUser;
import nari.mip.msg.openfire.user.MipUserAction;

public class XMPPMsgManager extends MsgManager{

	private PropertiesReader propertiesReader = SpringContextUtil.getBean("propertiesReader");
	
	@Override
	public MsgResponse getUserState(List<String> userIds) {
		
		MsgResponse response = new MsgResponse();
		
		IRestClient restClient=null;
		try {
			restClient = RestClient
					.createDefault(getXmppUserServerURL());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		List<MipUser> users = new ArrayList<MipUser>();
		
		for(String id : userIds){
			
			MipUser user = new MipUser();
			user.setUserName(id);
			
			users.add(user);
		}
		
		MipUserAction action =new MipUserAction();
		action.setAction(MipUserAction.ACTION_STATE);
		action.setMipUsers(users);
	
		Map<String, String> parames = new HashMap<String, String>();
		parames.put(MipUserAction.KEY, JsonUtil.toJSONString(action));

		RestResult result = restClient.post("", parames);
		if (result.isSuccessful()) {
			
			Map<String,Object> map = JsonUtil.parseMap(result.getRaw().toString());
			response.setSuccess(true);
			response.setValue(map);
			
		} else {
			
			response.setSuccess(false);
			response.setErrorMsg(result.getText());
		}
		
		return response;
	}

	@Override
	public MsgResponse addUser(List<MipUser> users) {
		
		MsgResponse response = new MsgResponse();
		IRestClient restClient=null;
		try {
			restClient = RestClient
					.createDefault(getXmppUserServerURL());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		MipUserAction action =new MipUserAction();
		action.setAction(MipUserAction.ACTION_ADD);
		
		action.setMipUsers(users);
	

		Map<String, String> parames = new HashMap<String, String>();
		parames.put(MipUserAction.KEY, JsonUtil.toJSONString(action));

		RestResult result = restClient.post("", parames);
		
		if (result.isSuccessful()) {
			
			response.setSuccess(true);
			
		} else {
			
			response.setSuccess(false);
			response.setErrorMsg(result.getText());
		}
		
		return response;
	}

	@Override
	public MsgResponse delUser(List<MipUser> users) {
		
		MsgResponse response = new MsgResponse();
		IRestClient restClient=null;
		try {
			restClient = RestClient
					.createDefault(getXmppUserServerURL());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		MipUserAction action =new MipUserAction();
		action.setAction(MipUserAction.ACTION_DEL);
		
		action.setMipUsers(users);
	

		Map<String, String> parames = new HashMap<String, String>();
		parames.put(MipUserAction.KEY, JsonUtil.toJSONString(action));

		RestResult result = restClient.post("", parames);
		
		if (result.isSuccessful()) {
			
			response.setSuccess(true);
			
		} else {
			
			response.setSuccess(false);
			response.setErrorMsg(result.getText());
		}
		
		return response;
	}

	@Override
	public MsgResponse editUser(List<MipUser> users) {

		MsgResponse response = new MsgResponse();
		IRestClient restClient=null;
		try {
			restClient = RestClient
					.createDefault(getXmppUserServerURL());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		MipUserAction action =new MipUserAction();
		action.setAction(MipUserAction.ACTION_EDIT);
		
		action.setMipUsers(users);
	

		Map<String, String> parames = new HashMap<String, String>();
		parames.put(MipUserAction.KEY, JsonUtil.toJSONString(action));

		RestResult result = restClient.post("", parames);
		
		if (result.isSuccessful()) {
			
			response.setSuccess(true);
			
		} else {
			
			response.setSuccess(false);
			response.setErrorMsg(result.getText());
		}
		
		return response;
	
	}

	@Override
	public MsgResponse sendMsg(MipMsg msg) {
		
		MsgResponse response = new MsgResponse();
				
		IRestClient restClient=null;
		try {
			restClient = RestClient
					.createDefault(getXmppMsgServerURL());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		Map<String, String> parames = new HashMap<String, String>();
		parames.put(MipMsg.KEY, JsonUtil.toJSONString(msg));

		RestResult result = restClient.post("", parames);
		
		if(result.isSuccessful()){
			
			response.setSuccess(true);
			
		}else{
			
			response.setSuccess(false);
			response.setErrorMsg(result.getText());
		}
		
		return response;
	}

	@Override
	public MsgResponse login(String username, String password) {
		return null;
	}

	@Override
	public String getMsgServerURL() {
		return propertiesReader.getString("msg_service_url");
	}
	
	private String getXmppMsgServerURL(){
		
		return getMsgServerURL()+"/msg";
	}
	
	private String getXmppUserServerURL(){
		
		return getMsgServerURL()+"/user";
	}

	@Override
	public String generateMsgUserId(String userName, String devId) {
		// TODO Auto-generated method stub
		return userName+"_"+devId;
	}

}
