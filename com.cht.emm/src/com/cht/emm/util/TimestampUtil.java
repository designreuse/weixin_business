package com.cht.emm.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimestampUtil {
	private static final SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒

	public static String toString(Timestamp time) {
		if (time == null) {
			return "";
		}
		return df.format(time);
	}
}
