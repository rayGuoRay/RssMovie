package com.ray.rssmovie.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ray.rssmovie.R;

import java.util.List;

/**
 * Description
 * Author      Ray.Guo
 * Date        17/4/29 22:02
 */
public class EasyListingView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "EasyListingView";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private LoadDataCallBack mLoadCallback;
    private boolean mIsLoadingMore;

    //start load data in implements class
    public interface LoadDataCallBack {
        void onTopLoadStarted();
        void onBottomLoadStarted(int lastPosition);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d(TAG, "EasyListingView scroll stated:" + newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrollToBottomLoadMore();
        }
    };

    public EasyListingView(Context context) {
        this(context, null);
    }

    public EasyListingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyListingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View easyView = LayoutInflater.from(context).inflate(R.layout.layout_easy_listing, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) easyView.findViewById(R.id.easylist_swipe_layout);
        mRecyclerView = (RecyclerView) easyView.findViewById(R.id.easylist_recycler_view);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(scrollListener);
        // init param
        mIsLoadingMore = false;
    }

    public void startRefresh(boolean isRefresh) {
        mSwipeRefreshLayout.setRefreshing(isRefresh);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void setLoadDataCallback(LoadDataCallBack callback) {
        this.mLoadCallback = callback;
    }

    public void loadFinishedNotify() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        mIsLoadingMore = false;
    }

    @Override
    public void onRefresh() {
        if(mLoadCallback != null) {
            mLoadCallback.onTopLoadStarted();
        }
    }

    private void scrollToBottomLoadMore() {
        if (mAdapter == null) {
            return;
        }
        int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
        if (lastVisiblePosition + 1 == mAdapter.getItemCount()) {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                return;
            }
            if (!mIsLoadingMore) {
                mIsLoadingMore = true;
                if (mLoadCallback != null) {
                    mLoadCallback.onBottomLoadStarted(lastVisiblePosition);
                }
            }
        }
    }
}
