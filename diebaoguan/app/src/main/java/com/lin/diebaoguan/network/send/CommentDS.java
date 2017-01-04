package com.lin.diebaoguan.network.send;

/**
 * Created by linx on 2017/1/411:35.
 * mail :1057705307@QQ.com.
 * describe:评论
 */
public class CommentDS extends BaseSendTemplate {
    private String authkey;//login接口返回的授权key
    private String docid;//文章id
    private String ispic;//是否是光影集图片的评论
    private String username;//用户名
    private String uid;//用户id
    private String content;//内容

    public CommentDS() {
    }

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getIspic() {
        return ispic;
    }

    public void setIspic(String ispic) {
        this.ispic = ispic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
