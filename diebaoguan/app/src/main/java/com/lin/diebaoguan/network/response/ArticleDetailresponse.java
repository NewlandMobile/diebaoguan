package com.lin.diebaoguan.network.response;

import com.lin.diebaoguan.network.bean.Data;

/**
 * Created by linx on 2017/1/316:21.
 * mail :1057705307@QQ.com.
 * describe:
 */
public class ArticleDetailresponse extends BaseResponseTemplate {
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
