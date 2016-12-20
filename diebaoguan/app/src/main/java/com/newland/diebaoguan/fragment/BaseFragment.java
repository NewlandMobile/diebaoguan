package com.newland.diebaoguan.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.diebaoguan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    private String topTitle;

    private View inflate;
    private TextView text_top;
    private RelativeLayout rl_content;
    private int layoutId;
    private Context context;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_base, container, false);
            initView();
        }
        return inflate;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        text_top = (TextView) inflate.findViewById(R.id.base_toptext);
        rl_content = (RelativeLayout) inflate.findViewById(R.id.base_content);
        text_top.setText(topTitle);
        View inflate = LayoutInflater.from(context).inflate(layoutId, null);
        rl_content.addView(inflate);
    }

    /**
     * 初始化参数
     */
    public void initArgument(Context context, String topTitle, int layoutId) {
        this.topTitle = topTitle;
        this.layoutId = layoutId;
        this.context = context;
    }

}
