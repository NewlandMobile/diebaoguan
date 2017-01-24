package com.lin.diebaoguan.common;

import com.lin.diebaoguan.dbg.fragment.ApplyFragment;
import com.lin.diebaoguan.dbg.fragment.ExperienceFragment;
import com.lin.diebaoguan.dbg.fragment.HeadlineFragment;
import com.lin.diebaoguan.dbg.fragment.NewproductFragment;
import com.lin.diebaoguan.dbg.fragment.PriceFragment;
import com.lin.diebaoguan.fsb.fragment.AiMeiZhiFragment;
import com.lin.diebaoguan.fsb.fragment.AiMeiZhuangFragment;
import com.lin.diebaoguan.fsb.fragment.FSBAiMeiFangFragment;
import com.lin.diebaoguan.fsb.fragment.FSBSyntheticalFragment;
import com.lin.diebaoguan.fsb.fragment.WenYanWenFragment;
import com.lin.diebaoguan.gyj.fragment.DigitalFragment;
import com.lin.diebaoguan.gyj.fragment.GYJSyntheticalFragment;
import com.lin.diebaoguan.gyj.fragment.OriginalFragment;
import com.lin.diebaoguan.gyj.fragment.PhoneFragment;
import com.lin.diebaoguan.gyj.fragment.TabletFragment;
import com.lin.diebaoguan.menu.fragment.DBFragment;
import com.lin.diebaoguan.menu.fragment.LifeFragment;
import com.lin.diebaoguan.menu.fragment.PicFragment;

/**
 * Created by linx on 2016/12/2116:21.
 * mail :1057705307@QQ.com.
 * describe:常用数据
 */

public class Const {
    public static String[] DBG_TOPICS = new String[]{"头条", "新品", "价格", "体验", "应用"};
    public static String[] FSB_TOPICS = new String[]{"综合", "爱美志", "雯琰文", "爱美坊", "爱美妆"};
    public static String[] GYJ_TOPICS = new String[]{"综合", "精品原创", "数码漫谈", "手机美图", "平板美图"};
    public static String[] COLLECT_TOPICS = new String[]{"谍报", "生活", "图片"};

    public static Class[] DBG_Fragments = new Class[]{HeadlineFragment.class, NewproductFragment.class, PriceFragment.class, ExperienceFragment.class, ApplyFragment.class};
    public static Class[] FSB_Fragments = new Class[]{FSBSyntheticalFragment.class, AiMeiZhiFragment.class, WenYanWenFragment.class, FSBAiMeiFangFragment.class, AiMeiZhuangFragment.class};
    public static Class[] GYJ_Fragments = new Class[]{GYJSyntheticalFragment.class, OriginalFragment.class, DigitalFragment.class, PhoneFragment.class, TabletFragment.class};
    public static Class[] COLLECT_Fragments = new Class[]{DBFragment.class, LifeFragment.class, PicFragment.class};

    public static String[] POP_TITLE = new String[]{"爱美妆", "爱美坊", "雯琰文", "爱美志", "应用谍报", "体验谍报", "价格谍报", "新品谍报"};
    public static String[] PUSHAD = new String[]{"移动订货", "订单状态实时知晓", "图形化下单", "有事没事来溜溜，不一样的上海", "惊喜不断", "爱上不一样的生活"};

    public static String COMMENTSUCCESS = "60000011";//评论成功返回码
    public static String FEEDBACKSUCCESS = "60000001";//意见反馈返回码
    //    public final static int ROWS = 20;//分页用
    public final static int ROWS = 10;//分页用
    public static String SP_ISFIRSTNAME = "ISFIRST";//是否首次
    public static String SP_ISFIRSTKEY = "isFirst";

    public static String OFFICIAL_WEBSITE = "http://www.cnmo.com";//官网

    public static final int ZOOM_BIG = 130;
    public static final int ZOOM_MIDDLE = 100;
    public static final int ZOOM_SMALL = 80;
}
