package com.lin.diebaoguan.network.send;

import com.google.gson.Gson;
import com.lin.diebaoguan.common.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * It's Created by NewLand-JianFeng on 2016/12/28.
 */

public class BaseSendTemplate {
    static Gson gson= new Gson();

    String apiid="3";
    String timestamp;
    String token1;
    String module;
    String returnformat="json";
    String encoding="utf8";

    public String getApiid() {
        return apiid;
    }

    public void setApiid(String apiid) {
        this.apiid = apiid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken1() {
        return token1;
    }

    public void setToken1(String token1) {
        this.token1 = token1;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getReturnformat() {
        return returnformat;
    }

    public void setReturnformat(String returnformat) {
        this.returnformat = returnformat;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void initTimePart(){
        long currentTime = System.currentTimeMillis() / 1000;
        setTimestamp(String.valueOf(currentTime));
        setToken1(CommonUtils.getToken(currentTime));
    }

    public Map<String, String> parseParams(){
        Map<String, String> params = new HashMap<>();
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()){
            String name=keys.next();
            String value=null;
            try {
                value=jsonObject.getString(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (value!=null){
                params.put(name,value);
            }
        }
        return params;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

}
