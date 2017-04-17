package com.ray.rssmovie.index;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description
 * Author      Ray.Guo
 * Date        17/4/16 13:28
 */
public class IndexFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListFragment;

    public IndexFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        mListFragment = fragments;
    }

    @Override
    public int getCount() {
        return mListFragment == null ? 0 : mListFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
//        return (mListTitle == null || position >= mListTitle.size()) ? null : mListTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return (mListFragment == null || position >= mListFragment.size()) ? null : mListFragment.get(position);
    }
}
