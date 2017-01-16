package com.lin.diebaoguan.fsb.fragment;


import android.support.v4.app.Fragment;

import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.uibase.BasePullToRefrshListViewFragment;
import com.lin.lib_volley_https.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FSBAiMeiFangFragment extends BasePullToRefrshListViewFragment {
    @Override
    protected void getData(int pageOffset, VolleyListener volleyListener) {
        CommonUtils.fetchDataAtFsbOrDbg(pageOffset, this, true, 3, volleyListener);
    }
}
