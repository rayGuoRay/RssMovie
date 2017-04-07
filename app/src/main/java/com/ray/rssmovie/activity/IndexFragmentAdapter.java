package com.ray.rssmovie.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by guolei on 17-4-7.
 */

public class IndexFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListFragment;
    private List<String> mListTitle;

    public IndexFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<String> titles) {
        super(fragmentManager);
        mListFragment = fragments;
        mListTitle = titles;
    }

    @Override
    public int getCount() {
        return mListFragment == null ? 0 : mListFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (mListTitle == null || position >= mListTitle.size()) ? null : mListTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return (mListFragment == null || position >= mListFragment.size()) ? null : mListFragment.get(position);
    }
}