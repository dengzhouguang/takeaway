package com.dzg.takeaway.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/14.
 */

public class FoodEvaluateAdapter extends RecyclerView.Adapter<FoodEvaluateAdapter.ViewHolder> {
    private List<Evaluate.ItemRatingListBean> mList;

    public FoodEvaluateAdapter(List<Evaluate.ItemRatingListBean> list) {
        List<Evaluate.ItemRatingListBean> list2=new ArrayList<>();
        for (Evaluate.ItemRatingListBean bean:list){
            if (!bean.getRating_text().equals(""))
                list2.add(bean);
        }
        this.mList = list2;
    }

    public List<Evaluate.ItemRatingListBean> getList() {
        return mList;
    }

    public void setList(List<Evaluate.ItemRatingListBean> list) {
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluate_detail_food_evaluate, parent, false);
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
        @BindView(R.id.item_evaluate_detail_food_evaluatetv)
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindViewHolder(int position) {
                content.setText(Html.fromHtml(TextUtils.formatHtml(mList.get(position).getRate_name(), mList.get(position).getRating_text())));
        }

    }
}
