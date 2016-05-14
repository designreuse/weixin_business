/**
 * @Title: LocalTestValidator.java
 * @Package: nari.mip.backstage.rpc.remoteValid
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-2 上午10:33:08
 * @Version: 1.0
 */
package com.cht.emm.rpc.remoteValid;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.cht.emm.util.Method;
import com.cht.emm.util.RemoteValidator;
import com.cht.emm.util.Response;
import com.cht.emm.util.RestClient;


/**
 * @Class: LocalTestValidator
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class LocalTestValidator implements RemoteValidator {

	/* (non-Javadoc)
	 * @see nari.mip.backstage.util.RemoteValidator#valid(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Response valid(String userName, String password,String url, String others) {
		// TODO Auto-generated method stub
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("userName", userName));
		parameters.add(new BasicNameValuePair("password", password));
		parameters.add(new BasicNameValuePair("other",others));
		String val = RestClient.getBody(Method.Post, "http://localhost:8080/mip/rest/user/valid", parameters);
		JSONObject result = JSONObject.parseObject(val);
		Response response = new Response();
		if(result.getBooleanValue("successful")){
			response.setSuccessful(true);
		}
		return response;
	}

}
