package com.lin.diebaoguan.network.send;

/**
 * Created by linx on 2017/1/509:24.
 * mail :1057705307@QQ.com.
 * describe:获取评论列表
 */
public class CommentListDs extends BaseSendTemplate {
    private String ispic;
    private String docid;
    private String avatar;
    private String offset;
    private String rows;
    private String onetime;

    public String getIspic() {
        return ispic;
    }

    public void setIspic(String ispic) {
        this.ispic = ispic;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getOnetime() {
        return onetime;
    }

    public void setOnetime(String onetime) {
        this.onetime = onetime;
    }
}
