package com.cht.emm.controller.info_center;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.controller.BaseController;
import com.cht.emm.service.InfoCenterService;
import com.cht.emm.util.Response;
import com.cht.emm.vo.CounterVO;

@Controller
public class InfoCenterServiceController extends BaseController {
	@Autowired
	@Qualifier("infoCenterService")
	InfoCenterService infoCenterService;

	@RequestMapping(value = "/rest/infocenter/app/tops", method = RequestMethod.GET)
	@ResponseBody
	public Response getTopApp() {
		Response response = new Response();
//		List<AppVO> apps = ConstantValue.getApps();
//		int top = 10000;
//		for (AppVO app : apps) {
//			top = top - (int) (Math.random() * 1000);
//			app.setDownloadCount(top);
//		}
		response.setSuccessful(true);
		response.setResultValue(infoCenterService.getCounters(InfoCenterService.TYPE_APPTOP, null));
		return response;
	}

	@RequestMapping(value = "/rest/infocenter/device/brand", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceStatusCount() {
		Response response = new Response();
		List<CounterVO> counters = infoCenterService.getCounters(
				InfoCenterService.TYPE_DEVICEBRAND, null);

		response.setSuccessful(true);
		response.setResultValue(counters);
		return response;

	}

	@RequestMapping(value = "/rest/infocenter/device/ops", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceTypeCount() {
		Response response = new Response();
		List<CounterVO> counters = infoCenterService.getCounters(
				InfoCenterService.TYPE_DEVICEOS, null);

		response.setSuccessful(true);
		response.setResultValue(counters);
		return response;

	}

	@RequestMapping(value = "/rest/infocenter/device/count", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceCount() {
		Response response = new Response();
		List<CounterVO> counters = infoCenterService.getCounters(
				InfoCenterService.TYPE_DEVICESTATUS, null);

		response.setSuccessful(true);
		response.setResultValue(counters);
		return response;

	}

	@RequestMapping(value = "/rest/infocenter/device/alarm", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceAlarm() {
		Response response = new Response();
		List<CounterVO> counters = infoCenterService.getCounters(
				InfoCenterService.TYPE_DEVICEALARM, null);

		response.setSuccessful(true);
		response.setResultValue(counters);
		return response;

	}

	@RequestMapping(value = "/rest/infocenter/device/online", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceOnline() {
		Response response = new Response();
		List<CounterVO> counters = infoCenterService.getCounters(
				InfoCenterService.TYPE_USERONLIE, null);

		response.setSuccessful(true);
		response.setResultValue(counters);
		return response;

	}
}
