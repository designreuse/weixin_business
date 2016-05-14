package com.cht.emm.rpc.model;

import java.io.Serializable;
import java.util.List;


public class MipUserAction implements Serializable {

	public static final String KEY="mipuseraction";
	
	public static final String ACTION_ADD="action_add";
	public static final String ACTION_DEL="action_del";
	public static final String ACTION_EDIT="action_edit";
	public static final String ACTION_GET="action_get";
	public static final String ACTION_STATE="action_state";
	
	private static final long serialVersionUID = -8868615329362596300L;
	
	private String action;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<MipUser> getMipUsers() {
		return mipUsers;
	}

	public void setMipUsers(List<MipUser> mipUsers) {
		this.mipUsers = mipUsers;
	}

	private List<MipUser> mipUsers;
}
