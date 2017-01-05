package com.lin.diebaoguan.network.response;

import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.bean.Paging;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;

/**
 * Created by Alan on 2017/1/1 0001.
 */

public class NormalResponse extends BaseResponseTemplate {
//    文章信息返回值
//    Result result;
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
