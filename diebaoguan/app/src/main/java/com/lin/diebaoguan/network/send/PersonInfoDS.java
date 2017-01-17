package com.lin.diebaoguan.network.send;

/**
 * It's Created by NewLand-JianFeng on 2017/1/17.
 */

public class PersonInfoDS extends BaseSendTemplate {
   /* uid	整型
    get方式	用户id
    avatarsize	字符串
    get方式	头像的大小，分三种尺寸  分别传'big', 'middle', 'small'*/

    private String uid;
    private String avatarsize;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAvatarsize(String avatarsize) {
        this.avatarsize = avatarsize;
    }
}
