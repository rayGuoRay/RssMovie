package com.ray.rssmovie.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ray.rssmovie.R;

/**
 * Description
 * Author      Ray.Guo
 * Date        17/4/29 22:02
 */
public class EasyListingView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "EasyListingView";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private LoadDataCallBack mLoadCallback;
    private boolean mIsLoadingMore;

    private int[] mStagLastPositions;

    //start load data in implements class
    public interface LoadDataCallBack {
        void onTopLoadStarted();
        void onBottomLoadStarted(int lastPosition);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EasyListingView);
        int layoutType = typedArray.getInt(R.styleable.EasyListingView_easyLayoutType, 0);
        int gridColumn = typedArray.getInt(R.styleable.EasyListingView_easyGridColumnNum, 1);
        int spanCount = typedArray.getInt(R.styleable.EasyListingView_easySpanCount, 1);
        int spanOrientation = typedArray.getInt(R.styleable.EasyListingView_easySpanOriention, 1);
        Log.d("raytest", "LayoutType:" + layoutType);
        Log.d("raytest", "GridCoulumn:" + gridColumn);
        Log.d("raytest", "SpanCount:" + spanCount);
        Log.d("raytest", "SpanOrientation:" + spanOrientation);
        typedArray.recycle();
        View easyView = LayoutInflater.from(context).inflate(R.layout.layout_easy_listing, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) easyView.findViewById(R.id.easylist_swipe_layout);
        mRecyclerView = (RecyclerView) easyView.findViewById(R.id.easylist_recycler_view);
        mLayoutManager = new LinearLayoutManager(context);
        switch (layoutType) {
            case 1:
                mLayoutManager = new GridLayoutManager(context, gridColumn);
                break;
            case 2:
                if (spanOrientation == 0) {
                    mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
                } else {
                    mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
                }
                break;
            default:
                mLayoutManager = new LinearLayoutManager(context);
                break;
        }
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
        int lastVisiblePosition = -1;
        if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisiblePosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisiblePosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            if (mStagLastPositions == null) {
                mStagLastPositions = new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()];
            }
            ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(mStagLastPositions);
            lastVisiblePosition = findMax(mStagLastPositions);
        }
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

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
