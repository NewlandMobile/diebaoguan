package com.lin.diebaoguan.network.bean;

import java.io.Serializable;

/**
 * It's Created by NewLand-JianFeng on 2016/12/29.
 * <p>
 * 注册里的返回信息会用到
 * 解析类（解析模版）在写的时候注意3点：
 * 1.该使用整型的可以用整型，（不是只能用String）
 * 2.Set方法可以不写（写get在自己代码调用的时候会比直接引用成员变量好）
 * 3.JSon结构一定要分析清楚
 */

public class Data implements Serializable {
    String uid;
    String key;
    Result[] result;
    Paging paging;
    //17-01-03 文章详情
    private Info info;
    /*id	整型	图集id
    isCollected	字符串	表示是否收藏，1为收藏，0为未收藏
    title	字符串	图集标题
    date	字符串	时间
    author	字符串	作者
    picUrl	字符串	图集图片地址*/
    String id;
    int isCollected;
    String title;
    String date;
    String author;
    String[] picUrl;
    String digest;
    String docUrl;
    /*收藏分类id,
            1为谍报收藏
    2为生活收藏
    3为图片收藏*/
    int cid;
    /*
    logoSrc 欢迎界面logo
     */
    private String logoSrc;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Result[] getResult() {
        return result;
    }

    public void setResult(Result[] result) {
        this.result = result;
    }

    public Paging getPaging() {
        return paging;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getPicUrl() {
        return picUrl;
    }

    public String getDigest() {
        return digest;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public String getLogoSrc() {
        return logoSrc;
    }

    public int getCid() {
        return cid;
    }
}
