package com.cht.emm.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResourceUrlManager {

	
	public static ResourceUrlManager getSingleton(){
		
		if(null==mSingleton){
			
			mSingleton=new ResourceUrlManager();
		}
		
		return mSingleton;
	}
	
	
	/**
	 * 获取spring mvc映射的所有rest路径
	 * @return
	 * @throws Exception
	 */
	public List<String> getAllResourceUrl() throws Exception{
		
		List<String> urlList=new ArrayList<String>();
		
		// 自定义过滤规则
		List<String> classFilters = new ArrayList<String>();
		classFilters.add("*Controller*");

		// 创建一个扫描处理器，排除内部类 扫描符合条件的类
		ClassPathScanHandler handler = new ClassPathScanHandler(true, true,
				classFilters);

//		System.out.println("开始递归扫描jar文件的包：nari.mip.backstage.controller 下符合自定义过滤规则的类...");
		Set<Class<?>> calssList = handler.getPackageAllClasses(
				"nari.mip.backstage.controller", true);

		// 遍历包下面的类列表
		for (Class<?> cla : calssList) {

			String parentLoc = "";
			// 找到RequestMapping类注解，和方法上的注解合并
			for (java.lang.annotation.Annotation anno : cla.getAnnotations()) {

				if (anno.annotationType().getName().contains("RequestMapping")) {

					Method m = anno.getClass().getDeclaredMethod("value", null);
					String[] values = (String[]) m.invoke(anno, null);
					if (values != null && values.length > 0)
						parentLoc = values[0];

					if (!parentLoc.startsWith("/"))
						parentLoc = "/" + parentLoc;

				}

			}

			java.lang.reflect.Method[] methods = cla.getMethods();

			// 遍历方法列表，找到RequestMapping注解
			for (java.lang.reflect.Method method : methods) {

				java.lang.annotation.Annotation[] annotaions = method
						.getAnnotations();

				for (java.lang.annotation.Annotation anno : annotaions) {

					if (anno.annotationType().getName()
							.contains("RequestMapping")) {

						String curLoc = "";
						Method m = anno.getClass().getDeclaredMethod("value",
								null);
						String[] values = (String[]) m.invoke(anno, null);

						if (values != null && values.length > 0) {

							curLoc = values[0];
							if (!curLoc.startsWith("/"))
								curLoc = "/" + curLoc;
						}

						curLoc = parentLoc + curLoc;

						curLoc.replace("//", "/");

//						System.out.println(curLoc);
						
						urlList.add(curLoc);

					}
				}
			}

		}
	
		return urlList;
	}
	
	private static ResourceUrlManager mSingleton;
	
	private ResourceUrlManager(){
	
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		List<String> urlList = ResourceUrlManager.getSingleton().getAllResourceUrl();
		
		for(String url:urlList){
			
			System.out.println(url);
		}
	}

}
