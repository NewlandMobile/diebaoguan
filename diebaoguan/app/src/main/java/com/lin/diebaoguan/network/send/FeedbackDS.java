package com.lin.diebaoguan.network.send;

/**
 * Created by linx on 2017/1/2217:12.
 * mail :1057705307@QQ.com.
 * describe:意见反馈发送
 */
public class FeedbackDS extends BaseSendTemplate {
    private String clientversion;
    private String username;
    private String uid;
    private String imei;
    private String email;
    private String content;


    public String getClientversion() {
        return clientversion;
    }

    public void setClientversion(String clientversion) {
        this.clientversion = clientversion;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
