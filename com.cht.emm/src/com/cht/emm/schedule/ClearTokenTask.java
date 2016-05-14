package com.cht.emm.schedule;

import java.sql.Timestamp;
import java.util.Date;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cht.emm.security.token.TokenChecker;

public class ClearTokenTask {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void run() {
		System.out.println(new Date(System.currentTimeMillis())
				+ "token记录清理任务开始：");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Timestamp time = new Timestamp(System.currentTimeMillis()
					- TokenChecker.TOKEN_INVALIDATE_TIME);
			int count = session
					.createQuery(
							"delete from Token where updateTime<:time and device_id is not null")
					.setTimestamp("time", time).executeUpdate();
			System.out.println("删除" + count + "条token记录");
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		System.out.println(new Date(System.currentTimeMillis())
				+ "token记录清理任务结束！");
	}

}
