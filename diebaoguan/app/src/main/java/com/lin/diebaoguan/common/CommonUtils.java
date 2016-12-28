package com.lin.diebaoguan.common;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.network.VolleyRequest;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.BaseSendTemplate;
import com.lin.lib_volley_https.HTTPUtils;
import com.lin.lib_volley_https.VolleyListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lin on 2016/12/2311:07.
 * mail :1057705307@QQ.com.
 * describe:工具类
 */

public class CommonUtils <T extends BaseResponseTemplate>{
    /* token 使用固定值 */
    private static String tokenA = "d19cf361181f5a169c107872e1f5b722";
    private static String URL = "http://api.cnmo.com/client";

    private static CommonUtils commonUtils=new CommonUtils();

    public static CommonUtils getInstance(){
        return commonUtils;
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken(long timeMillis) {
        String token1;
        token1 = md5(tokenA + timeMillis);
        return token1;
    }

    public static String md5(String str) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));

            }

            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * post请求
     *
     * @param params
     * @param volleyListener
     */
    public static void httpPost(Map<String, String> params, VolleyListener volleyListener) {
        HTTPUtils.post(MyAppication.getInstance(), URL, params, volleyListener);
    }

    /**
     * get请求不带参数
     *
     * @param url
     * @param volleyListener
     */
    public static void httpGet(String url, VolleyListener volleyListener) {
        HTTPUtils.get(MyAppication.getInstance(), url, volleyListener);
    }

    /**
     * get请求带参数
     *
     * @param params
     * @param volleyListener
     */
    public static void httpGet(Map<String, String> params, VolleyListener volleyListener) {
        StringBuilder encodedParams = new StringBuilder();
        String url = "";
        Iterator<Map.Entry<String, String>> var5 = params.entrySet().iterator();
        while (var5.hasNext()) {
            Map.Entry<String, String> uee = var5.next();
            encodedParams.append(uee.getKey());
            encodedParams.append('=');
            encodedParams.append(uee.getValue());
            encodedParams.append('&');
        }
        url = URL + "?" + encodedParams.toString();
        String finalUrl = url.substring(0, url.length() - 1);
        Log.e("get请求带参数", "==URL：" + finalUrl);
        HTTPUtils.get(MyAppication.getInstance(), finalUrl, volleyListener);
    }

    public void  JsonPost(Class<T> responseClass, BaseSendTemplate params, VolleyListener volleyListener){
        VolleyRequest<T> volleyRequest=new VolleyRequest<>(Request.Method.POST,URL,responseClass,volleyListener);
        volleyRequest.setParamsString(params.parseParams());
        RequestQueue requestQueue = HTTPUtils.getmRequestQueue();
        if (requestQueue==null){
            HTTPUtils.init(MyAppication.getInstance());
            requestQueue=HTTPUtils.getmRequestQueue();
        }
        volleyRequest.setShouldCache(true);
        requestQueue.add(volleyRequest);


    }
}

