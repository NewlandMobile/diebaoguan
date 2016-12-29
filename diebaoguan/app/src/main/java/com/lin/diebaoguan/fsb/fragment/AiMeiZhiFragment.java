package com.lin.diebaoguan.fsb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AiMeiZhiFragment extends PullToRefreshBaseFragment {


    public AiMeiZhiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ai_mei_zhi, container, false);
    }

}
