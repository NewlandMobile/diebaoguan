package com.lin.diebaoguan.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lin.diebaoguan.BaseActivity;
import com.lin.diebaoguan.BaseRedTitleBarActivity;
import com.lin.diebaoguan.R;

/**
 * It's Created by NewLand-JianFeng on 2016/12/26.
 */

public class RegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerProtocolButton= (Button) findViewById(R.id.button2);
        registerProtocolButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        Button registerConfirmButton= (Button) findViewById(R.id.button3);
        registerConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("注册键 按下");
            }
        });
    }
}
