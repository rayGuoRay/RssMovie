package com.ray.rssmovie.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ray.rssmovie.R;
import com.ray.rssmovie.adapter.EasyListingAdapter;

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
    private EasyListingAdapter mAdapter;

    private int lastVisibleItem;

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d(TAG, "EasyListingView scroll stated:" + newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrollToLoadMore();
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
        View easyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_easy_listing, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) easyView.findViewById(R.id.easylist_swipe_layout);
        mRecyclerView = (RecyclerView) easyView.findViewById(R.id.easylist_recycler_view);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new EasyListingAdapter(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void scrollToLoadMore() {
        int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
        if (lastVisiblePosition + 1 == mAdapter.getItemCount()) {

        }
    }

    private void loadData() {
        // TODO: 17/4/29 to load more data at here
    }
}
