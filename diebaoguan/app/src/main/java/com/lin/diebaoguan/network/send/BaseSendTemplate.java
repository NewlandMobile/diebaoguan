package com.lin.diebaoguan.network.send;

/**
 * It's Created by NewLand-JianFeng on 2016/12/28.
 */

public class BaseSendTemplate {
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


}
