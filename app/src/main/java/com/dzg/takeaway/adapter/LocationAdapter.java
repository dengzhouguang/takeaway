package com.dzg.takeaway.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.mvp.model.Address;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/2.
 */

public class LocationAdapter extends RecyclerView.Adapter {
    private List<Address> mList;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public LocationAdapter(List<Address> mList) {
        this.mList = mList;
    }
    public void initDatas(List<Address> list){
        mList.clear();
        if (list!=null)
        mList.addAll(list);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
     ViewHolder viewHolder= (ViewHolder) holder;
     viewHolder.textView.setText(mList.get(position).getName());
     viewHolder.textView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             mOnItemClickListener.onItemClick(mList.get(position));
         }
     });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.location_item_nearbytv)
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
