package com.lin.diebaoguan.network.response;

import com.google.gson.Gson;

/**
 * It's Created by NewLand-JianFeng on 2016/12/28.
 */

public class BaseResponseTemplate {
    static Gson gson=new Gson();
    //
    int status;
    //
    String code;
    //
    String message;
//    //
//    String data;
//    //
//    String uid;
//    // 授权key
//    String key;

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson gson) {
        BaseResponseTemplate.gson = gson;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }

    public static <T extends BaseResponseTemplate> T parseObject(String jsonString, Class<T> clazz){
        return gson.fromJson(jsonString,clazz);
    }

    @Override
    public String toString() {
//        return super.toString();
        return new Gson().toJson(this);
    }
}
