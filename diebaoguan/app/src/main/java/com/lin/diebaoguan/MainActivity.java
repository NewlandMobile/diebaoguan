package com.lin.diebaoguan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.diebaoguan.activity.LoginActivity;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.fragment.AiMeiFangFragment;
import com.lin.diebaoguan.fragment.DieBaoGuanFragment;
import com.lin.diebaoguan.fragment.FengShangBiaoFragment;
import com.lin.diebaoguan.fragment.GuangYinJiFragment;
import com.lin.diebaoguan.menu.AboutActivity;
import com.lin.diebaoguan.menu.AppRecommActivity;
import com.lin.diebaoguan.menu.SettingActivity;


public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        judgeFirstPage();
        setContentView(R.layout.activity_main);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        setAddTab(R.string.diebaoguan, DieBaoGuanFragment.class, R.drawable.inducator_selector);
        setAddTab(R.string.fengshanbiao, FengShangBiaoFragment.class, R.drawable.inducator_selector);
        setAddTab(R.string.guangyinji, GuangYinJiFragment.class, R.drawable.inducator_selector);
        setAddTab(R.string.aimeifang, AiMeiFangFragment.class, R.drawable.inducator_selector);
    }

    private void judgeFirstPage() {
        //保存判断为已经进入过了
        sharedPreferences = getSharedPreferences(Const.SP_ISFIRSTNAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(Const.SP_ISFIRSTKEY, false);
        edit.commit();
        if (!MyAppication.getInstance().hasLogined()) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }

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

    private static final int ITEM_ID = 33;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, ITEM_ID, 1, "我的收藏").setIcon(R.drawable.menu_favorites);
        menu.add(Menu.NONE, ITEM_ID + 1, 2, "应用推荐").setIcon(R.drawable.menu_apps_recommended);
        menu.add(Menu.NONE, ITEM_ID + 2, 3, "版本更新").setIcon(R.drawable.menu_update);
        menu.add(Menu.NONE, ITEM_ID + 3, 4, "设置").setIcon(R.drawable.menu_settings);
        menu.add(Menu.NONE, ITEM_ID + 4, 5, "关于").setIcon(R.drawable.about);
        menu.add(Menu.NONE, ITEM_ID + 5, 6, "退出").setIcon(R.drawable.menu_exit);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        int index = itemId - ITEM_ID + 1;
        switch (index) {
            case 1:
                break;
            case 2://应用推荐
                startActivity(new Intent(this, AppRecommActivity.class));
                break;
            case 3:
                break;
            case 4:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case 5://关于
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case 6:
                System.exit(0);
                break;

        }
        return true;
    }
}
