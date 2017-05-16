package com.ray.rssmovie.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.easylistview.EasyListHolderCallBack;
import com.ray.easylistview.EasyListView;
import com.ray.rssmovie.R;
import com.ray.rssmovie.adapter.EasyListingAdapter;
import com.ray.rssmovie.application.AppConstant;
import com.ray.rssmovie.base.BaseLazyFragment;
import com.ray.rssmovie.bean.MovieList;
import com.ray.rssmovie.bean.MovieSubject;
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
public class UserFragment extends BaseLazyFragment implements EasyListView.LoadDataCallBack {

    @BindView(R.id.user_elv)
    EasyListView mUserElv;
    private int mTotalCount;
    Unbinder unbinder;

    private List<MovieSubject> list = new ArrayList<MovieSubject>();

    private Observer<MovieSubject> observer = new Observer<MovieSubject>() {
        @Override
        public void onNext(MovieSubject subject) {
            list.add(subject);
        }

        @Override
        public void onCompleted() {
            if (mUserElv == null) {
                return;
            }
            mUserElv.setSourceList(list);
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
        mUserElv.bindAdapter(new EasyListHolderCallBack() {
            @Override
            public RecyclerView.ViewHolder onCreateNormal(View view) {
                return super.onCreateNormal(view);
            }
        });
        mUserElv.setLoadDataCallback(this);
        mUserElv.setTopRefreshing(true);
        onTopLoadStarted();
    }

    @Override
    public void onTopLoadStarted() {
        list.clear();
        startRxLoad(0);
    }

    @Override
    public void onBottomLoadStarted(int position) {
        if (position >= mTotalCount) {
            mUserElv.setFootViewState(EasyListingAdapter.FOOT_STATE_LOAD_NOMORE);
            return;
        }
        mUserElv.setFootViewState(EasyListingAdapter.FOOT_STATE_LOADING);
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
