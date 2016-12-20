package com.newland.diebaoguan.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newland.diebaoguan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AiMeiFangFragment extends BaseFragment {


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
        View view = super.onCreateView(inflater, container, savedInstanceState);
        TextView textView = (TextView)view.findViewById(R.id.text101);
        textView.setText("aaaaaaaaaaaaaa");
        return view;
    }
}
