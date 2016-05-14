package com.cht.emm.controller.enterprise_info;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cht.emm.controller.BaseController;


@Controller
public class EnterpriseInfoPageController extends BaseController{

	@RequestMapping(value="/console/talks",method=RequestMethod.GET)
	public String talks(){
		
		return "enterprise-info/talks";
	}
	
	@RequestMapping(value="/console/talks_new",method=RequestMethod.GET)
	public String talks_new(){
		
		return "enterprise-info/talks_new";
	}
	
	@RequestMapping(value="/console/talks_talk",method=RequestMethod.GET)
	public String talks_talk(){
		
		return "enterprise-info/talks_talk";
	}
	
	
	@RequestMapping(value="/console/talks_msg",method=RequestMethod.GET)
	public String talks_msg(){
		
		return "enterprise-info/talks_msg";
	}
	
}
