package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.RestaurantDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/6.
 */

public class FavorableAdapter extends RecyclerView.Adapter {
    private List<RestaurantDetail.ActivitiesBean> mDatas;
    private Context mContext;

    public FavorableAdapter(List<RestaurantDetail.ActivitiesBean> datas, Context context) {
        this.mDatas = datas;
        this.mContext=context;
    }

    public void setDatas(List<RestaurantDetail.ActivitiesBean> datas){
        Log.e("数据情况",Boolean.valueOf(datas==null)+"");
        mDatas=datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorable, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ViewHolder viewHolder= (ViewHolder) holder;
    viewHolder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    class  ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.restaurant_detail_favourableiv)
        ImageView favourableIv;
        @BindView(R.id.restaurant_detail_favourabletv)
        TextView favourableTv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void onBindViewHolder( int position){
            String icon_name = mDatas.get(position).getIcon_name();
            if (icon_name.equals("首"))
                favourableIv.setImageResource(R.mipmap.shou);
            else if (icon_name.equals("减"))
                favourableIv.setImageResource(R.mipmap.jian);
            else {
                favourableIv.setImageResource(R.mipmap.te);
            }
            favourableTv.setText(mDatas.get(position).getDescription());
        }
    }
}
