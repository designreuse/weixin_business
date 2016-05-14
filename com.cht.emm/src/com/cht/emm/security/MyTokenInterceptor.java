package com.cht.emm.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cht.emm.security.token.TokenChecker;
import com.cht.emm.util.StringUtil;

public class MyTokenInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private TokenChecker tokenChecker;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub

		String contextPath = request.getServletPath();

		// 如果是移动端的登陆和注册服务，则不检验令牌
		if (contextPath.contains("rest/mobile/environment")
				|| contextPath.contains("rest/mobile/state")
				|| contextPath.contains("rest/mobile/login")
				|| contextPath.contains("rest/mobile/tool/login")
				|| contextPath.contains("rest/mobile/register")
				|| contextPath.contains("rest/mobile/validDevice")
				|| contextPath.contains("rest/mobile/configs")
				|| contextPath.contains("rest/mobile/strategys")
//				|| contextPath.contains("rest/mobile/app/download")
				|| contextPath.contains("rest/mobile/token/state")
				)

			return super.preHandle(request, response, handler);

		String tokenId = request.getHeader("token");

		if (StringUtil.isNullOrBlank(tokenId))
			return false;

		if (!tokenChecker.check(tokenId))
			return false;

		return super.preHandle(request, response, handler);
	}

}
