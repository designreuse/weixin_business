package ${packageName};

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;

import nari.mip.backstage.util.Method;
import nari.mip.backstage.util.RemoteValidator;
import nari.mip.backstage.util.Response;
import nari.mip.backstage.util.RestClient;

/**
 * @Class: ${fileName}
 * @Author:  
 * @Description: 
 */
public class ${fileName} implements RemoteValidator {

	/* (non-Javadoc)
	 * @see nari.mip.backstage.util.RemoteValidator#valid(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Response valid(String userName, String password,String url, String others) {
		// TODO Auto-generated method stub
		Response response =new Response();
		/**
		 * 根据内容和协议处理消息的发送和接收
		 * RestClient主要方法
		 * 		String getBody(Method method, String url,List<NameValuePair> parameters)
		 * 	
		 * Method：
		 * 		Method.GET	:以GET的方式发送消息
		 * 		Method.POST	:以POST的方式发送消息
		 */
		return response;
	}

}