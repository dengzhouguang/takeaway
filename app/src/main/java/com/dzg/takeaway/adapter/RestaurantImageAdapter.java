package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.listener.OnItem2ClickListener;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.util.TextUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/14.
 */

public class RestaurantImageAdapter extends RecyclerView.Adapter<RestaurantImageAdapter.ViewHolder> {
    private ArrayList<RestaurantDetail.AlbumsBean> mList;
    private Context mContext;
    private OnItem2ClickListener<ArrayList<RestaurantDetail.AlbumsBean>,Integer> mOnItem2ClickListener;

    public void setOnItemClickListener(OnItem2ClickListener onItemClickListener) {
        this.mOnItem2ClickListener = onItemClickListener;
    }

    public RestaurantImageAdapter(ArrayList<RestaurantDetail.AlbumsBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_image, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_restaurant_imageview)
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void onBindViewHolder( int position) {
            Glide.with(mContext).load(TextUtils.formatMediumImageUrl(mList.get(position).getCover_image_hash())).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (mOnItem2ClickListener!=null){
                    mOnItem2ClickListener.onItemClick(mList,position);
                }
                }
            });

        }
    }
}
