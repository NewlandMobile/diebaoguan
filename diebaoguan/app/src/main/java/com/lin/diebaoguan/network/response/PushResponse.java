package com.lin.diebaoguan.network.response;

import com.lin.diebaoguan.network.bean.Data;

/**
 * Created by ASUS on 2017/2/13.
 */

public class PushResponse extends BaseResponseTemplate {
    private Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }
}
