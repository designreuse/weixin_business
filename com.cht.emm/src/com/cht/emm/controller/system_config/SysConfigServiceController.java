/**
 * @Title: SysConfigServiceController.java
 * @Package: nari.mip.backstage.controller.system_config
 * @Description: 系统配置项 转发器
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-25 下午4:17:54
 * @Version: 1.0
 */
package com.cht.emm.controller.system_config;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.controller.BaseController;
import com.cht.emm.model.PlatFormAgent;
import com.cht.emm.service.PlatFormAgentService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.FileUtil;
import com.cht.emm.util.PageInfo;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.util.Response;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.db19.ParameterUtil;
import com.cht.emm.vo.FileSetVO;
import com.cht.emm.vo.Parameter;
import com.cht.emm.vo.PlatFormAgentVO;

import nariis.pi3000.framework.utility.StringUtil;

/**
 * @Class: SysConfigServiceController
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
@Controller
public class SysConfigServiceController extends BaseController {

	public final static String KEY_ADDEDPROPERTIES	= "addedProperties";
	public final static String KEY_REMOVEDPROPERTIES="";
	
	
	@Resource
	PlatFormAgentService platFormAgentService;
	@Resource
	UserService userService;
	@Resource
	PropertiesReader propertiesReader;

	final String apkRoot = "resources/apk";
	final String apkName = "nari.mip.console.apk";

	/**
	 * @Name: getPlatAgent
	 * @Decription: 显示系统软件的平台软件情况
	 * @Time: 2015-2-25 下午4:22:55
	 * @return Response
	 */
	@RequestMapping(value = "/rest/sysConfig/platform_agent/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPlatfromAgent(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrderBy orderBy = new OrderBy();
		orderBy.add(Order.asc("os"));
		orderBy.add(Order.desc("versionCode"));
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		int countall = platFormAgentService.countAll();
		List<PlatFormAgentVO> allAgentVOs = platFormAgentService.queryForPage(
				countall, null, orderBy, pageInfo.getStart(),
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countall);
		resultMap.put("iTotalDisplayRecords", countall);
		resultMap.put("aaData", allAgentVOs);
		return resultMap;
	}

	/**
	 * @Name: getPlatAgentHistory
	 * @Decription: 根据id获得更新的历史记录
	 * @Time: 2015-2-25 下午4:29:44
	 * @param id
	 * @return Response
	 */
	@RequestMapping(value = "/rest/sysConfig/platform_agent/history", method = RequestMethod.POST)
	@ResponseBody
	public Response getPlatfromAgentHistory(String id) {
		Response response = new Response();

		return response;
	}

	/**
	 * @Name: updatePlatAgent
	 * @Decription: 更新平台客户端
	 * @Time: 2015-2-25 下午4:32:20
	 * @param id
	 * @param version
	 * @param versionCode
	 * @return Response
	 */
	@RequestMapping(value = "/rest/sysConfig/platform_agent/update", method = RequestMethod.POST)
	@ResponseBody
	public Response updatePlatAgent(HttpServletRequest request) {
		Response response = new Response();
		String webPath = request.getContextPath();
		System.out.println(webPath);
		String dirPath = request.getSession().getServletContext()
				.getRealPath("/");
		System.out.println(dirPath);

		String publisher = request.getParameter("publisher");
		String path = request.getParameter("path");
		String versionName = request.getParameter("versionName");
		Integer versionCode = Integer.parseInt(request
				.getParameter("versionCode"));
		String icon = request.getParameter("icon");
		String pkgName = request.getParameter("pkgName");
		Integer os = Integer.parseInt(request.getParameter("os"));
		// String url = request.getParameter("url");

		Integer latestVersionCode = platFormAgentService.getMaxVersion(os)
				.getVersionCode();
		if (versionCode > latestVersionCode) {
			PlatFormAgent agent = new PlatFormAgent();
			agent.setId(UUIDGen.getUUID());
			agent.setCreateTime(new Timestamp(new Date().getTime()));
			agent.setCreator(userService.get(publisher));
			agent.setIconUrl(StringUtil.substringAfterLast(icon, webPath));

			agent.setPackageName(pkgName);
			agent.setVersionCode(versionCode);
			agent.setVersionName(versionName);

			String vfileString = apkRoot + File.separator + "v" + versionName
					+ File.separator + apkName;
			agent.setUrl(vfileString.replace("\\", "/"));
			agent.setPath(replaceFileSeparator(dirPath + vfileString));
			agent.setOs(os);
			agent.save();
			// copy 到最新版本
			File targetFile = new File(replaceFileSeparator(request
					.getSession().getServletContext().getRealPath(apkRoot)
					+ File.separator + apkName));
			FileUtil.copyFile(new File(path), targetFile);
			// copy到对应版本
			File vtargetFile = new File(replaceFileSeparator(dirPath
					+ File.separator + vfileString));
			String vdir = replaceFileSeparator(dirPath + apkRoot
					+ File.separator + "v" + versionName + File.separator);
			File vtargetDir = new File(vdir);
			if (!vtargetDir.exists()) {
				vtargetDir.mkdirs();
			}
			FileUtil.copyFile(new File(path), vtargetFile);
			response.setSuccessful(true);
		} else {
			response.setResultMessage("版本太旧，请使用更新的版本");
		}
		return response;
	}

	@RequestMapping(value = "/rest/sysConfig/properties/save",method = RequestMethod.POST)
	@ResponseBody
	public Response updateSysProperties(HttpServletRequest request) {
		Response response = new Response();
		String[] items = propertiesReader.getString("cf_items").split(",");
		if (items.length > 0) {
			for (String item : items) {
				String value = request.getParameter(item);
				propertiesReader.setValue(item, value);
			}
			response.setSuccessful(true);
		}

		return response;
	}

	@RequestMapping(value = "/rest/sysConfig/properties/unconfiged",method = RequestMethod.POST)
	@ResponseBody
	public Response loadUnconfigedProperties(){
		Response response = new Response();
		List<String> allNames = propertiesReader.getPropertyNames();
		List<String> unconfiged = new ArrayList<String>();
		//需要配置的属性列表存储在 cf_items下
		String configed = propertiesReader.getString("cf_items");
		
		//找到没有进行配置的选项，注意以cf_items 开头的需要进行过滤,
		//这些选项需要通过程序控制纯粹的手动操作会出现问题
		for (String name : allNames) {
			if(configed.indexOf(name) == -1 && !name.startsWith("cf_items")){
				unconfiged.add(name);
			}
		}
		response.setSuccessful(true);
		response.setResultValue(unconfiged);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rest/sysConfig/properties/saveconfiged",method = RequestMethod.POST)
	@ResponseBody
	public Response saveconfigedProperties(HttpServletRequest request){
		HttpSession session = request.getSession();
		List<String> added = (List<String>) session.getAttribute("addedProperties");
		List<String> removed = (List<String>) session.getAttribute("removedProperties");
		Response response = new Response();
		if(added==null){
			added = new ArrayList<String>();
		}
	
		String[] configed = propertiesReader.getString("cf_items").split(",");
		for (int i = 0; i < configed.length; i++) {
			added.add(configed[i]);
		}
		
		if(removed!=null){
			added.removeAll(removed);
		}
		
		//根据最终的结果获得所有结果
		
		StringBuilder configedProperties = new StringBuilder();
		StringBuilder configedPropertiesNames = new StringBuilder();
		response.setSuccessful(true);
		for (String proper : added) {
			String value = request.getParameter(proper);
			if(StringUtil.isNotBlank(value)){
				if(StringUtil.isNotBlank(configedProperties.toString())){
					configedProperties.append(",");
					configedPropertiesNames.append(",");
					
					
				}else {
					
				}
				
				configedProperties.append(proper);
				configedPropertiesNames.append(value);
			}
		}
		if(response.isSuccessful()){
			propertiesReader.setValue("cf_items", configedProperties.toString());
			propertiesReader.setValue("cf_items_names", configedPropertiesNames.toString());
			resetSession(request);
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rest/sysConfig/properties/changeop",method = RequestMethod.POST)
	@ResponseBody
	public Response changeConfigedProperties(String key,String op,HttpServletRequest request){
		Response response = new Response();
		HttpSession session = request.getSession();
		response.setSuccessful(true);
		if("add".equals(op)){
			List<String> dels = (List<String>) session.getAttribute(KEY_REMOVEDPROPERTIES);
			if(dels!=null){
				if(dels.contains(key)){
					dels.remove(key);
				}
				
			}
			List<String> adds = (List<String>) session.getAttribute(KEY_ADDEDPROPERTIES);
			if(adds!=null  ){
				if(!adds.contains(key)){
					adds.add(key);
				}
				
			}else {
				adds = new ArrayList<String>();
				session.setAttribute(KEY_ADDEDPROPERTIES, adds);
				adds.add(key);
			}
 		}else if("del".equals(op)){
 			List<String> adds = (List<String>) session.getAttribute(KEY_ADDEDPROPERTIES);
			if(adds!=null){
				if(adds.contains(key)){
					adds.remove(key);
				}
			}
			List<String> dels = (List<String>) session.getAttribute(KEY_REMOVEDPROPERTIES);
			if(dels!=null  ){
				if(!dels.contains(key)){
					dels.add(key);
				}
				
			}else {
				dels = new ArrayList<String>();
				session.setAttribute(KEY_REMOVEDPROPERTIES, dels);
				dels.add(key);
			}
		}else {
			response.setSuccessful(false);
		}
		return response;
	}
	@RequestMapping(value = "/rest/sysConfig/properties/list",method = RequestMethod.POST)
	@ResponseBody
	public Response getConfigedProperties(){
		Response response = new Response();
		FileSetVO sysParams = new FileSetVO();
		List<Parameter> params = new ArrayList<Parameter>();
		String[] items = propertiesReader.getString("cf_items").split(",");
		String[] itemNames = propertiesReader.getString("cf_items_names").split(",");
		if(items.length >0 ){
			for (int i = 0; i < items.length; i++) {
				Parameter p = new Parameter();
				p.setKey(items[i]);
				p.setTitle(itemNames[i]);
				p.setValue(propertiesReader.getString(items[i]));
				params.add(p);
			}
		}
		sysParams.setParams(params);
		response.setResultValue(params);
		response.setSuccessful(true);
		return response;
	}
	
	
	private String replaceFileSeparator(String dir) {
		if ("/".equals(File.separator)) {
			return dir.replace("\\", "/");
		} else {
			return dir.replace("/", "\\");
		}

	}
	
	
	public static void resetSession(HttpServletRequest request ){
		HttpSession session = request.getSession();
		session.setAttribute(KEY_ADDEDPROPERTIES, null);
		session.setAttribute(KEY_REMOVEDPROPERTIES, null);
	}

}
