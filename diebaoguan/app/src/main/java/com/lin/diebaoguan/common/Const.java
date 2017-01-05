package com.lin.diebaoguan.common;

import com.lin.diebaoguan.dbg.fragment.ApplyFragment;
import com.lin.diebaoguan.dbg.fragment.ExperienceFragment;
import com.lin.diebaoguan.dbg.fragment.HeadlineFragment;
import com.lin.diebaoguan.dbg.fragment.PriceFragment;
import com.lin.diebaoguan.dbg.fragment.NewproductFragment;
import com.lin.diebaoguan.fsb.fragment.FSBAiMeiFangFragment;
import com.lin.diebaoguan.fsb.fragment.AiMeiZhiFragment;
import com.lin.diebaoguan.fsb.fragment.AiMeiZhuangFragment;
import com.lin.diebaoguan.fsb.fragment.FSBSyntheticalFragment;
import com.lin.diebaoguan.fsb.fragment.WenYanWenFragment;
import com.lin.diebaoguan.gyj.fragment.DigitalFragment;
import com.lin.diebaoguan.gyj.fragment.GYJSyntheticalFragment;
import com.lin.diebaoguan.gyj.fragment.OriginalFragment;
import com.lin.diebaoguan.gyj.fragment.PhoneFragment;
import com.lin.diebaoguan.gyj.fragment.TabletFragment;

/**
 * Created by linx on 2016/12/2116:21.
 * mail :1057705307@QQ.com.
 * describe:常用数据
 */

public class Const {
    public static String[] DBG_TOPICS = new String[]{"头条", "新品", "价格", "体验", "应用"};
    public static String[] FSB_TOPICS = new String[]{"综合", "爱美志", "雯琰文", "爱美坊", "爱美妆"};
    public static String[] GYJ_TOPICS = new String[]{"综合", "精品原创", "数码漫谈", "手机美图", "平板美图"};


    public static Class[] DBG_Fragments = new Class[]{HeadlineFragment.class, NewproductFragment.class, PriceFragment.class, ExperienceFragment.class, ApplyFragment.class};
    public static Class[] FSB_Fragments = new Class[]{FSBSyntheticalFragment.class, AiMeiZhiFragment.class, WenYanWenFragment.class, FSBAiMeiFangFragment.class, AiMeiZhuangFragment.class};
    public static Class[] GYJ_Fragments = new Class[]{GYJSyntheticalFragment.class, OriginalFragment.class, DigitalFragment.class, PhoneFragment.class, TabletFragment.class};

    public static String COMMENTSUCCESS = "60000011";//评论成功返回码
}
