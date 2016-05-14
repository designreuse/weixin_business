package com.cht.emm.controller.mobile;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nari.mip.msg.openfire.user.MipUser;
import nariis.pi3000.framework.json.JSONException;
import nariis.pi3000.framework.json.JSONObject;
import nariis.pi3000.framework.utility.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.emm.model.Application;
import com.cht.emm.model.ApplicationVersion;
import com.cht.emm.model.NotificationMO;
import com.cht.emm.model.Token;
import com.cht.emm.model.User;
import com.cht.emm.msg.MsgManagerFactory;
import com.cht.emm.msg.MsgResponse;
import com.cht.emm.security.token.TokenChecker;
import com.cht.emm.service.ApplicationService;
import com.cht.emm.service.DeviceService;
import com.cht.emm.service.NewsService;
import com.cht.emm.service.NotificationMOService;
import com.cht.emm.service.TokenService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.FileUtil;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.util.RestStatusCode;
import com.cht.emm.util.Status;
import com.cht.emm.vo.ApplicationScoreVO;
import com.cht.emm.vo.ApplicationScoreVO1;
import com.cht.emm.vo.ApplicationVO1;
import com.cht.emm.vo.ApplicationVO3;
import com.cht.emm.vo.DeviceVO;
import com.cht.emm.vo.NewsEntity;

/**
 * 面向移动客户端的rest服务
 * 
 * @author luoyupan
 * 
 */
@Controller
@RequestMapping("/rest/mobile")
public class MobileServiceController {

	/**
	 * 应用管理服务
	 */
	@Resource(name = "applicationServiceImpl")
	private ApplicationService applicationServiceImpl;

	@Resource(name = "tokenService")
	private TokenService tokenService;

