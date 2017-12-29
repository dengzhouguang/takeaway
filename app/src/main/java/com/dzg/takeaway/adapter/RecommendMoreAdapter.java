package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.util.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzhouguang on 2017/12/6.
 */

public class RecommendMoreAdapter extends RecyclerView.Adapter {
    private static int TYPE_TOP = 1;
    private static int TYPE_NORMAL = 2;
    private List<SearchRestaurant.RestaurantWithFoodsBean.FoodsBean> mList;
    private Context mContext;
    private boolean mIsTop;

    public boolean isTop() {
        return mIsTop;
    }

    public RecommendMoreAdapter(List<SearchRestaurant.RestaurantWithFoodsBean.FoodsBean> list, Context mContext, boolean isTop) {
        this.mList = list;
        this.mContext = mContext;
        this.mIsTop = isTop;
    }

    public void setData(List<SearchRestaurant.RestaurantWithFoodsBean.FoodsBean> list) {
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_top, parent, false);
            return new TopViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_normal, parent, false);
            return new NormalViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_TOP)
            ((TopViewHolder) holder).onBindViewHolder(position);
        else
            ((NormalViewHolder) holder).onBindViewHolder(position);
    }


    @Override
    public int getItemViewType(int position) {
        if (mIsTop && position == 0) {
            return TYPE_TOP;
        }

        return TYPE_NORMAL;
    }


    @Override
    public int getItemCount() {
        if (mIsTop) {
            if (mList.size() < 4) {
                return 1;
            } else {
                return mList.size() - 2;
            }
        }
        return mList.size();
    }

    class TopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_recommend_toprv)
        RecyclerView topRv;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindViewHolder(int position) {
            topRv.setLayoutManager(new GridLayoutManager(mContext, 3));
            topRv.setNestedScrollingEnabled(false);
            topRv.setHasFixedSize(false);
            if (mList.size() < 4)
                topRv.setAdapter(new TopFoodAdapter(mList, mContext));
            else
                topRv.setAdapter(new TopFoodAdapter(mList.subList(0, 3), mContext));

        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_recommend_normalrl)
        RelativeLayout normalRl;
        @BindView(R.id.item_recommend_normal_nametv)
        TextView nameTv;
        @BindView(R.id.item_recommend_normal_moneytv)
        TextView moneyTv;
        @BindView(R.id.item_recommend_normal_salestv)
        TextView salesTv;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindViewHolder(int position) {
            if (mIsTop) {
                nameTv.setText(mList.get(position + 2).getName());
                moneyTv.setText(TextUtils.formatMoney(mList.get(position + 2).getPrice()));
                salesTv.setText(TextUtils.formatSales(mList.get(position + 2).getMonth_sales()));
            } else {
                nameTv.setText(mList.get(position).getName());
                moneyTv.setText(TextUtils.formatMoney(mList.get(position).getPrice()));
                salesTv.setText(TextUtils.formatSales(mList.get(position).getMonth_sales()));
            }
        }

        @OnClick(R.id.item_recommend_normalrl)
        public void onClick(View view) {

        }
    }
}
