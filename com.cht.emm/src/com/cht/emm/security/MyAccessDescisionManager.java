package com.cht.emm.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @description  访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源 ;做最终的访问控制决定
 * @author aokunsang
 * @date 2012-8-16
 */
public class MyAccessDescisionManager implements AccessDecisionManager {

	/**
	 * @description 认证用户是否具有权限访问该url地址
	 * 
	 */
	@Override
	public void decide(Authentication authentication, Object obj,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,InsufficientAuthenticationException {
		System.out.println("MyAccessDescisionManager.decide()------------------验证用户是否具有一定的权限--------");
		if(configAttributes==null) return;
		Iterator<ConfigAttribute> it = configAttributes.iterator();
		while(it.hasNext()){
			String needRole = it.next().getAttribute();
			//authentication.getAuthorities()  用户所有的权限
			for(GrantedAuthority ga:authentication.getAuthorities()){
				if(needRole.equals(ga.getAuthority())){
					return;
				}
			}
		}
		throw new AccessDeniedException("--------MyAccessDescisionManager：decide-------权限认证失败！");
	}

	/**
	 * 启动时候被AbstractSecurityInterceptor调用，决定AccessDecisionManager是否可以执行传递ConfigAttribute。
	 */
	@Override
	public boolean supports(ConfigAttribute configAttribute) {
		System.out.println("MyAccessDescisionManager.supports()------------角色名："+configAttribute.getAttribute());
		return true;
	}

	/**
	 * 被安全拦截器实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("MyAccessDescisionManager.supports()--------------------------------");
		return true;
	}

}
