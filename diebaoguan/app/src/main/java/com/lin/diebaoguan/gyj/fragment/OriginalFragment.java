package com.lin.diebaoguan.gyj.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.GyjOriginalDetailsActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.bean.Paging;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.NormalDS;
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
    private int ROWS=20;
    private int currentOffset =0;
    private AdapterView.OnItemClickListener itemClickListener=null;
    // 获取完网络数据后 会更新这一变量，用于下次获取前的校验
    private Paging paging=null;
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
        }
        myAdapter=new MyAdapter();
        itemClickListener=new MyItemClickListener();
        refreshGridView.setOnItemClickListener(itemClickListener);
        refreshGridView.setAdapter(myAdapter);
        initRefreshListener();
//        showProgress();
        fetchListData(currentOffset);
        return view;
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            fetchDetailWithPicId(myAdapter.getItem(position).getPicid());
        }
    }

    private void fetchDetailWithPicId(int picid) {
        NormalDS params=new NormalDS();
        params.setPicid(picid);
        params.setModule("api_libraries_sjdbg_tudetail");
        params.setUid(MyAppication.getUid());
        params.setSize(500);
        CommonUtils.normalGetWayFetch(params, this, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d(volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.d(s);
                NormalResponse response=NormalResponse.parseObject(s,NormalResponse.class);
                LogUtils.d(response.toString());
                gotoDetaiActivity(response);
            }
        });
    }

    private void gotoDetaiActivity(NormalResponse response) {
        if (response==null)
            return;
        Data data=response.getData();
        if (response.getData()==null)
            return;
        Intent detailIntent=new Intent(getActivity(), GyjOriginalDetailsActivity.class);
        detailIntent.putExtra("Data",  data);
        startActivity(detailIntent);
    }

    private void initRefreshListener() {
        refreshListener2=new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//                showToast("onPullDownToRefresh");
//                refreshGridView.onRefreshComplete();
                currentOffset=0;
                fetchListData(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
//                showToast("onPullUpToFefresh");
                checkAndLoagMoreData();
//                refreshGridView.onRefreshComplete();
            }
        };
        refreshGridView.setOnRefreshListener(refreshListener2);
    }

    private void checkAndLoagMoreData() {
        if (paging==null)
        {
            refreshGridView.onRefreshComplete();
            return;
        }

        if (paging.getCurrentPage()>=paging.getTotal()){
            showToast("没有更多信息了");
            refreshGridView.onRefreshComplete();
            return;
        }
        currentOffset+=ROWS;
        fetchListData(currentOffset);
    }


    private void fetchListData(int offset) {
//        params.setCid(2);
//        params.setOffset(0);
        CommonUtils.fetchDataAtGyjPage(OriginalFragment.this, 2,offset,ROWS,new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d("网络请求出错："+volleyError);
                showToast(volleyError.getMessage());
//                dismissProgress();
                if (refreshGridView.isRefreshing()){
                    refreshGridView.onRefreshComplete();
                }
            }

            @Override
            public void onResponse(String s) {
//                dismissProgress();
                NormalResponse response=
                        NormalResponse.
                                parseObject(s,
                                        NormalResponse.class);
                LogUtils.d(response.toString());
                updateDataToGridView(response);
                if (refreshGridView.isRefreshing()){
                    refreshGridView.onRefreshComplete();
                }
            }
        });
    }

    private void updateDataToGridView(NormalResponse response) {
        Result[] results=response.getData().getResult();
        paging=response.getData().getPaging();
        List<Result> resultList=myAdapter.getDataList();
        //如果  位移为0 ，一般是 下拉刷新或者是初始化，可以将UI也初始化
        if (currentOffset==0){
            resultList.clear();
        }
        for (Result result:results){
            resultList.add(result);
        }
        myAdapter.setDataList(resultList);
        myAdapter.notifyDataSetChanged();
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
        public Result getItem(int position) {
            return dataList.get(position);
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
