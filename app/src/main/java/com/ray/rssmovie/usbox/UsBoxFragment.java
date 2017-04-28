package com.ray.rssmovie.usbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.rssmovie.R;
import com.ray.rssmovie.base.BaseLazyFragment;

/**
 * Created by guolei on 17-4-7.
 */

public class UsBoxFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout sw;
    private RecyclerView rl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usbox, container, false);
        // TODO: 17-4-28  init view
        return rootView;
    }

    @Override
    protected void loadData() {
        super.loadData();

//        sw.setColorSchemeResources(color);
        sw.setRefreshing(true);
        sw.setOnRefreshListener(this);

        rl.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void onRefresh() {

    }
}
