package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Device;
import com.cht.emm.model.User;
import com.cht.emm.vo.ConfigVO;
import com.cht.emm.vo.DeviceDetailVO;
import com.cht.emm.vo.DeviceVO;
import com.cht.emm.vo.StrategyVO;
import com.cht.emm.vo.UserVO;


public interface DeviceService extends IBaseService<Device, String> {

	public List<DeviceVO> listAllDevices();

	public List<UserVO> getUsers();

	public void addDevice(DeviceVO device);

	public void deleteDevice(String id);

	public DeviceVO getDevice(String id);

	public DeviceDetailVO getDeviceDetail(String id);

	public void activateDevice(String id);

	public void denyDevice(String id, String remark);

	public void cancelDevice(String id, String remark);

	public void addConfig(ConfigVO config);

	public void deleteConfig(String id);

	public ConfigVO getConfig(String id);

	public void addStrategy(StrategyVO strategy);

	public void deleteStrategy(String id);

	public StrategyVO getStrategy(String id);

	public List<DeviceVO> queryForPage(int countFilter, String conditionQuery,
			String orderList, int i, Integer length);

	public void register(DeviceVO vo, User user);

	public boolean checkUserDevice(String id, String deviceID);

	public String getEnvironment(String deviceID);

	public String getConfigs(String deviceID);

	public String getStrategys(String deviceID);

	public List<ConfigVO> getConfigPages(String id, int i, Integer length);

	public List<StrategyVO> getStrategyPages(String id, int i, Integer length);

}