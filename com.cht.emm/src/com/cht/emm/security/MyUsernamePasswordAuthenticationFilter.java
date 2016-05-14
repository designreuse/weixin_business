package com.cht.emm.security;


import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
 



import org.springframework.security.authentication.AuthenticationServiceException;  
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;  
import org.springframework.security.core.Authentication;  
import org.springframework.security.core.AuthenticationException;  
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;  

import com.cht.emm.security.entity.UsernamePasswordAuthenticationTokenExt;
import com.cht.emm.service.ThirdPartConfigService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.PropertiesReader;

  
/* 
 *  
 * UsernamePasswordAuthenticationFilter源码 
    attemptAuthentication 
        this.getAuthenticationManager() 
            ProviderManager.java 
                authenticate(UsernamePasswordAuthenticationToken authRequest) 
                    AbstractUserDetailsAuthenticationProvider.java 
                        authenticate(Authentication authentication) 
                            P155 user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication); 
                                DaoAuthenticationProvider.java 
                                    P86 loadUserByUsername 
 */  
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{  
    public static final String VALIDATE_CODE = "validate_code";  
    public static final String USERNAME = "username";  
    public static final String PASSWORD = "password";  
      
    private UserService userService;  

    
    private ThirdPartConfigService thirdPartConfigService;
    
    private PropertiesReader propertiesReader;
    
    
    /**
	 * @param propertiesReader the propertiesReader to set
	 */
	public void setPropertiesReader(PropertiesReader propertiesReader) {
		this.propertiesReader = propertiesReader;
	}

	/**
	 * @param thirdPartConfigService the thirdPartConfigService to set
	 */
	public void setThirdPartConfigService(
			ThirdPartConfigService thirdPartConfigService) {
		this.thirdPartConfigService = thirdPartConfigService;
	}

	public void setUserService(UserService userService) {  
        this.userService = userService;  
    }  
  
    @Override  
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {  
        if (!request.getMethod().equals("POST")) {  
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());  
        }  
        //检测验证码  
        checkValidateCode(request);  
          
        String username = obtainUsername(request);  
        String password = obtainPassword(request);  
       
        //验证用户账号与密码是否对应  
        username = username.trim();
          
//        User user = this.userService.loadUserByUserName(username);  
//          
//        if(user == null || !user.getPassword().equals(password)) {  
//  
//            throw new AuthenticationServiceException("用户名或者密码错误！");   
//        }  
        /**
         * 如果发现用户来源不为空(即不是本地用户)，应当事先进行远程验证，然后在做适当的处理 
         */
        String userSource =  propertiesReader.getString("login_validator");
       
        
        //UsernamePasswordAuthenticationToken实现 Authentication  
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationTokenExt(username, password,userSource);  
        // Place the last username attempted into HttpSession for views  
          
        // 允许子类设置详细属性  
        setDetails(request, authRequest);  
          
        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication  
        return this.getAuthenticationManager().authenticate(authRequest);  
    }  
      
    protected void checkValidateCode(HttpServletRequest request) {   
        
    	HttpSession session = request.getSession();  
          
        String sessionValidateCode = obtainSessionValidateCode(session);   
        //让上一次的验证码失效  
        session.setAttribute(VALIDATE_CODE, null);  
        String validateCodeParameter = obtainValidateCodeParameter(request);    
        if (validateCodeParameter.isEmpty() || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {    
            throw new AuthenticationServiceException("验证码错误！");    
          
        }    
    }  
      
    private String obtainValidateCodeParameter(HttpServletRequest request) {  
        Object obj = request.getParameter(VALIDATE_CODE);  
        return null == obj ? "" : obj.toString();  
    }  
  
    protected String obtainSessionValidateCode(HttpSession session) {  
        Object obj = session.getAttribute(VALIDATE_CODE);  
        return null == obj ? "" : obj.toString();  
    }  
  
    @Override  
    protected String obtainUsername(HttpServletRequest request) {  
        Object obj = request.getParameter(USERNAME);  
        return null == obj ? "" : obj.toString();  
    }  
  
    @Override  
    protected String obtainPassword(HttpServletRequest request) {  
        Object obj = request.getParameter(PASSWORD);  
        return null == obj ? "" : obj.toString();  
    }  
    
    
}  