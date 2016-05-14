/**   
 * @Title: KeyValue.java 
 * @Package com.nari 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-12 下午4:58:34 
 * @version V1.0   
 */
package com.cht.emm.util;

/**
 * @ClassName: KeyValue
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-12 下午4:58:34
 * 
 */
public class KeyValue<T, V> {
	private T key;
	private V value;

	/**
	 * @return key
	 */
	public T getKey() {
		return key;
	}

	/**
	 * @param key
	 *            要设置的 key
	 */
	public void setKey(T key) {
		this.key = key;
	}

	/**
	 * @return valueV
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param valueV
	 *            要设置的 valueV
	 */
	public void setValue(V value) {
		this.value = value;
	}
}
