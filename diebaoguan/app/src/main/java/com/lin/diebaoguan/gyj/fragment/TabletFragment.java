package com.lin.diebaoguan.gyj.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lin.diebaoguan.uibase.BaseGridViewFragment;

/**
 * 平板美图
 * A simple {@link Fragment} subclass.
 */
public class TabletFragment extends BaseGridViewFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCid(4);
    }

}
