package com.lin.diebaoguan.network.send;

/**
 * Created by linx on 2017/1/616:44.
 * mail :1057705307@QQ.com.
 * describe:文章收藏取消
 */
public class ArticleCollectDS extends BaseSendTemplate {
    private String docid;
    private int cid;
    private String authkey;
    private String uid;
    private String method;

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
