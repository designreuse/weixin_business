package com.cht.emm.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.cht.emm.vo.CounterVO;

@Service("infoCenterService")
public class InfoCenterService {
	public static String TYPE_APPTOP = "appTop";

	public static String TYPE_DEVICEBRAND = "deviceBrand";

	public static String TYPE_DEVICESTATUS = "deviceStatus";

	public static String TYPE_DEVICEALARM = "deviceAlarm";

	public static String TYPE_DEVICEOS = "deviceOs";

	public static String TYPE_USERONLIE = "userOnline";

	public static String TYPE_APPCOUNT = "appCount";

	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<CounterVO> getCounters(String type, String args[]) {
		List<CounterVO> counters = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			counters = null;
			if (TYPE_APPTOP.equals(type)) {

				List<Object[]> result = session
						.createSQLQuery(
								"SELECT CONCAT(T1.APP_ID,''),T2.APP_NAME,CNT,"
										+ " RN FROM (SELECT T.APP_ID, COUNT(T.APP_ID) CNT,"
										+ "ROW_NUMBER() OVER(ORDER BY COUNT(T.APP_ID) DESC) "
										+ "RN FROM NAP_APP_STATISTICS T WHERE T.OPT_TYPE = '1'"
										+ " GROUP BY T.APP_ID) T1 LEFT JOIN APP_APPLICATIONS T2"
										+ " ON T1.APP_ID = T2.APP_ID WHERE RN <= 10")
						.list();
				counters = extractCounters(result);
			}
			if (TYPE_DEVICEBRAND.equals(type)) {
				List<Object[]> result = session
						.createSQLQuery(
								"SELECT * from (SELECT T.BRAND as ID ,T.BRAND,COUNT(1) "
										+ "as CNT, ROW_NUMBER() OVER( ORDER BY COUNT(1) DESC) RN FROM NAP_DEVICE_AUTH T GROUP "
										+ "BY T.BRAND ) T1 where  T1.RN <4")
						.list();
				counters = extractCounters(result);

				Integer total = countDevice();
				for (CounterVO counterVO : counters) {
					total = total - counterVO.getCount();
				}
				CounterVO counterVO = new CounterVO();
				counterVO.setId("other");
				counterVO.setCount(total);
				counterVO.setName("其他");
				counters.add(counterVO);
			}

			if (TYPE_DEVICESTATUS.equals(type)) {
				List<Object[]> result = session
						.createSQLQuery(
								"SELECT CONCAT(DEVICE_COMPLIANCE,''),DECODE(DEVICE_COMPLIANCE,'1','合规','不合规') as NAME,count(DEVICE_CODE)"
										+ " FROM GW_SYS_DEVICE_SECURITY GROUP BY DEVICE_COMPLIANCE")
						.list();
				counters = extractCounters(result);
			}

			if (TYPE_DEVICEALARM.equals(type)) {
				counters = new ArrayList<CounterVO>();
				Date date = new Date();
				Timestamp timestamp = new Timestamp(
						(date.getTime() / 1000 - 2592000) * 1000);
				BigDecimal totalOb = (BigDecimal) session
						.createSQLQuery(
								"SELECT  count( distinct T.DEVICE_CODE)"
										+ " FROM APP_CLIENT_EXCEPTION_LOG T where CREATE_TIME > ?")
						.setTimestamp(0, timestamp).uniqueResult();
				Integer alarmed = totalOb.intValue();
				Integer total = countDevice();
				CounterVO alarmedDevice = new CounterVO();
				alarmedDevice.setCount(alarmed);
				alarmedDevice.setName("已报警设备");
				alarmedDevice.setId("alarm");
				counters.add(alarmedDevice);
				CounterVO normalDevice = new CounterVO();
				normalDevice.setCount(total - alarmed);
				normalDevice.setName("未报警设备");
				normalDevice.setId("normal");
				counters.add(normalDevice);
			}

			if (TYPE_DEVICEOS.equals(type)) {
				List<Object[]> result = session
						.createSQLQuery(
								"SELECT T1.CODE,T1.NAME, COUNT(T.OS_TYPE) "
										+ " FROM NAP_DEVICE_AUTH T "
										+ " LEFT JOIN T_SYS_COM_CODE T1 ON T.OS_TYPE = T1.CODE "
										+ " WHERE T1.PARENT_ID = "
										+ " (SELECT T2.ID FROM T_SYS_COM_CODE T2 WHERE T2.CODE = 'XTLX')"
										+ " GROUP BY T1.CODE,T.OS_TYPE, T1.NAME")
						.list();
				counters = extractCounters(result);
			}

			if (TYPE_USERONLIE.equals(type)) {
				List<Object[]> result = session
						.createSQLQuery(
								"SELECT CONCAT(T.STATUS,''),DECODE(T.STATUS,'1','在线','离线') as NAME ,COUNT(*) "
										+ "  FROM NAP_USER_ONLINE_CACHE T GROUP BY T.STATUS")
						.list();
				counters = extractCounters(result);

			}

			if (TYPE_APPCOUNT.equals(type)) {
				long starttime = getStartTime();
				counters = new ArrayList<CounterVO>();
				CounterVO day = new CounterVO();
				day.setCount(countApp(1, starttime));
				day.setName("当天");
				day.setId("day");
				counters.add(day);
				CounterVO week = new CounterVO();
				week.setCount(countApp(2, starttime));
				week.setName("当天");
				week.setId("day");
				counters.add(week);
				CounterVO month = new CounterVO();
				month.setCount(countApp(1, starttime));
				month.setName("当天");
				month.setId("day");
				counters.add(month);

			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}

		return counters;
	}

	private List<CounterVO> extractCounters(List<Object[]> objects) {
		List<CounterVO> counters = new ArrayList<CounterVO>();
		if (objects != null && objects.size() > 0) {
			for (Object[] object : objects) {
				CounterVO counterVO = new CounterVO();
				counterVO.setId((String) object[0]);
				String name = (String) object[1];
				counterVO.setName(name == null || "".equals(name) ? "未知"
						: name);
				counterVO.setCount(((BigDecimal) object[2]).intValue());
				counters.add(counterVO);
			}
		}
		return counters;

	}

	private Integer countDevice() {
		Integer total = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			BigDecimal result = (BigDecimal) session
					.createSQLQuery("SELECT COUNT(1) FROM NAP_DEVICE_AUTH T")
					.list().get(0);
			total = result.intValue();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return total;
	}

	public Integer countApp(int type, long time) {
		Timestamp starTimestamp = null;

		switch (type) {
		case 1:
			starTimestamp = new Timestamp(time);
			break;

		case 2:
			starTimestamp = new Timestamp(time - 604800000);
			break;
		case 3:
			starTimestamp = new Timestamp((time / 1000 - 2592000) * 1000);
			break;
		default:
			break;
		}
		Integer total = 0;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			BigDecimal totalOb = (BigDecimal) session
					.createSQLQuery(
							"SELECT count(*) FROM APP_APPLICATIONS T"
									+ " where T.STATE =1 AND UPDATE_TIME >= ?")
					.setTimestamp(0, starTimestamp).uniqueResult();
			total = totalOb.intValue();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}
		return total;
	}

	public Long getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}
}
