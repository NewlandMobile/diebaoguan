package com.lin.diebaoguan.menu;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;

/**
 * 关于
 */
public class AboutActivity extends BaseRedTitleBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initTitleBar(getString(R.string.aboutwe),true,false,false,R.layout.activity_about);
    }
}
