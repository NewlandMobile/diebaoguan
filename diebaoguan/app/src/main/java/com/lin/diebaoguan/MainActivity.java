package com.lin.diebaoguan;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.diebaoguan.fragment.AiMeiFangFragment;
import com.lin.diebaoguan.fragment.DieBaoGuanFragment;
import com.lin.diebaoguan.fragment.FengShangBiaoFragment;
import com.lin.diebaoguan.fragment.GuangYinJiFragment;


public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        setAddTab(R.string.diebaoguan, DieBaoGuanFragment.class, R.drawable.inducator_selector);
        setAddTab(R.string.fengshanbiao, FengShangBiaoFragment.class, R.drawable.inducator_selector);
        setAddTab(R.string.guangyinji, GuangYinJiFragment.class, R.drawable.inducator_selector);
        setAddTab(R.string.aimeifang, AiMeiFangFragment.class, R.drawable.inducator_selector);
    }

    /**
     * @param indicator 标签
     * @param cls
     * @param srcID     背景
     */
    private void setAddTab(int indicator, Class<?> cls, int srcID) {
        TabHost.TabSpec newTabSpec = mTabHost.newTabSpec(indicator + "");
        View view = getLayoutInflater().inflate(R.layout.item_inducator, null);
        TextView textview = (TextView) view.findViewById(R.id.inducator_text);
        textview.setText(getResources().getString(indicator));
        textview.setTextSize(14);
        textview.setTextColor(getResources().getColor(R.color.white));
        textview.setBackgroundResource(srcID);
        mTabHost.addTab(newTabSpec.setIndicator(view), cls, null);
        if (indicator == R.string.aimeifang) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    /**
                     * 预留 TODO 做跳转使用
                     */
                    Toast.makeText(MainActivity.this, "==长点击==", Toast.LENGTH_SHORT).show();
                    mTabHost.setCurrentTab(0);
                    return true;
                }
            });
        }
    }
}
