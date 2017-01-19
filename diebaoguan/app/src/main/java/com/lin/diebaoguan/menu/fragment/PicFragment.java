package com.lin.diebaoguan.menu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lin.diebaoguan.uibase.BaseCollectFragment;

/**
 * 图片收藏界面
 */
public class PicFragment extends BaseCollectFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCid(3);
    }
}
