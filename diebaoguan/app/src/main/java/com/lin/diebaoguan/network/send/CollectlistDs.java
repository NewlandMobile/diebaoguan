package com.lin.diebaoguan.network.send;

/**
 * Created by linx on 2017/1/1911:09.
 * mail :1057705307@QQ.com.
 * describe:收藏列表
 */
public class CollectlistDS extends BaseSendTemplate {
    private String cid;
    private String uid;
    private String offset;
    private String rows;

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOffset() {
        return offset;
    }

    public String getRows() {
        return rows;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
}
