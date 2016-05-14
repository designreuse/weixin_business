/**
 * @Title: SysConfigPageController.java
 * @Package: nari.mip.backstage.controller.system_config
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-25 下午4:09:17
 * @Version: 1.0
 */
package com.cht.emm.controller.system_config;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cht.emm.controller.BaseController;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.vo.KeyValueVO;



/**
 * @Class: SysConfigPageController
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Controller
public class SysConfigPageController extends BaseController {
	
    @Resource(name="propertiesReader")
	PropertiesReader propertiesReader;
	
	@RequestMapping(value="/console/platform_agent/history", method = RequestMethod.GET)
	public String platAgentList(){
		
		return "sys-config/agent_history";
	}
	
	
	@RequestMapping(value="/console/platform_agent/update", method = RequestMethod.GET)
	public String platAgentAdd(){
		
		return "sys-config/agent-update";
	}
	
	@RequestMapping(value="/console/sysconfig/params",method = RequestMethod.GET)
	public ModelAndView sysParameters(){	 
		//配置openfire相关的
//		List<FileSetVO> parameters = new ArrayList<FileSetVO>();
		

		return new ModelAndView("sys-config/key_parameters");
	}
	
	@RequestMapping(value="/console/sysconfig/params/config",method = RequestMethod.GET)
	public ModelAndView configPropertiesList(HttpServletRequest request){
		SysConfigServiceController.resetSession(request);
		String[] configed = propertiesReader.getString("cf_items").split(",");
		String[] configedName = propertiesReader.getString("cf_items_names").split(",");
		List<KeyValueVO> list = new ArrayList<KeyValueVO>();
		if(configed.length > 0){
			for (int i = 0; i < configed.length; i++) {
				list.add(new KeyValueVO(configed[i], configedName[i]));
			}
		}
 		return new ModelAndView("sys-config/choose-properties","properties",list);
	}
	 
}
