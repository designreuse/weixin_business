package com.cht.emm.controller.monitor_analysis;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cht.emm.controller.BaseController;


@Controller
public class MonitorAnalysisPageController extends BaseController{

	@RequestMapping(value="/console/monitor_devices",method=RequestMethod.GET)
	public String news(){
		
		return "monitor-analysis/monitor_devices";
	}
	
	@RequestMapping(value="/console/monitor_device",method=RequestMethod.GET)
	public String news_new(){
		
		return "monitor-analysis/monitor_device";
	}
	
	@RequestMapping(value="/console/monitor_info_center",method=RequestMethod.GET)
	public String info_center(){
		
		return "monitor-analysis/info_center";
	}
}
