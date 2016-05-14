package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.NotificationMO;


public interface NotificationMOService extends
		IBaseService<NotificationMO, String> {

	public List<NotificationMO> queryNotification(String username);

	public void createNotifications(List<NotificationMO> notificationMOs);

	public NotificationMO queryNotificationByUserName(String userName, String id);

	public void sendOfflineMsg(String username, String deviceID);
}
