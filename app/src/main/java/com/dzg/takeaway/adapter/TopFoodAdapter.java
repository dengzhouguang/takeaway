package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.util.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/6.
 */

public class TopFoodAdapter extends RecyclerView.Adapter {
    private List<SearchRestaurant.RestaurantWithFoodsBean.FoodsBean> mList;
    private Context mContext;

    public TopFoodAdapter(List<SearchRestaurant.RestaurantWithFoodsBean.FoodsBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setData(List<SearchRestaurant.RestaurantWithFoodsBean.FoodsBean> list) {
        this.mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_top_food,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
              ViewHolder viewHolder= (ViewHolder) holder;
              viewHolder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class  ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_recommend_top_foodiv)
        ImageView foodIv;
        @BindView(R.id.item_recommend_top_food_nametv)
        TextView nameTv;
        @BindView(R.id.item_recommend_top_food_moneytv)
        TextView moneyTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindViewHolder(int position) {
            Glide.with(mContext).load(TextUtils.formatImageUrl(mList.get(position).getImage_path())).into(foodIv);
            nameTv.setText(mList.get(position).getName());
            moneyTv.setText(TextUtils.formatMoney(mList.get(position).getPrice()));
        }
    }
}
