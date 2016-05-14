package com.cht.emm.util;

/**
 * 定义 HTTP 请求状态码
 * 
 * @author wjun
 * 
 */
public interface RestStatusCode {

	/**
	 * Web服务异常
	 */
	public final static int WEB_SERVICES_EXCEPTION = 500;

	/**
	 * 用户校验失败
	 */
	public final static int INVALIDATE_USER_PASSWD = 600;

	/**
	 * 无权使用当前设备（用户与设备未建立关联关系）
	 */
	public final static int INVALIDATE_USER_BUILDING_DEV = 601;

	/**
	 * 用户令牌失效
	 */
	public final static int INVALIDATE_TOKEN = 602;

	/**
	 * 当前为试用版本，会话数已满，无法创建令牌
	 */
	public final static int FAIL_CREATE_TOKEN_TRIAL = 603;

	/**
	 * 当前证书限制会话数已满，无法创建令牌
	 */
	public final static int FAIL_CREATE_TOKEN_FULL = 604;

	/**
	 * 客户端异常
	 */
	public final static int FAIL_CILENT_EXCEPTION = 605;

	/**
	 * 设备已注册
	 */
	public final static int FAIL_DEVICE_REGISTERED = 606;

	/**
	 * GIS服务已关闭
	 */
	public final static int INVALIDATE_GIS_CLOSED = 651;

	/**
	 * GIS服务异常
	 */
	public final static int FAIL_GIS_INVOKE_EXCEPTION = 652;

	/**
	 * 地图不存在
	 */
	public final static int FAIL_GIS_MAP_NOTFOUND = 653;

	/**
	 * 请求GIS服务超时
	 */
	public final static int FAIL_GIS_INVOKE_TIMEOUT = 654;

	/**
	 * 设备校验失败
	 */
	public final static int INVALIDATE_DEVICE = 608;

	/**
	 * 无权登录主站系统(用户和业务系统未建立关联关系）
	 */
	public final static int INVALIDATE_USER_BUILDING_SYSTEM = 609;

	/**
	 * 下载离线数据包失败
	 */
	public final static int FAIL_PACKAGE_DOWNLOAD = 620;

	/**
	 * 上传增量数据包失败
	 */
	public final static int FAIL_PACKAGE_UPLOAD = 630;

	/**
	 * 上传平台程序集失败
	 */
	public final static int FAIL_ASSEMBILES_UPLOAD = 640;

	/**
	 * 下载平台程序集失败
	 */
	public final static int FAIL_ASSEMBILES_DOWNLOAD = 641;

	/**
	 * 解析平台程序集失败
	 */
	public final static int FAIL_ASSEMBILES_PARSE = 642;

	/**
	 * 上传附件失败
	 */
	public final static int FAIL_ATTACHMENT_UPLOAD = 660;

	/**
	 * 下载附件失败
	 */
	public final static int FAIL_ATTACHMENT_DOWNLOAD = 661;

	/**
	 * 查询附件失败
	 */
	public final static int FAIL_ATTACHMENT_QUERY = 662;

	/**
	 * REST服务失败HTTP 状态码（失败默认值）
	 */
	public final static int FAIL_REST_SERVICE_ERROR = 500;

	/**
	 * REST服务成功HTTP 状态码（成功默认值）
	 */
	public final static int FAIL_REST_SERVICE_OK = 200;
	
	/**
	 * 消息推送用户注册失败
	 */
	public final static int FAIL_REST_MSG_PUSH_REGISTER = 700;
}
