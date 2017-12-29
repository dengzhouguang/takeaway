package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.util.List;

public class BigramHeaderAdapter implements StickyHeadersAdapter<BigramHeaderAdapter.ViewHolder> {

    private  Context mContext;
    private List<RestaurantMenu.FoodsBean> dataList;
    private List<RestaurantMenu> goodscatrgoryEntities;
    public BigramHeaderAdapter(Context context, List<RestaurantMenu.FoodsBean> items
            , List<RestaurantMenu> goodscatrgoryEntities) {
        this.mContext = context;
        this.dataList = items;
        this.goodscatrgoryEntities = goodscatrgoryEntities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_goods_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {
        headerViewHolder.tvGoodsItemTitle.setText(goodscatrgoryEntities.get(dataList.get(position).getId()).getName());
        headerViewHolder.tvGoodsItemDetail.setText(goodscatrgoryEntities.get(dataList.get(position).getId()).getDescription());
    }

    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getId();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGoodsItemTitle;
        TextView tvGoodsItemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGoodsItemTitle =itemView.findViewById(R.id.restaurant_detail_menutv);
            tvGoodsItemDetail=itemView.findViewById(R.id.restaurant_detail_menu_detailtv);
        }
    }
}
