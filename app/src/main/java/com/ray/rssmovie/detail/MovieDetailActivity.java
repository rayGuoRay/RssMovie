package com.ray.rssmovie.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ray.rssmovie.R;
import com.ray.rssmovie.base.BaseActivity;
import com.ray.rssmovie.bean.MovieDetail;
import com.ray.rssmovie.network.RetrofitWrapper;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guolei on 17-5-4.
 */

public class MovieDetailActivity extends BaseActivity {

    private TextView title;
    private SimpleDraweeView alterImage;
    private TextView summary;

    private String subjectId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView) findViewById(R.id.title);
        summary = (TextView) findViewById(R.id.summary);
        alterImage = (SimpleDraweeView) findViewById(R.id.sd_image);

        subjectId = getIntent().getStringExtra("subjectId");
        Observable<MovieDetail> observable = RetrofitWrapper.getInstance().getNetWorkApi().getSubjectDetail(subjectId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieDetail -> {
                    title.setText(movieDetail.title);
                    summary.setText(movieDetail.summary);
                    alterImage.setImageURI(movieDetail.images.large);
                });
    }


}
