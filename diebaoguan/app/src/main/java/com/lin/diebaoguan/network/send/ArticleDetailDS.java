package com.lin.diebaoguan.network.send;

/**
 * Created by linx on 2017/1/315:37.
 * mail :1057705307@QQ.com.
 * describe: 文章详情
 */
public class ArticleDetailDS extends BaseSendTemplate {
    private String docid;
    private String uid;
    private String size;
    private String offset;
    private String rows = "1";


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
