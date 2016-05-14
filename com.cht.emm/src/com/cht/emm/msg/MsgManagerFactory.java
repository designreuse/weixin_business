package com.cht.emm.msg;

public class MsgManagerFactory {

	private MsgManagerFactory(){
		
	}
	
	public static final int XMPP_MANAGER=0;
	
	public static MsgManager getMsgManager(int msgMangerType){
		
		switch(msgMangerType){
		
			case XMPP_MANAGER:
			
				if(null == xmppManager){
					
					xmppManager=new XMPPMsgManager();
				}
				
				return xmppManager;
				
		}
		
		return null;
	}
	
	private static XMPPMsgManager xmppManager;
}
