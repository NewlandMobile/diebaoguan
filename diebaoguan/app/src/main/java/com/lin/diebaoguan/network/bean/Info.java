package com.lin.diebaoguan.network.bean;

/**
 * Created by linx on 2017/1/316:41.
 * mail :1057705307@QQ.com.
 * describe:文章详情info
 */
public class Info {
    private String docid;
    private String title;
    private String type;
    private int cid;
    private String isCollected;
    private String date;
    private String content;
    private String docUrl;
    private String author;
    private String[] picUrl;

    public Info() {
    }

    public String getDocid() {
        return docid;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getCid() {
        return cid;
    }

    public String getIsCollect() {
        return isCollected;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
