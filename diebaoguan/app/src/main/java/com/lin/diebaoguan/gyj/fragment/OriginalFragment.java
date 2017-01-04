package com.lin.diebaoguan.gyj.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.DieBaoGuanAndFengShangBiaoResponse;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 精品原创
 * A simple {@link Fragment} subclass.
 */
public class OriginalFragment extends PullToRefreshBaseFragment {

//    private List<Result>  dataList=null;

    private View view;
//    private GridView gridView;
    private MyAdapter myAdapter=null;
    private PullToRefreshBase.OnRefreshListener2 refreshListener2;
    private PullToRefreshGridView refreshGridView;
//    private final ProgressDialog progressDialog = CommonUtils.showProgressDialog(getActivity());

    public OriginalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(),0, false, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view==null){
            view=super.onCreateView(inflater,container,savedInstanceState);
            ViewGroup parentView= (ViewGroup) basePullToRefreshScrollView.getParent();
            parentView.removeView(basePullToRefreshScrollView);
            parentView.removeView(basePullToRefreshListView);
            refreshGridView= (PullToRefreshGridView) inflater.inflate(R.layout.fragment_original,null);
//            refreshGridView= (PullToRefreshGridView) baseView.findViewById(R.id.gridView_original);
            refreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
            parentView.addView(refreshGridView);
//             refreshGridView= (PullToRefreshGridView) view.findViewById(R.id.gridView);
//            View baseView = inflater.inflate(R.layout.fragment_original, container, false);
//            initView(baseView);
//            baseContent.addView(baseView);
        }
        myAdapter=new MyAdapter();
        refreshGridView.setAdapter(myAdapter);
        initRefreshListener();
        showProgress();
        fetchInitData();
        return view;
    }

    private void initRefreshListener() {
        refreshListener2=new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                showToast("onPullDownToRefresh");
                refreshGridView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                showToast("onPullUpToFefresh");

                refreshGridView.onRefreshComplete();
            }
        };
        refreshGridView.setOnRefreshListener(refreshListener2);
    }


    private void fetchInitData() {
        CommonUtils.fetchDataAtGyjPage(new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d("网络请求出错："+volleyError);
                showToast(volleyError.getMessage());
                dissProgress();
            }

            @Override
            public void onResponse(String s) {
                dissProgress();
                DieBaoGuanAndFengShangBiaoResponse response=
                        DieBaoGuanAndFengShangBiaoResponse.
                                parseObject(s,
                                        DieBaoGuanAndFengShangBiaoResponse.class);
//                showToast(response.toString());
                LogUtils.d(response.toString());
                Result[] results=response.getData().getResult();
                List<Result> resultList=new ArrayList<Result>(results.length);
                for (Result result:results){
                    resultList.add(result);
                }
                myAdapter.setDataList(resultList);
                myAdapter.notifyDataSetChanged();

            }
        });
//        progressDialog.dismiss();
    }

    class MyAdapter extends BaseAdapter{

        class ViewHolder {
            TextView textView;
            ImageView imageView;
        }
        private List<Result>  dataList=new ArrayList<>();
        private LayoutInflater layoutInflater=LayoutInflater.from(getActivity());

        public List<Result> getDataList() {
            return dataList;
        }

        public void setDataList(List<Result> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=layoutInflater.inflate(R.layout.item_gridview_gyj,null);
                viewHolder=new ViewHolder();
                convertView.setTag(viewHolder);
                viewHolder.textView= (TextView) convertView.findViewById(R.id.textView2);
                viewHolder.imageView= (ImageView) convertView.findViewById(R.id.imageView3);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            Result result=dataList.get(position);
            IMAGEUtils.displayImage(result.getPicUrl(),viewHolder.imageView);
            viewHolder.textView.setText(result.getTitle());
            
            return convertView;
        }
    }
}
