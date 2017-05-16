package com.ray.rssmovie.usbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.easylistview.EasyListView;
import com.ray.rssmovie.R;
import com.ray.rssmovie.adapter.EasyListingAdapter;
import com.ray.rssmovie.base.BaseLazyFragment;
import com.ray.rssmovie.bean.MovieSubject;
import com.ray.rssmovie.bean.UsBoxMovie;
import com.ray.rssmovie.bean.UsBoxMovieList;
import com.ray.rssmovie.network.RetrofitWrapper;

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

public class UsBoxFragment extends BaseLazyFragment implements EasyListView.LoadDataCallBack {

    private static final int MAX_COUNT = 10;

    @BindView(R.id.user_elv)
    EasyListView mUserElv;

    Unbinder unbinder;

    private int mTotalCount;
    private List<MovieSubject> list = new ArrayList<MovieSubject>();

    private Observer<UsBoxMovie> observer = new Observer<UsBoxMovie>() {
        @Override
        public void onNext(UsBoxMovie subject) {
            list.add(subject.subject);
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
            ((EasyListingAdapter) mAdapter).setListData(list);
            mUserElv.loadFinishedToNotify();
        }

        @Override
        public void onError(Throwable e) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        mUserElv = (EasyListView) rootView.findViewById(R.id.user_elv);
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
        mAdapter.setListData(list);
        mUserElv.setAdapter(mAdapter);
        mUserElv.setLoadDataCallback(this);
        mUserElv.setTopRefreshing(true);
        onTopLoadStarted();
    }

    @Override
    public void onTopLoadStarted() {
        list.clear();
        startRxLoad();
    }

    @Override
    public void onBottomLoadStarted(int position) {
        if (position >= MAX_COUNT) {
            RecyclerView.Adapter mAdapter = mUserElv.getAdapter();
            ((EasyListingAdapter) mAdapter).setFootState(EasyListingAdapter.FOOT_STATE_LOAD_NOMORE);
            return;
        }
    }

    private void startRxLoad() {
        Observable<UsBoxMovieList> observable = RetrofitWrapper.getInstance().getNetWorkApi().getNabor();
        observable.map(movieList -> movieList.subjects)
                .flatMap(subjectList -> Observable.from(subjectList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
