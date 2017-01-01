package com.lin.diebaoguan.network.bean;

/**
 * It's Created by NewLand-JianFeng on 2016/12/29.
 *
 * 注册里的返回信息会用到
 * 解析类（解析模版）在写的时候注意3点：
 * 1.该使用整型的可以用整型，（不是只能用String）
 * 2.Set方法可以不写（写get在自己代码调用的时候会比直接引用成员变量好）
 * 3.JSon结构一定要分析清楚
 */

public class Data {
    String uid;
    String key;
    Result[] result;
    Paging paging;

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
}
