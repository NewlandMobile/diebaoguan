package com.lin.diebaoguan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;

/**
 * 该类为带下拉刷新fragment的基类
 * 使用是可通过baseContent来填充要求下拉刷新的复合型内容，
 * mPullToRefreshListView来实现下拉listview监听
 */
public class PullToRefreshBaseFragment extends Fragment {

    public View inflate;//整体view
    public PullToRefreshScrollView basePullToRefreshScrollView;//用于实现下拉刷新
    public PullToRefreshListView basePullToRefreshListView;//用于实现下拉刷新listview控件
    public LinearLayout baseContent;//用于下拉刷新内容的填充
    public LinearLayout baseLinearLayout;//用于填充在顶部的广告
    private int layoutId;//用于加载内容
    private Context context;//上下文
    private boolean scrollviewVisiable;
    private boolean listViewVisiable;
    private ProgressDialog progressDialog;

    public PullToRefreshBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_pulltorefresh_base, container, false);
        basePullToRefreshListView = (PullToRefreshListView) inflate.findViewById(R.id.pull_refresh_list);
        basePullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新
        baseLinearLayout = (LinearLayout) inflate.findViewById(R.id.basepull_banner);
        basePullToRefreshScrollView = (PullToRefreshScrollView) inflate.findViewById(R.id.pull_refresh_scrollview);
        if (scrollviewVisiable) {
            baseContent = (LinearLayout) inflate.findViewById(R.id.basepull_content);
            View inflate = LayoutInflater.from(context).inflate(layoutId, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            baseContent.addView(inflate, params);
            basePullToRefreshScrollView.setVisibility(View.VISIBLE);
            basePullToRefreshListView.setVisibility(View.GONE);
        }
        if (listViewVisiable) {
            basePullToRefreshScrollView.setVisibility(View.GONE);
            basePullToRefreshListView.setVisibility(View.VISIBLE);
        }

        return inflate;
    }

    /**
     * 初始化主要参数.如果内容本身只有listview可直接使scroll不可见，
     * listview可见，只要适配listview的内容即可,
     * scrollviewVisiable 与 listViewVisiable 二选一
     * 都为true 原则上只显示scrollview
     * 如果内容为复合型，则可以使用scroll来实现。
     *
     * @param context            上下文
     * @param layoutId           layout
     * @param scrollviewVisiable 是否让basePullToRefreshScrollView
     * @param listViewVisiable   是否让listViewVisiable可见
     */
    public void initArgument(Context context, int layoutId, boolean scrollviewVisiable, boolean listViewVisiable) {
        this.layoutId = layoutId;
        this.context = context;
        this.scrollviewVisiable = scrollviewVisiable;
        this.listViewVisiable = listViewVisiable;
    }

    public void showProgress() {
        if (progressDialog==null){  progressDialog = CommonUtils.showProgressDialog(getActivity());
        }
        if (progressDialog.isShowing()){
            return;
        }
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }

    //本类名  方便测试打印
//    protected final String classNameString=this.getClass().getName();

    public void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    public void showLongTimeToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }


}
