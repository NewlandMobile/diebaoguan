package com.lin.diebaoguan.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;

/**
 * 服务条款/免责声明界面
 */
public class TermsofServiceAndDisclaimerActivity extends BaseRedTitleBarActivity {

    private TextView text_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String activitylog = intent.getStringExtra("activitylog");
        if ("TermsofService".equals(activitylog)) {
            initTitleBar(getString(R.string.termsofservice), true, false, false, R.layout.activity_termsof_service);
            text_content = (TextView) findViewById(R.id.text_content);
            text_content.setText(getResources().getString(R.string.termsofservice_content));
        } else {
            initTitleBar(getString(R.string.disclaimer), true, false, false, R.layout.activity_termsof_service);
            text_content = (TextView) findViewById(R.id.text_content);
            text_content.setText(getResources().getString(R.string.disclaimer_content));
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
