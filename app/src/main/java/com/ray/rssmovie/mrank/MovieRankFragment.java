package com.ray.rssmovie.mrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.rssmovie.R;
import com.ray.rssmovie.base.BaseLazyFragment;

/**
 * Created by guolei on 17-4-7.
 */

public class MovieRankFragment extends BaseLazyFragment {

    @butterknife.BindView(R.id.list_rl)
    android.support.v7.widget.RecyclerView listRl;
    private View rootView;

    private boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_movie_rank, container, false);
            ButterKnife.inject(this, view);
            isPrepared = true;
            lazyLoad();
        }
        return rootView;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }
}
