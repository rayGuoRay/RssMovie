package com.ray.rssmovie.mrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.rssmovie.R;
import com.ray.rssmovie.adapter.EasyListingAdapter;
import com.ray.rssmovie.application.AppConstant;
import com.ray.rssmovie.base.BaseLazyFragment;
import com.ray.rssmovie.bean.MovieList;
import com.ray.rssmovie.bean.MovieSubject;
import com.ray.rssmovie.network.RetrofitWrapper;
import com.ray.rssmovie.widget.EasyListingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guolei on 17-4-7.
 */

public class MovieRankFragment extends BaseLazyFragment implements EasyListingView.LoadDataCallBack {

    @BindView(R.id.user_elv)
    EasyListingView mUserElv;

    Unbinder unbinder;
    private int mTotalCount;
    private List<MovieSubject> mList = new ArrayList<MovieSubject>();

    private Observer<MovieSubject> observer = new Observer<MovieSubject>() {
        @Override
        public void onNext(MovieSubject subject) {
            mList.add(subject);
        }

        @Override
        public void onCompleted() {
            if (mUserElv == null) {
                return;
            }
            RecyclerView.Adapter mAdapter = mUserElv.getAdapter();
            if (mAdapter == null) {
                return;
            }
            ((EasyListingAdapter) mAdapter).setListData(mList);
            mUserElv.loadFinishedNotify();
        }

        @Override
        public void onError(Throwable e) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void loadData() {
        super.loadData();
        EasyListingAdapter mAdapter = new EasyListingAdapter(getContext(), this);
        mAdapter.setListData(mList);
        mUserElv.setAdapter(mAdapter);
        mUserElv.setLoadDataCallback(this);
        mUserElv.startRefresh(true);
        onTopLoadStarted();
    }

    @Override
    public void onTopLoadStarted() {
        mList.clear();
        startRxLoad(0);
    }

    @Override
    public void onBottomLoadStarted(int position) {
        if (position >= mTotalCount) {
            RecyclerView.Adapter mAdapter = mUserElv.getAdapter();
            ((EasyListingAdapter) mAdapter).setFootState(EasyListingAdapter.FOOT_STATE_LOAD_NOMORE);
            return;
        }
        RecyclerView.Adapter mAdapter = mUserElv.getAdapter();
        ((EasyListingAdapter) mAdapter).setFootState(EasyListingAdapter.FOOT_STATE_LOADING);
        startRxLoad(position + 1);
    }

    private void startRxLoad(int start) {
        Observable<MovieList> observable = RetrofitWrapper.getInstance().getNetWorkApi().getTop250(start, AppConstant.PAGE_SIZE);
        observable.map(movieList -> {
                    mTotalCount = movieList.total;
                    return movieList.subjects;
                })
                .flatMap(subjectList -> Observable.from(subjectList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
