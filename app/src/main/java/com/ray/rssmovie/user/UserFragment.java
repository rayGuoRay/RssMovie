package com.ray.rssmovie.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.rssmovie.R;
import com.ray.rssmovie.base.BaseLazyFragment;

/**
 * Created by guolei on 17-4-7.
 */

public class UserFragment extends BaseLazyFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index, container, false);
        Log.d("raytest", "User Fragment OnCreate View");
        return rootView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("raytest", "User Fragment setUserVisible Hint:" + isVisibleToUser);
    }

    @Override
    protected void loadData() {
        super.loadData();
    }
}
