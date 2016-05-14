/**   
 * @Title: Status.java 
 * @Package nari.mip.backstage.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-23 下午3:46:44 
 * @version V1.0   
 */
package com.cht.emm.util;

/**
 * @ClassName: Status
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-23 下午3:46:44
 * 
 */
public enum Status {
	VALID_USER_NOT_EXIST(401, "用户不存在"), VALID_USER_DEVICE_ID_NOT_MATCH(402,
			"与用户设备不匹配"), PARAMETER_IS_EMPTY(101, "参数不完整"), VALID_USER_VALIDED(
			200, "通过验证")

	;

	private int code;
	private String desc;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return desc;
	}

	private Status(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * <p>
	 * Title: toString
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return code + ":" + desc;
	}

}
