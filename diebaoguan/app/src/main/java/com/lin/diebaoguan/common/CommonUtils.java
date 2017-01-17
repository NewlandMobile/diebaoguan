package com.lin.diebaoguan.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Log;

import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.BaseSendTemplate;
import com.lin.diebaoguan.network.send.CommentDS;
import com.lin.diebaoguan.network.send.NormalDS;
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
        com.lin.lib_volley_https.HTTPUtils.post(MyAppication.getInstance(), URL, params, volleyListener);
    }

    /**
     * post请求
     *
     * @param fragment       fragment
     * @param params
     * @param volleyListener
     */
    public static void httpPost(Fragment fragment, Map<String, String> params, VolleyListener volleyListener) {
        HttpUtils.post(MyAppication.getInstance(), fragment, URL, params, volleyListener);
    }

    /**
     * get请求不带参数
     *
     * @param url
     * @param volleyListener
     */
    public static void httpGet(String url, VolleyListener volleyListener) {
        com.lin.lib_volley_https.HTTPUtils.get(MyAppication.getInstance(), url, volleyListener);
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
        com.lin.lib_volley_https.HTTPUtils.get(MyAppication.getInstance(), finalUrl, volleyListener);
    }

    /**
     * get请求带参数,传入fragment
     *
     * @param fragment       fragment
     * @param params
     * @param volleyListener
     */
    public static void httpGet(Fragment fragment, Map<String, String> params, VolleyListener volleyListener) {
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
        HttpUtils.get(MyAppication.getInstance(), fragment, finalUrl, volleyListener);
    }

    /**
     * 显示加载进度框
     *
     * @param contenxt
     */
    public static ProgressDialog showProgressDialog(Context contenxt) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(contenxt);
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
     * @param fragment
     * @param cid            传值1,2,3,4,5分别对应光影集：综合，精品原创，数码漫谈，手机美图，平板美图.
     * @param pageOffset         获取第几页
     * @param volleyListener 响应监听
     */
    public static void fetchDataAtGyjPage(PullToRefreshBaseFragment fragment, int cid, int pageOffset, VolleyListener volleyListener) {
        NormalDS params = new NormalDS();
        params.setModule("api_libraries_sjdbg_tulist");

        params.setCid(cid);
        params.setOffset(pageOffset * Const.ROWS);
        params.setRows(Const.ROWS);
        normalGetWayFetch(params, fragment, volleyListener);
    }

    public static void normalGetWayFetch(BaseSendTemplate sendParams, PullToRefreshBaseFragment fragment, VolleyListener volleyListener) {
        sendParams.initTimePart();
        CommonUtils.httpGet(fragment, sendParams.parseParams(), volleyListener);
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
    public static void fetchDataAtFsbOrDbg(boolean isFengShangBiao, int detailPageNum, VolleyListener volleyListener) {
        NormalDS sendParams = new NormalDS();
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
     * 根据 具体板块内容，获取后台信息
     * 用于谍报馆与风尚标模块
     *
     * @param pageOffset          获取第几页
     * @param isFengShangBiao 是否属于风尚标板块
     * @param detailPageNum   具体板块的数值 （当isclass=0时，传值1,2,3,4分别对应谍报馆：新品，价格，体验，应用.
     *                        当isclass=1时，传值1,2,3,4分别对应风尚标：综合，爱美妆，爱美访，雯琰文
     *                        ）
     */
    public static void fetchDataAtFsbOrDbg(int pageOffset, Fragment fragment, boolean isFengShangBiao, int detailPageNum, VolleyListener volleyListener) {
        NormalDS sendParams = new NormalDS();
        sendParams.setModule(moduleString);
        sendParams.setIsclass(isFengShangBiao ? 1 : 0);
        sendParams.setCid(detailPageNum);
        sendParams.setOffset(pageOffset * Const.ROWS);
        sendParams.setRows(Const.ROWS);
        sendParams.initTimePart();

        CommonUtils.httpGet(fragment, sendParams.parseParams(), volleyListener);
    }

    /**
     * 发表评论
     *
     * @param content        内容
     * @param docid          文章id
     * @param volleyListener 监听
     */
    public static void sendComment(String content, String docid, VolleyListener volleyListener) {
        CommentDS commentDS = new CommentDS();
        commentDS.setAuthkey(MyAppication.getKey());
        commentDS.setModule("api_libraries_sjdbg_comment");
        commentDS.setDocid(docid);
        commentDS.setUsername(MyAppication.getUserName());
        commentDS.setUid(MyAppication.getUid());
        commentDS.setContent(content);
        commentDS.initTimePart();

        CommonUtils.httpPost(commentDS.parseParams(), volleyListener);

    }

    /**
     * 通过sp保存信息
     *
     * @param key    键
     * @param values 值
     */
    public static void saveBySp(Context context, String key, Object values) {
        SharedPreferences sp = context.getSharedPreferences("DieBaoGuan", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (values instanceof String) {
            edit.putString(key, (String) values);
        } else if (values instanceof Boolean) {
            edit.putBoolean(key, (Boolean) values);
        } else if (values instanceof Integer) {
            edit.putInt(key, (Integer) values);
        } else if (values instanceof Float) {
            edit.putFloat(key, (Float) values);
        }

        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
//        edit.commit();
    }

    /**
     * 获取sp保存的 数据
     *
     * @param context
     * @param key
     * @return
     */
    public static Object getSp(Context context, String key, Object defValue) {
        SharedPreferences sp = context.getSharedPreferences("DieBaoGuan", Context.MODE_PRIVATE);
        Object result = null;
        if (!sp.contains(key)) {
            return defValue;
        }
        if (defValue instanceof String) {
            result = sp.getString(key, (String) defValue);
        } else if (defValue instanceof Boolean) {
            result = sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Integer) {
            result = sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Float) {
            result = sp.getFloat(key, (Float) defValue);
        }
        return result;
    }
}

