package com.dzg.takeaway.adapter.listener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.EvaluateCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/14.
 */

public class EvaluateCategoryAdapter extends RecyclerView.Adapter<EvaluateCategoryAdapter.ViewHolder> {
    private List<EvaluateCategory> mList;
    private OnItemClickListener<EvaluateCategory> mOnItemClickListener;
    private Context mContext;

    public EvaluateCategoryAdapter(List<EvaluateCategory> mList, Context context) {
        this.mContext = context;
        this.mList = mList;
        if (mList != null && mList.size() > 0) {
            mList.get(0).setSelected(true);
        }
    }

    public void setDatas(List<EvaluateCategory> list){
        this.mList = list;
        if (mList != null && mList.size() > 0) {
            mList.get(0).setSelected(true);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<EvaluateCategory> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluate_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_evaluate_categorytv)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(int position) {
            textView.setText(mList.get(position).getName() + "(" + mList.get(position).getAmount() + ")");
            if (mList.get(position).isSelected()) {
                if (mList.get(position).getRecord_type() != 3)
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorMain));
                else
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.color_cc));
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                if (mList.get(position).getRecord_type() != 3)
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.color_ebf5ff));
                else
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.color_f5));
                textView.setTextColor(mContext.getResources().getColor(R.color.color_6d7885));
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mList.size(); i++) {
                        if (i == position) {
                            mList.get(i).setSelected(true);
                        } else {
                            mList.get(i).setSelected(false);
                        }
                    }
                    mOnItemClickListener.onItemClick(mList.get(position));
                }
            });
        }
    }
}
