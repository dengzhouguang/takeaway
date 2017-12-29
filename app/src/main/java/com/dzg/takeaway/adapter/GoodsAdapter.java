package com.dzg.takeaway.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.listener.OnItemClickViewListener;
import com.dzg.takeaway.event.GoodsListEvent;
import com.dzg.takeaway.event.MessageEvent;
import com.dzg.takeaway.mvp.model.Food;
import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.dzg.takeaway.ui.activity.RestaurantDetailActivity;
import com.dzg.takeaway.util.DensityUtil;
import com.dzg.takeaway.util.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private List<RestaurantMenu.FoodsBean> mDatas;
    private int[] goodsNum;
    private int buyNum;
    private int totalPrice;
    private int[] mSectionIndices;
    private int[] mGoodsCategoryBuyNums;
    private Activity mActivity;
    private TextView shopCart;
    private ImageView buyImg;
    private List<RestaurantMenu> goodscatrgoryEntities;
    private List<Food> mFoodList;
    private String[] mSectionLetters;
    private List<RestaurantMenu.FoodsBean> selectGoods = new ArrayList<>();
    private OnItemClickViewListener<RestaurantMenu.FoodsBean, View, Integer, ViewHolder> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickViewListener<RestaurantMenu.FoodsBean, View, Integer, ViewHolder> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public GoodsAdapter(Activity activity, List<RestaurantMenu.FoodsBean> items, List<RestaurantMenu> goodscatrgoryEntities) {
        this.mActivity = activity;
        this.mDatas = items;
        this.goodscatrgoryEntities = goodscatrgoryEntities;
        initGoodsNum();
        mFoodList = new ArrayList<>();
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
        mGoodsCategoryBuyNums = getBuyNums();
        setHasStableIds(true);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_restaurant_detail_goods_list, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return mDatas.get(position).hashCode();
    }

    public void clear() {
        mSectionIndices = new int[0];
        mSectionLetters = new String[0];
        notifyDataSetChanged();
    }

    /*public void restore() {
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
        notifyDataSetChanged();
    }*/

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position + 1 == mDatas.size()) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, DensityUtil.dp2px(50));
            holder.itemView.setLayoutParams(layoutParams);
        } else {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            holder.itemView.setLayoutParams(layoutParams);
        }

        //设置名
        holder.goodsCategoryName.setText(mDatas.get(position).getName());
      /*  //设置说明
        holder.tvGoodsDescription.setText(mDatas.get(position).getDescription());*/
        //设置价格
        holder.tvGoodsPrice.setText(TextUtils.formatMoney(mDatas.get(position).getSpecfoods().get(0).getPrice()));
        if (mDatas.get(position).getImage_path() != null) {
            Glide.with(mActivity)
                    .load(TextUtils.formatImageUrl(mDatas.get(position).getImage_path()))
                    .into(holder.ivGoodsImage);
            holder.ivGoodsImage.setVisibility(View.VISIBLE);
        } else {
            holder.ivGoodsImage.setVisibility(View.GONE);
        }
        //通过判别对应位置的数量是否大于0来显示隐藏数量
        isSelected(goodsNum[position], holder);

        //加号按钮点击
        holder.ivGoodsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShopCart(position, holder);

            }
        });
        //减号点击按钮点击
        holder.ivGoodsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deteleShopCart(position, holder);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(mDatas.get(position), v, position, holder);
            }
        });
    }

    public void setShopCart(TextView shopCart) {
        this.shopCart = shopCart;
    }

    /**
     * 初始化各个商品的购买数量
     */
    private void initGoodsNum() {
        int leng = mDatas.size();
        goodsNum = new int[leng];
        for (int i = 0; i < leng; i++) {
            goodsNum[i] = 0;
        }
    }

    /**
     * 开始动画
     *
     * @param view
     */
    public void startAnim(View view) {
        buyImg = new ImageView(mActivity);
        buyImg.setBackgroundResource(R.mipmap.icon_goods_add_item);// 设置buyImg的图片
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        view.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        ((RestaurantDetailActivity) mActivity).setAnim(buyImg, startLocation);// 开始执行动画
    }

    /**
     * 判断商品是否有添加到购物车中
     *
     * @param i  条目下标
     * @param vh ViewHolder
     */
    private void isSelected(int i, ViewHolder vh) {
        if (i == 0) {
            vh.tvGoodsSelectNum.setVisibility(View.GONE);
            vh.ivGoodsMinus.setVisibility(View.GONE);
        } else {
            vh.tvGoodsSelectNum.setVisibility(View.VISIBLE);
            vh.tvGoodsSelectNum.setText(i + "");
            vh.ivGoodsMinus.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 存放每个组里的添加购物车的数量
     *
     * @return
     */
    public int[] getBuyNums() {
        int[] letters = new int[goodscatrgoryEntities.size()];
        for (int i = 0; i < goodscatrgoryEntities.size(); i++) {
            letters[i] = goodscatrgoryEntities.get(i).getBugNum();
        }
        return letters;
    }

    /**
     * 存放每个分组的第一条的ID
     *
     * @return
     */
    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        int lastFirstPoi = -1;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getId() != lastFirstPoi) {
                lastFirstPoi = mDatas.get(i).getId();
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    /**
     * 填充每一个分组要展现的数据
     *
     * @return
     */
    private String[] getSectionLetters() {
        String[] letters = new String[mSectionIndices.length];
        for (int i = 0; i < mSectionIndices.length; i++) {
            letters[i] = goodscatrgoryEntities.get(i).getName();
        }
        return letters;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 显示减号的动画
     *
     * @return
     */
    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    /**
     * 隐藏减号的动画
     *
     * @return
     */
    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 4f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    public void addShopCart(int position, ViewHolder holder) {
        goodsNum[position]++;
        selectGoods.add(mDatas.get(position));
        mGoodsCategoryBuyNums[mDatas.get(position).getId()]++;
        buyNum++;
        totalPrice += mDatas.get(position).getSpecfoods().get(0).getPrice();
        if (goodsNum[position] <= 1) {
            holder.ivGoodsMinus.setAnimation(getShowAnimation());
            holder.tvGoodsSelectNum.setAnimation(getShowAnimation());
            holder.ivGoodsMinus.setVisibility(View.VISIBLE);
            holder.tvGoodsSelectNum.setVisibility(View.VISIBLE);
            mFoodList.add(new Food(goodsNum[position], holder, mDatas.get(position), position));
        } else {
            for (Food food : mFoodList) {
                if (food.getPosition() == position) {
                    food.setNum(goodsNum[position]);
                    break;
                }
            }
        }
        startAnim(holder.ivGoodsAdd);
        changeShopCart();
        if (mOnGoodsNunChangeListener != null)
            mOnGoodsNunChangeListener.onNumChange();
        isSelected(goodsNum[position], holder);
    }

    public void deteleShopCart(int position, ViewHolder holder) {
        if (goodsNum[position] > 0) {
            goodsNum[position]--;
            selectGoods.remove(mDatas.get(position));
            mGoodsCategoryBuyNums[mDatas.get(position).getId()]--;
            isSelected(goodsNum[position], holder);
            buyNum--;
            totalPrice -= mDatas.get(position).getSpecfoods().get(0).getPrice();
            if (goodsNum[position] <= 0) {
                holder.ivGoodsMinus.setAnimation(getHiddenAnimation());
                holder.tvGoodsSelectNum.setAnimation(getHiddenAnimation());
                holder.ivGoodsMinus.setVisibility(View.GONE);
                holder.tvGoodsSelectNum.setVisibility(View.GONE);
                for (Food food : mFoodList) {
                    if (food.getPosition() == position) {
                        mFoodList.remove(food);
                        break;
                    }
                }
            } else {
                for (Food food : mFoodList) {
                    if (food.getPosition() == position) {
                        food.setNum(goodsNum[position]);
                        break;
                    }
                }
            }

            changeShopCart();
            if (mOnGoodsNunChangeListener != null)
                mOnGoodsNunChangeListener.onNumChange();
        } else {

        }
    }

    /**
     * 修改购物车状态
     */
    private void changeShopCart() {
        EventBus.getDefault().post(new MessageEvent(mFoodList));
        EventBus.getDefault().post(new GoodsListEvent(mGoodsCategoryBuyNums));
        if (shopCart == null) return;
        if (buyNum > 0) {
            shopCart.setVisibility(View.VISIBLE);
            shopCart.setText(buyNum + "");
        } else {
            shopCart.setVisibility(View.GONE);
        }

    }

    private OnShopCartGoodsChangeListener mOnGoodsNunChangeListener = null;

    public void setOnShopCartGoodsChangeListener(OnShopCartGoodsChangeListener e) {
        mOnGoodsNunChangeListener = e;
    }

    public interface OnShopCartGoodsChangeListener {
        public void onNumChange();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsImage)
        ImageView ivGoodsImage;
        @BindView(R.id.goodsCategoryName)
        TextView goodsCategoryName;
/*        @BindView(R.id.tvGoodsDescription)
        TextView tvGoodsDescription;*/
        @BindView(R.id.goodsInfo)
        LinearLayout goodsInfo;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @BindView(R.id.tvGoodsIntegral)
        TextView tvGoodsIntegral;
        @BindView(R.id.ivGoodsMinus)
        ImageView ivGoodsMinus;
        @BindView(R.id.tvGoodsSelectNum)
        TextView tvGoodsSelectNum;
        @BindView(R.id.ivGoodsAdd)
        ImageView ivGoodsAdd;

        public ViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);

        }
    }


}
