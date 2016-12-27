package com.lin.diebaoguan.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lin.diebaoguan.BaseActivity;
import com.lin.diebaoguan.R;

/**
 * It's Created by NewLand-JianFeng on 2016/12/26.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_password;
    private Button btn_show_password_plain;
    private EditText et_email;
    private CheckBox checkBox_read;
    private Button btn_goto_register_protocol;
    private Button btn_register;
    private Button button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_show_password_plain = (Button) findViewById(R.id.btn_show_password_plain);
        et_email = (EditText) findViewById(R.id.et_email);
        checkBox_read = (CheckBox) findViewById(R.id.checkBox_read);
        btn_goto_register_protocol = (Button) findViewById(R.id.btn_goto_register_protocol);
        btn_register = (Button) findViewById(R.id.btn_register);
        button_cancel = (Button) findViewById(R.id.button_cancel);

        btn_show_password_plain.setOnClickListener(this);
        btn_goto_register_protocol.setOnClickListener(this);
        btn_goto_register_protocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        btn_register.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String content=((TextView)v).getText().toString();
        showToast("按下了 "+ content+" 键");
        switch (v.getId()) {
            case R.id.btn_show_password_plain:

                break;
            case R.id.btn_goto_register_protocol:

                break;
            case R.id.btn_register:

                break;
            case R.id.button_cancel:

                break;
        }
    }

    private void submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "中文、英文或数字", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "4-20位", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email地址", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
