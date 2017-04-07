package com.ray.rssmovie.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ray.rssmovie.R;
import com.ray.rssmovie.bean.MovieList;
import com.ray.rssmovie.network.RetrofitWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IndexActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<String> mListTitle;
    private List<Fragment> mListFragment;

    private IndexFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        search("景甜", "");
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.index_tl_spec);
        viewPager = (ViewPager) findViewById(R.id.index_vp_content);

        mListTitle = new ArrayList<String>();
        mListTitle.add("Fragment A");
        mListTitle.add("Fragment B");
        mListTitle.add("Fragment C");

        tabLayout.addTab(tabLayout.newTab().setText(mListTitle.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(mListTitle.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(mListTitle.get(2)));

        mListFragment = new ArrayList<Fragment>();
        mListFragment.add(new FragmentA());
        mListFragment.add(new FragmentB());
        mListFragment.add(new FragmentC());

        adapter = new IndexFragmentAdapter(getSupportFragmentManager(), mListFragment, mListTitle);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void search(String name, String tag) {
        Map<String, String> paramMap = new HashMap<String, String>();
        if (!TextUtils.isEmpty(name)) {
            paramMap.put("q", name);
        }
        if (!TextUtils.isEmpty(tag)) {
            paramMap.put("tag", tag);
        }
        paramMap.put("start", "0");
        paramMap.put("count", "20");
        Observable<MovieList> observable = RetrofitWrapper.getInstance().getNetWorkApi().searchMovie(paramMap);
        observable.map(moveList -> moveList.subjects)
                .flatMap(movieSubjects -> Observable.from(movieSubjects))
                .map(subject -> subject.title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(string -> Log.d("raytest", "Movie String:" + string));
    }
}
