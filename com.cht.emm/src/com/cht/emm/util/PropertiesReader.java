/**   
 * @Title: PropertiesReader.java 
 * @Package nari.mip.backstage.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-29 下午3:10:01 
 * @version V1.0   
 */
package com.cht.emm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: PropertiesReader
 * @Description: 读取配置文件中的属性信息
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-29 下午3:10:01
 * 
 */
public class PropertiesReader {
	private String file;
	private String path;

	private Map<String, String> properties;
	private List<String> propertyNames;
	Properties prop = null;

	/**
	 * @return file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file
	 *            要设置的 file
	 */
	public void setFile(String file) {
		this.file = file;
	}

	public PropertiesReader(String file) throws IOException {
		this.file = file;
		this.properties  = new HashMap<String, String>();
		this.propertyNames = new ArrayList<String>();
		prop = new Properties();
		InputStream fis = null;
		if (file.toLowerCase().startsWith("classpath")) {
			file = file.replace("classpath:", "");
			fis = this.getClass().getClassLoader().getResourceAsStream(file);
			path = this.getClass().getClassLoader().getResource(file).getPath();
		} else {
			fis = new FileInputStream(file);
			path = file;
		}
		prop.load(fis);
		Enumeration<?> proNames = prop.propertyNames();
		while (proNames.hasMoreElements()) {
			String object = (String) proNames.nextElement();
			propertyNames.add(object);
			properties.put(object, prop.getProperty(object));
		}
	}

	public String getString(String name) {
		if (properties.get(name) != null) {
			return properties.get(name);
		} else {
			String value = prop.getProperty(name);
			if (!StringUtil.isNullOrBlank(value)) {
				properties.put(name, value);
			}
			return value;
		}
	}

	public Integer getInt(String name) {
		String value = getString(name);
		if (!StringUtil.isNullOrBlank(value)) {
			return Integer.parseInt(value);
		} else {
			return null;
		}

	}
	
	public boolean getBoolean(String name){
		String value = getString(name);
		if(!StringUtil.isNullOrBlank(value)){
			if(value.toLowerCase().equals("true")){
				return true;
			}else if(value.toLowerCase().equals("false")){
				return false;
			}else {
				try {
					if(Integer.parseInt(name)>0){
						return true;
					}else{
						return  false;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			return false;
	}
	
	public void setValue(String key,String value){
		if(properties.get(key)!=null){
			properties.put(key, value);
		}
		 OutputStream fos;
		try {
			if (file.toLowerCase().startsWith("classpath")) {
				
				fos = new FileOutputStream(path);
			} else {
				fos = new FileOutputStream(file);
			}
			fos = new FileOutputStream(path);
			prop.setProperty(key, value);
			prop.store(fos, "Update '" + key + "' value");
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getProperties(){
		
		return properties;
	}

	public List<String> getPropertyNames(){
		return propertyNames;
	}
}
