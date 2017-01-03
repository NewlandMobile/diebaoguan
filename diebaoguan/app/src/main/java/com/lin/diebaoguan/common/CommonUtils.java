package com.lin.diebaoguan.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.DieBaoGuanAndFengShangBiaoDS;
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

public class CommonUtils<T extends BaseResponseTemplate> {
    /* token 使用固定值 */
    private static String tokenA = "d19cf361181f5a169c107872e1f5b722";
    private static String URL = "http://api.cnmo.com/client";
    //  这个固定值用的页面也会比较多，可以考虑放到底层基类去。
    private static final String moduleString = "api_libraries_sjdbg_articlelist";

    private static CommonUtils commonUtils = new CommonUtils();

    public static CommonUtils getInstance() {
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

    /**
     * 显示加载进度框
     *
     * @param contenx
     */
    public static ProgressDialog showProgressDialog(Context contenx) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(contenx);
        progressDialog.setTitle("");
        progressDialog.setMessage("正在拼命加载。。。");
        return progressDialog;
    }

    /**
     * 显示加载进度框
     *
     * @param contenx
     */
    public static ProgressDialog showProgressDialog(Context contenx, String title, String message) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(contenx);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        return progressDialog;
    }


    /**
     * 根据 具体板块内容，获取后台信息
     * 用于谍报馆与风尚标模块
     *
     * @param isFengShangBiao 是否属于风尚标板块
     * @param detailPageNum   具体板块的数值 （当isclass=0时，传值1,2,3,4分别对应谍报馆：新品，价格，体验，应用.
     *                        当isclass=1时，传值1,2,3,4分别对应风尚标：综合，爱美妆，爱美访，雯琰文
     *                        ）
     */
    public static void fetchDataFromNetWork(boolean isFengShangBiao, int detailPageNum, VolleyListener volleyListener) {
        DieBaoGuanAndFengShangBiaoDS sendParams = new DieBaoGuanAndFengShangBiaoDS();
        sendParams.setModule(moduleString);
        sendParams.setIsclass(isFengShangBiao ? 1 : 0);
        sendParams.setCid(detailPageNum);
        //TODO 这几个参数后期要改活的
        sendParams.setOffset(0);
        sendParams.setRows(12);
        sendParams.initTimePart();

        CommonUtils.httpGet(sendParams.parseParams(), volleyListener);
    }

    /**
     * 通过sp保存信息
     *
     * @param key    键
     * @param values 值
     */
    public static void saveBySp(Context context, String key, String values) {
        SharedPreferences sp = context.getSharedPreferences("DieBaoGuan", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, values);
        edit.commit();
    }

    /**
     * 获取sp保存的 数据
     *
     * @param context
     * @param key
     *
     * @return
     */
    public static String getSp(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("DieBaoGuan", Context.MODE_PRIVATE);
        String result = sp.getString(key, "");
        return result;
    }
}

