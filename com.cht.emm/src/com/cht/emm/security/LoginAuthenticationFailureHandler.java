package com.cht.emm.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		System.out
				.println("-------LoginAuthenticationFailureHandler.onAuthenticationFailure()------------验证失败！！！"+"原因："+authException.getMessage());
		
		 handle(request, response, authException);
	     clearAuthenticationAttributes(request);
		
	}

	
	protected Log logger = LogFactory.getLog(this.getClass());
	 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	protected void handle(HttpServletRequest request, 
		      HttpServletResponse response, AuthenticationException authException) throws IOException{
		        
				String targetUrl = determineTargetUrl(authException);
		 
		        if (response.isCommitted()) {
		            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
		            return;
		        }
		 
		        redirectStrategy.sendRedirect(request, response, targetUrl);
		    }
		 
   
    protected String determineTargetUrl(AuthenticationException authException) {
       
    	String turl="";
    	
    	if(authException instanceof BadCredentialsException){
    		
    		turl = "/login?type=1";
    	}
    	
    	if(authException instanceof SessionAuthenticationException){
    		
    		turl = "/login?type=2";
    	}
    	
    	if(authException instanceof AuthenticationServiceException){
    		
    		turl = "/login?type=3";
    	}
    	
    	return  turl;
    	
    }
 
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
