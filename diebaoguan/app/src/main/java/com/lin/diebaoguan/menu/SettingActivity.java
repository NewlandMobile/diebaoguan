package com.lin.diebaoguan.menu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.GuideActivity;
import com.lin.diebaoguan.activity.LoginActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
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
    private Button btn_login;
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
            btn_login.setText("注销");
            String uid = MyAppication.getInstance().getUid();
            fetchUserData(uid);
        } else {
            btn_login.setText("登录");
        }

//        setContentView(R.layout.activity_setting);
    }

    private void fetchUserData(String uid) {
        LogUtils.d("uid=" + uid);
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
        btn_login = (Button) findViewById(R.id.btn_login_or_logout);
        btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case setting_rl_goto_textSize:
//                showToast("字体大小设置");
                showTextSizeChooseDialog();
                break;
            case R.id.setting_rl_goto_clear_cache:
                showToast("清除缓存");
                break;
            case R.id.setting_rl_goto_push_server:
                showToast("推送服务");
                break;
            case R.id.setting_rl_goto_feedback://意见反馈
                if (MyAppication.hasLogined()) {
                    startActivity(new Intent(this, FeedbackActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.setting_rl_goto_guide://新手指南
                startActivity(new Intent(this, GuideActivity.class));
                finish();
                break;
            case R.id.btn_login_or_logout:
                showToast("登录或注销");
                if (MyAppication.hasLogined()) {
                    MyAppication.logout();
                    btn_login.setText("登录");
                } else {
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    finish();
                }
                break;
        }
    }

    private void showTextSizeChooseDialog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("字体大小");
//        final RadioGroup radioGroup= (RadioGroup) LayoutInflater.from(this).inflate(R.layout.view_setting_textsize,null);
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_setting_textsize, null);
        Button btn_cancel = (Button) rootView.findViewById(R.id.dialog_cancel_setting);
        Button btn_confirm = (Button) rootView.findViewById(R.id.dialog_confirm_setting);
        final RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group_textSize_setting);
//        builder.setView(rootView);

//        这个方案被否决，因为没有RadioGroup
        /*builder.setItems(new String[]{"大","中","小"},new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("第"+which+"个按键按下");
            }
        });*/
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                int id = radioGroup.getCheckedRadioButtonId();
//                String printString = null;
//                switch (id) {
//                    case R.id.radioButton_big:
//                        printString = "da";
//                        break;
//                    case R.id.radioButton_middle:
//                        printString = "zhong";
//                        break;
//                    case R.id.radioButton_small:
//                        printString = "xiao";
//                        break;
//                    default:
//                        printString = "匹配失败";
//
//                }
//                showToast("按键匹配：" + printString);
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        final AlertDialog dialog= builder.create();
        final Dialog dialog = new Dialog(this, R.style.add_dialog);
        ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.addContentView(rootView,layout);
        dialog.setContentView(rootView);
//        dialog.setView(rootView);
//        ViewParent viewParent=  rootView.getParent();
//        ((ViewGroup)viewParent).setBackgroundColor(Color.GRAY);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int id = radioGroup.getCheckedRadioButtonId();
                int zommNum = 100;
//                String printString = null;
                switch (id) {
                    case R.id.radioButton_big:
                        //TODO  这些值后期可调的
                        zommNum = Const.ZOOM_BIG;
//                        printString = "da";
                        break;
                    case R.id.radioButton_middle:
                        zommNum = Const.ZOOM_MIDDLE;
//                        MyAppication.setTextSizeZoom(100);
//                        printString = "zhong";
                        break;
                    case R.id.radioButton_small:
                        zommNum = Const.ZOOM_SMALL;
//                        MyAppication.setTextSizeZoom(80);
//                        printString = "xiao";
                        break;
                    default:
                        zommNum = 100;
//                        printString = "匹配失败";

                }
                MyAppication.setTextSizeZoom(zommNum);
//                showToast("按键匹配：" + printString);
            }
        });
        dialog.show();
    }
}
