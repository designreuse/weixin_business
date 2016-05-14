/**
 * @Title: ThirdPartPageController.java
 * @Package: nari.mip.backstage.controller.thirdpart
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-23 下午2:30:34
 * @Version: 1.0
 */
package com.cht.emm.controller.thirdpart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.controller.BaseController;
import com.cht.emm.model.RemoteClass;
import com.cht.emm.service.GroupService;
import com.cht.emm.service.RemoteClassService;
import com.cht.emm.service.ThirdPartConfigService;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.vo.RemoteClassVO;
import com.cht.emm.vo.ThirdPartConfigVO;
import com.cht.emm.vo.UserVO;

import nariis.pi3000.framework.utility.StringUtil;

/**
 * @Class: ThirdPartPageController
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 第三方接入的页面控制器
 */
@Controller
public class ThirdPartPageController extends BaseController{
	
	@Resource(name="thirdPartConfigService")
	private ThirdPartConfigService thirdPartConfigService;
	
	@Resource(name = "groupService")
	private GroupService groupService;
	
	@Resource(name="remoteClassService")
	private RemoteClassService remoteClassService;
	
	@Resource(name="propertiesReader")
	private PropertiesReader propertiesReader;
	
	@RequestMapping(value="/console/thirdpart/list",method=RequestMethod.GET)
	public String configList(){
		return "thirdPart/config_list";
	}
	
	@RequestMapping(value="/console/thirdpart/remote/list",method=RequestMethod.GET)
	public String remoteClassList(){
		return "thirdPart/remoteClasses";
	}
	
	
	@RequestMapping(value="/console/thirdpart/create",method=RequestMethod.GET)
	public ModelAndView create_thirdpartconfig(){
		List<String> classes = new ArrayList<String>();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("enabled", 1));
		List<RemoteClass> lists = remoteClassService.listAll(query, null, -1, -1);
		if(lists!= null && lists.size() > 0 ){
			for (RemoteClass remoteClass : lists) {
				classes.add(remoteClass.getPackageName()+"."+remoteClass.getClassName());
			}
		}
		 
		return new ModelAndView("thirdPart/add_config","classes",classes);
	}
	
	@RequestMapping(value="/console/thirdpart/remote/create",method=RequestMethod.GET)
	public ModelAndView createRemoteClass(){
		 List<String> packages = new ArrayList<String>();
		 String packageString = propertiesReader.getString("allowed_packages");
		 if(StringUtil.isNotBlank(packageString)){
			 String[] pkgs = packageString.split(",");
			 for (String pkg : pkgs) {
				packages.add(pkg);
			}
		 }
//		 PropertiesReader. allowed_packages
		 return new ModelAndView("thirdPart/add_remoteClass","packages",packages);
	}
	
	@RequestMapping(value="/console/thirdpart/edit",method=RequestMethod.GET)
	public ModelAndView edit_thirdpartconfig(String id){
		Map<String, Object> result = new HashMap<String, Object>();
		ThirdPartConfigVO config = thirdPartConfigService.getById(id);
		result.put("config", config);
		 List<String> classes = new ArrayList<String>();
			ConditionQuery query = new ConditionQuery();
			query.add(Restrictions.eq("enabled", 1));
			List<RemoteClass> lists = remoteClassService.listAll(query, null, -1, -1);
			if(lists!= null && lists.size() > 0 ){
				for (RemoteClass remoteClass : lists) {
					classes.add(remoteClass.getPackageName()+"."+remoteClass.getClassName());
				}
			}
		 result.put("classes", classes);
		return new ModelAndView("thirdPart/edit_config",result);
	}
	
	@RequestMapping(value="/console/thirdpart/remote/edit",method=RequestMethod.GET)
	public ModelAndView editRemoteClass(String id){
		Map<String, Object> result = new HashMap<String, Object>();
		RemoteClassVO config = remoteClassService.getById(id);
		result.put("remoteClass", config);
		 List<String> classes = new ArrayList<String>();
		 classes.add("nari.mip.backstage.rpc.remoteValid.LocalTestValidator");
		 result.put("classes", classes);
		return new ModelAndView("thirdPart/edit_remoteClass",result);
	}
	
	@RequestMapping(value="/console/thirdpart/info",method=RequestMethod.GET)
	public ModelAndView thirdpartconfig_info(String id){
		Map<String, Object> result = new HashMap<String, Object>();
		ThirdPartConfigVO config = thirdPartConfigService.getById(id);
		result.put("config", config);
		
		int userCount = 0;
		if( StringUtil.isNotBlank(config.getGroupId())){
			List<UserVO> users = groupService.getById(config.getGroupId()).getUsers();
			if(users!=null ){
				userCount = users.size();
			}
		}
		result.put("userCount",userCount);
		return new ModelAndView("thirdPart/config_info",result);
	}
	
	@RequestMapping(value="/console/thirdpart/accessManage",method=RequestMethod.GET)
	public ModelAndView thirdpartAccessManage(){
		Map<String, Object> result = new HashMap<String, Object>();
		String defaultAccessValidator = propertiesReader.getString("login_validator");
		result.put("validator",defaultAccessValidator);
		return new ModelAndView("thirdPart/choose_Validator",result);
	}
	
}
