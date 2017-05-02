package com.ray.rssmovie.mrank;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.rssmovie.R;
import com.ray.rssmovie.base.BaseLazyFragment;

import butterknife.ButterKnife;

/**
 * Created by guolei on 17-4-7.
 */

public class MovieRankFragment extends BaseLazyFragment {

    @butterknife.BindView(R.id.list_rl)
    android.support.v7.widget.RecyclerView listRl;
    View rootView;

    @Override
    public void onAttach(Context context) {
        Log.d("raytest", "Fragment onAttach");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_movie_rank, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("raytest", "Fragment onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("raytest", "Fragment onStart");
        super.onStart();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("raytest", "Fragment SetUserVisibleHint:" + isVisibleToUser);
    }

    @Override
    protected void loadData() {
        super.loadData();
    }
}
