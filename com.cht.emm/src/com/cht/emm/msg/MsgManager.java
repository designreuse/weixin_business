package com.cht.emm.msg;

import java.util.List;

import nari.mip.msg.openfire.msg.MipMsg;
import nari.mip.msg.openfire.user.MipUser;

public abstract class MsgManager {
	
	public abstract MsgResponse login(String username,String password);
	public abstract MsgResponse getUserState(List<String> userIds);
	public abstract MsgResponse addUser(List<MipUser> users);
	public abstract MsgResponse delUser(List<MipUser> users);
	public abstract MsgResponse editUser(List<MipUser> users);
	public abstract MsgResponse sendMsg(MipMsg msg);
	public abstract String getMsgServerURL();
	
	public abstract String generateMsgUserId(String userName,String devId);
	
}
