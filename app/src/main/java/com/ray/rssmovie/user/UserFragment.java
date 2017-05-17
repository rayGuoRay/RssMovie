package com.ray.rssmovie.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ray.easyrefreshview.EasyRefreshHolderCallBack;
import com.ray.easyrefreshview.EasyRefreshView;
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
public class UserFragment extends BaseLazyFragment implements EasyRefreshView.LoadDataCallBack {

    @BindView(R.id.user_elv)
    EasyRefreshView mUserElv;
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
        }

        @Override
        public void onError(Throwable e) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        mUserElv = (EasyRefreshView) rootView.findViewById(R.id.user_elv);
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
        mUserElv.bindAdapter(new EasyRefreshHolderCallBack() {
            @Override
            public RecyclerView.ViewHolder onCreateNormal(View view) {
                return new NormalHolder(view);
            }

            @Override
            public void onBindNormal(RecyclerView.ViewHolder holder, int position) {
                super.onBindNormal(holder, position);
                if (list == null || list.size() == 0) {
                    return;
                }
                MovieSubject subject = list.get(position);
                String subjectTitle = null;
                String imageUrl = null;
                String subjectId = subject.id;
                subjectTitle = subject.title;
                imageUrl = subject.images.large;
                if (!TextUtils.isEmpty(subjectTitle)) {
                    ((NormalHolder) holder).tv.setText(subjectTitle);
                }
                if (!TextUtils.isEmpty(imageUrl)) {
                    ((NormalHolder) holder).iv.setImageURI(imageUrl);
                }
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

    class NormalHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_content) View cv;
        @BindView(R.id.iv_data)
        SimpleDraweeView iv;
        @BindView(R.id.tv_date)
        TextView tv;

        public NormalHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
