package com.ray.rssmovie.usbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.ray.rssmovie.bean.UsBoxMovie;
import com.ray.rssmovie.bean.UsBoxMovieList;
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

public class UsBoxFragment extends BaseLazyFragment implements EasyListingView.LoadDataCallBack {

    private EasyListingView mElv;
    private List<MovieSubject> list = new ArrayList<MovieSubject>();

    private Observer<UsBoxMovie> observer = new Observer<UsBoxMovie>() {
        @Override
        public void onNext(UsBoxMovie subject) {
            list.add(subject.subject);
            mElv.loadFinishedNotify();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        mElv = (EasyListingView) rootView.findViewById(R.id.user_elv);
        return rootView;
    }

    @Override
    protected void loadData() {
        super.loadData();
        EasyListingAdapter mAdapter = new EasyListingAdapter(getContext(), this);
        mAdapter.setListData(list);
        mElv.setAdapter(mAdapter);
        mElv.setLoadDataCallback(this);
        mElv.startRefresh(true);
        onTopLoadStarted();
    }

    @Override
    public void onTopLoadStarted() {
        list.clear();
        startRxLoad();
    }

    @Override
    public void onBottomLoadStarted(int position) {
//        startRxLoad();
    }

    private void startRxLoad() {
        Observable<UsBoxMovieList> observable = RetrofitWrapper.getInstance().getNetWorkApi().getNabor();
        observable.map(movieList -> movieList.subjects)
                .flatMap(subjectList -> Observable.from(subjectList))
//                .map(subject -> subject.title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
