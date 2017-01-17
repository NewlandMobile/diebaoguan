package com.lin.diebaoguan.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.PersonInfoResponse;
import com.lin.diebaoguan.network.send.PersonInfoDS;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;
import com.lin.lib_volley_https.VolleyListener;

import static com.lin.diebaoguan.R.id.setting_rl_goto_textSize;

/**
 * 设置
 */
public class SettingActivity extends BaseRedTitleBarActivity implements View.OnClickListener {

    private TextView tv_have_not_login;
    private ImageView account_Image;
    //    private View setting_rl_goto_textSize;
    private CheckBox setting_checkbox_wifi;
    private CheckBox setting_checkbox_save_flow;
    private CheckBox setting_checkbox_offline_download;
    private TextView setting_tv_item_instruction_cache;
//    private View setting_rl_goto_clear_cache;
//    private View setting_rl_goto_push_server;
//    private View setting_rl_goto_feedback;
//    private View setting_rl_goto_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("设置", true, false, false, R.layout.activity_setting);
        initView();
        if (MyAppication.getInstance().hasLogined()) {
            String uid = MyAppication.getInstance().getUid();
            fetchUserData(uid);
        }

//        setContentView(R.layout.activity_setting);
    }

    private void fetchUserData(String uid) {
        PersonInfoDS personInfoDS = new PersonInfoDS();
        personInfoDS.setModule("api_libraries_sjdbg_userinfo");
        personInfoDS.setUid(uid);
        personInfoDS.initTimePart();
//        头像的大小，分三种尺寸  分别传'big', 'middle', 'small'
        personInfoDS.setAvatarsize("big");
        CommonUtils.normalGetWayFetch(personInfoDS, null, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                showToast(volleyError.toString());
                LogUtils.e(volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
//                showToast(s);
                LogUtils.d(s);
                PersonInfoResponse response = PersonInfoResponse.parseObject(s, PersonInfoResponse.class);
                LogUtils.d(response.toString());
                String userName = response.getData().getUsername();
                String avater = response.getData().getAvatar();
                if (userName != null) {
                    tv_have_not_login.setText(userName);
                }
                if (avater != null) {
                    IMAGEUtils.displayImage(avater, account_Image);
                }
            }
        });

    }

    private void initView() {
        tv_have_not_login = (TextView) findViewById(R.id.tv_have_not_login);
        account_Image = (ImageView) findViewById(R.id.account_Image);
//        setting_rl_goto_textSize =
        findViewById(setting_rl_goto_textSize).setOnClickListener(this);
//        setting_rl_goto_textSize.setOnClickListener(this);
        setting_checkbox_wifi = (CheckBox) findViewById(R.id.setting_checkbox_wifi);
        setting_checkbox_save_flow = (CheckBox) findViewById(R.id.setting_checkbox_save_flow);
        setting_checkbox_offline_download = (CheckBox) findViewById(R.id.setting_checkbox_offline_download);
        setting_tv_item_instruction_cache = (TextView) findViewById(R.id.setting_tv_item_instruction_cache);
//        setting_rl_goto_clear_cache =
        findViewById(R.id.setting_rl_goto_clear_cache).setOnClickListener(this);
//        setting_rl_goto_clear_cache.setOnClickListener(this);
//        setting_rl_goto_push_server =
        findViewById(R.id.setting_rl_goto_push_server).setOnClickListener(this);
//        setting_rl_goto_push_server.setOnClickListener(this);
//        setting_rl_goto_feedback =
        findViewById(R.id.setting_rl_goto_feedback).setOnClickListener(this);
//        setting_rl_goto_feedback.setOnClickListener(this);
//        setting_rl_goto_guide =
        findViewById(R.id.setting_rl_goto_guide).setOnClickListener(this);
//        setting_rl_goto_guide.setOnClickListener(this);
        findViewById(R.id.btn_login_or_logout).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case setting_rl_goto_textSize:
                showToast("字体大小设置");
                break;
            case R.id.setting_rl_goto_clear_cache:
                showToast("清除缓存");
                break;
            case R.id.setting_rl_goto_push_server:
                showToast("推送服务");
                break;
            case R.id.setting_rl_goto_feedback:
                showToast("意见反馈");
                break;
            case R.id.setting_rl_goto_guide:
                showToast("新手指南");
                break;
            case R.id.btn_login_or_logout:
                showToast("登录或注销");
                break;
        }
    }
}
