package com.cht.emm.security.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;

public class SmartPrincipal {

	private String username;
	private String ip;
	private int userType;

	/**
	 * @return the userType
	 */
	public int getUserType() {
		return userType;
	}

	public SmartPrincipal(String username, String ip) {
		Assert.notNull(username,
				"username cannot be null (violation of interface contract)");
		Assert.notNull(ip,
				"username cannot be null (violation of interface contract)");
		this.username = username;
		this.ip = ip;
	}

	public SmartPrincipal(Authentication authentication) {
		Assert.notNull(authentication,
				"authentication cannot be null (violation of interface contract)");

		String username = null;

		if (authentication.getPrincipal() instanceof UserDetails) {
			username = ((UserDetails) authentication.getPrincipal())
					.getUsername();
			 
		} else {
			username = (String) authentication.getPrincipal();
		}

		String ip = ((WebAuthenticationDetails) authentication.getDetails())
				.getRemoteAddress();
		this.username = username;
		this.ip = ip;
	}

	public boolean equalsIp(SmartPrincipal smartPrincipal) {
		return this.ip.equals(smartPrincipal.getIp());
	}
	
	public boolean equalsUsername(SmartPrincipal smartPrincipal){
		
		return this.username.equals(smartPrincipal.getUsername());
	}

	@Override
	public boolean equals(Object obj) {
		
		boolean flag=false;
		
		if (obj instanceof SmartPrincipal) {
			
			SmartPrincipal smartPrincipal = (SmartPrincipal) obj;

			flag = equalsUsername(smartPrincipal);
		}
		
		return flag;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public String toString() {
		return "SmartPrincipal:{username=" + username + ",ip=" + ip + "}";
	}
	
	public String getIp(){
		
		return this.ip;
	}
	
	public String getUsername(){
		
		return this.username;
	}
}
