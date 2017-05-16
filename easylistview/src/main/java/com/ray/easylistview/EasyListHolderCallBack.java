package com.ray.easylistview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by guolei on 17-5-16.
 */

public class EasyListHolderCallBack {

    public RecyclerView.ViewHolder onCreateNormal(View view) {
        return new EasyListAdapterHolder(view);
    }

    public RecyclerView.ViewHolder onCreateLoading(View view) {
        return new EasyListAdapterHolder(view);
    }

    public RecyclerView.ViewHolder onCreateNoMore(View view) {
        return new EasyListAdapterHolder(view);
    }

    public RecyclerView.ViewHolder onCreateError(View view) {
        return new EasyListAdapterHolder(view);
    }

    public void onBindNormal(RecyclerView.ViewHolder holder, int position) {
        // TODO: 17-5-16  
    }

    public void onBindLoading(RecyclerView.ViewHolder holder, int position) {
        // TODO: 17-5-16  
    }

    public void onBindNoMore(RecyclerView.ViewHolder holder, int position) {
        // TODO: 17-5-16  
    }

    public void onBindError(RecyclerView.ViewHolder holder, int position) {
        // TODO: 17-5-16
    }
}
