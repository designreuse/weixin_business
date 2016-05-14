package com.cht.emm.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cht.emm.model.User;
import com.cht.emm.service.UserService;
import com.cht.emm.vo.RoleVO;

/**
 * @description  认证完成后,将用户信息查询出来
 * @author cht
 */
public class MyUserDetailServiceImpl implements UserDetailsService {

	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		System.out.println("---------MyUserDetailServiceImpl:loadUserByUsername------正在加载用户名和密码，用户名为："+username);
		
		User user = userService.loadUserByUserName(username);
		if(user==null){
			throw new UsernameNotFoundException("用户名没有找到!");
		}
		
//		boolean enabled = true;                //是否可用
//        boolean accountNonExpired = true;        //是否过期
//        boolean credentialsNonExpired = true;   
//        boolean accountNonLocked = true;  
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		//如果你使用资源和权限配置在xml文件中，如：<intercept-url pattern="/user/admin" access="hasRole('ROLE_ADMIN')"/>；
		//并且也不想用数据库存储，所有用户都具有相同的权限的话，你可以手动保存角色(如：预订网站)。
		//authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		List<RoleVO> roles = userService.findUserRolesByUsername(username);
		
		System.out.println(roles.get(0).getRoleName());
		
		for(RoleVO role : roles){
			GrantedAuthority ga = new SimpleGrantedAuthority(role.getRoleName());
			authorities.add(ga);	
		}
		
		return new MyUserDetails(user, authorities);
	}
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
