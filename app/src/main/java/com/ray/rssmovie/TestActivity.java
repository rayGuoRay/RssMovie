package com.ray.rssmovie;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.ray.rssmovie.base.BaseActivity;

/**
 * Created by guolei on 17-4-20.
 */

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_film_item);
    }
}
