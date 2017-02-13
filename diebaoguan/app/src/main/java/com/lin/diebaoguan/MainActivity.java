package com.lin.diebaoguan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.activity.LoginActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.fragment.AiMeiFangFragment;
import com.lin.diebaoguan.fragment.DieBaoGuanFragment;
import com.lin.diebaoguan.fragment.EvolveFragment;
import com.lin.diebaoguan.fragment.FengShangBiaoFragment;
import com.lin.diebaoguan.fragment.GuangYinJiFragment;
import com.lin.diebaoguan.menu.AboutActivity;
import com.lin.diebaoguan.menu.AppRecommActivity;
import com.lin.diebaoguan.menu.CollectActivity;
import com.lin.diebaoguan.menu.SettingActivity;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.BaseSendTemplate;
import com.lin.diebaoguan.uibase.BaseActivity;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FragmentTabHost mTabHost;

    private ArrayList<String> arrayList = new ArrayList<>();
    private static final int ITEM_ID = 33;
    private PopupWindow popupWindow;
    private View view_pop;//popupwindowview
    private int[] popitems = new int[]{R.id.itempop_text1, R.id.itempop_text2, R.id.itempop_text3, R.id.itempop_text4, R.id.itempop_text5, R.id.itempop_text6, R.id.itempop_text7, R.id.itempop_text8};
    private TextView lastTabofToplevel;
    private Dialog updateDialog;
    private String downloadUrl;//下载路径
    private FragmentManager myFragmentManager;
    private String[] tagsArray=new String[]{"diebaoguan","fengshanbiao","guangyinji","aimeifang"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        judgeFirstPage();
        setContentView(R.layout.activity_main);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        myFragmentManager=getSupportFragmentManager();
        mTabHost.setup(this,myFragmentManager , R.id.realtabcontent);
        setAddTab(tagsArray[0], R.string.diebaoguan, DieBaoGuanFragment.class, R.drawable.inducator_selector);
        setAddTab(tagsArray[1], R.string.fengshanbiao, FengShangBiaoFragment.class, R.drawable.inducator_selector);
        setAddTab(tagsArray[2], R.string.guangyinji, GuangYinJiFragment.class, R.drawable.inducator_selector);
        setAddTab(tagsArray[3], R.string.aimeifang, AiMeiFangFragment.class, R.drawable.inducator_amf_select);
        initList();
        //获取屏幕宽度
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        LogUtils.e("==screenWidth==" + screenWidth + "==" + height);

        view_pop = getLayoutInflater().inflate(R.layout.view_popupwindow, null);
        view_pop.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow = new PopupWindow(view_pop, screenWidth / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        //给popupwindow添加一个空背景，使点击popupwindow时能使其消失。
        Drawable background = new BitmapDrawable();
        popupWindow.setBackgroundDrawable(background);
        popupWindow.setOutsideTouchable(true);//点击外面窗口消失
        for (int i = 0; i < popitems.length; i++) {
            TextView item_text = (TextView) view_pop.findViewById(popitems[i]);
            item_text.setText(arrayList.get(i));
            item_text.setOnClickListener(this);
        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lastTabofToplevel.setBackgroundResource(R.drawable.inducator_amf_select);
            }
        });
    }

    private void initList() {
        Collections.addAll(arrayList, Const.POP_TITLE);
    }

    private void judgeFirstPage() {
        //保存判断为已经进入过了
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SP_ISFIRSTNAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(Const.SP_ISFIRSTKEY, false);
        edit.apply();
        if (!MyApplication.hasLogined()) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }

    }

    /**
     * @param indicator 标签
     * @param cls      Fragment Class
     * @param srcID     背景
     */
    private void setAddTab(String tag, int indicator, Class<?> cls, int srcID) {
        TabHost.TabSpec newTabSpec = mTabHost.newTabSpec(tag);
        final View view = getLayoutInflater().inflate(R.layout.item_inducator, null);
        TextView textView;
        textView = (TextView) view.findViewById(R.id.inducator_text);
        textView.setText(getResources().getString(indicator));
        textView.setTextSize(14);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setBackgroundResource(srcID);
        mTabHost.addTab(newTabSpec.setIndicator(view), cls, null);
        if (indicator == R.string.aimeifang) {
            lastTabofToplevel =textView;
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    lastTabofToplevel.setBackgroundResource(R.drawable.diya_choice_bgon);
                    int[] location = new int[2];
                    lastTabofToplevel.getLocationOnScreen(location);
                    int height1 = view_pop.getMeasuredHeight();
                    popupWindow.showAtLocation(lastTabofToplevel, Gravity.NO_GRAVITY, location[0], location[1] - height1);
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            case 1://收藏
                startActivity(new Intent(this, CollectActivity.class));
                break;
            case 2://应用推荐
                startActivity(new Intent(this, AppRecommActivity.class));
                break;
            case 3://版本更新
                getUpdateInfo();
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

    private void jumpToSpecFramgent(final int topLevelNum, final int secondLevelNum){
        mTabHost.setCurrentTab(topLevelNum);
        Runnable updateLevel2TabHost=new Runnable() {
            @Override
            public void run() {
                EvolveFragment topLevelFragment= (EvolveFragment) myFragmentManager.findFragmentByTag(tagsArray[topLevelNum]);
                if (topLevelFragment!=null){
                    topLevelFragment.setCurrentTab(secondLevelNum);
                }else {
                    LogUtils.e("没能拿到Fragment");
                }
            }
        };
        mTabHost.post(updateLevel2TabHost);
    }

    private void lastTabLongClickHandleWithFramgentNums(int topLevelNum, int secondLevelNum){
        popupWindow.dismiss();
//        lastTabofToplevel.setBackgroundResource(R.drawable.inducator_amf_select);
//        lastTabofToplevel.setBackground(getResources().getDrawable(R.drawable.inducator_amf_select));
        jumpToSpecFramgent(topLevelNum,secondLevelNum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.itempop_text1:
                lastTabLongClickHandleWithFramgentNums(1,4);
                break;
            case R.id.itempop_text2:
                lastTabLongClickHandleWithFramgentNums(1,3);
                break;
            case R.id.itempop_text3:
                lastTabLongClickHandleWithFramgentNums(1,2);
                break;
            case R.id.itempop_text4:
                lastTabLongClickHandleWithFramgentNums(1,1);
                break;
            case R.id.itempop_text5:
                lastTabLongClickHandleWithFramgentNums(0,4);
                break;
            case R.id.itempop_text6:
                lastTabLongClickHandleWithFramgentNums(0,3);
                break;
            case R.id.itempop_text7:
                lastTabLongClickHandleWithFramgentNums(0,2);
                break;
            case R.id.itempop_text8:
                lastTabLongClickHandleWithFramgentNums(0,1);
                break;
            case R.id.update_cancle:
                updateDialog.dismiss();
                break;
            case R.id.update_sure:
                updateDialog.dismiss();
                Uri uri = Uri.parse(downloadUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

        }
    }

    /**
     * 版本更新
     */
    public String getUpdateInfo() {
        final ProgressDialog dialog = CommonUtils.showProgressDialog(this, "", "正在获取版本信息");
        dialog.show();
        BaseSendTemplate sendTemplate = new BaseSendTemplate();
        sendTemplate.setModule("api_libraries_sjdbg_updatedbg");
        sendTemplate.initTimePart();
        CommonUtils.httpGet(sendTemplate.parseParams(), new VolleyListener() {


            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                showToast(getResources().getString(R.string.getdatafail));
            }

            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                int localVersion = CommonUtils.getVersionCode(MainActivity.this);
                NormalResponse response = NormalResponse.parseObject(s, NormalResponse.class);
                int status = response.getStatus();
                if (status == 1) {
                    Data data = response.getData();
                    int versionCode = data.getVersionCode();
                    if (localVersion >= versionCode) {
                        showToast("已经是最新的版本");
                    } else {
                        String updateInfo = data.getUpdateInfo();
                        downloadUrl = data.getDownloadUrl();
                        createDialog(updateInfo);
                    }
                }
            }
        });
        return "";
    }

    private void createDialog(String updateInfo) {
        updateDialog = new Dialog(this, R.style.MyDialog);
        View inflate = getLayoutInflater().inflate(R.layout.view_updateapp, null);
        updateDialog.setContentView(inflate);
        updateDialog.setCanceledOnTouchOutside(true);
        updateDialog.show();
        ImageView imageView = (ImageView) inflate.findViewById(R.id.update_cancle);
        imageView.setOnClickListener(this);
        TextView text_info = (TextView) inflate.findViewById(R.id.update_message);
        text_info.setText(updateInfo);
        Button btn_sure = (Button) inflate.findViewById(R.id.update_sure);
        btn_sure.setOnClickListener(this);
    }
}
