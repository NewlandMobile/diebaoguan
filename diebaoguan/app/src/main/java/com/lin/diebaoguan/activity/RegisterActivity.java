package com.lin.diebaoguan.activity;

import android.os.Bundle;
import android.widget.Button;

import com.lin.diebaoguan.BaseActivity;
import com.lin.diebaoguan.R;

/**
 * It's Created by NewLand-JianFeng on 2016/12/26.
 */

public class RegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerProtocol= (Button) findViewById(R.id.button2);
    }
}
