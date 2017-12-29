package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/4.
 */

public class SearchRestaurantAdapter extends RecyclerView.Adapter {
    private static int TYPE_RESTAURANT = 0;
    private static int TYPE_SEARCH = 1;
    private SearchRestaurant mSearchRestaurant;
    private Context mContext;
    private int mSize;
    private List<SearchRestaurant.RestaurantWithFoodsBean.RestaurantBean> mList;
    private OnItemClickListener<String> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<String> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public SearchRestaurantAdapter(SearchRestaurant searchRestaurant, Context content) {
        this.mSearchRestaurant = searchRestaurant;
        this.mContext = content;
        this.mSize = 0;
        this.mList = new ArrayList<>();
        List<SearchRestaurant.RestaurantWithFoodsBean> restaurant_with_foods = searchRestaurant.getRestaurant_with_foods();
        if (restaurant_with_foods != null) {
            for (SearchRestaurant.RestaurantWithFoodsBean restaurantWithFoodsBean : restaurant_with_foods) {
                if (restaurantWithFoodsBean.getRestaurant().getName().contains(searchRestaurant.getHighlights().get(0))) {
                    mList.add(restaurantWithFoodsBean.getRestaurant());
                    ++mSize;
                }
            }
            mSize = mSize + searchRestaurant.getHighlights().size();
        }
    }

    public void setData(SearchRestaurant searchRestaurant) {
        this.mSearchRestaurant = searchRestaurant;
        mSize = 0;
        this.mList = new ArrayList<>();
        List<SearchRestaurant.RestaurantWithFoodsBean> restaurant_with_foods = searchRestaurant.getRestaurant_with_foods();
        if (restaurant_with_foods != null) {
            for (SearchRestaurant.RestaurantWithFoodsBean restaurantWithFoodsBean : restaurant_with_foods) {
                if (searchRestaurant.getHighlights().size()>0)
                if (restaurantWithFoodsBean.getRestaurant().getName().contains(searchRestaurant.getHighlights().get(0))) {
                    mList.add(restaurantWithFoodsBean.getRestaurant());
                    ++mSize;
                }
            }
            if (searchRestaurant.getHighlights()!=null)
            mSize = mSize + searchRestaurant.getHighlights().size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mList.size()) {
            return TYPE_RESTAURANT;
        } else {
            return TYPE_SEARCH;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_RESTAURANT == viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_restaurant, parent, false);
            return new RestaurantViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_restaurant2, parent, false);
            return new SearchViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_RESTAURANT) {
            RestaurantViewHolder viewHolder = (RestaurantViewHolder) holder;
            viewHolder.onBindViewHolder(position);
        } else {
            SearchViewHolder viewHolder = (SearchViewHolder) holder;
            viewHolder.onBindViewHolder(position);
        }
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_search_restaurantiv)
        ImageView imageView;
        @BindView(R.id.item_search_restaurantll)
        LinearLayout linearLayout;
        @BindView(R.id.item_search_restaurant_nametv)
        TextView name;
        @BindView(R.id.item_search_restaurant_salestv)
        TextView sales;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindViewHolder(int position) {
            SearchRestaurant.RestaurantWithFoodsBean.RestaurantBean restaurant = mSearchRestaurant.getRestaurant_with_foods().get(position).getRestaurant();
            Glide.with(mContext).load(TextUtils.formatImageUrl(restaurant.getImage_path())).into(imageView);
            name.setText(restaurant.getName());
            sales.setText(TextUtils.formatSum(restaurant.getRating(),restaurant.getRecent_order_num(),restaurant.getDistance()));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo
                    Restaurant restaurant1=new Restaurant();
                /* mList.get(position);
                    Intent intent=new Intent();
                    intent.setClass(mContext, RestaurantDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("restaurant",mDatas.get(position));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);*/
                }
            });
        }
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_search_restauranttv)
        TextView name;
        @BindView(R.id.item_search_restaurantll)
        LinearLayout linearLayout;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindViewHolder(int position) {
            name.setText(mSearchRestaurant.getHighlights().get(position - mList.size()));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(mSearchRestaurant.getHighlights().get(position - mList.size()));
                }
            });
        }
    }
}
