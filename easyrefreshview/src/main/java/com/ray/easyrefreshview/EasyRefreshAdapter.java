package com.ray.easyrefreshview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guolei on 17-5-16.
 */

public class EasyRefreshAdapter extends RecyclerView.Adapter {

    /* Constant of ItemType */
    private static final int TYPE_ITEM_NORMAL = 0;
    private static final int TYPE_ITEM_LOADING = 1;
    private static final int TYPE_ITEM_NOMORE = 2;
    private static final int TYPE_ITEM_ERROR = 3;

    /* Constant of FootState */
    public static final int FOOT_STATE_LOADING = 0;
    public static final int FOOT_STATE_LOAD_ERROR = 1;
    public static final int FOOT_STATE_LOAD_NOMORE = 2;

    private Context mContext;
    private EasyRefreshHolderCallBack mHolderCallback;
    private int mNormalLayoutId;
    private int mLoadingLayoutId;
    private int mNoMoreLayoutId;
    private int mErrorLayoutId;
    private int mFootState;
    private List mDataList = new ArrayList();

    public EasyRefreshAdapter(Context context) {
        this.mContext = context;
    }

    public void setEasyList(List data) {
        mDataList.clear();
        if (data == null || data.size() <= 0) {
            return;
        }
        mDataList.addAll(data);
    }

    public void setNormalLayoutId(int normalLayoutId) {
        this.mNormalLayoutId = normalLayoutId;
    }

    public void setLoadingLayoutId(int loadingLayoutId) {
        this.mLoadingLayoutId = loadingLayoutId;
    }

    public void setNoMoreLayoutId(int noMoreLayoutId) {
        this.mNoMoreLayoutId = noMoreLayoutId;
    }

    public void setErrorLayoutId(int errorLayoutId) {
        this.mErrorLayoutId = errorLayoutId;
    }

    public void setFootViewState(int state) {
        this.mFootState = state;
    }

    public void setHolderCallBack(EasyRefreshHolderCallBack callBack) {
        this.mHolderCallback = callBack;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasFootView() && position + 1 == getItemCount()) {
            switch (mFootState) {
                case FOOT_STATE_LOADING:
                    return TYPE_ITEM_LOADING;
                case FOOT_STATE_LOAD_NOMORE:
                    return TYPE_ITEM_NOMORE;
                case FOOT_STATE_LOAD_ERROR:
                    return TYPE_ITEM_ERROR;
            }
        }
        return TYPE_ITEM_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mDataList == null || mDataList.size() <= 0) {
            return 0;
        }
        return hasFootView() ? mDataList.size() + 1 : mDataList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_ITEM_NORMAL:
                view = LayoutInflater.from(mContext).inflate(mNormalLayoutId, parent, false);
                break;
            case TYPE_ITEM_LOADING:
                view = LayoutInflater.from(mContext).inflate(mLoadingLayoutId, parent, false);
                break;
            case TYPE_ITEM_NOMORE:
                view = LayoutInflater.from(mContext).inflate(mNoMoreLayoutId, parent, false);
                break;
            case TYPE_ITEM_ERROR:
                view = LayoutInflater.from(mContext).inflate(mErrorLayoutId, parent, false);
                break;
        }
        if (view == null) {
            return null;
        }
        return createViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mHolderCallback == null) {
            return;
        }
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_ITEM_NORMAL:
                mHolderCallback.onBindNormal(holder, position);
                break;
            case TYPE_ITEM_LOADING:
                mHolderCallback.onBindLoading(holder, position);
                break;
            case TYPE_ITEM_NOMORE:
                mHolderCallback.onBindNoMore(holder, position);
                break;
            case TYPE_ITEM_ERROR:
                mHolderCallback.onBindError(holder, position);
                break;
        }
    }

    private RecyclerView.ViewHolder createViewHolder(View view, int viewType) {
        if (mHolderCallback == null) {
            return null;
        }
        switch (viewType) {
            case TYPE_ITEM_NORMAL:
                return mHolderCallback.onCreateNormal(view);
            case TYPE_ITEM_LOADING:
                return mHolderCallback.onCreateLoading(view);
            case TYPE_ITEM_NOMORE:
                return mHolderCallback.onCreateNoMore(view);
            case TYPE_ITEM_ERROR:
                return mHolderCallback.onCreateError(view);
            default:
                return null;
        }
    }

    private boolean hasFootView() {
        if (mLoadingLayoutId <= 0 || mNoMoreLayoutId <=0 || mErrorLayoutId <= 0) {
            return false;
        }
        return true;
    }
}
