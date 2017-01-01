package com.lin.diebaoguan.fsb.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.send.DieBaoGuanAndFengShangBiaoDS;
import com.lin.diebaoguan.network.response.DieBaoGuanAndFengShangBiaoResponse;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 综合
 */
public class FSBSyntheticalFragment extends PullToRefreshBaseFragment {
    public FSBSyntheticalFragment() {
        // Required empty public constructor
    }

    private List<String> list = new ArrayList<>();
    private ListView refreshableView;
    private View view;
    private DieBaoGuanAndFengShangBiaoDS sendParams=new DieBaoGuanAndFengShangBiaoDS();
    //TODO  这个固定值用的页面也会比较多，可以考虑放到底层基类去。
    private static final String moduleString="api_libraries_sjdbg_articlelist";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), 0, false, true);
        fetchDataFromNetWork(true,1);
    }

    /**
     * 根据 具体板块内容，获取后台信息
     * @param isFengShangBiao 是否属于风尚标板块
     * @param detailPageNum 具体板块的数值 （当isclass=0时，传值1,2,3,4分别对应谍报馆：新品，价格，体验，应用.
    当isclass=1时，传值1,2,3,4分别对应风尚标：综合，爱美妆，爱美访，雯琰文
    ）
     */
    private void fetchDataFromNetWork(boolean isFengShangBiao,int detailPageNum) {
        sendParams.setModule(moduleString);
        sendParams.setIsclass(isFengShangBiao?1:0);
        sendParams.setCid(detailPageNum);
        //TODO 这几个参数后期要改活的
        sendParams.setOffset(0);
        sendParams.setRows(2);
//        sendParams.setOnetime();
        sendParams.initTimePart();

        CommonUtils.httpGet(sendParams.parseParams(), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d(volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.d(s);
                DieBaoGuanAndFengShangBiaoResponse response=DieBaoGuanAndFengShangBiaoResponse.
                        parseObject(s,DieBaoGuanAndFengShangBiaoResponse.class);
                LogUtils.d(response.toString());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            refreshableView = basePullToRefreshListView.getRefreshableView();
            refreshableView.setAdapter(new RefreshListAdapter(getActivity(), list));
        }
        return view;
    }

}
