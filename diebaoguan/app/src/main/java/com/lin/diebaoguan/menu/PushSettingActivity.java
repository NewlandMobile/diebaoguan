package com.lin.diebaoguan.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lin.diebaoguan.MyApplication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.service.PushNotificationService;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;

/**
 * It's Created by NewLand-JianFeng on 2017/1/23.
 */

public class PushSettingActivity extends BaseRedTitleBarActivity implements CompoundButton.OnCheckedChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("推送", true, false, false, R.layout.activity_push_setting);
        initView();
    }

    private void initView() {
        CheckBox cb_notification = (CheckBox) findViewById(R.id.setting_push_notifications);
        CheckBox cb_sound = (CheckBox) findViewById(R.id.setting_sound);
        CheckBox cb_vibration = (CheckBox) findViewById(R.id.setting_Vibration);
        cb_notification.setOnCheckedChangeListener(this);
        cb_sound.setOnCheckedChangeListener(this);
        cb_vibration.setOnCheckedChangeListener(this);

        cb_notification.setChecked(MyApplication.isPushNotification());

        cb_sound.setChecked(MyApplication.isSound());
        cb_vibration.setChecked(MyApplication.isVibrate());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.setting_push_notifications:
                Intent intent = new Intent(this, PushNotificationService.class);
                //传到application进行保存，并且此时开启获取推送的服务
                MyApplication.setIsPushNotification(isChecked);
                if (isChecked) {//开启服务
                    startService(intent);
                } else {//关闭服务
                    stopService(intent);
                }
                break;
            case R.id.setting_sound:
                MyApplication.setIsSound(isChecked);
                break;
            case R.id.setting_Vibration:
                MyApplication.setIsVibrate(isChecked);
                break;
        }
    }
}