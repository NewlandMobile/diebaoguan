package com.lin.diebaoguan.network.send;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.lin.diebaoguan.common.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
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

    String returnformat;

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

//        Field[] fields= this.getClass().getDeclaredFields();
//        Class stringClass=String.class;
//        for (Field field : fields){
////            field.setAccessible(true);
//            String name=field.getName();
//            String value=null;
//            if (!stringClass.equals(field.getType())){
//                continue;
//            }
//            try {
//                value= (String) field.get(this);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            params.put(name,value);
//        }

//        params.put("apiid", 3 + "");
//        params.put("token1", token1);
//
//        params.put("module", "api_libraries_sjdbg_startuplogo");
//        params.put("returnformat", "json");
//        params.put("encoding", "utf8");
//        String jsonString = toString();

        return params;

        //        try {
//            // TODO 回头考虑一下 这两行代码的 必要性
//            JSONObject jsonObject = new JSONObject(jsonString);
//            jsonString = jsonObject.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

}
