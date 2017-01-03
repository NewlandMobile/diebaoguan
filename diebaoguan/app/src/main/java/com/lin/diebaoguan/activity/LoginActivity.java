package com.lin.diebaoguan.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.lin.diebaoguan.BaseActivity;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;

/**
 * Created by Alan on 2017/1/2 0002.
 * 登录页
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(this);
        progressDialog.show();
    }
}
