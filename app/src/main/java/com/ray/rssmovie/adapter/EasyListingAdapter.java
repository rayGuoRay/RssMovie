package com.ray.rssmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ray.rssmovie.R;
import com.ray.rssmovie.bean.MovieSubject;

import java.util.List;

/**
 * Description
 * Author      Ray.Guo
 * Date        17/4/29 22:39
 */
public class EasyListingAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context mContext;
    private List<MovieSubject> mData;

    public EasyListingAdapter(Context context) {
        this.mContext = context;
    }

    public void setListData(List data) {
        this.mData = data;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return (mData == null || mData.size() == 0) ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_normal, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            MovieSubject subject = mData.get(position);
            ((ItemViewHolder) holder).tv.setText(subject.title);
            ((ItemViewHolder) holder).iv.setImageURI(subject.images.large);

        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView iv;
        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_date);
            iv = (SimpleDraweeView) view.findViewById(R.id.iv_data);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
