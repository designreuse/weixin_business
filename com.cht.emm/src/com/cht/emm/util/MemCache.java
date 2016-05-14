/**
 * @Title: MemCache.java
 * @Package: nari.mip.backstage.util
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-1-9 上午9:57:12
 * @Version: 1.0
 */
package com.cht.emm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Class: MemCache
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class MemCache {
	private Map<String, Map> caches = new HashMap<String, Map>();
	public  Map getCache(String key){
		synchronized (caches) {
			Map cache = caches.get(key);
			if(cache == null) {
				cache = new ConcurrentHashMap();
			}
			caches.put(key, cache);
		}
		return caches.get(key);
	}
	
	
	
}
