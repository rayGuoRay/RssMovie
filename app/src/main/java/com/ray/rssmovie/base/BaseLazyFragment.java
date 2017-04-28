package com.ray.rssmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Description
 * Author      Ray.Guo
 * Date        17/4/17 22:24
 */
public abstract class BaseLazyFragment extends Fragment {

    protected boolean mIsPrepared;
    protected boolean mIsVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {}

    protected void lazyLoad() {
        if (!mIsVisible || !mIsPrepared) {
            return;
        }
        loadData();
    }

    protected void loadData() {}
}
