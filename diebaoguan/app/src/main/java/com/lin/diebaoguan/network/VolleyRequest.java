package com.lin.diebaoguan.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.lib_volley_https.VolleyListener;

import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * It's Created by NewLand-JianFeng on 2016/12/28.
 */

public class VolleyRequest <T extends BaseResponseTemplate> extends JsonRequest <T>{
    static final String tag=VolleyRequest.class.getName();

    private final Class<T> mClass;
    private String params;
    private String url;


    private VolleyRequest(int method, String url, String requestBody, Response.Listener listener, Response.ErrorListener errorListener,Class<T> mClass) {
        super(method, url, requestBody, listener, errorListener);
        this.url=url;
        this.mClass=mClass;

    }

    public VolleyRequest(int method, String url, Class<T> mClass, final VolleyListener volleyListener){
        this(method, url, null, new Response.Listener<T>() {
            @Override
            public void onResponse(T o) {
                volleyListener.onResponse(o);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyListener.onErrorResponse(volleyError);
            }
        }, mClass);
    }

    public void setParamsString(String params){
        this.params=params;
        setRedirectUrl(url+"?"+params);
    }

    @Override
    public byte[] getBody() {
//        byte[] data= new byte[0];
//        try {
//            data = params.getBytes("UTF-8");
//            LogUtils.e(params);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return data;
        return super.getBody();
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser
                    .parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        LogUtils.e(parsed);
//        parsed = getDebugData(parsed);
        T result =  T.parseObject(parsed,mClass);
        if (result==null){
            LogUtils.e("返回结果解析失败");
            return Response.error(new VolleyError("本地对象解析失败"));
        }else {
            LogUtils.e("返回结果解析成功");
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        }
//        return null;
    }
}
