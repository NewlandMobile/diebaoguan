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

    /**
     * 当前 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY
     */
    public static final String KEY_WEIBO = "3171395802";//微博key值
    /**
     * 当前 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * <p>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * <p>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";


    /** 腾讯app_id */
    public static final String APP_ID = "1105579857";
}
