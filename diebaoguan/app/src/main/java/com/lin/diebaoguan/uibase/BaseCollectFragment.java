package com.lin.diebaoguan.uibase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.bean.Paging;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.ArticleCollectDS;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 收藏界面的基类
 */
public class BaseCollectFragment extends PullToRefreshBaseFragment {
    private View view;
    protected List<Result> dataList = new ArrayList<>();
    private CollectListAdapter adapter;
    private int cid;
    protected ListView refreshableView;
    private int currentPageOffset = 0;//  当前已加载多少页
    private int totalPage;//总共有多少页

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), 0, false, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            refreshableView = basePullToRefreshListView.getRefreshableView();
            adapter = new CollectListAdapter();
            refreshableView.setAdapter(adapter);
            getData(0);
            basePullToRefreshListView.setOnRefreshListener(refreshListener);
        }
        return view;
    }

    private void getData(int pageOffset) {
        CommonUtils.fetchDataAtCollect(this, cid, pageOffset, volleyListener);
    }

    /**
     * 子类必须实现该方法，来获取对应的模块数据
     *
     * @param cid
     */
    protected void initCid(int cid) {
        this.cid = cid;
    }

    /**
     * 刷新回调
     */
    private PullToRefreshBase.OnRefreshListener2 refreshListener = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            currentPageOffset = 0;
            getData(currentPageOffset);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            if (currentPageOffset >= totalPage) {
                showToast(getString(R.string.alreadyatthebottom));
                basePullToRefreshListView.onRefreshComplete();
            } else {
                getData(currentPageOffset);
            }
        }
    };
    /**
     * 联网获取数据回调
     */
    private VolleyListener volleyListener = new VolleyListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogUtils.d(volleyError.toString());
            basePullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onResponse(String s) {
            basePullToRefreshListView.onRefreshComplete();
            NormalResponse normalResponse = NormalResponse.parseObject(s, NormalResponse.class);
            int status = normalResponse.getStatus();
            if (status == 1) {
                Data data = normalResponse.getData();
                Result[] result = data.getResult();
                // 假如是下拉刷新而不是上拉加载更多，需要先清旧数据，再加
                if (currentPageOffset == 0) {
                    dataList.clear();
                }
                Collections.addAll(dataList, result);
                adapter.notifyDataSetChanged();
                Paging paging = normalResponse.getData().getPaging();
                totalPage = paging.getPages();
                final int offset = currentPageOffset * Const.ROWS - 1;
                refreshableView.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshableView.smoothScrollToPosition(offset);
                    }
                });
                currentPageOffset += 1;
            }
        }
    };

    /**
     * Created by linx on 2017/1/1911:29.
     * mail :1057705307@QQ.com.
     * describe:收藏列表适配器
     */
    public class CollectListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_collectlist, null);
            TextView title = (TextView) inflate.findViewById(R.id.itemcollect_title);
            TextView content = (TextView) inflate.findViewById(R.id.itemcollect_abstract);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.itemcollect_iamge);
            ImageView collectImage = (ImageView) inflate.findViewById(R.id.itemcollect_image2);
            final Result result = dataList.get(position);
            title.setText(result.getTitle());
            content.setText(result.getContent());
            IMAGEUtils.displayImage(result.getPicUrl(), imageView);
            collectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataList.remove(position);
                    adapter.notifyDataSetChanged();
                    postCollect(result.getDocid());
                }
            });
            return inflate;
        }
    }

    /**
     * 取消收藏
     *
     * @param docid
     */
    private void postCollect(String docid) {
        ArticleCollectDS articleCollectDS = new ArticleCollectDS();
        articleCollectDS.setModule("api_libraries_sjdbg_articlecollect");
        articleCollectDS.setDocid(docid);
        articleCollectDS.setCid(1);
        articleCollectDS.setAuthkey(MyAppication.getKey());
        articleCollectDS.setUid(MyAppication.getUid());
        articleCollectDS.setMethod("cancel");
        articleCollectDS.initTimePart();
        CommonUtils.httpPost(articleCollectDS.parseParams(), collectListener);
    }

    /**
     * 收藏取消监听
     */
    VolleyListener collectListener = new VolleyListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogUtils.e(volleyError.toString());
            showToast(getString(R.string.collectedfailed) + volleyError.toString());
        }

        @Override
        public void onResponse(String s) {
            LogUtils.e(s);
            BaseResponseTemplate response = BaseResponseTemplate.parseObject(s, BaseResponseTemplate.class);
            int status = response.getStatus();
            String message = response.getMessage();
            if (status == 1) {
                showToast(message);
            } else {
                showToast(getString(R.string.collectedfailed) + message);
            }
        }
    };
}
