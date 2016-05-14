package com.cht.emm.rpc.model;

import java.io.Serializable;

public class MipUser implements Serializable {

	public static final int STATE_ONLINE = 0;
	
	public static final int STATE_OFFLINE = 1;
	
	public static final int STATE_NO_EXISTS = 2;
	
	private static final long serialVersionUID = -8868615329362596309L;
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	private String password;
	
	private String email;
	
	private String alias;
	
}
