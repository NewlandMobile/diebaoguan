package com.lin.diebaoguan.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.uibase.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * 带有二级导航的界面基础类
 */
public class EvolveFragment extends BaseFragment {
    private String[] topicList;//顶部小模块集合
    private Class[] fragmentList;//小模块结合
    private View view;
    protected FragmentTabHost mTabHost;

    public EvolveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
//            assert view != null;
            mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
            mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.evolve_realtabcontent);
            for (int i = 0; i < topicList.length; i++) {
                setAddTab(topicList[i], fragmentList[i]);
            }
        }
        return view;
    }

    /**
     * 初始化主要参数.
     * topics与fragmentList数量上要求保持一致
     *
     * @param context      上下文
     * @param topTitle     大模块的标题
     * @param topics       小模块的标题
     * @param fragmentList
     */
    public void initArgument(Context context, String topTitle, String[] topics, Class[] fragmentList) {
        this.topicList = topics;
        this.fragmentList = fragmentList;
        super.initArgument(context, topTitle, R.layout.fragment_evolve);
    }

    /**
     * @param indicator 标签
     * @param cls
     */
    private void setAddTab(String indicator, Class<?> cls) {
        TabHost.TabSpec newTabSpec = mTabHost.newTabSpec(indicator);
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_inducator_top, null);
        TextView textview = (TextView) view.findViewById(R.id.inducator_top_text);
        textview.setText(indicator);
        textview.setBackground(getResources().getDrawable(R.drawable.inducator_top_selector));
        mTabHost.addTab(newTabSpec.setIndicator(view), cls, null);
    }

//    public void aa() {
//        LogUtils.e(" 我是谍报馆");
//    }

    public void setCurrentTab(int index) {
//        LogUtils.e("setCurrentTab==进来了=== "+index);
        mTabHost.setCurrentTab(index);
    }
}
