package com.lin.diebaoguan.activity;

import android.os.Bundle;

import com.lin.diebaoguan.BaseRedTitleBarActivity;
import com.lin.diebaoguan.R;

/**
 * It's Created by NewLand-JianFeng on 2017/1/5.
 */

public class GyjOriginalDetailsActivity extends BaseRedTitleBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.JingPinOrigin), true, true, false, R.layout.activity_article_details);
        initView();
    }

    private void initView() {

    }
}
