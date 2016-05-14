package com.cht.emm.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cht.emm.model.User;
import com.cht.emm.service.UserService;
import com.cht.emm.vo.RoleVO;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		log.debug("username = " + username + " password = " + password);
		
		//本地数据库验证
		User user = userService.loadUserByUserName(username);
		
		if(user==null){
			throw new UsernameNotFoundException("用户名没有找到!");
		}
		
		if(!user.getPassword().equals(password)){
			
			throw new AuthenticationException("密码错误！") {
				private static final long serialVersionUID = -5506038021601762240L;
			};
		}
		//可以到第三方系统去认证
		boolean hasAuthority=true;
		if(!hasAuthority){
			
			throw new AuthenticationException("fail msg") {
				private static final long serialVersionUID = -5506038021601762240L;
			};
		}		
		
		Set<GrantedAuthority> authorities=this.getAuthoritiesByUsername(username);
		
		return new UsernamePasswordAuthenticationToken(username, password,
				authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private static Logger log = Logger
			.getLogger(CustomAuthenticationProvider.class);
	
	private UserService userService;
	
	public void setUserService(UserService userService){
		
		this.userService=userService;
	}
	
	private Set<GrantedAuthority> getAuthoritiesByUsername(String username){
		
		User user = userService.loadUserByUserName(username);
		if(user==null){
			throw new UsernameNotFoundException("用户名没有找到!");
		}
		
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		List<RoleVO> roles = userService.findUserRolesByUsername(username);
		
		for(RoleVO role : roles){
			GrantedAuthority ga = new SimpleGrantedAuthority(role.getRoleName());
			authorities.add(ga);	
		}
		
		return authorities;
	}

}
