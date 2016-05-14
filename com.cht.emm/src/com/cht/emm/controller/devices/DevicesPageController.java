package com.cht.emm.controller.devices;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cht.emm.controller.BaseController;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * 设备管理页面转换控制器
 * 
 * @author luoyupan
 * 
 */
@Controller
public class DevicesPageController extends BaseController {

	/**
	 * 设备列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/devices", method = RequestMethod.GET)
	public String devices() {

		return "devices/devices";
	}

	/**
	 * 设备详情页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/devices_detail", method = RequestMethod.GET)
	public String devices_detail() {

		return "devices/devices_detail";
	}

	/**
	 * 创建设备页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/devices_new", method = RequestMethod.GET)
	public String devices_new() {

		return "devices/devices_new";
	}

	/**
	 * 设备生命周期页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/devices_life", method = RequestMethod.GET)
	public String devices_life() {

		return "devices/devices_life";
	}

	/**
	 * 设备申请驳回或注销说明页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/devices_remark", method = RequestMethod.GET)
	public String devices_remark() {

		return "devices/devices_remark";
	}

	/**
	 * 配置列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/configs", method = RequestMethod.GET)
	public String configs() {

		return "devices/configs";
	}

	/**
	 * 创建配置页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/configs_new", method = RequestMethod.GET)
	public String configs_new() {

		return "devices/configs_new";
	}

	/**
	 * 创建wifi配置页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/configs_wifi", method = RequestMethod.GET)
	public String configs_wifi() {

		return "devices/configs_wifi";
	}

	/**
	 * 创建vpn配置页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/configs_vpn", method = RequestMethod.GET)
	public String configs_vpn() {

		return "devices/configs_vpn";
	}

	/**
	 * 策略列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/strategys", method = RequestMethod.GET)
	public String strategys() {

		return "devices/strategys";
	}

	/**
	 * 创建策略页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/console/strategys_new", method = RequestMethod.GET)
	public String strategys_new() {

		return "devices/strategys_new";
	}
	
	@RequestMapping(value="console/device/msg",method=RequestMethod.GET)
public ModelAndView  sendMessage(String deviceId){
		return new ModelAndView("devices/sendMessage", "deviceId", deviceId);
	}

}
