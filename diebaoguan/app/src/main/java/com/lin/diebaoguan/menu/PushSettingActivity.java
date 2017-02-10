package com.lin.diebaoguan.menu;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
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

        cb_notification.setChecked(MyAppication.isPushNotification());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.setting_push_notifications:
                //传到application进行保存，并且此时开启获取推送的服务
                MyAppication.setIsPushNotification(isChecked);
                if (isChecked) {//开启服务
//startService(new Intent())
                } else {//关闭服务

                }


                break;
            case R.id.setting_sound:
                if (MyAppication.isPushNotification()) {

                } else {
                    Toast.makeText(this, R.string.pleaseselectpushsetting, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.setting_Vibration:
                if (MyAppication.isPushNotification()) {

                } else {
                    Toast.makeText(this, R.string.pleaseselectpushsetting, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}