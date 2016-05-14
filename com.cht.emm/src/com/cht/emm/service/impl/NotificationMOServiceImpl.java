package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.NotificationMO;
import com.cht.emm.service.NotificationMOService;
import com.cht.emm.util.OpenfireUtil;

/**
 * 应用管理服务
 * 
 * @author luoyupan
 * 
 */
@Service
@Transactional(readOnly = true, timeout = 2, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class NotificationMOServiceImpl extends
		BaseService<NotificationMO, String> implements NotificationMOService {
	// @Resource(name = "notificationMODaoImpl")
	// private NotificationMODaoImpl notificationMODaoImpl;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<NotificationMO> queryNotification(String username) {
		return this.baseDao
				.getSession()
				.createCriteria(NotificationMO.class)
				.add(Restrictions.eq("username", username))
				.add(Restrictions.or(Restrictions.eq("status", "0"),
						Restrictions.eq("status", "1")))
				.addOrder(Order.desc("createTime")).list();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void createNotifications(List<NotificationMO> notificationMOs) {
		for (NotificationMO noti : notificationMOs) {
			this.baseDao.saveOrUpdate(noti);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public NotificationMO queryNotificationByUserName(String userName, String id) {
		List<NotificationMO> list = this.baseDao.getSession()
				.createCriteria(NotificationMO.class)
				.add(Restrictions.eq("username", userName))
				.add(Restrictions.eq("messageId", id))
				.addOrder(Order.desc("createTime")).list();
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Resource(name = "notificationMODaoImpl")
	@Override
	public void setBaseDao(IBaseDao<NotificationMO, String> baseDao) {
		this.baseDao = baseDao;
	}

	public void sendOfflineMsg(String username, String deviceID) {
		try {
//			// 查找该设备是否有离线消息
//			List<NotificationMO> moes = this.baseDao.listAll(
//					" where status<2 and username='" + deviceID + "' ",
//					" order by createTime asc ", 1, 1);
//			if (moes.size() == 0) {
//				return;
//			}
//			NotificationMO mo = moes.get(0);
//			// 调用OPENFIRE服务判断用户是否在线
//			int state = OpenfireUtil.getUserStatus(username);
//			if (state != 1) {
//				return;
//			}
//			// 若用户在线则发送离线消息
//			List<String> receivers = new ArrayList<String>();
//			receivers.add(username);
//			OpenfireUtil.sendMessage(mo.getId(), "sender", receivers, deviceID,
//					mo.getMessage(), mo.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
