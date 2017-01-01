package com.lin.diebaoguan.network.send;

/**
 * Created by Alan on 2017/1/1 0001.
 * 谍报馆和风尚标接口
 */

public class DieBaoGuanAndFengShangBiaoDS extends BaseSendTemplate {
    //0或者1 0表示是谍报馆相关内容，1表示风尚标相关内容，爱美志特殊处理
    int isclass;

    //当isclass=0时，传值1,2,3,4分别对应谍报馆：新品，价格，体验，应用.
//    当isclass=1时，传值1,2,3,4分别对应风尚标：综合，爱美妆，爱美访，雯琰文
//            爱美志用另外一个接口

    int cid;
//    分页用，数据记录的起始行数。0为第一条记录。
    int offset;
    //分页用，数据记录每次取得的行数。不传此参数则默认获取10条记录。
    int rows;
    //分页用，传第一条新闻的时间戳，第一页下不用传值。
    String onetime;

    public int getIsclass() {
        return isclass;
    }

    public void setIsclass(int isclass) {
        this.isclass = isclass;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getOnetime() {
        return onetime;
    }

    public void setOnetime(String onetime) {
        this.onetime = onetime;
    }
}
