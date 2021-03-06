package com.lin.diebaoguan.network.bean;

import java.io.Serializable;

/**
 * Created by Alan on 2017/1/1 0001.
 * 文章信息返回值
 */

public class Result implements Serializable {
    /*    docid	整型	文章id
        title	字符串	文章标题
        date	字符串	时间
        picUrl	字符串	图片地址
        content	字符串	简介
        */
    String docid;
    String title;
    String date;
    String picUrl;
    String content;
    String type;
    int picid;
    /*
    评论
     */
    private String uid;
    private String username;
    private String avatar;
    /*
    爱美志
     */
    private String pic;
    private String author;

    public String getDocid() {
        return docid;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getPicUrl() {
        return picUrl;
    }


    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getPicid() {
        return picid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getPic() {
        return pic;
    }

    public String getAuthor() {
        return author;
    }

    public void setPicid(int picid) {
        this.picid = picid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
}
