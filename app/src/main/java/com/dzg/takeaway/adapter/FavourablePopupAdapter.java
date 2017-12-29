package com.dzg.takeaway.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.RestaurantDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/12.
 */

public class FavourablePopupAdapter extends RecyclerView.Adapter<FavourablePopupAdapter.ViewHolder> {
    private List<RestaurantDetail.ActivitiesBean> mDatas;

    public FavourablePopupAdapter(List<RestaurantDetail.ActivitiesBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourable_popup, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_favourable_popup_favourable_nametv)
        TextView nameTv;
        @BindView(R.id.item_favourable_popup_favourable_contenttv)
        TextView contentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindViewHolder(int position) {
            String string = mDatas.get(position).getIcon_name();
            if ("特".equals(string)) {
                nameTv.setText("特价");
            } else if ("首".equals(string)) {
                nameTv.setText("首单");
            } else if ("减".equals(string)) {
                nameTv.setText("满减");
            } else if ("折".equals(string)) {
                nameTv.setText("折扣");
            } else if ("赠".equals(string)) {
                nameTv.setText("赠送");
            } else if ("领".equals(string)) {
                nameTv.setText("领券");
            }
            nameTv.setBackgroundColor(Integer.valueOf(mDatas.get(position).getIcon_color(), 16)+0xff000000);
            contentTv.setText(mDatas.get(position).getDescription());
        }
    }
}
