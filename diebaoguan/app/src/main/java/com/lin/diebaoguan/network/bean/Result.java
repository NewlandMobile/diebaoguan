package com.lin.diebaoguan.network.bean;

/**
 * Created by Alan on 2017/1/1 0001.
 * 文章信息返回值
 */

public class Result {
//    docid	整型	文章id
//    title	字符串	文章标题
//    date	字符串	时间
//    picUrl	字符串	图片地址
//    content	字符串	简介
    int docid;
    String title;
    String date;
    String picUrl;
    String content;
    String type;
    int picid;

    public int getDocid() {
        return docid;
    }
//
//    public void setDocid(int docid) {
//        this.docid = docid;
//    }
//
    public String getTitle() {
        return title;
    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
    public String getDate() {
        return date;
    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
    public String getPicUrl() {
        return picUrl;
    }
//
//    public void setPicUrl(String picUrl) {
//        this.picUrl = picUrl;
//    }
//
    public String getContent() {
        return content;
    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
    public String getType() {
        return type;
    }
//
//    public void setType(String type) {
//        this.type = type;
//    }


    public int getPicid() {
        return picid;
    }
}
