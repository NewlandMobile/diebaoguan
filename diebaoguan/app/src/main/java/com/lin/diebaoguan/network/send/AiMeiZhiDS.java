package com.lin.diebaoguan.network.send;

/**
 * Created by linx on 2017/1/1515:06.
 * mail :1057705307@QQ.com.
 * describe:爱美志
 */
public class AiMeiZhiDS extends BaseSendTemplate {
    private String offset;
    private String rows;


    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
