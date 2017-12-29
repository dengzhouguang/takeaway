package com.dzg.takeaway.event;

import com.dzg.takeaway.adapter.GoodsAdapter;

/**
 * Created by dengzhouguang on 2017/12/13.
 */

public class CartMessage {
    public static final  int TYPE_ADD=0;
    public static final  int TYPE_MINU=1;
    private int type;

    private int position;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CartMessage(int type, int position, GoodsAdapter.ViewHolder viewHolder) {
        this.type = type;
        this.position = position;
        this.viewHolder = viewHolder;
    }

    private GoodsAdapter.ViewHolder viewHolder;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public GoodsAdapter.ViewHolder getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(GoodsAdapter.ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }
}
