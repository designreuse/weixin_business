package com.cht.emm.controller.info_center;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cht.emm.controller.BaseController;
import com.cht.emm.service.InfoCenterService;
import com.cht.emm.vo.ApplicationVO2;
import com.cht.emm.vo.DeviceLog;
import com.cht.emm.vo.DeviceVO1;
import com.cht.emm.vo.DeviceVO2;

@Controller
public class InfoCenterPageController extends BaseController {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	@Autowired
	@Qualifier("infoCenterService")
	private InfoCenterService infoCenterService;

	@RequestMapping(value = "/console/info_center", method = RequestMethod.GET)
	public ModelAndView info_center() {
		Map<String, Object> result = new HashMap<String, Object>();
		long startTime = infoCenterService.getStartTime();

		result.put("month", infoCenterService.countApp(3, startTime));
		result.put("week", infoCenterService.countApp(2, startTime));
		result.put("day", infoCenterService.countApp(1, startTime));
		return new ModelAndView("info-center/info_center", result);
	}

	@RequestMapping(value = "/console/device_monitor", method = RequestMethod.GET)
	public ModelAndView device_monitor(String id) {
		DeviceVO2 vo = new DeviceVO2();
		vo.setName(id);
		Session session = sessionFactory.openSession();
		ScrollableResults results = session
				.createSQLQuery(
						"select T.CREATE_TIME,T.LOG_PLACE from (SELECT * FROM NAP_USER_OPERATION_LOG WHERE device_id='"
								+ id
								+ "' ORDER BY CREATE_TIME DESC) T where rownum=1")
				.scroll();
		while (results.next()) {
			Object[] obj = results.get();
			vo.setStart_addr((String) obj[1]);
			vo.setStart_time(((Date) obj[0]).toString());
			break;
		}
		results = session
				.createSQLQuery(
						"SELECT DEVICE_COMPLIANCE,DEVICE_PASSWORD_MODIFY,DEVICE_LOC FROM GW_SYS_DEVICE_SECURITY where DEVICE_CODE='"
								+ id + "'").scroll();
		while (results.next()) {
			Object[] obj = results.get();
			vo.setAddr((String) obj[2]);
			vo.setCompliance(((BigDecimal) obj[0]).intValue());
			vo.setPassword(((BigDecimal) obj[1]).intValue());
			break;
		}
		results = session.createSQLQuery(
				"SELECT NETWORK_TYPE FROM GW_SYS_DEVICE_NETWORK_MONITOR WHERE DEVICE_CODE = '"
						+ id + "'").scroll();
		while (results.next()) {
			Object[] obj = results.get();
			vo.setNet(((BigDecimal) obj[0]).intValue());
			break;
		}

		List<ApplicationVO2> apps = new ArrayList<ApplicationVO2>();
		results = session
				.createSQLQuery(
						"SELECT T1.OPT_TYPE, T2.APP_NAME FROM NAP_APP_STATISTICS T1 LEFT JOIN APP_APPLICATIONS T2 ON T1.APP_ID = T2.APP_ID WHERE T1.LOGIN_DEVICE='"
								+ id + "' ORDER BY T2.APP_NAME ").scroll();
		while (results.next()) {
			Object[] obj = results.get();
			ApplicationVO2 app = new ApplicationVO2();
			app.setName((String) obj[1]);
			app.setStatus((String) obj[0]);
			apps.add(app);
		}
		vo.setApps(apps);

		List<DeviceLog> logs = new ArrayList<DeviceLog>();
		results = session
				.createSQLQuery(
						"SELECT ACCESS_USER,ACCESS_TIME,ACCESS_SYSTEM,ACCESS_DEVICE FROM GW_SYS_DEVICE_DATA_AUDITS where ACCESS_DEVICE='"
								+ id + "' order by access_time desc").scroll();
		while (results.next()) {
			Object[] obj = results.get();
			DeviceLog log = new DeviceLog();
			log.setUsername((String) obj[0]);
			log.setTime((Timestamp) obj[1]);
			log.setSystem((String) obj[2]);
			logs.add(log);
		}
		vo.setLogs(logs);
		session.close();
		return new ModelAndView("info-center/device_monitor", "device", vo);
	}

	@RequestMapping(value = "/console/devices_monitor", method = RequestMethod.GET)
	public ModelAndView devices_monitor() {

		Map<String, Object> result = new HashMap<String, Object>();
		Session session = sessionFactory.openSession();
		ScrollableResults results = session
				.createSQLQuery(
						" SELECT T.IMEI, T.MAC_ADDRESS, T.BRAND, T.OS_VERSION, T.OS_TYPE, t1.NAME OS_NAME, T.DEVICE_ID FROM NAP_DEVICE_AUTH T LEFT JOIN T_SYS_COM_CODE T1 ON T.OS_TYPE = T1.CODE WHERE T1.PARENT_ID = (SELECT T2.ID FROM T_SYS_COM_CODE T2 WHERE T2.CODE = 'XTLX')")
				.scroll();
		List<DeviceVO1> deviceVo = new ArrayList<DeviceVO1>();
		while (results.next()) {
			Object[] obj = results.get();
			DeviceVO1 vo = new DeviceVO1();
			vo.setImei((String) obj[0]);
			vo.setMac((String) obj[1]);
			vo.setBrand((String) obj[2]);
			vo.setOs_version((String) obj[3]);
			vo.setOs_type((String) obj[4]);
			vo.setOs_name((String) obj[5]);
			vo.setDevice_id((String) obj[6]);
			deviceVo.add(vo);
		}
		result.put("devices", deviceVo);
		session.close();
		return new ModelAndView("info-center/devices_monitor", result);
	}

}
