package com.lin.diebaoguan.network.bean;

/**
 * Created by linx on 2017/1/1718:57.
 * mail :1057705307@QQ.com.
 * describe:应用推荐
 */
public class RecommendAppList {
    private String appid;
    private String name;
    private String goodName;
    private String filesize;
    private String downloads;
    private String downloadUrl;
    private String score;
    private String headPictureSrc;

    public String getAppid() {
        return appid;
    }

    public String getName() {
        return name;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getFilesize() {
        return filesize;
    }

    public String getDownloads() {
        return downloads;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getScore() {
        return score;
    }

    public String getHeadPictureSrc() {
        return headPictureSrc;
    }
}
