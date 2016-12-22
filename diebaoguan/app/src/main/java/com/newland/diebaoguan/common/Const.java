package com.newland.diebaoguan.common;

import com.newland.diebaoguan.dbg.fragment.ApplyFragment;
import com.newland.diebaoguan.dbg.fragment.ExperienceFragment;
import com.newland.diebaoguan.dbg.fragment.HeadlineFragment;
import com.newland.diebaoguan.dbg.fragment.PriceFragment;
import com.newland.diebaoguan.dbg.fragment.TitleFragment;

/**
 * Created by linx on 2016/12/2116:21.
 * mail :1057705307@QQ.com.
 * describe:常用数据
 */

public class Const {
    public static String[] DBG_TOPICS = new String[]{"头条", "标题", "价格", "体验", "应用"};
    public static String[] FSB_TOPICS = new String[]{"头条", "标题", "价格", "体验", "应用"};
    public static String[] GYJ_TOPICS = new String[]{"头条", "标题", "价格", "体验", "应用"};


    public static Class[] DBG_Fragments = new Class[]{HeadlineFragment.class, TitleFragment.class, PriceFragment.class, ExperienceFragment.class, ApplyFragment.class};
}
