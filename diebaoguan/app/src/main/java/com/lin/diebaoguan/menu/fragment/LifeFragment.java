package com.lin.diebaoguan.menu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.uibase.BaseCollectFragment;

/**
 * 生活收藏界面
 */
public class LifeFragment extends BaseCollectFragment implements AdapterView.OnItemClickListener {

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCid(2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            refreshableView.setOnItemClickListener(this);
        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("currentOffset", position - 1);
        intent.putExtra("allItem", dataList.toArray());
        startActivity(intent);
    }
}
