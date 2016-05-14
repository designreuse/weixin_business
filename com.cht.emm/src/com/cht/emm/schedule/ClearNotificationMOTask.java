package com.cht.emm.schedule;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ClearNotificationMOTask {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void run() {
		System.out
				.println(new Date(System.currentTimeMillis()) + "消息记录清理任务开始：");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			int count = session.createQuery(
					"delete from NotificationMO where status>=2")
					.executeUpdate();
			System.out.println("删除" + count + "条消息记录");
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
		System.out
				.println(new Date(System.currentTimeMillis()) + "消息记录清理任务结束！");
	}

}
