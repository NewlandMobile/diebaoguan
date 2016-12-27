package com.lin.diebaoguan.common;

import android.content.Context;
import android.util.Log;

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

public class CommonUtils {
    /* token 使用固定值 */
    private static String tokenA = "d19cf361181f5a169c107872e1f5b722";
    private static String URL = "http://api.cnmo.com/client";


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
     * @param context
     * @param params
     * @param volleyListener
     */
    public static void httpPost(Context context, Map<String, String> params, VolleyListener volleyListener) {
        HTTPUtils.post(context, URL, params, volleyListener);
    }

    /**
     * get请求不带参数
     *
     * @param context
     * @param url
     * @param volleyListener
     */
    public static void httpGet(Context context, String url, VolleyListener volleyListener) {
        HTTPUtils.get(context, url, volleyListener);
    }

    /**
     * get请求带参数
     *
     * @param context
     * @param params
     * @param volleyListener
     */
    public static void httpGet(Context context, Map<String, String> params, VolleyListener volleyListener) {
        StringBuilder encodedParams = new StringBuilder();
        String url = "";
        Iterator var5 = params.entrySet().iterator();
        while (var5.hasNext()) {
            java.util.Map.Entry uee = (java.util.Map.Entry) var5.next();
            encodedParams.append(uee.getKey());
            encodedParams.append('=');
            encodedParams.append(uee.getValue());
            encodedParams.append('&');
        }
        url = URL + "?" + encodedParams.toString();
        String finalUrl = url.substring(0, url.length() - 1);
        Log.e("get请求带参数", "==URL：" + finalUrl);
        HTTPUtils.get(context, finalUrl, volleyListener);
    }
}

