package com.cht.emm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nari.mip.msg.openfire.user.MipUser;
import nariis.pi3000.framework.json.JSONArray;
import nariis.pi3000.framework.json.JSONException;
import nariis.pi3000.framework.json.JSONObject;
import nariis.pi3000.framework.utility.StringUtil;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.impl.ApplicationDeployDaoImpl;
import com.cht.emm.dao.impl.ConfigDaoImpl;
import com.cht.emm.dao.impl.ConfigUserDaoImpl;
import com.cht.emm.dao.impl.DeviceDaoImpl;
import com.cht.emm.dao.impl.DeviceDetailDaoImpl;
import com.cht.emm.dao.impl.StrategyDaoImpl;
import com.cht.emm.dao.impl.StrategyUserDaoImpl;
import com.cht.emm.dao.impl.UserDaoImpl;
import com.cht.emm.dao.impl.UserDeviceDaoImpl;
import com.cht.emm.model.Config;
import com.cht.emm.model.Device;
import com.cht.emm.model.DeviceDetail;
import com.cht.emm.model.PlatFormAgent;
import com.cht.emm.model.Strategy;
import com.cht.emm.model.Token;
import com.cht.emm.model.User;
import com.cht.emm.model.id.ApplicationDeploy;
import com.cht.emm.model.id.ConfigUser;
import com.cht.emm.model.id.StrategyUser;
import com.cht.emm.model.id.UserDevice;
import com.cht.emm.msg.MsgManagerFactory;
import com.cht.emm.msg.MsgResponse;
import com.cht.emm.service.DeviceService;
import com.cht.emm.service.PlatFormAgentService;
import com.cht.emm.service.TokenService;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.util.TimestampUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.vo.ApplicationVO1;
import com.cht.emm.vo.ConfigVO;
import com.cht.emm.vo.DeviceDetailVO;
import com.cht.emm.vo.DeviceVO;
import com.cht.emm.vo.StrategyVO;
import com.cht.emm.vo.UserVO;

