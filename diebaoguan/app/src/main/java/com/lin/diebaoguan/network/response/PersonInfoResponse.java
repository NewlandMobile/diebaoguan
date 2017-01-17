package com.lin.diebaoguan.network.response;

import com.lin.diebaoguan.network.bean.Data;

/**
 * It's Created by NewLand-JianFeng on 2017/1/17.
 */

public class PersonInfoResponse extends BaseResponseTemplate {

    private Data data;

    public class Data {
        /*username	字符串	用户名
    avatar	字符串	用户头像地址*/
        String username;
        String avatar;

        public String getUsername() {
            return username;
        }

        public String getAvatar() {
            return avatar;
        }
    }

    public Data getData() {
        return data;
    }

    //    public String getUsername() {
//        return username;
//    }
//
//    public String getAvater() {
//        return avater;
//    }
}
