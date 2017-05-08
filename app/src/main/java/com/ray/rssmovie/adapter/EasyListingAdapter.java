package com.ray.rssmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ray.rssmovie.R;
import com.ray.rssmovie.bean.MovieSubject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description
 * Author      Ray.Guo
 * Date        17/4/29 22:39
 */
public class EasyListingAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public interface OnListItemClickCallback {
        void onListItemClick(int position, String id);
    }

    private Context mContext;
    private List<MovieSubject> mData;
    private OnListItemClickCallback mCallback;

    public EasyListingAdapter(Context context, OnListItemClickCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder == false) {
            return;
        }
        MovieSubject subject = mData.get(position);
        String subjectTitle = null;
        String imageUrl = null;
//            if (TextUtils.isEmpty(subject.data)) {
        String subjectId = subject.id;
        subjectTitle = subject.title;
        imageUrl = subject.images.large;
//            } else {
//                BaseSubject baseSubject = subject.subject;
//                subjectTitle = baseSubject.title;
//                imageUrl = baseSubject.images.large;
//            }
        if (!TextUtils.isEmpty(subjectTitle)) {
            ((ItemViewHolder) holder).tv.setText(subjectTitle);
        }
        if (!TextUtils.isEmpty(imageUrl)) {
            ((ItemViewHolder) holder).iv.setImageURI(imageUrl);
        }
        ((ItemViewHolder) holder).cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onListItemClick(position, subjectId);
                }
            }
        });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_content) View cv;
        @BindView(R.id.iv_data) SimpleDraweeView iv;
        @BindView(R.id.tv_date) TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }
}
