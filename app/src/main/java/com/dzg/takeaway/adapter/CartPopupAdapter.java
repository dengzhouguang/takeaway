package com.dzg.takeaway.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.event.CartMessage;
import com.dzg.takeaway.mvp.model.Food;
import com.dzg.takeaway.util.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/13.
 */

public class CartPopupAdapter extends RecyclerView.Adapter<CartPopupAdapter.ViewHolder> {
    private List<Food>mList;

    public CartPopupAdapter(List<Food> mList) {
        this.mList = mList;
    }
    public void setData(List<Food> list){
        this.mList=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_popup, parent, false);
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

    class ViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.item_cart_popup_nametv)
        TextView nameTv;
        @BindView(R.id.item_cart_popup_moneytv)
        TextView moneyTv;
        @BindView(R.id.item_cart_popup_numtv)
        TextView numTv;
        @BindView(R.id.item_cart_popup_addiv)
        ImageView addIv;
        @BindView(R.id.item_cart_popup_minuiv)
        ImageView minuIv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void onBindViewHolder(int position){
            nameTv.setText(mList.get(position).getFoodsBean().getName());
            moneyTv.setText(TextUtils.formatMoney(mList.get(position).getFoodsBean().getSpecfoods().get(0).getPrice()));
            numTv.setText(TextUtils.formatNum(mList.get(position).getNum()));
            addIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CartMessage(CartMessage.TYPE_ADD,mList.get(position).getPosition(),mList.get(position).getViewHolder()));
                }
            });
            minuIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CartMessage(CartMessage.TYPE_MINU,mList.get(position).getPosition(),mList.get(position).getViewHolder()));
                }
            });
        }
    }
}
