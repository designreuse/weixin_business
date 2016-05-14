package com.cht.emm.security;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cht.emm.model.User;

@SuppressWarnings("serial")
public class MyUserDetails implements UserDetails {

	public MyUserDetails(User user, Collection<GrantedAuthority> authorities) {
		this.setAlias(user.getDetail().getUserAlias());
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = authorities;
		this.userId = user.getId();
		this.userType = user.getUserType();
		this.userSource = user.getUserSource();
		if(this.userSource == 1){
			username = username.substring(0,username.indexOf("|"));
		}
		if(this.authorities !=null && this.authorities.size()>0){
			for (GrantedAuthority auth : this.authorities) {
				if("ROLE_ADMIN".equals(auth.getAuthority() )){
					isAdmin =true;
					break;
				}
				
			}
		}
	}

	private String alias;
	private String username;
	private String password;
	private boolean isAdmin;
	private String userId;
	private int userType;
	private Collection<GrantedAuthority> authorities;
	private Integer userSource =0 ;

	/**
	 * @return the userResource
	 */
	public Integer getUserSource() {
		return userSource;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof MyUserDetails) {
			MyUserDetails another = (MyUserDetails) obj;
			return this.getUsername().equals(another.getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getUsername().hashCode();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * @return the userType
	 */
	public int getUserType() {
		return userType;
	}
}
