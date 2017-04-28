package com.ray.rssmovie.index;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ray.rssmovie.R;
import com.ray.rssmovie.TestActivity;
import com.ray.rssmovie.base.BaseActivity;
import com.ray.rssmovie.index.presenter.IndexPresenter;
import com.ray.rssmovie.mrank.MovieRankFragment;
import com.ray.rssmovie.usbox.UsBoxFragment;
import com.ray.rssmovie.user.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndexActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener, ActivityCompat.OnRequestPermissionsResultCallback {

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.MANAGE_DOCUMENTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.MANAGE_DOCUMENTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d("raytest", "we should explain why we need this permission!");
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_DOCUMENTS}, 999);
            }
        } else {
            Log.d("raytest", "read in =normal");
            readDocumentUi();
        }
    }

    private void readDocumentUi() {
//        Uri imageListUri = DocumentsContract.buildDocumentUri("com.smartisan.filemanager.providers.documents", "gallery.preview");
//        Cursor cursor = getContentResolver().query(imageListUri, null, null, null, null);
//        while (cursor.moveToNext()) {
//            String itemUriStr = cursor.getString(1); // the column 1 is uri
//            Log.d("raytest", "ItemString:" + itemUriStr);
//        }
//        DocumentsContract.deleteDocument(getContentResolver(), imageListUri);
        String str = "test";
        String str1 = "test1:1:10";
        String str2 = "test2:0";
        String[] s = str.split(":");
//        int m = str1.indexOf(":");
        Log.d("raytest", "Index Is:" + s.length);
        Log.d("raytest", "Index Str:" + s[0]);
//        Log.d("raytest", "Index2 Is:" + m);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("raytest", "Permission Size:" + permissions.length + "-----ResultSize:" + grantResults.length);
        for (int i = 0; i < permissions.length; i++) {
            Log.d("raytest", "Permission:" + permissions[i] + " GrantResult:" + grantResults[i]);
        }
        if (requestCode == 999) {
            Log.d("raytest", "read in callback");
            readDocumentUi();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2222) {
            Log.d("raytest", "Activity Restult:" + data.getData());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
