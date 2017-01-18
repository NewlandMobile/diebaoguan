package com.lin.diebaoguan.menu;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;

/**
 * 收藏界面
 */
public class CollectActivity extends BaseRedTitleBarActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("我的收藏", true, false, false, R.layout.activity_collect);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (int i = 0; i < Const.COLLECT_TOPICS.length; i++) {
            setAddTab(Const.COLLECT_TOPICS[i], Const.COLLECT_Fragments[i]);
        }
    }

    /**
     * @param indicator 标签
     * @param cls
     */
    private void setAddTab(String indicator, Class<?> cls) {
        TabHost.TabSpec newTabSpec = mTabHost.newTabSpec(indicator);
        View view = getLayoutInflater().inflate(R.layout.item_inducator_top, null);
        TextView textview = (TextView) view.findViewById(R.id.inducator_top_text);
        textview.setText(indicator);
        mTabHost.addTab(newTabSpec.setIndicator(view), cls, null);
    }
}
