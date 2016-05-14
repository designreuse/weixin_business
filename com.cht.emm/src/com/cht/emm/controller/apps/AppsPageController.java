package com.cht.emm.controller.apps;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cht.emm.controller.BaseController;

/**
 * 
 * 应用管理页面转换控制器
 * 
 * @author luoyupan
 * 
 */
@Controller
public class AppsPageController extends BaseController {

	/**
	 * 应用列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps", method = RequestMethod.GET)
	public String apps() {
		return "apps/apps";
	}

	/**
	 * 创建应用页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_new", method = RequestMethod.GET)
	public String apps_new() {

		return "apps/apps_new";
	}

	/**
	 * 应用详情页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_detail", method = RequestMethod.GET)
	public String apps_detail() {
		return "apps/apps_detail";
	}

	/**
	 * 应用分类列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_type", method = RequestMethod.GET)
	public String apps_type() {

		return "apps/apps_type";
	}

	/**
	 * 创建应用分类页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_type_new", method = RequestMethod.GET)
	public String apps_type_new() {

		return "apps/apps_type_new";
	}

	/**
	 * 指定应用分类下的应用列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_type_apps", method = RequestMethod.GET)
	public String apps_type_apps() {

		return "apps/apps_type_apps";
	}

	/**
	 * 应用授权页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_auth", method = RequestMethod.GET)
	public String apps_auth() {
		return "apps/apps_auth";
	}

	/**
	 * 应用权限页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_permission", method = RequestMethod.GET)
	public String apps_permission() {
		return "apps/apps_permission";
	}

	/**
	 * web应用详情页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_detail_web", method = RequestMethod.GET)
	public String apps_detail_web() {
		return "apps/apps_detail_web";
	}

	/**
	 * zip应用详情页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/apps_detail_zip", method = RequestMethod.GET)
	public String apps_detail_zip() {
		return "apps/apps_detail_zip";
	}
}
