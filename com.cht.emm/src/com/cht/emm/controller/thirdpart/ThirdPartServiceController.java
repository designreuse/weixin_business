/**
 * @Title: ThirdPartServiceController.java
 * @Package: nari.mip.backstage.controller.thirdpart
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-23 下午3:03:03
 * @Version: 1.0
 */
package com.cht.emm.controller.thirdpart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.controller.BaseController;
import com.cht.emm.model.Group;
import com.cht.emm.model.RemoteClass;
import com.cht.emm.model.ThirdPartConfig;
import com.cht.emm.service.GroupService;
import com.cht.emm.service.RemoteClassService;
import com.cht.emm.service.ThirdPartConfigService;
import com.cht.emm.util.CodeGenerator;
import com.cht.emm.util.ColumnProperty;
import com.cht.emm.util.KeyValue;
import com.cht.emm.util.PageInfo;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.util.Response;
import com.cht.emm.util.StringUtil;
import com.cht.emm.util.TimestampUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.db19.ParameterUtil;
import com.cht.emm.util.objectcopier.RemoteClassCopier;
import com.cht.emm.vo.RemoteClassVO;
import com.cht.emm.vo.ThirdPartConfigVO;



/**
 * @Class: ThirdPartServiceController
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
@Controller
public class ThirdPartServiceController extends BaseController {

	@Resource(name = "thirdPartConfigService")
	private ThirdPartConfigService thirdPartConfigService;
	
	@Resource(name = "groupService")
	GroupService groupService;
	
	@Resource(name="remoteClassService")
	private RemoteClassService remoteClassService;
	
	@Resource(name="propertiesReader")
	private PropertiesReader propertiesReader;
	
	@Resource(name="codeGenerator")
	CodeGenerator codeGenerator;
	
	@RequestMapping(value = "/rest/thirdpart/checkNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validNameExist(String thirdPartName) {
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		result = thirdPartConfigService.checkThirdPartName(thirdPartName);
		map.put("valid", result);
		return map;
	}

	@RequestMapping(value = "/rest/thirdpart/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(String name, String remoteUrl,
			String className, String other, String groupName) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		ThirdPartConfig config = new ThirdPartConfig();
		config.setName(name);
		config.setId(UUIDGen.getUUID());
		config.setClassName(className);
		config.setOthers(other);
		config.setRemoteUrl(remoteUrl);
		Group group = new Group();
		group.setId(UUIDGen.getUUID());
		group.setGroupName(groupName);
		group.setGroupType(Group.GROUP_TYPE.DEP.getType());
		group.setThirdPartType(1);
		group.setParentGroup(groupService.getThirdpartTopGroup());
		config.setGroup(group);

		ThirdPartConfig model = thirdPartConfigService.save(config);
		if (model != null) {
			result = true;
		}
		map.put("successful", result);
		return map;
	}

	@RequestMapping(value = "/rest/thirdpart/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(String id, String remoteUrl,
			String className, String other) {
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		ThirdPartConfig thirdPartConfig = thirdPartConfigService.get(id);
		thirdPartConfig.setClassName(className);
//		thirdPartConfig.setName(name);
		thirdPartConfig.setRemoteUrl(remoteUrl);
		thirdPartConfig.setOthers(other);
		//Group group = thirdPartConfig.getGroup();
//		group.setGroupName(groupName);
		//group.update();
		thirdPartConfigService.update(thirdPartConfig);
//		thirdPartConfig.update();
		result = true;
		map.put("successful", result);
		return map;
	}

	@RequestMapping(value = "/rest/thirdpart/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pages(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		List<Criterion> criterions = new ArrayList<Criterion>();
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		String conditionQuery = "";

		/**
		 * 查询信息
		 */
		if (search != null && !"".equals(search)) {
			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					criterions.add(Restrictions.like(
							columnProperty.getColName(), search,
							MatchMode.ANYWHERE));
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}

					condition.append(columnProperty.getColName() + " like '%"
							+ search + "%' ");
				}
			}
		}
		if (!StringUtil.isNullOrBlank(condition.toString())) {
			conditionQuery = " where ( " + condition.toString() + ") ";
		}

		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		// StringBuilder orderBy = new StringBuilder();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (!"".equals(orderBy.toString())) {
				orderBy.append(",");
			}
			String colName = columns.get(keyValue.getKey()).getColName();
			if ("name".equals(colName)) {
				orderBy.append(" name " + keyValue.getValue().toLowerCase());
			}

		}
		String orderList = "";
		if (!"".equals(orderBy.toString())) {
			orderList = " order by " + orderBy.toString();
		}

		int countFilter = thirdPartConfigService.countAll(conditionQuery);
		Page<ThirdPartConfigVO> users = thirdPartConfigService.queryForPage(
				countFilter, conditionQuery, orderList, pageInfo.getStart()
						/ pageInfo.getLength() + 1, pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countFilter);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", users.getItems());
		return resultMap;
	}

	/**
	 * @Name: list
	 * @Decription: 查询可用的第三方接入列表
	 * @Time: 2015-3-24 上午11:23:42
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/rest/thirdpart/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list() {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 查询所有可用的ThirdPartConfig
		 */
	
		List<ThirdPartConfigVO> vos = thirdPartConfigService.getAllList();
		map.put("list", vos);
		map.put("successful", true);
		return map;
	}

	@RequestMapping(value = "/rest/thirdpart/checkGroupNameExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validGroupNameExist(String id,String groupName) {
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		Group pGroup = groupService.getThirdpartTopGroup();
		result = groupService.checkSubName(id, pGroup.getId(), groupName);
		map.put("valid", result);
		return map;
	}
	
	
	@RequestMapping(value="/rest/thirdpart/remote/checkClassNameExists",method = RequestMethod.POST)
	@ResponseBody
	public Response checkRemoteClassName(String id,String name){
		Response response = new Response();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("className", name));
		if(StringUtil.isNullOrBlank(id)){
			query.add(Restrictions.ne("id", id));
		}
		List<RemoteClass> lists = remoteClassService.listAll(query, null, -1, -1);
		if(lists== null || lists.size() == 0 ){
			response.setSuccessful(true);
		}
		return response;
	}
	
	@RequestMapping(value="/rest/thirdpart/remote/list",method = RequestMethod.POST)
	@ResponseBody
	public Response getRemoteClassList(){
		Response response = new Response();
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("enabled", 1));
		List<RemoteClass> lists = remoteClassService.listAll(query, null, -1, -1);
		if(lists!= null && lists.size() > 0 ){
			 List<RemoteClassVO> results = RemoteClassCopier.copy(lists);
			 response.setResultValue(results);
			 response.setSuccessful(true);
		}
		return response;
	}
	
	@RequestMapping(value = "/rest/thirdpart/remote/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> classPages(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		List<Criterion> criterions = new ArrayList<Criterion>();
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		String conditionQuery = "";

		/**
		 * 查询信息
		 */
		if (search != null && !"".equals(search)) {
			for (ColumnProperty columnProperty : columns) {
				if (columnProperty.isSearchable()) {
					criterions.add(Restrictions.like(
							columnProperty.getColName(), search,
							MatchMode.ANYWHERE));
					if (!"".equals(condition.toString())) {
						condition.append(" or ");
					}

					condition.append(columnProperty.getColName() + " like '%"
							+ search + "%' ");
				}
			}
		}
		if (!StringUtil.isNullOrBlank(condition.toString())) {
			conditionQuery = " where ( " + condition.toString() + ") ";
		}

		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		// StringBuilder orderBy = new StringBuilder();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			String colName = columns.get(keyValue.getKey()).getColName();
			if(!StringUtil.isNullOrBlank(colName)){
				if (!"".equals(orderBy.toString())) {
					orderBy.append(",");
				}
				orderBy.append(colName+ " " +keyValue.getValue().toLowerCase());
			}
			
		}
		String orderList = "";
		if (!"".equals(orderBy.toString())) {
			orderList = " order by " + orderBy.toString();
		}

		int countFilter = remoteClassService.countAll(conditionQuery);
		Page<RemoteClassVO> classes = remoteClassService.queryForPage(
				countFilter, conditionQuery, orderList, pageInfo.getStart()
						/ pageInfo.getLength() + 1, pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countFilter);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", classes.getItems());
		return resultMap;
	}
	@RequestMapping(value = "/rest/thirdpart/remote/template", method = RequestMethod.POST)
	@ResponseBody
	public Response loadTemplate(String className,String packageName,HttpServletRequest request){
		Response response = new Response();
		String temp = propertiesReader.getString("valid_temp_file");
		String template = request.getSession().getServletContext()
                .getRealPath(temp);
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader insReader = new InputStreamReader(  
                    new FileInputStream(template), "utf-8");  

            BufferedReader bufReader = new BufferedReader(insReader);  

            String line = new String();  
            while ((line = bufReader.readLine()) != null) {  
//                System.out.println(line);
            	sb.append(line+"\n");
            }  
            bufReader.close();  
            insReader.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(sb.length()>0){
			String  content = sb.toString();
			response.setResultValue(content.replaceAll("\\$\\{fileName\\}", className).replaceAll("\\$\\{\\s*packageName\\s*\\}", packageName).replaceAll("\\$\\{\\s*date\\s*\\}", TimestampUtil.toString(new Timestamp(new Date().getTime()))));
			response.setSuccessful(true);
		}
		return response;
	}
	@RequestMapping(value = "/rest/thirdpart/remote/complie", method = RequestMethod.POST)
	@ResponseBody
	public Response compile(String className,String packageName,String content,HttpServletRequest request){
		Response response = new Response();
		BufferedReader br = new BufferedReader(new StringReader(content));
		String filePath = this.getClass().getClassLoader().getResource("/").getPath()+packageName.replaceAll("\\.", "\\"+File.separator)+"\\"+File.separator;
		String exceptions = codeGenerator.fileWriter(br, filePath+className+".java");
		if(StringUtil.isNullOrBlank(exceptions)){
			exceptions = codeGenerator.javac(filePath+className+".java");
			if(StringUtil.isNullOrBlank(exceptions)){
				response.setSuccessful(true);
			}else{
				response.setResultMessage(exceptions);
			}
		}else {
			response.setResultMessage(exceptions);
		}
		return response;
	}
	
	@RequestMapping(value = "/rest/thirdpart/remote/save", method = RequestMethod.POST)
	@ResponseBody
	public Response save(String className,String packageName,String content,String classDesc,Integer enabled){
		Response response = new Response();
		RemoteClass remoteClass = new RemoteClass();
		remoteClass.setClassName(className);
		remoteClass.setDesc(classDesc);
		remoteClass.setPackageName(packageName);
		remoteClass.setContent(content);
		remoteClass.setEnabled(enabled==null?0:enabled);
		remoteClass.setId(UUIDGen.getUUID());
		remoteClassService.save(remoteClass);
		response.setSuccessful(true);
		return response;
	}
	
	@RequestMapping(value = "/rest/thirdpart/remote/update", method = RequestMethod.POST)
	@ResponseBody
	public Response update(String id,String content,String classDesc,Integer enabled){
		Response response = new Response();
		RemoteClass remoteClass = remoteClassService.get(id);
		remoteClass.setDesc(classDesc);
		remoteClass.setContent(content);
		remoteClass.setEnabled(enabled==null?0:enabled);
		 
		remoteClassService.update(remoteClass);
		response.setSuccessful(true);
		return response;
	}
	@RequestMapping(value = "/rest/thirdpart/remote/get", method = RequestMethod.POST)
	@ResponseBody
	public Response get(String id){
		Response response = new Response();
		RemoteClassVO remoteClass = remoteClassService.getById(id);
		response.setSuccessful(true);
		response.setResultValue(remoteClass);
		return response;
	}
	
	
	@RequestMapping(value = "/rest/thirdpart/access/save", method = RequestMethod.POST)
	@ResponseBody
	public Response accessSave(String id){
		Response response = new Response();
		propertiesReader.setValue("login_validator", id);
		response.setSuccessful(true);
		return response;
	}
}
