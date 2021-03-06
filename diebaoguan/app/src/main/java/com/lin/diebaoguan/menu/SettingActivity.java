package com.lin.diebaoguan.menu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyApplication;
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
    private CheckBox setting_checkbox_save_flow;
    private TextView setting_tv_item_instruction_cache;
    private Button btn_login;
    private Dialog clearDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("设置", true, false, false, R.layout.activity_setting);
        initView();
        initUIContent();
    }

    private void initUIContent() {
        if (MyApplication.hasLogined()) {
            btn_login.setText("注销");
            String uid = MyApplication.getUid();
            fetchUserData(uid);
        } else {
            btn_login.setText("登录");
        }
        boolean blockImage = MyApplication.isBlockImage();
        setting_checkbox_save_flow.setChecked(blockImage);

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
                LogUtils.e(volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.d(s);
                PersonInfoResponse response = PersonInfoResponse.parseObject(s, PersonInfoResponse.class);
                LogUtils.d(response.toString());
                String userName = response.getData().getUsername();
                String avatar = response.getData().getAvatar();
                if (userName != null) {
                    tv_have_not_login.setText(userName);
                }
                if (avatar != null) {
                    IMAGEUtils.displayImage(avatar, account_Image);
                }
            }
        });

    }

    private void initView() {
        tv_have_not_login = (TextView) findViewById(R.id.tv_have_not_login);
        account_Image = (ImageView) findViewById(R.id.account_Image);
        findViewById(setting_rl_goto_textSize).setOnClickListener(this);
        setting_checkbox_save_flow = (CheckBox) findViewById(R.id.setting_checkbox_save_flow);
        setting_checkbox_save_flow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyApplication.setBlockImage(isChecked);
            }
        });

        //TODO  这行内容也要初始化    后期再补
        setting_tv_item_instruction_cache = (TextView) findViewById(R.id.setting_tv_item_instruction_cache);
        findViewById(R.id.setting_rl_goto_clear_cache).setOnClickListener(this);
        findViewById(R.id.setting_rl_goto_push_server).setOnClickListener(this);
        findViewById(R.id.setting_rl_goto_feedback).setOnClickListener(this);
        findViewById(R.id.setting_rl_goto_guide).setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login_or_logout);
        btn_login.setOnClickListener(this);
        //设置缓存大小
        String totalCacheSize;
        try {
            totalCacheSize = CommonUtils.getTotalCacheSize(this);
            setting_tv_item_instruction_cache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case setting_rl_goto_textSize:
                showTextSizeChooseDialog();
                break;
            case R.id.setting_rl_goto_clear_cache:
                showToast("清除缓存");
                GreateClearCacheDialog();
                break;
            case R.id.setting_rl_goto_push_server:
                showToast("推送服务");
                startActivity(new Intent(SettingActivity.this, PushSettingActivity.class));
                break;
            case R.id.setting_rl_goto_feedback://意见反馈
                if (MyApplication.hasLogined()) {
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
                if (MyApplication.hasLogined()) {
                    MyApplication.logout();
                    btn_login.setText("登录");
                } else {
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    finish();
                }
                break;
            case R.id.clear_sure:
                CommonUtils.cleanInternalCache(this);
                setting_tv_item_instruction_cache.setText("0k");
                clearDialog.dismiss();
                break;
            case R.id.clear_cancle:
                clearDialog.dismiss();
                break;
        }
    }

    /**
     * 创建清楚缓存dialog
     */
    private void GreateClearCacheDialog() {
        clearDialog = new Dialog(this, R.style.MyDialog);
        View inflate = getLayoutInflater().inflate(R.layout.view_clear, null);
        clearDialog.setContentView(inflate);
        clearDialog.setCanceledOnTouchOutside(true);
        clearDialog.show();
        TextView text_clear = (TextView) inflate.findViewById(R.id.clear_sure);
        TextView text_cancle = (TextView) inflate.findViewById(R.id.clear_cancle);
        text_clear.setOnClickListener(this);
        text_cancle.setOnClickListener(this);
    }

    private void showTextSizeChooseDialog() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_setting_textsize, null);
        Button btn_cancel = (Button) rootView.findViewById(R.id.dialog_cancel_setting);
        Button btn_confirm = (Button) rootView.findViewById(R.id.dialog_confirm_setting);
        final RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group_textSize_setting);
        final Dialog dialog = new Dialog(this, R.style.add_dialog);
        dialog.setContentView(rootView);
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
                int zoomNum;

                switch (id) {
                    case R.id.radioButton_big:
                        //TODO  这些值后期可调的
                        zoomNum = Const.ZOOM_BIG;
//                        printString = "da";
                        break;
                    case R.id.radioButton_middle:
                        zoomNum = Const.ZOOM_MIDDLE;
                        break;
                    case R.id.radioButton_small:
                        zoomNum = Const.ZOOM_SMALL;
                        break;
                    default:
                        zoomNum = 100;
                }
                MyApplication.setTextSizeZoom(zoomNum);
            }
        });
        dialog.show();
    }
}
