package com.lin.diebaoguan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lin.diebaoguan.R;

/**
 * 该类为带下拉刷新fragment的基类
 * 使用是可通过baseContent来填充内容，
 * mPullToRefreshListView来实现下拉监听
 */
public class PullToRefreshBaseFragment extends Fragment {

    public View inflate;//整体view
    public PullToRefreshScrollView basePullToRefreshScrollView;//用于实现下拉刷新
    public LinearLayout baseContent;//用于下拉刷新内容的填充
    public LinearLayout baseLinearLayout;//用于填充在顶部的广告
    private int layoutId;
    private Context context;


    public PullToRefreshBaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_pulltorefresh_base, container, false);
            baseContent = (LinearLayout) inflate.findViewById(R.id.basepull_content);
            baseLinearLayout = (LinearLayout) inflate.findViewById(R.id.basepull_banner);
            basePullToRefreshScrollView = (PullToRefreshScrollView) inflate.findViewById(R.id.pull_refresh_scrollview);
            View inflate = LayoutInflater.from(context).inflate(layoutId, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            baseContent.addView(inflate, params);
        }
        return inflate;
    }

    /**
     * 初始化主要参数.
     *
     * @param context  上下文
     * @param layoutId layout
     */
    public void initArgument(Context context, int layoutId) {
        this.layoutId = layoutId;
        this.context = context;
    }
}
