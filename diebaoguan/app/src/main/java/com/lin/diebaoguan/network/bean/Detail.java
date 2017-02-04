package com.lin.diebaoguan.network.bean;

/**
 * Created by linx on 2017/2/410:31.
 * mail :1057705307@QQ.com.
 * describe:本地数据使用，判断是否已经收藏，在界面结束时清空缓冲
 */
public class Detail extends Result {
    private boolean isCollected;

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
