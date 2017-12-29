package com.dzg.takeaway.mvp.model;

import com.dzg.takeaway.adapter.GoodsAdapter;

/**
 * Created by dengzhouguang on 2017/12/13.
 */

public class Food {
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public GoodsAdapter.ViewHolder getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(GoodsAdapter.ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public RestaurantMenu.FoodsBean getFoodsBean() {
        return foodsBean;
    }

    public void setFoodsBean(RestaurantMenu.FoodsBean foodsBean) {
        this.foodsBean = foodsBean;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private GoodsAdapter.ViewHolder viewHolder;
    private RestaurantMenu.FoodsBean foodsBean;
    private int position;

    public Food(int num, GoodsAdapter.ViewHolder viewHolder, RestaurantMenu.FoodsBean foodsBean, int position) {
        this.num = num;
        this.viewHolder = viewHolder;
        this.foodsBean = foodsBean;
        this.position = position;
    }
}
