package com.newland.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newland.diebaoguan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlineFragment extends Fragment {


    public HeadlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_headline, container, false);
    }

}
