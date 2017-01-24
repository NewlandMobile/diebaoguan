package com.lin.diebaoguan.gyj.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.uibase.BasePullToRefrshListViewFragment;
import com.lin.lib_volley_https.VolleyListener;

/**
 * 综合
 * A simple {@link Fragment} subclass.
 */
public class GYJSyntheticalFragment extends BasePullToRefrshListViewFragment {
    @Override
    protected void getData(int pageOffset, VolleyListener volleyListener) {
        CommonUtils.fetchDataAtGyjPage(GYJSyntheticalFragment.this, 1, pageOffset, volleyListener);
    }

    @Override
    protected void addSpecialArgu(Intent intent) {
        intent.putExtra("ActivityLog", "GYJSYNTH");
    }
}
