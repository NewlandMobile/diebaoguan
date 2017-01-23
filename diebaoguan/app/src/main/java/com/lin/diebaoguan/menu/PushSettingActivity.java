package com.lin.diebaoguan.menu;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;

/**
 * It's Created by NewLand-JianFeng on 2017/1/23.
 */

public class PushSettingActivity extends BaseRedTitleBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        super.onCreate(savedInstanceState);
        initTitleBar("推送", true, false, false, R.layout.activity_push_setting);
    }
}
