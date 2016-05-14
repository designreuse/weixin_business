package com.cht.emm.util;

import java.util.ArrayList;
import java.util.List;

public class ByteTool {
	/**
	 * 计算某个资源所具有的操作权限的和值
	 * 
	 * @param indexes
	 *            所有需要左移的参数
	 * @return Integer
	 */
	public static Integer sum(List<Integer> indexes) {

		Integer result = 0;
		for (Integer i : indexes) {
			System.out.println(1 << (i - 1));
			result += 1 << (i - 1);
		}
		return result;
	}

	/**
	 * 计算sum对应index位的二进制值是否为1，用于权限判断
	 * 
	 * @param index
	 * @param sum
	 * @return boolean
	 */
	public static boolean valid(int index, int sum) {
		boolean result = false;
		int value = 1 << (index - 1);
		if ((value & sum) > 0) {
			result = true;
		}
		return result;
	}

	public static void main(String[] args) {
		List<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			indexes.add(i + 1);
		}
		System.out.println(sum(indexes));
	}
}
