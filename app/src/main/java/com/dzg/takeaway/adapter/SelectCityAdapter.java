package com.dzg.takeaway.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.mvp.model.CitySelectBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectCityAdapter extends RecyclerView.Adapter {
    private List<CitySelectBean> mData;
    private OnItemClickListener<CitySelectBean> onItemClickListener;

    public SelectCityAdapter(List<CitySelectBean> list) {
        this.mData = list;
    }

    public void setOnItemClickListener(OnItemClickListener<CitySelectBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void addDatas(List<CitySelectBean> list){
        mData.addAll(list);
    }
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate((R.layout.item_location_city_select), parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
     ViewHolder viewHolder= (ViewHolder) holder;
     viewHolder.textView.setText(mData.get(position).getCity());
     viewHolder.textView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             onItemClickListener.onItemClick(mData.get(position));
         }
     });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvCity)
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}