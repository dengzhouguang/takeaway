package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/15.
 */

public class EvaluateImageAdapter extends RecyclerView.Adapter<EvaluateImageAdapter.ViewHolder> {
    private  List<Evaluate.ItemRatingListBean> mList;
    private Context mContext;
    public EvaluateImageAdapter(Context context, List<Evaluate.ItemRatingListBean> listBeans) {
        List<Evaluate.ItemRatingListBean> list=new ArrayList<>();
        for (Evaluate.ItemRatingListBean bean:listBeans){
            if (!"".equals(bean.getImage_hash())){
                list.add(bean);
            }
        }
        this.mList=list;
        Log.e("list",list.size()+"");
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluate_detail_food_imageview, parent, false);
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
    class  ViewHolder extends  RecyclerView.ViewHolder{
       @BindView(R.id.item_evaluate_detail_food_imageview)
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void onBindViewHolder(int position) {
            Glide.with(mContext).load(TextUtils.formatMediumImageUrl(mList.get(position).getImage_hash())).into(imageView);
        }
    }
}
