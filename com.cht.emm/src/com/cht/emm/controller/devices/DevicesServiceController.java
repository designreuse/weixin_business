package com.cht.emm.controller.devices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import nari.mip.msg.openfire.msg.MipMsg;
import nari.mip.msg.openfire.user.MipUser;
import nariis.pi3000.framework.utility.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.controller.BaseController;
import com.cht.emm.model.NotificationMO;
import com.cht.emm.model.Token;
import com.cht.emm.msg.MsgManagerFactory;
import com.cht.emm.msg.MsgResponse;
import com.cht.emm.security.ConstantId;
import com.cht.emm.service.ApplicationDeployService;
import com.cht.emm.service.ConfigService;
import com.cht.emm.service.DeviceService;
import com.cht.emm.service.NotificationMOService;
import com.cht.emm.service.StrategyService;
import com.cht.emm.service.TokenService;
import com.cht.emm.service.UserDeviceService;
import com.cht.emm.util.ColumnProperty;
import com.cht.emm.util.DeviceOrderConstants;
import com.cht.emm.util.KeyValue;
import com.cht.emm.util.PageInfo;
import com.cht.emm.util.Response;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.db19.ParameterUtil;
import com.cht.emm.vo.ApplicationDeployVO;
import com.cht.emm.vo.ConfigVO;
import com.cht.emm.vo.DeviceVO;
import com.cht.emm.vo.MessageVO;
import com.cht.emm.vo.StrategyVO;

/**
 * 设备管理相关rest服务
 * 
 * @author luoyupan
 * 
 */
@Controller
@RequestMapping("/rest/device")
public class DevicesServiceController extends BaseController {
	/**
	 * 设备管理服务
	 */
	@Resource(name = "deviceServiceImpl")
	private DeviceService deviceServiceImpl;
	/**
	 * 应用部署记录管理服务
	 */
	@Resource(name = "applicationDeployServiceImpl")
	private ApplicationDeployService applicationDeployServiceImpl;
	/**
	 * 配置管理服务
	 */
	@Resource(name = "configServiceImpl")
	private ConfigService configServiceImpl;
	/**
	 * 策略管理服务
	 */
	@Resource(name = "strategyServiceImpl")
	private StrategyService strategyServiceImpl;

	@Resource(name = "tokenService")
	private TokenService tokenService;

	@Resource(name = "notificationMOServiceImpl")
	private NotificationMOService notificationMOServiceImpl;

	@Resource(name = "userDeviceServiceImpl")
	private UserDeviceService userDeviceServiceImpl;