	@Resource
	private TokenChecker tokenChecker;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "deviceServiceImpl")
	private DeviceService deviceServiceImpl;

	@Resource(name = "newsServiceImpl")
	private NewsService newsServiceImpl;

	@Resource(name = "notificationMOServiceImpl")
	private NotificationMOService notificationMOServiceImpl;

	@Resource
	PropertiesReader propertiesReader;

	/**
	 * 服务状态测试接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "state", method = RequestMethod.GET)
	@ResponseBody
	public boolean state() {

		return true;
	}

	/**
	 * 服务状态测试接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "token/state/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean tokenState(@PathVariable String id) {

		return tokenChecker.check(id);
	}

	/**
	 * 移动客户端自动注册
	 * 
	 * @param device
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public void registerDevice(@RequestParam String device,
			@RequestParam String username, @RequestParam String password,
			HttpServletResponse response) {
		// 此处需要判断系统的当前用户验证方式，是本地验证还是三方验证

		// 三方验证时，需要远程验证用户账户，验证通过后需要在本地生成用户映射关系

		User user = userService.checkUser(username, password);
		// 判断用户是否存在
		if (user != null) {

			DeviceVO vo = new DeviceVO();
			vo.fromJSON(device);
			deviceServiceImpl.register(vo, user);

			if (propertiesReader.getBoolean("msg_used")) {

				// 注册消息推送账号
				String msgUserId = MsgManagerFactory.getMsgManager(
						MsgManagerFactory.XMPP_MANAGER).generateMsgUserId(
						username, vo.getId());

				List<MipUser> msgUsers = new ArrayList<MipUser>();
				MipUser mipUser = new MipUser();
				mipUser.setUserName(msgUserId);
				mipUser.setPassword(password);
				msgUsers.add(mipUser);

				MsgResponse msgRes = MsgManagerFactory.getMsgManager(
						MsgManagerFactory.XMPP_MANAGER).addUser(msgUsers);
				if (!msgRes.isSuccess()) {

					response.setStatus(RestStatusCode.FAIL_REST_MSG_PUSH_REGISTER);
					return;
				}

			}

		} else {
			response.setStatus(RestStatusCode.INVALIDATE_USER_PASSWD);
			return;
		}
	}

	/**
	 * 移动客户端登录
	 * 
	 * @param deviceID
	 * @param username
	 * @param password
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam String deviceID,
			@RequestParam String username, @RequestParam String password,
			HttpServletResponse response) {
		try {
			// 此处需要判断系统的当前用户验证方式，是本地验证还是三方验证

			// 三方验证时，需要远程验证用户账户，验证通过后需要在本地生成用户映射关系

			// 1、检查用户是否注册
			User user = userService.checkUser(username, password);
			if (user == null) {
				response.setStatus(RestStatusCode.INVALIDATE_USER_PASSWD);
				return null;
			}
			// 2、检查设备是否注册
			if (!deviceServiceImpl.checkUserDevice(user.getId(), deviceID)) {
				response.setStatus(RestStatusCode.INVALIDATE_USER_BUILDING_DEV);
				return null;
			}
			// 3、生成登陆令牌
			String tokenId = tokenChecker.checkAndUpdate(username, deviceID)
					.getTokenId();

			return tokenId;
		} catch (Exception e) {
			response.setStatus(RestStatusCode.WEB_SERVICES_EXCEPTION);
			return null;
		}
	}

	/**
	 * 同步工具登录
	 * 
	 * @param username
	 * @param password
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "tool/login", method = RequestMethod.POST)
	@ResponseBody
	public String loginFromTool(@RequestParam String username,
			@RequestParam String password, HttpServletResponse response) {
		try {
			// 1、检查用户是否注册
			User user = userService.checkUser(username, password);
			if (user == null) {
				response.setStatus(RestStatusCode.INVALIDATE_USER_PASSWD);
				return null;
			}
			// 3、生成登陆令牌
			String tokenId = tokenChecker.checkAndCreate(username, "")
					.getTokenId();
			return tokenId;
		} catch (Exception e) {
			response.setStatus(RestStatusCode.WEB_SERVICES_EXCEPTION);
			return null;
		}
	}

	/**
	 * TODO 获取第三方系统列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "sys", method = RequestMethod.GET)
	@ResponseBody
	public String getSystem() {
		JSONObject result = new JSONObject();
		try {
			result.put("sysID", "pms");
			result.put("name", "生产管理系统");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	/**
	 * 获取环境变量
	 * 
	 * @param deviceID
	 * @return
	 */
	@RequestMapping(value = "environment", method = RequestMethod.GET)
	@ResponseBody
	public String getEnvironment(@RequestParam String deviceID) {
		return deviceServiceImpl.getEnvironment(deviceID);
	}

	/**
	 * 下载应用
	 * 
	 * @param id
	 *            应用ID
	 * @param request
	 *            servlet请求对象
	 * @param response
	 *            servlet应答对象
	 * @return
	 */
	@RequestMapping(value = "app/download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void download(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		Application app = applicationServiceImpl.get(id);
		ApplicationVersion appVersion = applicationServiceImpl
				.getAppVersion(id);
		if (app != null && appVersion != null) {
			// 若应用状态为停用，则禁止下载
			if (app.getStatus() == 1) {
				return;
			}
			// 支持文件下载断点续传
			File file = new File(appVersion.getUrl());
			if (file.exists()) {
				boolean hasDownload = FileUtil.splitterFetch(file, request,
						response);
				// 若文件下载成功，则应用下载次数加1，并添加一条下载记录
				if (hasDownload) {
					app.setDownload_count(app.getDownload_count() + 1);
					applicationServiceImpl.saveOrUpdate(app);
					// 添加下载记录到MIP_SYS_APP_DEPLOY表中
					String tokenId = request.getHeader("token");
					if (StringUtil.isNotEmpty(tokenId)) {
						Token token = tokenService.get(tokenId);
						if (token != null) {
							applicationServiceImpl.saveAppDeploy(
									token.getUserName(), token.getDeviceId(),
									id);
						}
					}
				}
			}
		}
	}

	/**
	 * 获取应用对象列表
	 * 
	 * @return 应用对象列表
	 */
	@RequestMapping(value = "app/list", method = RequestMethod.GET)
	@ResponseBody
	public List<ApplicationVO1> getAppsOfUser(HttpServletRequest request) {
		String tokenId = request.getHeader("token");
		if (tokenId != null) {
			Token token = tokenService.getToken(tokenId);
			if (token != null) {
				return applicationServiceImpl.getAppsOfUser(
						token.getUserName(), token.getDeviceId(), request);
			}
		}
		return new ArrayList<ApplicationVO1>();
	}

	/**
	 * 获取应用对象详情
	 * 
	 * @param app_id
	 * @return
	 */
	@RequestMapping(value = "app/detail/{app_id}", method = RequestMethod.GET)
	@ResponseBody
	public ApplicationVO3 loadAppDetail(@PathVariable String app_id,
			HttpServletRequest request) {
		return applicationServiceImpl.loadAppDetail(app_id, request);
	}

	/**
	 * 获取指定应用在指定时间前创建的评分列表，最多返回十条记录
	 * 
	 * @param app_id
	 * @param time
	 * @return
	 */
	@RequestMapping(value = "app/score/more/{app_id}", method = RequestMethod.GET)
	@ResponseBody
	public List<ApplicationScoreVO1> getMoreAppScores(
			@PathVariable String app_id, @RequestParam String time) {
		try {
			Timestamp timestamp = new Timestamp(Long.parseLong(time));
			return applicationServiceImpl.getMoreAppScores(app_id, timestamp);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 创建应用评分记录
	 * 
	 * @param score
	 *            应用评分对象
	 * @return 记录创建时间
	 */
	@RequestMapping(value = "app/score/commit", method = RequestMethod.POST)
	@ResponseBody
	public void addScore(@RequestParam String score,
			@RequestParam String app_id, @RequestParam String comment,
			HttpServletRequest request) {
		String tokenId = request.getHeader("token");
		Token token = tokenService.getToken(tokenId);
		if (token != null) {
			applicationServiceImpl.addScore(Integer.parseInt(score), app_id,
					comment, token.getUserName());
		}
	}

	/**
	 * 获取指定用户对指定应用的评分记录
	 * 
	 * @param app_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "app/score/get/{app_id}", method = RequestMethod.GET)
	@ResponseBody
	public ApplicationScoreVO getScore(@PathVariable String app_id,
			HttpServletRequest request) {
		String tokenId = request.getHeader("token");
		if (tokenId != null) {
			Token token = tokenService.getToken(tokenId);
			if (token != null) {
				return applicationServiceImpl.getScore(app_id,
						token.getUserName());
			}
		}
		return new ApplicationScoreVO();
	}

	/**
	 * 获取最新的资讯列表，最多返回十条热点资讯和十五条普通资讯
	 * 
	 * @param time
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "news", method = RequestMethod.GET)
	@ResponseBody
	public String getNews(@RequestParam(defaultValue = "") String time,
			HttpServletRequest request) {
		try {
			Timestamp timestamp = null;
			if (!StringUtil.isNullOrEmpty(time)) {
				timestamp = new Timestamp(Long.parseLong(time));
			}
			return newsServiceImpl.loadNewsByTime(timestamp, request);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取指定时间前发布的普通资讯列表，最多返回十五条记录
	 * 
	 * @param time
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "news/more", method = RequestMethod.GET)
	@ResponseBody
	public String getMoreNews(@RequestParam String time,
			HttpServletRequest request) {
		try {
			Timestamp timestamp = new Timestamp(Long.parseLong(time));
			return newsServiceImpl.loadMoreNewsByTime(timestamp, request)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: validUserDevice
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param userName
	 * @param deviceId
	 * @return
	 * @return Response 返回类型
	 * @throws
	 */
	@RequestMapping(value = "validDevice", method = RequestMethod.POST)
	@ResponseBody
	public void validUserDevice(@RequestParam String userName,
			@RequestParam String deviceId, HttpServletResponse response) {
		User user = userService.loadUserByUserName(userName);
		if (user == null) {
			response.setStatus(Status.VALID_USER_NOT_EXIST.getCode());
			return;
		} else {
			if (!deviceServiceImpl.checkUserDevice(user.getId(), deviceId)) {
				response.setStatus(Status.VALID_USER_DEVICE_ID_NOT_MATCH
						.getCode());
				return;
			} else {
				response.setStatus(Status.VALID_USER_VALIDED.getCode());
				return;
			}
		}
	}

	/**
	 * 获取最新的资讯列表，最多返回十五条资讯
	 * 
	 * @param time
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "news/list", method = RequestMethod.GET)
	@ResponseBody
	public List<NewsEntity> listNews(
			@RequestParam(defaultValue = "") String time,
			HttpServletRequest request) {
		try {
			Timestamp timestamp = null;
			if (!StringUtil.isNullOrEmpty(time)) {
				timestamp = new Timestamp(Long.parseLong(time));
			}
			return newsServiceImpl.listNewsByTime(timestamp, request);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取指定时间前发布的资讯列表，最多返回十五条记录
	 * 
	 * @param time
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "news/list/more", method = RequestMethod.GET)
	@ResponseBody
	public List<NewsEntity> listMoreNews(@RequestParam String time,
			HttpServletRequest request) {
		try {
			Timestamp timestamp = new Timestamp(Long.parseLong(time));
			return newsServiceImpl.listMoreNewsByTime(timestamp, request);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "checkmsg", method = RequestMethod.POST)
	@ResponseBody
	public void CheckMsg(@RequestParam String msgID, HttpServletRequest request) {

		try {
			NotificationMO mo = notificationMOServiceImpl.get(msgID);
			if (mo != null) {
				mo.setStatus(NotificationMO.STATUS_RECEIVE);
				notificationMOServiceImpl.saveOrUpdate(mo);
			}
			String tokenId = request.getHeader("token");
			if (StringUtil.isNotEmpty(tokenId)) {
				Token token = tokenService.getToken(tokenId);
				if (token != null) {
					// 发送下一条离线消息
					notificationMOServiceImpl.sendOfflineMsg(
							token.getUserName(), token.getDeviceId());
				}
			}
		} catch (Exception e) {
		}
	}

	@RequestMapping(value = "configs", method = RequestMethod.GET)
	@ResponseBody
	public String getConfigs(@RequestParam String deviceID) {
		return deviceServiceImpl.getConfigs(deviceID);
	}

	@RequestMapping(value = "strategys", method = RequestMethod.GET)
	@ResponseBody
	public String getStrategys(@RequestParam String deviceID) {
		return deviceServiceImpl.getStrategys(deviceID);
	}
}
