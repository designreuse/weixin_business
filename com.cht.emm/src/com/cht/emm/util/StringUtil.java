package com.cht.emm.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 * 
 * @author cht
 * 
 */
public class StringUtil {

	/**
	 * 判断字符串是否为Null或者""
	 * 
	 * @param str
	 * @return true 表示为空
	 */
	public static boolean isNullOrEmpty(String str) {

		if (null == str || str.equals(""))

			return true;

		return false;
	}

	/**
	 * 判断字符串是否为Null或者""，"    "，检查条件更为严格。
	 * 
	 * @param str
	 * @return true 表示为空
	 */
	public static boolean isNullOrBlank(String str) {

		if (null == str || str.length() == 0)
			return true;

		for (int i = 0; i < str.length(); i++) {

			char c = str.charAt(i);

			if (c != ' ')
				return false;
		}

		return true;
	}
	
	/**
	 * @Name: filterId
	 * @Decription: 过滤所有id 
	 * @Time: 2014-12-30 下午3:38:35
	 * @param id
	 * @return String
	 */
	public static String filterId(String id){
		if(id== null || id.length()<36){
			if(!"-1".equals(id) && !"#".equals(id) && id!=null ){
				return id;
			}
			return null;
		}else {
			return id;
		}
		
	}
	
	public static List<String> getList(String[] ids) {
		if(ids == null){
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (String string : ids) {
			if(!isNullOrBlank(string)){
				list.add(string);
			}
			
		}
		return list;
	}
}