@Service
@Transactional(readOnly = true, timeout = 2, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class DeviceServiceImpl extends BaseService<Device, String> implements
		DeviceService {
	@Resource(name = "deviceDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<Device, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Resource(name = "deviceDaoImpl")
	private DeviceDaoImpl deviceDaoImpl;

	@Resource(name = "deviceDetailDaoImpl")
	private DeviceDetailDaoImpl deviceDetailDaoImpl;

	@Resource(name = "userDeviceDaoImpl")
	private UserDeviceDaoImpl userDeviceDaoImpl;

	@Resource(name = "configDaoImpl")
	private ConfigDaoImpl configDaoImpl;

	@Resource(name = "configUserDaoImpl")
	private ConfigUserDaoImpl configUserDaoImpl;

	@Resource(name = "strategyDaoImpl")
	private StrategyDaoImpl strategyDaoImpl;

	@Resource(name = "strategyUserDaoImpl")
	private StrategyUserDaoImpl strategyUserDaoImpl;

	@Resource(name = "applicationDeployDaoImpl")
	private ApplicationDeployDaoImpl applicationDeployDaoImpl;

	@Resource(name = "userDao")
	private UserDaoImpl userDaoImpl;

	@Resource(name = "tokenService")
	private TokenService tokenService;
	
	@Resource
	PropertiesReader propertiesReader;
	
	@Resource
	PlatFormAgentService platFormAgentService;

	/**
	 * 检查用户和设备是否已绑定
	 * 
	 * @param user
	 * @param device
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean checkUserDevice(String userID, String deviceID) {
		return userDeviceDaoImpl.getSession().createCriteria(UserDevice.class)
				.add(Restrictions.eq("user.id", userID))
				.add(Restrictions.eq("device.id", deviceID)).list().size() > 0;
	}

	/**
	 * 删除指定设备
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteDevice(String id) {
		Device device = this.baseDao.get(id);
		// 删除设备和用户的绑定记录
		List<UserDevice> userDevices = device.getUserDevices();
		for (UserDevice userDevice : userDevices) {
			userDeviceDaoImpl.deleteObject(userDevice);
		}
		// 删除应用的部署记录
		List<ApplicationDeploy> appDeploys = device.getAppDeploys();
		for (ApplicationDeploy appDeploy : appDeploys) {
			applicationDeployDaoImpl.deleteObject(appDeploy);
		}
		deviceDetailDaoImpl.delete(id);
		deviceDaoImpl.delete(id);

	}

	/**
	 * 获取设备VO
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public DeviceVO getDevice(String id) {
		DeviceVO vo = new DeviceVO();
		Device device = deviceDaoImpl.get(id);
		if (device != null) {
			vo.fromDevice(device);
			// 查询设备绑定的用户，用","隔开
			StringBuffer users = new StringBuffer();
			List<UserDevice> userDevices = device.getUserDevices();
			for (UserDevice userDevice : userDevices) {
				if (users.length() > 0) {
					users.append(",");
				}
				users.append(userDevice.getUser().getUsername());
			}
			vo.setUsers(users.toString());
		}
		return vo;
	}

	/**
	 * 获取设备列表
	 * 
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<DeviceVO> listAllDevices() {
		OrderBy orderby = new OrderBy();
		orderby.add(Order.asc("name"));
		List<Device> devices = this.baseDao.listAll(new ConditionQuery(),
				orderby, -1, -1);
		List<DeviceVO> vos = new ArrayList<DeviceVO>();

		for (Device device : devices) {
			DeviceVO vo = new DeviceVO();
			vo.fromDevice(device);
			// 查询设备绑定的用户，用","隔开
			StringBuffer users = new StringBuffer();
			List<UserDevice> userDevices = device.getUserDevices();
			for (UserDevice userDevice : userDevices) {
				if (users.length() > 0) {
					users.append(",");
				}
				users.append(userDevice.getUser().getUsername());
			}
			vo.setUsers(users.toString());
			// 设置设备的在线状态
			// 根据TOKEN获取当前在指定设备上登录的用户
			Token token = tokenService.getTokenByDevice(device.getId());
			if (token != null) {
				try {
					// 调用OPENFIRE服务判断用户是否在线
					List<String> userIds = new ArrayList<String>();
					userIds.add(token.getUserName());
					
					MsgResponse response = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).getUserState(userIds);
					
					if(response.isSuccess()){
						
						Map<String,Object> states = (Map<String,Object>)response.getValue();
						int state = (Integer)states.get(token.getUserName());
						if(state==MipUser.STATE_ONLINE){
							
							vo.setOnline(true);
						}
					}
				} catch (Exception e) {

				}
			}

			vos.add(vo);
		}
		return vos;
	}

	/**
	 * 移动客户端的注册操作
	 * 
	 * @param vo
	 * @param user
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void register(DeviceVO vo, User user) {
		// 更新设备信息

		Device device = deviceDaoImpl.get(vo.getId());
		device = vo.toDevice(device);
		deviceDaoImpl.saveOrUpdate(device);
		DeviceDetail detail = deviceDetailDaoImpl.get(vo.getId());
		deviceDetailDaoImpl.saveOrUpdate(vo.toDeviceDetail(detail));
		// 绑定设备和用户的关系，TODO 未加入管理员验证环节，可根据status字段判定是否通过验证
		if (!checkUserDevice(user.getId(), vo.getId())) {
			UserDevice userDevice = new UserDevice();
			userDevice.setDevice(device);
			userDevice.setUser(new User(user.getId()));
			userDevice.setId(UUIDGen.getUUID());
			userDevice.setStatus(0);
			userDeviceDaoImpl.save(userDevice);
		}
	}

	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<UserVO> getUsers() {
		List<UserVO> vos = new ArrayList<UserVO>();
		OrderBy orderby = new OrderBy();
		orderby.add(Order.asc("username"));
		List<User> users = userDaoImpl.listAll(new ConditionQuery(), orderby,
				-1, -1);
		for (User user : users) {
			UserVO vo = new UserVO();
			vo.setId(user.getId());
			vo.setUsername(user.getUsername());
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * 创建或更新设备
	 * 
	 * @param vo
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addDevice(DeviceVO vo) {
		// 创建或更新设备的基本信息和详情
		Device device = deviceDaoImpl.get(vo.getId());
		device = vo.toDevice(device);
		deviceDaoImpl.saveOrUpdate(device);
		DeviceDetail detail = deviceDetailDaoImpl.get(vo.getId());
		deviceDetailDaoImpl.saveOrUpdate(vo.toDeviceDetail(detail));
		// 先删除该设备和用户的所有关联记录，再根据users属性创建新的关联记录
		List<UserDevice> userDevices = userDeviceDaoImpl.getSession()
				.createCriteria(UserDevice.class)
				.add(Restrictions.eq("device.id", vo.getId())).list();
		for (UserDevice userDevice : userDevices) {
			userDeviceDaoImpl.deleteObject(userDevice);
		}
		String users = vo.getUsers();
		if (StringUtil.isNotEmpty(users)) {
			String[] userList = StringUtil.split(users, ",");
			for (String userID : userList) {
				UserDevice userDevice = new UserDevice();
				userDevice.setDevice(device);
				userDevice.setUser(new User(userID));
				userDevice.setId(UUIDGen.getUUID());
				userDevice.setStatus(0);
				userDeviceDaoImpl.save(userDevice);
			}
		}
	}

	/**
	 * 返回设备绑定的用户列表到移动客户端
	 * 
	 * @param deviceID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private JSONArray getUsersOfDevice(String deviceID) {
		JSONArray result = new JSONArray();
		List<UserDevice> userDevices = userDeviceDaoImpl.getSession()
				.createCriteria(UserDevice.class)
				.add(Restrictions.eq("device.id", deviceID)).list();
		for (UserDevice userDevice : userDevices) {
			result.put(userDevice.getUser().getUsername());
		}
		return result;
	}

	/**
	 * 获取设备的详细信息
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public DeviceDetailVO getDeviceDetail(String id) {
		DeviceDetailVO detailVo = new DeviceDetailVO();
		DeviceVO vo = new DeviceVO();
		Device device = deviceDaoImpl.get(id);
		if (device != null) {
			// 获取设备的基本信息和详情
			vo.fromDevice(device);
			DeviceDetail detail = deviceDetailDaoImpl.get(id);
			if (detail != null) {
				vo.fromDeviceDetail(detail);
			}
			// 获取所有和设备关联的用户，用","隔开
			StringBuffer users = new StringBuffer();
			List<UserDevice> userDevices = device.getUserDevices();
			for (UserDevice userDevice : userDevices) {
				if (users.length() > 0) {
					users.append(",");
				}
				users.append(userDevice.getUser().getUsername());
			}
			vo.setUsers(users.toString());
		}
		detailVo.setDevice(vo);
		return detailVo;
	}

	/**
	 * 返回设备的上下文信息到移动客户端
	 * 
	 * @param deviceID
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String getEnvironment(String deviceID) {
		JSONObject result = new JSONObject();
		try {
			// 设备绑定的用户列表，用","隔开
			result.put("users", getUsersOfDevice(deviceID));
			// 获取平台应用的最新版本信息
//			ConditionQuery query = new ConditionQuery();
//			query.add(Restrictions.eq("pkg_name", "nari.mip.console"));
//			OrderBy orderby = new OrderBy();
//			orderby.add(Order.desc("version_code"));
//			List<Application> apps = applicationDaoImpl.listAll(query, orderby,
//					1, 1);
//			if (apps.size() > 0) {
//				Application app = apps.get(0);
//				JSONObject platform = new JSONObject();
//				platform.put("id", app.getId());
//				platform.put("versionName", app.getVersion_name());
//				platform.put("versionCode", app.getVersion_code());
//				result.put("platform", platform);
//			}
			
			ApplicationVO1 app = platFormAgentService.getMaxVersion(PlatFormAgent.OS_ANDROID);
			
			if(app!=null){
				
				JSONObject platform = new JSONObject();
				
				platform.put("id", app.getId());
				platform.put("versionName", app.getVersionName());
				platform.put("versionCode", app.getVersionCode());
				result.put("platform", platform);
			}
			
			result.put("mobile_service_url", propertiesReader.getString("mobile_service_url"));
			result.put("msg_service_ip", propertiesReader.getString("msg_service_ip"));
			result.put("msg_push_port", propertiesReader.getString("msg_push_port"));

		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 激活指定设备
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void activateDevice(String id) {
		Device device = deviceDaoImpl.get(id);
		if (device != null) {
			device.setStatus(1);
			deviceDaoImpl.saveOrUpdate(device);
		}
	}

	/**
	 * 驳回指定设备
	 * 
	 * @param id
	 * @param remark
	 *            驳回原因
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void denyDevice(String id, String remark) {
		Device device = deviceDaoImpl.get(id);
		if (device != null) {
			device.setStatus(2);
			device.getDetail().setRemark(remark);
			deviceDetailDaoImpl.saveOrUpdate(device.getDetail());
			deviceDaoImpl.saveOrUpdate(device);
		}
	}

	/**
	 * 注销指定设备
	 * 
	 * @param id
	 * @param remark
	 *            注销原因
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void cancelDevice(String id, String remark) {
		Device device = deviceDaoImpl.get(id);
		if (device != null) {
			device.setStatus(3);
			deviceDaoImpl.saveOrUpdate(device);
		}
		DeviceDetail detail = deviceDetailDaoImpl.get(id);
		if (detail != null) {
			detail.setRemark(remark);
			detail.setDestroy(new Timestamp(System.currentTimeMillis()));
			deviceDetailDaoImpl.saveOrUpdate(detail);
		}
	}

	/**
	 * 创建或更新配置对象
	 * 
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addConfig(ConfigVO vo) {
		// 若创建，则设置ID
		if (vo.getId() == null) {
			vo.setId(UUIDGen.getUUID());
		}
		// 创建或更新配置对象
		Config config = configDaoImpl.get(vo.getId());
		config = vo.toConfig(config);
		configDaoImpl.saveOrUpdate(config);
		// 先删除该配置和用户的所有关联记录，再根据users属性创建新的关联记录
		List<ConfigUser> configUsers = config.getConfigUsers();
		for (ConfigUser configUser : configUsers) {
			configUserDaoImpl.deleteObject(configUser);
		}
		String users = vo.getUsers();
		if (StringUtil.isNotEmpty(users)) {
			String[] userList = StringUtil.split(users, ",");
			for (String userID : userList) {
				User user = userDaoImpl.get(userID);
				if (user != null) {
					if (user.getConfigUsers() != null) {
						configUserDaoImpl.deleteObjectList(user
								.getConfigUsers());
					}
				}
				ConfigUser configUser = new ConfigUser();
				configUser.setConfig(config);
				configUser.setUser(new User(userID));
				configUser.setId(UUIDGen.getUUID());
				configUser.setStatus(0);
				configUserDaoImpl.save(configUser);
			}
		}
	}

	/**
	 * 删除指定配置
	 * 
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteConfig(String id) {
		// 删除该配置和用户的所有关联记录
		List<ConfigUser> configUsers = configUserDaoImpl.getSession()
				.createCriteria(ConfigUser.class)
				.add(Restrictions.eq("config.id", id)).list();
		for (ConfigUser configUser : configUsers) {
			configUserDaoImpl.deleteObject(configUser);
		}
		// 删除该配置对象
		configDaoImpl.delete(id);
	}

	/**
	 * 获取指定配置
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ConfigVO getConfig(String id) {
		Config config = configDaoImpl.get(id);
		ConfigVO vo = new ConfigVO();
		if (config != null) {
			vo.fromConfig(config);
			StringBuilder sb = new StringBuilder();
			for (ConfigUser confUser : config.getConfigUsers()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(confUser.getUser().getId());
			}
			vo.setUsers(sb.toString());
		}
		return vo;
	}

	/**
	 * 创建或更新策略对象
	 * 
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addStrategy(StrategyVO vo) {
		// 若创建，则设置ID
		if (vo.getId() == null) {
			vo.setId(UUIDGen.getUUID());
		}
		// 创建或更新策略对象
		Strategy strategy = strategyDaoImpl.get(vo.getId());
		strategy = vo.toStrategy(strategy);
		strategyDaoImpl.saveOrUpdate(strategy);
		// 先删除该策略和用户的所有关联记录，再根据users属性创建新的关联记录
		List<StrategyUser> strategyUsers = strategy.getStrategyUsers();
		for (StrategyUser strategyUser : strategyUsers) {
			strategyUserDaoImpl.deleteObject(strategyUser);
		}
		String users = vo.getUsers();
		if (StringUtil.isNotEmpty(users)) {
			String[] usersList = StringUtil.split(users, ",");
			for (String userID : usersList) {
				User user = userDaoImpl.get(userID);
				if (user != null) {
					if (user.getStrategyUsers() != null) {
						strategyUserDaoImpl.deleteObjectList(user
								.getStrategyUsers());
					}
				}
				StrategyUser strategyUser = new StrategyUser();
				strategyUser.setStrategy(strategy);
				strategyUser.setUser(new User(userID));
				strategyUser.setId(UUIDGen.getUUID());
				strategyUser.setStatus(0);
				strategyUserDaoImpl.save(strategyUser);
			}
		}
	}

	/**
	 * 删除指定策略
	 * 
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteStrategy(String id) {
		// 删除该策略和设备的所有关联记录
		List<StrategyUser> strategyUsers = strategyUserDaoImpl.getSession()
				.createCriteria(StrategyUser.class)
				.add(Restrictions.eq("strategy.id", id)).list();
		for (StrategyUser strategyUser : strategyUsers) {
			strategyUserDaoImpl.deleteObject(strategyUser);
		}
		// 删除该策略对象
		strategyDaoImpl.delete(id);
	}

	/**
	 * 获取指定策略
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public StrategyVO getStrategy(String id) {
		Strategy strategy = strategyDaoImpl.get(id);
		StrategyVO vo = new StrategyVO();
		if (strategy != null) {
			vo.fromStrategy(strategy);
			StringBuilder sb = new StringBuilder();
			for (StrategyUser strategyUser : strategy.getStrategyUsers()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(strategyUser.getUser().getId());
			}
			vo.setUsers(sb.toString());
		}
		return vo;
	}

	@Override
	public List<DeviceVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length) {
		List<Device> devices = this.baseDao.listAll(whereClause, orderby, pn,
				length);
		List<DeviceVO> vos = new ArrayList<DeviceVO>();

		for (Device device : devices) {
			DeviceVO vo = new DeviceVO();
			vo.fromDevice(device);
			vo.fromDeviceDetail(device.getDetail());
			// 查询设备绑定的用户，用","隔开
			StringBuffer users = new StringBuffer();
			List<UserDevice> userDevices = device.getUserDevices();
			for (UserDevice userDevice : userDevices) {
				if (users.length() > 0) {
					users.append(",");
				}
				users.append(userDevice.getUser().getUsername());
			}
			vo.setUsers(users.toString());
			// 设置设备的在线状态
			// 根据TOKEN获取当前在指定设备上登录的用户
			Token token = tokenService.getTokenByDevice(device.getId());
			if (token != null) {
				try {
					// 调用OPENFIRE服务判断用户是否在线
					List<String> userIds = new ArrayList<String>();
					
					String userId = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).generateMsgUserId(token.getUserName(), device.getId());
					userIds.add(userId);
					
					MsgResponse response = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).getUserState(userIds);
					
					if(response.isSuccess()){
						
						Map<String,Object> states = (Map<String,Object>)response.getValue();
						int state = (Integer)states.get(userId);
						if(state == MipUser.STATE_ONLINE){
							
							vo.setOnline(true);
						}
					}
				} catch (Exception e) {

				}
			}
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public String getConfigs(String deviceID) {
		JSONArray configs = new JSONArray();

		Config defaultConfig = configDaoImpl.get("0");
		Device device = this.baseDao.get(deviceID);
		for (UserDevice userDevice : device.getUserDevices()) {
			User user = userDevice.getUser();
			JSONObject config = new JSONObject();
			try {
				config.put("username", user.getUsername());
				if (user.getConfigUsers().size() > 0) {
					config.put("config", user.getConfigUsers().get(0)
							.getConfig().getContent());
				} else {
					config.put("config", defaultConfig.getContent());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			configs.put(config);
		}
		return configs.toString();
	}

	@Override
	public String getStrategys(String deviceID) {
		JSONArray strategys = new JSONArray();

		Strategy defaultStrategy = strategyDaoImpl.get("0");
		Device device = this.baseDao.get(deviceID);
		for (UserDevice userDevice : device.getUserDevices()) {
			User user = userDevice.getUser();
			JSONObject strategy = new JSONObject();
			try {
				strategy.put("username", user.getUsername());
				if (user.getStrategyUsers().size() > 0) {
					strategy.put("strategy", user.getStrategyUsers().get(0)
							.getStrategy().getContent());
				} else {
					strategy.put("strategy", defaultStrategy.getContent());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			strategys.put(strategy);
		}
		return strategys.toString();
	}

	@Override
	public List<ConfigVO> getConfigPages(String id, int pn, Integer length) {
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("device.id", id));
		OrderBy orderby = new OrderBy();
		List<UserDevice> userDeviceResults = userDeviceDaoImpl.listAll(query,
				orderby, pn, length);
		List<ConfigVO> configs = new ArrayList<ConfigVO>();
		Config defaultConfig = configDaoImpl.get("0");
		for (UserDevice userDevice : userDeviceResults) {
			List<ConfigUser> configUsers = userDevice.getUser()
					.getConfigUsers();
			Config config = defaultConfig;
			if (configUsers.size() > 0) {
				config = configUsers.get(0).getConfig();
			}
			ConfigVO vo = new ConfigVO();
			vo.setName(config.getName());
			vo.setDesc(config.getDesc());
			vo.setCreator(userDevice.getUser().getUsername());
			vo.setTime(TimestampUtil.toString(config.getTime()));
			configs.add(vo);
		}
		return configs;
	}

	@Override
	public List<StrategyVO> getStrategyPages(String id, int pn, Integer length) {
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("device.id", id));
		OrderBy orderby = new OrderBy();
		List<UserDevice> userDeviceResults = userDeviceDaoImpl.listAll(query,
				orderby, pn, length);
		List<StrategyVO> strategies = new ArrayList<StrategyVO>();
		Strategy defaultStrategy = strategyDaoImpl.get("0");
		for (UserDevice userDevice : userDeviceResults) {
			List<StrategyUser> strategyUsers = userDevice.getUser()
					.getStrategyUsers();
			Strategy strategy = defaultStrategy;
			if (strategyUsers.size() > 0) {
				strategy = strategyUsers.get(0).getStrategy();
			}
			StrategyVO vo = new StrategyVO();
			vo.setName(strategy.getName());
			vo.setDesc(strategy.getDesc());
			vo.setCreator(userDevice.getUser().getUsername());
			vo.setTime(TimestampUtil.toString(strategy.getTime()));
			strategies.add(vo);
		}
		return strategies;
	}

}
