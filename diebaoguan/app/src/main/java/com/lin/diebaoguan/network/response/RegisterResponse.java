package com.lin.diebaoguan.network.response;

import com.lin.diebaoguan.network.bean.Data;

/**
 * It's Created by NewLand-JianFeng on 2016/12/28.
 */

public class RegisterResponse extends BaseResponseTemplate {
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
