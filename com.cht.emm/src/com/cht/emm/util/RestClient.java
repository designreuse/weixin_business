/**   
 * @Title: RestClient.java 
 * @Package nari.mip.backstage.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-28 下午2:53:14 
 * @version V1.0   
 */
package com.cht.emm.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cht.emm.rpc.model.RPCGroup;

/**
 * @ClassName: RestClient
 * @Description: Rest 服务客户端
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-28 下午2:53:14
 * 
 */
public class RestClient {

	/**
	 * 
	 * @Title: getBody
	 * @Description: 发送标准的http请求
	 * @param method
	 * @param url
	 * @param parameters
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getBody(Method method, String url,
			List<NameValuePair> parameters) {
		String body = null;
		CloseableHttpClient hc = HttpClients.createDefault();
		if (method == Method.GET) {
			try {

				// Get请求
				HttpGet httpget = new HttpGet(url);
				// 设置参数
				String str = EntityUtils.toString(new UrlEncodedFormEntity(
						parameters));
				httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
				// 发送请求
				HttpResponse httpresponse = hc.execute(httpget);
				// 获取返回数据
				HttpEntity entity = httpresponse.getEntity();
				body = EntityUtils.toString(entity);

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} finally {
				try {
					hc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (method == Method.Post) {
			try {
				// Post请求
				HttpPost httppost = new HttpPost(url);

				UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(
						parameters);
				encodedFormEntity.setContentEncoding("utf-8");
				// 设置参数
				httppost.setEntity(encodedFormEntity);

				// 发送请求
				HttpResponse httpresponse = hc.execute(httppost);
				// 获取返回数据
				HttpEntity entity = httpresponse.getEntity();
				body = EntityUtils.toString(entity);
				hc.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					hc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	/**
	 * @Title: post
	 * @Description: 发送字符串
	 * @param url
	 * @param type
	 * @param string
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String postJSON(String url, String string) {
		String body = null;
		CloseableHttpClient hc = HttpClients.createDefault();
		try {
			// Post请求
			HttpPost httppost = new HttpPost(url);
			httppost.addHeader("Content-type", "text/x-json");
			httppost.addHeader("Accept-Charset", "UTF-8");
			httppost.addHeader("contentType", "UTF-8");
			// 设置参数
			StringEntity s = new StringEntity(string, "utf-8");
			// s.setContentEncoding("UTF-8");
			// s.setContentType("UTF-8");
			httppost.setEntity(s);

			// 获取返回数据
			HttpResponse httpresponse = hc.execute(httppost);
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				hc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}

	public static void testUpdate() {
		try {
			List<RPCGroup> organizations = new ArrayList<RPCGroup>();
			RPCGroup org1 = new RPCGroup();
			org1.setId("test_group_1111111111111");
			org1.setOrg_desc("研发中心");
			org1.setOrg_name("南瑞信通研发中心");
			org1.setParent_id(null);
			org1.setStatus(1);
			organizations.add(org1);
			RPCGroup org2 = new RPCGroup();
			org2.setId("test_group_2222222222222");
			org2.setOrg_desc("研发中心2");
			org2.setOrg_name("南瑞信通研发中心2");
			org2.setParent_id(null);
			org2.setStatus(1);
			organizations.add(org2);
			System.out.println(net.sf.json.JSONArray.fromObject(organizations)
					.toString());
			JSONArray json = net.sf.json.JSONArray.fromObject(organizations);
			// String json =
			// "[{    id: \"4da0465b-3263-4f0b-9356-46a4766cccf7\", parent_id:"
			// +
			// " \"21ce83d4-a163-4b58-9df0-35e00c9d363d\",org_name: \"营运中心\", org_desc: \"营运中心\", status: 1  }]";
			// JSONObject result
			// =getJsonObject(json,"http://42.96.206.64:8007/add_or_update_org");
			String result = postJSON(
					"http://42.96.206.64:8007/add_or_update_org",
					json.toString());
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		testUpdate();
	}
}
