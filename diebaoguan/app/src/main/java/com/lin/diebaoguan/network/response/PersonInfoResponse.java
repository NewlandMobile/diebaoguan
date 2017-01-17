package com.lin.diebaoguan.network.response;

/**
 * It's Created by NewLand-JianFeng on 2017/1/17.
 */

public class PersonInfoResponse extends BaseResponseTemplate {
    /*username	字符串	用户名
    avatar	字符串	用户头像地址*/
    String username;
    String avater;

    public String getUsername() {
        return username;
    }

    public String getAvater() {
        return avater;
    }
}
