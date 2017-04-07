package com.ray.rssmovie.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.rssmovie.R;

/**
 * Created by guolei on 17-4-7.
 */

public class FragmentB extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index, container, false);
        return rootView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
