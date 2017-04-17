package com.ray.rssmovie.index;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ray.rssmovie.R;
import com.ray.rssmovie.base.BaseActivity;
import com.ray.rssmovie.index.presenter.IndexPresenter;
import com.ray.rssmovie.mrank.MovieRankFragment;
import com.ray.rssmovie.usbox.UsBoxFragment;
import com.ray.rssmovie.user.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndexActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.index_vp_content)
    ViewPager mIndexVpContent;
    @BindView(R.id.index_bn_bar)
    BottomNavigationBar mIndexBnBar;

    private List<Fragment> mIndexFragmentList;
    private IndexPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        initBottomBar();
        initViewPager();
    }

    private void initBottomBar() {
        mIndexBnBar.setTabSelectedListener(this);
        String[] itemNames = getResources().getStringArray(R.array.index_item_names);
        TypedArray itemIconsNormal = getResources().obtainTypedArray(R.array.index_item_icon_normal);
        TypedArray itemIconsActive = getResources().obtainTypedArray(R.array.index_item_icon_active);
        for (int i = 0; i < itemNames.length; i++) {
            int normalResId = itemIconsNormal.getResourceId(i, 0);
            int activeResId = itemIconsActive.getResourceId(i, 0);
            BottomNavigationItem navigationItem = new BottomNavigationItem(activeResId, itemNames[i]);
            navigationItem.setInactiveIconResource(normalResId);
            mIndexBnBar.addItem(navigationItem);
        }
        mIndexBnBar.initialise();
    }

    private void initViewPager() {
        mIndexFragmentList = new ArrayList<Fragment>();
        mIndexFragmentList.add(new MovieRankFragment());
        mIndexFragmentList.add(new UsBoxFragment());
        mIndexFragmentList.add(new UserFragment());
        IndexFragmentAdapter indexFragmentAdapter = new IndexFragmentAdapter(getSupportFragmentManager(), mIndexFragmentList);
        mIndexVpContent.setAdapter(indexFragmentAdapter);
        mIndexVpContent.addOnPageChangeListener(this);
        mIndexVpContent.setCurrentItem(0);
    }

    @Override
    public void onTabSelected(int position) {
        mIndexVpContent.setCurrentItem(position);
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndexBnBar.selectTab(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //    private void initView() {
//        tabLayout = (TabLayout) findViewById(R.id.index_tl_spec);
//        viewPager = (ViewPager) findViewById(R.id.index_vp_content);
//
//        mListTitle = new ArrayList<String>();
//        mListTitle.add("Fragment A");
//        mListTitle.add("Fragment B");
//        mListTitle.add("Fragment C");
//
//        tabLayout.addTab(tabLayout.newTab().setText(mListTitle.get(0)));
//        tabLayout.addTab(tabLayout.newTab().setText(mListTitle.get(1)));
//        tabLayout.addTab(tabLayout.newTab().setText(mListTitle.get(2)));
//
//        mListFragment = new ArrayList<Fragment>();
//        mListFragment.add(new FragmentA());
//        mListFragment.add(new FragmentB());
//        mListFragment.add(new FragmentC());
//
//        adapter = new IndexFragmentAdapter(getSupportFragmentManager(), mListFragment, mListTitle);
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//    }
//
//    public void search(String name, String tag) {
//        Map<String, String> paramMap = new HashMap<String, String>();
//        if (!TextUtils.isEmpty(name)) {
//            paramMap.put("q", name);
//        }
//        if (!TextUtils.isEmpty(tag)) {
//            paramMap.put("tag", tag);
//        }
//        paramMap.put("start", "0");
//        paramMap.put("count", "20");
//        Observable<MovieList> observable = RetrofitWrapper.getInstance().getNetWorkApi().searchMovie(paramMap);
//        observable.map(moveList -> moveList.subjects)
//                .flatMap(movieSubjects -> Observable.from(movieSubjects))
//                .map(subject -> subject.title)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(string -> Log.d("raytest", "Movie String:" + string));
//    }
}
