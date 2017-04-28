package com.ray.rssmovie.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by guolei on 17-4-28.
 */

public class EasyRecyclerView extends RelativeLayout {

    public EasyRecyclerView(Context context) {
        this(context, null);
    }

    public EasyRecyclerView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public EasyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, null, 0, 0)
    }

    public EasyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
    }
}
