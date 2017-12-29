package com.dzg.takeaway.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.ui.activity.RestaurantDetailActivity;
import com.dzg.takeaway.util.MathUtil;
import com.dzg.takeaway.util.TextUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/29.
 */

public class RestaurantAdapter extends XRecyclerView.Adapter {
    private List<Restaurant> mDatas;
    private Context mContext;

    public RestaurantAdapter(List<Restaurant> mDatas, Context context) {
        this.mDatas = mDatas;
        this.mContext = context;
    }

    public void addDatas(List<Restaurant> list) {
        boolean isExit;
        for (Restaurant restaurant : list) {
            isExit = false;
            for (Restaurant restaurant2 : mDatas) {
                if (restaurant.getId()==restaurant2.getId()) {
                    isExit = true;
                    break;
                }
            }
            if (!isExit)
                mDatas.add(restaurant);
        }
    }

    public void initDatas(List<Restaurant> list) {
        mDatas.clear();
        if (list!=null)
        mDatas.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Restaurant restaurant = mDatas.get(position);
        Glide.with(mContext).load(TextUtils.formatImageUrl(restaurant.getImage_path())).into(viewHolder.logoIv);
        viewHolder.nameTv.setText(mDatas.get(position).getName());
        viewHolder.starRb.setRating(MathUtil.parseStar((float) mDatas.get(position).getRating()));
        viewHolder.starTv.setText(TextUtils.formatRating(mDatas.get(position).getRating()));
        viewHolder.salesTv.setText(TextUtils.formatSales(mDatas.get(position).getRecent_order_num()));
        viewHolder.sendMoneyTv.setText(mDatas.get(position).getPiecewise_agent_fee().getDescription());
        viewHolder.timeAndDistanceTv.setText(TextUtils.formatDistanceAndTime(mDatas.get(position).getDistance(),mDatas.get(position).getOrder_lead_time()));
        if (mDatas.get(position).getActivities().size() > 1) {
            String iconName = mDatas.get(position).getActivities().get(0).getIcon_name();
            if (iconName.equals("首"))
                viewHolder.favourableIv.setImageResource(R.mipmap.shou);
            else if (iconName.equals("减"))
                viewHolder.favourableIv.setImageResource(R.mipmap.jian);
            else {
                viewHolder.favourableIv.setImageResource(R.mipmap.te);
            }
            viewHolder.favourableTv.setText(mDatas.get(position).getActivities().get(0).getDescription());
            iconName = mDatas.get(position).getActivities().get(1).getIcon_name();
            if (iconName.equals("首"))
                viewHolder.favourable2Iv.setImageResource(R.mipmap.shou);
            else if (iconName.equals("减"))
                viewHolder.favourable2Iv.setImageResource(R.mipmap.jian);
            else {
                viewHolder.favourable2Iv.setImageResource(R.mipmap.te);
            }
            viewHolder.favourable2Tv.setText(mDatas.get(position).getActivities().get(1).getDescription());
        } else if (mDatas.get(position).getActivities().size() == 1) {
            String iconName = mDatas.get(position).getActivities().get(0).getIcon_name();
            if (iconName.equals("首"))
                viewHolder.favourableIv.setImageResource(R.mipmap.shou);
            else if (iconName.equals("减"))
                viewHolder.favourableIv.setImageResource(R.mipmap.jian);
            else {
                viewHolder.favourableIv.setImageResource(R.mipmap.te);
            }
            viewHolder.favourableTv.setText(mDatas.get(position).getActivities().get(0).getDescription());
            viewHolder.favourable2Iv.setVisibility(View.GONE);
            viewHolder.favourable2Tv.setVisibility(View.GONE);
        } else {
            viewHolder.favourableIv.setVisibility(View.GONE);
            viewHolder.favourableTv.setVisibility(View.GONE);
            viewHolder.favourable2Iv.setVisibility(View.GONE);
            viewHolder.favourable2Tv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, RestaurantDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("restaurant",mDatas.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_logo)
        ImageView logoIv;
        @BindView(R.id.shop_name)
        TextView nameTv;
        @BindView(R.id.shop_starrb)
        RatingBar starRb;
        @BindView(R.id.shop_startv)
        TextView starTv;
        @BindView(R.id.shop_sales)
        TextView salesTv;
        @BindView(R.id.shop_sendmoney)
        TextView sendMoneyTv;
        @BindView(R.id.shop_timeanddistance)
        TextView timeAndDistanceTv;
        @BindView(R.id.shop_favourableiv)
        ImageView favourableIv;
        @BindView(R.id.shop_favourabletv)
        TextView favourableTv;
        @BindView(R.id.shop_favourable2iv)
        ImageView favourable2Iv;
        @BindView(R.id.shop_favourable2tv)
        TextView favourable2Tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
