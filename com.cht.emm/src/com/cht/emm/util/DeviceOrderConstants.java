package com.cht.emm.util;

/**
 * 客户端指令常量
 * 
 * @author luoyupan
 * 
 */
public interface DeviceOrderConstants {

	// 控制指令
	/**
	 * 锁屏
	 */
	public final static String LOCK_DEVICE = "lock";
	/**
	 * 解锁
	 */
	public final static String UNLOCK_DEVICE = "unlock";
	/**
	 * 擦除数据
	 */
	public final static String WIPE_DATA = "wipe";
	/**
	 * 推送配置
	 */
	public final static String CONFIG_DEVICE = "config";
	/**
	 * 发送消息
	 */
	public final static String SEND_MESSAGE = "message";

	// 配置关键字
	/**
	 * 应用黑白名单配置
	 */
	public final static String CONFIG_APP = "nari.mip.console.model.ApplicationConfig";
	/**
	 * 应用黑白名单类型
	 */
	public final static String CONFIG_APP_LIST_TYPE = "app_list_type";
	/**
	 * 应用黑名单
	 */
	public final static int CONFIG_BLACK_APP_LIST = 1;
	/**
	 * 应用白名单
	 */
	public final static int CONFIG_WHITE_APP_LIST = 2;
	/**
	 * 黑白名单上的应用ID，以“,”分隔
	 */
	public final static String CONFIG_APPS = "apps";

	/**
	 * WIFI配置
	 */
	public final static String CONFIG_WIFI = "nari.mip.console.model.WifiConfig";
	/**
	 * WIFI名称
	 */
	public final static String CONFIG_WIFI_NAME = "name";
	/**
	 * WIFI密码
	 */
	public final static String CONFIG_WIFI_PASSWORD = "password";

	/**
	 * VPN配置
	 */
	public final static String CONFIG_VPN = "nari.mip.console.model.VpnConfig";
	/**
	 * VPN名称
	 */
	public final static String CONFIG_VPN_NAME = "name";
	/**
	 * VPN密码
	 */
	public final static String CONFIG_VPN_PASSWORD = "password";

	/**
	 * 安全配置
	 */
	public final static String CONFIG_SECURITY = "nari.mip.console.model.SecurityConfig";
	/**
	 * 蓝牙配置
	 */
	public final static String CONFIG_BLUETOOTH = "bluetooth";
	/**
	 * 启用蓝牙
	 */
	public final static int CONFIG_BLUETOOTH_ENABLE = 1;
	/**
	 * 禁用蓝牙
	 */
	public final static int CONFIG_BLUETOOTH_DISABLE = 2;

	/**
	 * 摄像头配置
	 */
	public final static String CONFIG_CAMERA = "camera";
	/**
	 * 启用摄像头
	 */
	public final static int CONFIG_CAMERA_ENABLE = 1;
	/**
	 * 禁用摄像头
	 */
	public final static int CONFIG_CAMERA_DISABLE = 2;

	/**
	 * 审计配置
	 */
	public final static String CONFIG_AUDIT = "audit";
	/**
	 * 启用审计
	 */
	public final static int CONFIG_AUDIT_ENABLE = 1;
	/**
	 * 禁用审计
	 */
	public final static int CONFIG_AUDIT_DISABLE = 2;

	/**
	 * 数据加密配置
	 */
	public final static String CONFIG_DATA_ENCRYPT = "data_encrypt";
	/**
	 * 启用数据加密
	 */
	public final static int CONFIG_DATA_ENCRYPT_ENABLE = 1;
	/**
	 * 禁用数据加密
	 */
	public final static int CONFIG_DATA_ENCRYPT_DISABLE = 2;

	/**
	 * 密码配置
	 */
	public final static String CONFIG_PASSWORD = "nari.mip.console.model.PasswordConfig";
	/**
	 * 密码最大输入次数
	 */
	public final static String CONFIG_PWD_TIME_MAX = "pwd_time_max";
	/**
	 * 密码最小长度
	 */
	public final static String CONFIG_PWD_LENGTH_MIN = "pwd_length_min";
	/**
	 * 密码强度配置
	 */
	public final static String CONFIG_PASSWORD_QUALITY = "pwd_intensity";
	/**
	 * 密码强度：无限制
	 */
	public final static int CONFIG_PASSWORD_QUALITY_UNSPECIFIED = 0;
	/**
	 * 密码强度：包含字母
	 */
	public final static int CONFIG_PASSWORD_QUALITY_ALPHABETIC = 1;
	/**
	 * 密码强度：包含数字
	 */
	public final static int CONFIG_PASSWORD_QUALITY_NUMERIC = 2;
	/**
	 * 密码强度：包含字母和数字
	 */
	public final static int CONFIG_PASSWORD_QUALITY_ALPHANUMERIC = 3;
}
