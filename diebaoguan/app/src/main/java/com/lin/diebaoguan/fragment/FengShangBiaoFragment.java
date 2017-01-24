package com.lin.diebaoguan.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FengShangBiaoFragment extends EvolveFragment {


    private View view;

    public FengShangBiaoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), getResources().getString(R.string.fengshanbiao), Const.FSB_TOPICS, Const.FSB_Fragments);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            LogUtils.e("fengshangbiao");
            MyAppication.addFragment("fengshangbiao", this);
        }
        return view;
    }

//    @Override
//    public void onDestroyView() {
//        ViewGroup parent = (ViewGroup) view.getParent();
//        parent.removeView(view);
//        super.onDestroyView();
//    }

    public void aa(){
        LogUtils.e(" 我是风尚标");
    }
    public void setCurrentTab(int index) {
        LogUtils.e("=====FSB===进来了=== "+index);
        mTabHost.setCurrentTab(index);
    }
}
