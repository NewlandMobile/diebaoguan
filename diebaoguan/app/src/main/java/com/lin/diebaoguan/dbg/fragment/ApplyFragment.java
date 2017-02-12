package com.lin.diebaoguan.dbg.fragment;


import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.uibase.BasePullToRefrshListViewFragment;
import com.lin.lib_volley_https.VolleyListener;

/**
 * 价格界面
 */
public class ApplyFragment extends BasePullToRefrshListViewFragment {
    @Override
    protected void getData(int pageOffset, VolleyListener volleyListener) {
        CommonUtils.fetchDataAtFsbOrDbg(pageOffset, this, false, 4, volleyListener);
    }
}
