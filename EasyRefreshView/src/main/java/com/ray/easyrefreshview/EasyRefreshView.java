package com.ray.easyrefreshview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Description
 * Author      Ray.Guo
 * Date        17/4/29 22:02
 */
public class EasyRefreshView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "EasyRefreshView";

    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private EasyRefreshAdapter mAdapter;

    private LoadDataCallBack mLoadCallback;
    private boolean mIsLoadingMore;

    private int mLayoutType;
    private int mGridColumn;
    private int mSpanCount;
    private int mSpanOrientation;
    private int mNormalLayoutId;
    private int mLoadingLayoutId;
    private int mNoMoreLayoutId;
    private int mErrorLayoutId;
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

    public EasyRefreshView(Context context) {
        this(context, null);
    }

    public EasyRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        initParam(context, attrs);
        initView(context);
        initAdapter();
    }

    private void initParam(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EasyRefreshView);
        mLayoutType = typedArray.getInt(R.styleable.EasyRefreshView_layoutType, 0);
        mGridColumn = typedArray.getInt(R.styleable.EasyRefreshView_gridColumnNum, 1);
        mSpanCount = typedArray.getInt(R.styleable.EasyRefreshView_spanCount, 1);
        mSpanOrientation = typedArray.getInt(R.styleable.EasyRefreshView_spanOriention, 1);
        mNormalLayoutId = typedArray.getResourceId(R.styleable.EasyRefreshView_normalLayout, 0);
        mLoadingLayoutId = typedArray.getResourceId(R.styleable.EasyRefreshView_loadingLayout, 0);
        mNoMoreLayoutId = typedArray.getResourceId(R.styleable.EasyRefreshView_nomoreLayout, 0);
        mErrorLayoutId = typedArray.getResourceId(R.styleable.EasyRefreshView_errorLayout, 0);
        typedArray.recycle();
    }

    private void initView(Context context) {
        View easyView = LayoutInflater.from(context).inflate(R.layout.layout_easy_refresh, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) easyView.findViewById(R.id.easyrefresh_swipe_layout);
        mRecyclerView = (RecyclerView) easyView.findViewById(R.id.easyrefresh_recycler_view);
        mLayoutManager = new LinearLayoutManager(context);
        switch (mLayoutType) {
            case 1:
                mLayoutManager = new GridLayoutManager(context, mGridColumn);
                break;
            case 2:
                if (mSpanOrientation == 0) {
                    mLayoutManager = new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.HORIZONTAL);
                } else {
                    mLayoutManager = new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.VERTICAL);
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

    private void initAdapter() {
        this.mAdapter = new EasyRefreshAdapter(mContext);
        this.mAdapter.setNormalLayoutId(mNormalLayoutId);
        this.mAdapter.setLoadingLayoutId(mLoadingLayoutId);
        this.mAdapter.setNoMoreLayoutId(mNoMoreLayoutId);
        this.mAdapter.setErrorLayoutId(mErrorLayoutId);
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

    public void setLoadDataCallback(LoadDataCallBack callback) {
        this.mLoadCallback = callback;
    }

    public void setTopRefreshing(boolean isRefresh) {
        mSwipeRefreshLayout.setRefreshing(isRefresh);
    }

    public void bindAdapter(EasyRefreshHolderCallBack holderCallBack) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setEasyList(null);
        mAdapter.setHolderCallBack(holderCallBack);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setSourceList(List data) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setEasyList(data);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mIsLoadingMore = false;
        mAdapter.notifyDataSetChanged();
    }

    public void setFootViewState(final int footState) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setFootViewState(footState);
        post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
            }
        });
    }

//    public void loadFinishedToNotify() {
//        if (mSwipeRefreshLayout.isRefreshing()) {
//            mSwipeRefreshLayout.setRefreshing(false);
//        }
//        mAdapter.notifyDataSetChanged();
//        mIsLoadingMore = false;
//    }
}
