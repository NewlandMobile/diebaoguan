package com.lin.diebaoguan.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.fsb.fragment.FSBAiMeiFangFragment;
import com.lin.diebaoguan.uibase.BaseFragment;

/**
 * 爱美坊
 */
public class AiMeiFangFragment extends BaseFragment {
    private View view;

    public AiMeiFangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), getResources().getString(R.string.aimeifang), R.layout.fragment_ai_mei_fang);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            FragmentManager fragmentManager = getFragmentManager();//获得管理器
            // 这手偷梁换柱真是漂亮
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启一个新事务
            fragmentTransaction.add(R.id.framelayout, new FSBAiMeiFangFragment());
            fragmentTransaction.commit();
        }
        return view;
    }
}
