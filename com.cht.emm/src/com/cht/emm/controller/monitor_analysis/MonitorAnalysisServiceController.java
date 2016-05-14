package com.cht.emm.controller.monitor_analysis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.controller.BaseController;
import com.cht.emm.service.ApplicationDeployService;
import com.cht.emm.service.DeviceService;
import com.cht.emm.util.Response;
import com.cht.emm.vo.CounterVO;

@Controller
public class MonitorAnalysisServiceController extends BaseController {
	@Resource
	ApplicationDeployService applicationDeployService;

	@Resource
	DeviceService deviceService;

	@RequestMapping(value = "/rest/monitor_analysis/app/tops", method = RequestMethod.GET)
	@ResponseBody
	public Response getTopApp() {
		Response response = new Response();
		response.setSuccessful(true);
		String hql = "select   app.id,app.name,count(app.id) as total  from ApplicationDeploy  ";
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("aap.id"));

		response.setResultValue(applicationDeployService.getCounterVOs(hql,
				null, " order by total ", " group by app.id,app.name ", 10));
		return response;
	}

	@RequestMapping(value = "/rest/monitor_analysis/device/brand", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceStatusCount() {
		Response response = new Response();
		String hql = "select  name,name,count(*) as total  from Device ";

		response.setSuccessful(true);
		List<CounterVO> devices = applicationDeployService.getCounterVOs(hql,
				null, " order by total ", "group by name", 4);
		int count = deviceService.countAll();
		int used = 0;
		for (CounterVO counterVO : devices) {
			used += counterVO.getCount();
		}
		CounterVO other = new CounterVO("", "其他", count - used);
		devices.add(other);
		response.setResultValue(devices);
		return response;

	}

	@RequestMapping(value = "/rest/monitor_analysis/device/ops", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceTypeCount() {
		Response response = new Response();

		String hql = "select CONCAT(os,''),CONCAT( os,''), count(*) as total from Device  ";
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("os"));
		List<CounterVO> counters = applicationDeployService.getCounterVOs(hql,
				null, " order by total ", " group by os ", 5);
		for (CounterVO count : counters) {
			if ("0".equals(count.getId().trim())) {
				count.setName("Android");
			}
			if ("1".equals(count.getId().trim())) {
				count.setName("IOS");
			}
			if ("2".equals(count.getId().trim())) {
				count.setName("Windows Phone");
			}
		}

		response.setSuccessful(true);
		response.setResultValue(counters);
		return response;

	}

	/**
	 * @Title: getDeviceOnline
	 * @return
	 * @return Response 返回类型
	 * @throws
	 */
	// deviceController.getOnline
	@RequestMapping(value = "/rest/monitor_analysis/device/online", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceOnline(HttpServletRequest request) {
		Response response = new Response();
		int onlineNum = 0;
		try {// TODO 改为openfire模式
		// ClientSession[] sessions = new ClientSession[0];
		// sessions = SessionManager.getInstance().getSessions()
		// .toArray(sessions);
		// for (ClientSession session : sessions) {
		// if(session.getPresence().isAvailable()){
		// onlineNum ++;
		// }
		// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<CounterVO> counters = new ArrayList<CounterVO>();
		int total = deviceService.countAll();
		counters.add(new CounterVO("", "在线", onlineNum));
		counters.add(new CounterVO("", "离线", total - onlineNum));
		response.setSuccessful(true);
		response.setResultValue(counters);
		return response;

	}

	@RequestMapping(value = "/rest/monitor_analysis/app/download", method = RequestMethod.GET)
	@ResponseBody
	public Response getAppDownload() {
		Response response = new Response();
		response.setSuccessful(true);
		response.setResultValue(applicationDeployService
				.getAppDownLoadWithPeriod());
		return response;
	}

}
