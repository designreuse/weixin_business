package com.cht.emm.controller.news;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cht.emm.controller.BaseController;


@Controller
public class NewsPageController extends BaseController{

	@RequestMapping(value="/console/news",method=RequestMethod.GET)
	public String news(){
		
		return "news/news";
	}
	
	@RequestMapping(value="/console/news_new",method=RequestMethod.GET)
	public String news_new(){
		
		return "news/news_new";
	}
	
}
