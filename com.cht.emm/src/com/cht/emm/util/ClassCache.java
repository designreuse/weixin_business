package com.cht.emm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;


/**
 * @Class: ClassCache
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 远程验证类的类名存储器，对存储类实行单例管理
 */
public class ClassCache {
	/**
	 * @Name: getInstance
	 * @Decription: double check
	 * @Time: 2015-4-2 上午9:56:06
	 * @return ClassCache
	 */
	public static ClassCache getInstance(){
		if(cache==null){
			synchronized (obj) {
				if(cache == null ){
					cache = new ClassCache();
				}
			}
		}
		return cache;
	}
	
	private static ClassCache cache;
	
	private ClassCache(){}
	
	public static Object obj = new Object();
	
	Semaphore semaphore = new Semaphore(1);
	Map<String, Object> objectCacheMap = new HashMap<String, Object>();
	public Object getClassInstance(String className) throws ClassNotFoundException{
		Object object =null;
		
		try {
			semaphore.acquire();
			if(!objectCacheMap.containsKey(className)){
				Class class1 = Class.forName(className);
				object =class1.newInstance();
				objectCacheMap.put(className, object);
			}else {
				object = objectCacheMap.get(className);
			}
			semaphore.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(semaphore.availablePermits()==0){
				semaphore.release();
			}
		}
		
		
		return object;
	}
}
