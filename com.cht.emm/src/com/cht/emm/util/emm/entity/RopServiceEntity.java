package com.cht.emm.util.emm.entity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.cht.emm.util.emm.util.RopSignUtil;



public class RopServiceEntity extends RopSystemEntity {
    Map<String, String> serviceMap = new HashMap<String, String>();
    Map<String, String> sysMap = new HashMap<String, String>();
    public Map<String, String> allMap = new HashMap<String, String>();

    /**
     * @description 添加业务参数字段
     * @param key
     * @param value
     */
    public synchronized void put(String key, String value) {
        // if(key == null || key.length() <= 0){
        // System.err.println("参数名不能为空");
        // }
        // if(value==null){
        // System.err.println("参数值不能为null");
        // }
        // Assert.assertTrue("参数名不能为空", key == null || key.length() <= 0);

        if (serviceMap.containsKey(key)) {
            System.err.println("输入参数重复了，key值为：" + key);
        }
        serviceMap.put(key, value);
    }

    /**
     * @description 添加业务参数字段
     * @param urlParams like
     *            https://127.0.0.1:8443/thirdpartaccess?appKey=00001&
     *            method=mobileark
     *            .ssocheck&v=1.0&format=json&type=2&sessionId=868
     *            B5CD23E07F0A1C017FACD18718DF3
     * @throws UnsupportedEncodingException
     */
    public synchronized void putUrl(String urlParams) {
        try {
            allMap = parseUrlParams(urlParams);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * parse url
     * 
     * @throws UnsupportedEncodingException
     */
    protected Map<String, String> parseUrlParams(String urlParams) throws UnsupportedEncodingException {
        String tmp = urlParams;
        if (urlParams.indexOf("?") > -1) {
            tmp = urlParams.substring(urlParams.indexOf("?"));
        }
        String[] arr = tmp.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < arr.length; i++) {
            int index = arr[i].indexOf("=");
            if (index > -1) {
                String key = arr[i].substring(0, index);
                String value = arr[i].substring(index + 1);
                System.out.println("key:" + key + "\t value:" + value);
                map.put(key, value);
            }
        }
        return map;
    }

    public String sign(String secret) {
        sysMap = toMap();
        String sign = "";
        if (allMap.size() != 0 && !allMap.containsKey("sign")) {
            if (!sysMap.isEmpty()) {
                allMap.putAll(sysMap);
            }
            sign = sign + RopSignUtil.sign(allMap, secret);
            allMap.put("sign", sign);
        }
        else if (serviceMap.size() != 0 && !serviceMap.containsKey("sign")) {
            if (!sysMap.isEmpty()) {
                serviceMap.putAll(sysMap);
            }
            sign = sign + RopSignUtil.sign(serviceMap, secret);
            serviceMap.put("sign", sign);
        }
        else {
            allMap.putAll(sysMap);
            sign = sign + RopSignUtil.sign(allMap, secret);
            allMap.put("sign", sign);
        }
        return sign;
    }

    @Override
    public String toString() {
        String postBody = "";
        if (allMap.size() > serviceMap.size()) {
            postBody = genePostBody(allMap);
        }
        else {
            postBody = genePostBody(serviceMap);
        }
        return postBody;
    }

    private String genePostBody(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> m : map.entrySet()) {
            sb.append(m.getKey());
            sb.append("=");
            sb.append(specialChar(m.getKey(), m.getValue()));
            sb.append("&");
        }
        String body = sb.toString();
        if (body.endsWith("&")) {
            body = body.substring(0, body.length() - 1);
        }
        return body;
    }

    /** 当数据中存在加号(+)、连接符(&)或者百分号(%)时，服务器端接收数据时会丢失数据 */
    private String specialChar(String key, String value) {
        if (excludeKey(key)) {
            return value;
        }
        if (value == null) {
            return value;
        }
        value = value.replaceAll("%", "%25");
        value = value.replaceAll("&", "%26");
        value = value.replaceAll("[+]", "%2B");
        return value;
    }

    private boolean excludeKey(String key) {
        return sysMap.containsKey(key);
    }

    /** 增加业务请求参数 */
    public void addServiceParam(Map<String, String> params) {
        for (String key : params.keySet()) {
            if (serviceMap.containsKey(key)) {
                System.err.println("输入参数重复了，key值为：" + key);
            }
            serviceMap.put(key, params.get(key));
        }
    }

}
