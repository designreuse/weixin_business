package com.cht.emm.util;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cht.emm.vo.MessageVO;

import nari.mip.core.exception.MobileRuntimeException;
import nari.mip.core.web.rpc.IRestClient;
import nari.mip.core.web.rpc.RestClient;
import nari.mip.core.web.rpc.RestResult;

public class OpenfireUtil {
//	public static int getUserStatus(String username) throws Exception {
//		IRestClient restClient = RestClient
//				.createDefault("http://192.168.42.125:9090/plugins/presence/status");
//		Map<String, String> parames = new HashMap<String, String>();
//		parames.put("jid", username + "@zgc--20100929em");
//		parames.put("type", "xml");
//		int shOnLineState = -1;
//		RestResult result = restClient.post("", parames);
//		if (result.isSuccessful()) {
//			String strFlag = result.getText();
//
//			if (strFlag.indexOf("type=\"unavailable\"") >= 0) {
//				shOnLineState = 2; // 离线
//			}
//			if (strFlag.indexOf("type=\"error\"") >= 0) {
//				shOnLineState = 0;// 用户不存在
//			} else if (strFlag.indexOf("priority") >= 0
//					|| strFlag.indexOf("id=\"") >= 0) {
//				shOnLineState = 1;// 在线
//			}
//			System.out.println(shOnLineState);
//		} else {
//			System.out.println(result.toString());
//			throw new MobileRuntimeException(result.getText());
//		}
//		return shOnLineState;
//	}
//
//	public static void sendMessage(String id, String sender,
//			List<String> receivers, String title, String content, int type)
//			throws Exception {
//		IRestClient restClient = RestClient
//				.createDefault("http://192.168.42.125:9090/plugins/mipmessage/message");
//
//		MessageVO messageVO = new MessageVO();
//		messageVO.setId(id);
//		messageVO.setSender(sender);
//
//		messageVO.setReceivers(receivers);
//		messageVO.setReceiverAction("action_sys_msg");
//
//		messageVO.setLevel(1);
//		messageVO.setType(type);
//		messageVO.setDate(new Timestamp(System.currentTimeMillis()));
//		messageVO.setTitle(title);
//		messageVO.setContent(content);
//
//		Map<String, String> parames = new HashMap<String, String>();
//		parames.put("action", "PUSH_MESSAGE");
//		parames.put("params", messageVO.toJsonString());
//
//		RestResult result = restClient.post("", parames);
//		if (result.isSuccessful()) {
//			System.out.println(result);
//		} else {
//			System.out.println(result.toString());
//			throw new MobileRuntimeException(result.getText());
//		}
//	}
}
