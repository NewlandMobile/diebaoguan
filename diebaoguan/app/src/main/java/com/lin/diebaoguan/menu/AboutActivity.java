package com.lin.diebaoguan.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;

/**
 * 关于
 */
public class AboutActivity extends BaseRedTitleBarActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.aboutwe), true, false, false, R.layout.activity_about);
        initView();
    }

    private void initView() {
        RelativeLayout rl_url = (RelativeLayout) findViewById(R.id.about_url);
        RelativeLayout rl_termsofservice = (RelativeLayout) findViewById(R.id.about_termsofservice);
        RelativeLayout rl_disclaimer = (RelativeLayout) findViewById(R.id.about_disclaimer);

        rl_url.setOnClickListener(this);
        rl_termsofservice.setOnClickListener(this);
        rl_disclaimer.setOnClickListener(this);
        btn_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baseactivity_back:
                finish();
                break;
            case R.id.about_url:
                break;
            case R.id.about_termsofservice:
                break;
            case R.id.about_disclaimer:
                break;
        }
    }
}
