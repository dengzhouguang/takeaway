package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.util.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/14.
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder> {
    private List<Evaluate> mList;
    private Context mContext;

    public EvaluateAdapter(List<Evaluate> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    public void setDatas(List<Evaluate> list){
        this.mList=list;
    }

    public void addDatas(List<Evaluate> list){
        this.mList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluate_detail, parent, false);
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
        @BindView(R.id.item_evaluate_detail_anonymousiv)
        ImageView avatarIv;
        @BindView(R.id.item_evaluate_detail_nametv)
        TextView nameTv;
        @BindView(R.id.item_evaluate_detail_timetv)
        TextView timeTv;
        @BindView(R.id.item_evaluate_detail_satisfyiv)
        ImageView serviceSatisfyIv;
        @BindView(R.id.item_evaluate_detail_satisfytv)
        TextView serviceSatisfyTv;
        @BindView(R.id.item_evaluate_detail_good_satisfyiv)
        ImageView foodSatisfyIv;
        @BindView(R.id.item_evaluate_detail_good_satisfytv)
        TextView foodSatisfyTv;
        @BindView(R.id.item_evaluate_detail_deliverytv)
        TextView deliveryTimeTv;
        @BindView(R.id.item_evaluate_detail_rating_text_tv)
        TextView evaluateTv;
        @BindView(R.id.item_evaluate_detail_foodlistrv)
        RecyclerView foodEvaluateRv;
        @BindView(R.id.item_evaluate_detail_food_imagerv)
        RecyclerView foodImageRv;
        @BindView(R.id.item_evaluate_detail_good_view)
        View mDivider;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindViewHolder(int position) {
            if (!"".equals(mList.get(position).getAvatar()))
                Glide.with(mContext).load(TextUtils.formatImageUrl(mList.get(position).getAvatar())).into(avatarIv);
            else
                Glide.with(mContext).load(R.mipmap.anonymous).into(avatarIv);
            if (!mList.get(position).getUsername().equals("匿**户"))
                nameTv.setText(mList.get(position).getUsername());
            else
                nameTv.setText("匿名用户");
            timeTv.setText(mList.get(position).getRated_at());
            if (mList.get(position).getRating_star() == 5) {
                Glide.with(mContext).load(R.mipmap.excellent).into(serviceSatisfyIv);
                serviceSatisfyTv.setText("非常满意");
                serviceSatisfyTv.setTextColor(mContext.getResources().getColor(R.color.color_excellent));
            } else if (mList.get(position).getRating_star() > 3) {
                Glide.with(mContext).load(R.mipmap.satisfy).into(serviceSatisfyIv);
                serviceSatisfyTv.setText("满意");
                serviceSatisfyTv.setTextColor(mContext.getResources().getColor(R.color.color_satisfy));
            } else {
                Glide.with(mContext).load(R.mipmap.poor).into(serviceSatisfyIv);
                serviceSatisfyTv.setText("吐槽");
                serviceSatisfyTv.setTextColor(mContext.getResources().getColor(R.color.color_poor));
            }

            if (mList.get(position).getItem_rating_list().size() > 0)
                if (mList.get(position).getItem_rating_list().get(0).getRating_star() == 5) {
                    Glide.with(mContext).load(R.mipmap.excellent).into(foodSatisfyIv);
                    foodSatisfyTv.setText("非常满意");
                    foodSatisfyTv.setTextColor(mContext.getResources().getColor(R.color.color_excellent));
                } else if (mList.get(position).getRating_star() > 3) {
                    Glide.with(mContext).load(R.mipmap.satisfy).into(foodSatisfyIv);
                    foodSatisfyTv.setText("满意");
                    foodSatisfyTv.setTextColor(mContext.getResources().getColor(R.color.color_satisfy));
                } else {
                    Glide.with(mContext).load(R.mipmap.poor).into(foodSatisfyIv);
                    foodSatisfyTv.setText("吐槽");
                    foodSatisfyTv.setTextColor(mContext.getResources().getColor(R.color.color_poor));
                }

            if (!"".equals(mList.get(position).getTime_spent_desc())) {
                deliveryTimeTv.setText(mList.get(position).getTime_spent_desc());
                deliveryTimeTv.setVisibility(View.VISIBLE);
                mDivider.setVisibility(View.VISIBLE);
            } else {
                deliveryTimeTv.setVisibility(View.GONE);
                mDivider.setVisibility(View.GONE);
            }

            if (!"".equals(mList.get(position).getRating_text())) {
                evaluateTv.setText(mList.get(position).getRating_text());
                Log.e("error",mList.get(position).getRating_text());
                evaluateTv.setVisibility(View.VISIBLE);
            } else {
                evaluateTv.setVisibility(View.GONE);
            }

            foodEvaluateRv.setHasFixedSize(false);
            foodEvaluateRv.setNestedScrollingEnabled(false);
            foodEvaluateRv.setLayoutManager(new LinearLayoutManager(mContext));
            FoodEvaluateAdapter adapter=new FoodEvaluateAdapter(mList.get(position).getItem_rating_list());
            foodEvaluateRv.setAdapter(adapter);

            foodImageRv.setHasFixedSize(false);
            foodImageRv.setNestedScrollingEnabled(false);
            foodImageRv.setLayoutManager(new GridLayoutManager(mContext,3));
            foodImageRv.setAdapter(new EvaluateImageAdapter(mContext,mList.get(position).getItem_rating_list()));





        }
    }
}
