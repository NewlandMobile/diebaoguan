package com.lin.diebaoguan.network.response;

import com.google.gson.Gson;

/**
 * It's Created by NewLand-JianFeng on 2016/12/28.
 */

public class BaseResponseTemplate {
    static Gson gson=new Gson();
    //
    String status;
    //
    String code;
    //
    String message;
    //
    String data;
    //
    String uid;
    // 授权key
    String key;

    public static <T extends BaseResponseTemplate> T parseObject(String jsonString, Class<T> clazz){
        return gson.fromJson(jsonString,clazz);
    }

    @Override
    public String toString() {
//        return super.toString();
        return new Gson().toJson(this);
    }
}
