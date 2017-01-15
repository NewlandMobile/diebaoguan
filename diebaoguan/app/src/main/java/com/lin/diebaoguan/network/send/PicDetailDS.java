package com.lin.diebaoguan.network.send;

/**
 * Created by linx on 2017/1/1516:48.
 * mail :1057705307@QQ.com.
 * describe:
 */
public class PicDetailDS extends BaseSendTemplate {
    private String docid;
    private String uid;
    private String size;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