	/**
	 * 获取所有的设备对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseBody
	public Response findAllDevices() {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(deviceServiceImpl.listAllDevices());
		return res;
	}

	/**
	 * 获取所有的用户对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "users", method = RequestMethod.GET)
	@ResponseBody
	public Response getUsers() {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(deviceServiceImpl.getUsers());
		return res;
	}

	/**
	 * 创建或更新设备
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Response addDevice(@RequestBody DeviceVO device) {
		Response res = new Response();
		deviceServiceImpl.addDevice(device);
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 删除设备
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteDevice(@RequestParam String ids) {
		Response res = new Response();
		// 逐个删除应用
		for (String id : ids.split(",")) {
			deviceServiceImpl.deleteDevice(id);
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 获取指定设备基本信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response getDevice(@PathVariable String id) {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(deviceServiceImpl.getDevice(id));
		return res;
	}

	/**
	 * 获取指定设备详细信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response getDeviceDetail(@PathVariable String id) {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(deviceServiceImpl.getDeviceDetail(id));
		return res;
	}

	/**
	 * 激活设备
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "activate", method = RequestMethod.POST)
	@ResponseBody
	public Response activateDevice(@RequestParam String ids) {
		Response res = new Response();
		for (String id : ids.split(",")) {
			deviceServiceImpl.activateDevice(id);
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 驳回设备注册申请
	 * 
	 * @param ids
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "deny", method = RequestMethod.POST)
	@ResponseBody
	public Response denyDevice(@RequestParam String ids,
			@RequestParam String remark) {
		Response res = new Response();
		for (String id : ids.split(",")) {
			deviceServiceImpl.denyDevice(id, remark);
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 注销设备
	 * 
	 * @param ids
	 * @param remark
	 *            注销说明
	 * @return
	 */
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	@ResponseBody
	public Response cancelDevice(@RequestParam String ids,
			@RequestParam String remark) {
		Response res = new Response();
		for (String id : ids.split(",")) {
			deviceServiceImpl.cancelDevice(id, remark);
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 创建或更新配置
	 * 
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "config/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addConfig(@RequestBody ConfigVO config) {
		Response res = new Response();
		deviceServiceImpl.addConfig(config);
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 删除配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "config/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteConfig(@RequestParam String ids) {
		Response res = new Response();
		for (String id : ids.split(",")) {
			if ("0".equals(id)) {
				continue;
			}
			deviceServiceImpl.deleteConfig(id);
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 获取指定配置信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "config/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response getConfig(@PathVariable String id) {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(deviceServiceImpl.getConfig(id));
		return res;
	}

	/**
	 * 创建或更新策略
	 * 
	 * @param strategy
	 * @return
	 */
	@RequestMapping(value = "strategy/add", method = RequestMethod.POST)
	@ResponseBody
	public Response addStrategy(@RequestBody StrategyVO strategy) {
		Response res = new Response();
		deviceServiceImpl.addStrategy(strategy);
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 删除策略
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "strategy/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteStrategy(@RequestParam String ids) {
		Response res = new Response();
		for (String id : ids.split(",")) {
			if ("0".equals(id)) {
				continue;
			}
			deviceServiceImpl.deleteStrategy(id);
		}
		res.setSuccessful(true);
		return res;
	}

	/**
	 * 获取指定策略信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "strategy/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response getStrategy(@PathVariable String id) {
		Response res = new Response();
		res.setSuccessful(true);
		res.setResultValue(deviceServiceImpl.getStrategy(id));
		return res;
	}

	@RequestMapping(value = "sendMsg", method = RequestMethod.POST)
	@ResponseBody
	public Response sendMsg(@RequestParam String deviceID,String content) {
		
		return sendMessage(deviceID, content,"消息发送成功");
	}
	/**
	 * 锁定设备
	 * 
	 * @param p_deviceID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "lock", method = RequestMethod.POST)
	@ResponseBody
	public Response lockScreen(@RequestParam String p_deviceID) {
		
		return doDevice(p_deviceID, DeviceOrderConstants.LOCK_DEVICE,"锁屏成功");
	}

	private Response doDevice(String deviceId,String cmd,String tip){
		

		Response res = new Response();
		try {
			// 根据TOKEN获取当前在指定设备上登录的用户
			Token token = tokenService.getTokenByDevice(deviceId);
			if (token == null) {
				res.setSuccessful(true);
				res.setResultValue(1);
				res.setResultMessage("设备当前不在线，是否保存离线消息，等待设备上线时执行当前操作？");
				return res;
			}
			
			String userId = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).generateMsgUserId(token.getUserName(), deviceId);
			// 调用OPENFIRE服务判断用户是否在线
			List<String> userIds = new ArrayList<String>();
			userIds.add(userId);
			
			MsgResponse response = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).getUserState(userIds);
			
			if(!response.isSuccess()){
				
				res.setSuccessful(false);
				res.setResultValue(1);
				res.setResultMessage(response.getErrorMsg());
				return res;
			}else{
				
				Map<String,Object> states = (Map<String,Object>)response.getValue();
				int state = (Integer)states.get(userId);
				if(state!=MipUser.STATE_ONLINE){
					
					res.setSuccessful(true);
					res.setResultValue(1);
					res.setResultMessage("用户未登陆消息推送服务！");
					return res;
				}
			}
			
			// 若用户在线则发送锁屏指令，否则返回用户提示
			List<String> receivers = new ArrayList<String>();
			receivers.add(userId);
			String msgID = UUIDGen.getUUID();
			
			MipMsg msg = new MipMsg();
			msg.setCategory(MipMsg.CMD);
			msg.setType(MipMsg.TYPE_ONLINE);
			msg.setId(msgID);
			msg.setContent(cmd);
			msg.setSender(ConstantId.USER_ADMIN_ID);
			msg.setReceivers(receivers);
			
			
			MsgResponse msgResponse = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).sendMsg(msg);
			
//			// 保存已发送的消息
//			NotificationMO mo = new NotificationMO();
//			mo.setId(msgID);
//			mo.setUsername(p_deviceID);
//			mo.setMessage(DeviceOrderConstants.LOCK_DEVICE);
//			mo.setType(MessageVO.ORDER);
//			mo.setStatus(NotificationMO.STATUS_SEND);
//			mo.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			notificationMOServiceImpl.saveOrUpdate(mo);
			
			if(msgResponse.isSuccess()){
				
				// 返回结果
				res.setSuccessful(true);
				res.setResultValue(0);
				res.setResultMessage(tip);
				
			}else{
				
				res.setSuccessful(false);
				res.setResultMessage(msgResponse.getErrorMsg());
			}
			

		} catch (Exception e) {
			res.setSuccessful(false);
			res.setResultMessage(e.getMessage());
		}
		return res;
	
	}
	private Response sendMessage(String deviceId,String content,String tip){
		Response res = new Response();
		try {
			// 根据TOKEN获取当前在指定设备上登录的用户
			Token token = tokenService.getTokenByDevice(deviceId);
			if (token == null) {
				res.setSuccessful(false);
				res.setResultValue(1);
				res.setResultMessage("设备当前不在线，是否保存离线消息，等待设备上线时执行当前操作？");
				return res;
			}
			
			String userId = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).generateMsgUserId(token.getUserName(), deviceId);
			// 调用OPENFIRE服务判断用户是否在线
			List<String> userIds = new ArrayList<String>();
			userIds.add(userId);
			
			MsgResponse response = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).getUserState(userIds);
			
			if(!response.isSuccess()){
				
				res.setSuccessful(false);
				res.setResultValue(1);
				res.setResultMessage(response.getErrorMsg());
				return res;
			}else{
				
				Map<String,Object> states = (Map<String,Object>)response.getValue();
				int state = (Integer)states.get(userId);
				if(state!=MipUser.STATE_ONLINE){
					
					res.setSuccessful(true);
					res.setResultValue(1);
					res.setResultMessage("用户未登陆消息推送服务！");
					return res;
				}
			}
			
			// 若用户在线则发送锁屏指令，否则返回用户提示
			List<String> receivers = new ArrayList<String>();
			receivers.add(userId);
			String msgID = UUIDGen.getUUID();
			
			MipMsg msg = new MipMsg();
			msg.setCategory(MipMsg.MSG);
			msg.setType(MipMsg.TYPE_ONLINE);
			msg.setId(msgID);
			msg.setContent(content);
			msg.setSender(ConstantId.USER_ADMIN_ID);
			msg.setReceivers(receivers);
			
			
			MsgResponse msgResponse = MsgManagerFactory.getMsgManager(MsgManagerFactory.XMPP_MANAGER).sendMsg(msg);
			
//			// 保存已发送的消息
//			NotificationMO mo = new NotificationMO();
//			mo.setId(msgID);
//			mo.setUsername(p_deviceID);
//			mo.setMessage(DeviceOrderConstants.LOCK_DEVICE);
//			mo.setType(MessageVO.ORDER);
//			mo.setStatus(NotificationMO.STATUS_SEND);
//			mo.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			notificationMOServiceImpl.saveOrUpdate(mo);
			
			if(msgResponse.isSuccess()){
				
				// 返回结果
				res.setSuccessful(true);
				res.setResultValue(0);
				res.setResultMessage(tip);
				
			}else{
				
				res.setSuccessful(false);
				res.setResultMessage(msgResponse.getErrorMsg());
			}
			

		} catch (Exception e) {
			res.setSuccessful(false);
			res.setResultMessage(e.getMessage());
		}
		return res;
	}
	
	/**
	 * 解锁设备
	 * 
	 * @param p_deviceID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "unlock", method = RequestMethod.POST)
	@ResponseBody
	public Response unlockScreen(@RequestParam String p_deviceID) {
		
		return doDevice(p_deviceID, DeviceOrderConstants.UNLOCK_DEVICE,"屏幕解锁成功");
	}

	/**
	 * 分页加载设备对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDevicePages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = "";
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery = " where " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("os".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = deviceServiceImpl.countAll(conditionQuery);
		int countAll = deviceServiceImpl.countAll();
		List<DeviceVO> devices = deviceServiceImpl.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", devices);
		return resultMap;
	}

	/**
	 * 分页加载指定设备的应用部署情况
	 * 
	 * @return
	 */
	@RequestMapping(value = "appDeploys/pages/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAppDeployPages(@PathVariable String id,
			HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("appname".equals(colnName)) {
						condition.append(" app.name like '%" + search + "%' ");
					} else if ("username".equals(colnName)) {
						condition.append(" user like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = " where device.id='" + id + "'";
		String searchQuery = conditionQuery;
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("time".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = applicationDeployServiceImpl.countAll(conditionQuery);
		int countAll = applicationDeployServiceImpl.countAll(searchQuery);
		List<ApplicationDeployVO> appDeploys = applicationDeployServiceImpl
				.queryForPage(countFilter, " from ApplicationDeploy ",
						conditionQuery, orderList, pageInfo.getStart()
								/ pageInfo.getLength() + 1,
						pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", appDeploys);
		return resultMap;
	}

	/**
	 * 分页加载驳回状态的设备对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "deny/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDenyDevicePages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = " where status=2 ";
		String searchQuery = conditionQuery;
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("os".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = deviceServiceImpl.countAll(conditionQuery);
		int countAll = deviceServiceImpl.countAll(searchQuery);
		List<DeviceVO> devices = deviceServiceImpl.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", devices);
		return resultMap;
	}

	/**
	 * 分页加载注册状态的设备对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "register/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRegisterDevicePages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = " where status=0 ";
		String searchQuery = conditionQuery;
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("os".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = deviceServiceImpl.countAll(conditionQuery);
		int countAll = deviceServiceImpl.countAll(searchQuery);
		List<DeviceVO> devices = deviceServiceImpl.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", devices);
		return resultMap;
	}

	/**
	 * 分页加载激活状态的设备对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "activate/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getActivateDevicePages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = " where status=1 ";
		String searchQuery = conditionQuery;
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("os".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = deviceServiceImpl.countAll(conditionQuery);
		int countAll = deviceServiceImpl.countAll(searchQuery);
		List<DeviceVO> devices = deviceServiceImpl.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", devices);
		return resultMap;
	}

	/**
	 * 分页加载注销状态的设备对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "cancel/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCancelDevicePages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = " where status=3 ";
		String searchQuery = conditionQuery;
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery += " and " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("os".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = deviceServiceImpl.countAll(conditionQuery);
		int countAll = deviceServiceImpl.countAll(searchQuery);
		List<DeviceVO> devices = deviceServiceImpl.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", devices);
		return resultMap;
	}

	/**
	 * 分页加载设备配置对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "config/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigPages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = "";
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery = " where " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("time".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = configServiceImpl.countAll(conditionQuery);
		int countAll = configServiceImpl.countAll();
		List<ConfigVO> configs = configServiceImpl.queryForPage(countFilter,
				conditionQuery, orderList,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", configs);
		return resultMap;
	}

	/**
	 * 分页加载设备配置对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "strategy/all/pages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStrategyPages(HttpServletRequest request) {
		List<ColumnProperty> columns = ParameterUtil
				.getColumnProperties(request);
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		StringBuilder condition = new StringBuilder();
		String search = pageInfo.getSearch();
		if (StringUtil.isNotEmpty(search)) {

			for (ColumnProperty columnProperty : columns) {

				if (columnProperty.isSearchable()) {
					String colnName = columnProperty.getColName();
					if (condition.length() > 0) {
						condition.append(" or ");
					}
					if ("name".equals(colnName)) {
						condition.append(" name like '%" + search + "%' ");
					}
				}
			}

		}

		String conditionQuery = "";
		String orderList = "";
		if (condition.length() > 0) {
			conditionQuery = " where " + condition.toString();
		}
		List<KeyValue<Integer, String>> columnOrder = pageInfo.getOrders();

		StringBuilder orderBy = new StringBuilder();

		for (KeyValue<Integer, String> keyValue : columnOrder) {
			if (orderBy.length() > 0) {
				orderBy.append(",");
			}

			String colName = columns.get(keyValue.getKey()).getColName();
			if ("time".equals(colName)) {
				orderBy.append(" " + colName + " "
						+ keyValue.getValue().toLowerCase());
			}

		}
		if (orderBy.length() > 0) {
			orderList = " order by " + orderBy.toString();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = strategyServiceImpl.countAll(conditionQuery);
		int countAll = strategyServiceImpl.countAll();
		List<StrategyVO> strategies = strategyServiceImpl.queryForPage(
				countFilter, conditionQuery, orderList, pageInfo.getStart()
						/ pageInfo.getLength() + 1, pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", strategies);
		return resultMap;
	}

	/**
	 * 分页加载指定设备的配置绑定情况
	 * 
	 * @return
	 */
	@RequestMapping(value = "configDevices/pages/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigDevicesPages(@PathVariable String id,
			HttpServletRequest request) {
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		List<ConfigVO> configs = deviceServiceImpl.getConfigPages(id,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = userDeviceServiceImpl.countAll(" where device.id='"
				+ id + "'");
		int countAll = countFilter;
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", configs);
		return resultMap;
	}

	/**
	 * 分页加载指定设备的策略绑定情况
	 * 
	 * @return
	 */
	@RequestMapping(value = "strategyDevices/pages/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStrategyDevicesPages(@PathVariable String id,
			HttpServletRequest request) {
		PageInfo pageInfo = ParameterUtil.getPageInfo(request);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int countFilter = userDeviceServiceImpl.countAll(" where device.id='"
				+ id + "'");
		int countAll = countFilter;
		List<StrategyVO> strategies = deviceServiceImpl.getStrategyPages(id,
				pageInfo.getStart() / pageInfo.getLength() + 1,
				pageInfo.getLength());
		resultMap.put("sEcho", Integer.parseInt(request.getParameter("sEcho")));
		resultMap.put("iTotalRecords", countAll);
		resultMap.put("iTotalDisplayRecords", countFilter);
		resultMap.put("aaData", strategies);
		return resultMap;
	}

	/**
	 * 擦除设备
	 * 
	 * @param p_deviceID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "wipe", method = RequestMethod.POST)
	@ResponseBody
	public Response wipeData(@RequestParam String p_deviceID) {
		
		return doDevice(p_deviceID, DeviceOrderConstants.WIPE_DATA, "数据擦除成功");
	}

	/**
	 * 保存离线消息
	 * 
	 * @param p_deviceID
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "offline", method = RequestMethod.POST)
	@ResponseBody
	public Response saveOfflineMsg(@RequestParam String p_deviceID,
			@RequestParam String msg) {
		Response res = new Response();
		try {
			// 保存已发送的消息
			NotificationMO mo = new NotificationMO();
			mo.setId(UUIDGen.getUUID());
			mo.setUsername(p_deviceID);

			if (StringUtil.equals(DeviceOrderConstants.LOCK_DEVICE, msg)
					|| StringUtil.equals(DeviceOrderConstants.UNLOCK_DEVICE,
							msg)
					|| StringUtil.equals(DeviceOrderConstants.WIPE_DATA, msg)) {
				mo.setType(MessageVO.ORDER);
				mo.setMessage(msg);
			} 
			mo.setStatus(NotificationMO.STATUS_NOT_SEND);
			mo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			notificationMOServiceImpl.saveOrUpdate(mo);
			// 返回结果
			res.setSuccessful(true);

		} catch (Exception e) {
			res.setSuccessful(false);
		}
		return res;
	}

}
