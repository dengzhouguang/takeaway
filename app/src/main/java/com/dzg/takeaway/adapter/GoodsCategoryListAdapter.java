package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.RestaurantMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoodsCategoryListAdapter extends RecyclerView.Adapter {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_END = 1;
    //当前选中的位置
    private int mSelectPosition;
    private List<RestaurantMenu> mDatas;
    public Context mContext;

    public GoodsCategoryListAdapter(List<RestaurantMenu> mDatas, Context context) {
        mDatas.add(new RestaurantMenu());
        this.mDatas = mDatas;
        this.mContext = context;
    }

    public void changeData(List<RestaurantMenu> dataList) {
        this.mDatas = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == mDatas.size()) {
            return TYPE_END;
        } else return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_category_list, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_category_end, parent, false);
            return new NoThingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, final int position) {
        if (getItemViewType(position) == TYPE_END) {
            return;
        }
        ViewHolder holder= (ViewHolder) viewholder;
        holder.goodsCategoryName.setText(mDatas.get(position).getName());
        holder.shopCartNum.setText(String.valueOf(mDatas.get(position).getBugNum()));
        if (mDatas.get(position).getBugNum() > 0) {
            holder.shopCartNum.setVisibility(View.VISIBLE);
        } else {
            holder.shopCartNum.setVisibility(View.GONE);
        }

        if (mSelectPosition != -1) {
            if (mSelectPosition == position) {
                holder.itemView.setBackgroundResource(R.drawable.goods_category_list_bg_select);
                holder.goodsCategoryName.setTextColor(mContext.getResources().getColor(R.color.color_33));
            } else {
                holder.itemView.setBackgroundResource(R.drawable.goods_category_list_bg_normal);
                holder.goodsCategoryName.setTextColor(mContext.getResources().getColor(R.color.color_75));
            }
        } else {
            holder.itemView.setBackgroundResource(R.drawable.goods_category_list_bg_normal);
            holder.goodsCategoryName.setTextColor(mContext.getResources().getColor(R.color.color_75));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            }
        });

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     * 设置选中index
     *
     * @param position
     */
    public void setCheckPosition(int position) {
        this.mSelectPosition = position;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goodsCategoryNameTV)
        TextView goodsCategoryName;
        @BindView(R.id.restaurant_detail_shopCartNumTV)
        TextView shopCartNum;

        public ViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }
    public static class NoThingViewHolder extends RecyclerView.ViewHolder{

        public NoThingViewHolder(View itemView) {
            super(itemView);
        }
    }

}
