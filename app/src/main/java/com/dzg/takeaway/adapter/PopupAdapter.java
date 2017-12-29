package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.mvp.model.SortPopupBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/5.
 */

public class PopupAdapter extends RecyclerView.Adapter {
    private List<SortPopupBean> mDatas;
    private OnItemClickListener<Integer> mOnItemClickListener;
    private Context mContext;

    public PopupAdapter(List<SortPopupBean> list, Context context) {
        this.mDatas = list;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setSelected(int position) {
        List<SortPopupBean> list = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++)
            if (position != i)
                list.add(new SortPopupBean(mDatas.get(i).getName(), false));
            else
                list.add(new SortPopupBean(mDatas.get(i).getName(), true));
        mDatas = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_up_item, null);
        ViewHolder h = new ViewHolder(view);
        h.itemView.setBackgroundResource(R.drawable.pw_item_bg);
        return h;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv.setText(mDatas.get(position).getName());
        if (mDatas.get(position).isSelected()) {
            viewHolder.iv.setVisibility(View.VISIBLE);
            viewHolder.tv.setTextColor(mContext.getResources().getColor(R.color.colorMain));
        } else {
            viewHolder.iv.setVisibility(View.INVISIBLE);
            viewHolder.tv.setTextColor(mContext.getResources().getColor(R.color.colorLightBlack));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name)
        TextView tv;
        @BindView(R.id.item_yes)
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}