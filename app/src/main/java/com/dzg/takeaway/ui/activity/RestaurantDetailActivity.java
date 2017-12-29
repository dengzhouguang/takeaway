package com.dzg.takeaway.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dzg.takeaway.App;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.CartPopupAdapter;
import com.dzg.takeaway.adapter.FavourablePopupAdapter;
import com.dzg.takeaway.adapter.TabFragmentAdapter;
import com.dzg.takeaway.event.MessageEvent;
import com.dzg.takeaway.injector.component.ApplicationComponent;
import com.dzg.takeaway.injector.component.DaggerRestaurantDetailComponent;
import com.dzg.takeaway.injector.component.RestaurantDetailComponent;
import com.dzg.takeaway.injector.module.ActivityModule;
import com.dzg.takeaway.injector.module.RestaurantDetailModule;
import com.dzg.takeaway.mvp.contract.RestaurantDetailContract;
import com.dzg.takeaway.mvp.model.Food;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.presenter.RestaurantDetailPresenter;
import com.dzg.takeaway.ui.fragment.EvaluateFragment;
import com.dzg.takeaway.ui.fragment.GoodsFragment;
import com.dzg.takeaway.ui.fragment.RestaurantFragment;
import com.dzg.takeaway.util.AlphaUtil;
import com.dzg.takeaway.util.AnimationUtil;
import com.dzg.takeaway.util.DensityUtil;
import com.dzg.takeaway.util.ImageUtil;
import com.dzg.takeaway.util.StatusBarUtils;
import com.dzg.takeaway.util.TextUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class RestaurantDetailActivity extends BaseActivity implements RestaurantDetailContract.View {
    @Inject
    RestaurantDetailPresenter mPresenter;
    @BindView(R.id.restaurant_detail_nametv)
    TextView mNameTv;
    @BindView(R.id.restaurant_detail_appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.restaurant_detail_magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.restaurant_detail_vp)
    ViewPager mViewPager;
    @BindView(R.id.restaurant_detail_shopCartNum)
    TextView mShopCartNumTv;
    @BindView(R.id.restaurant_detail_totalPricetv)
    TextView mTotalPriceTv;
    @BindView(R.id.restaurant_detail_noShoptv)
    TextView mNoShopTv;
    @BindView(R.id.restaurant_detail_shopCartMainrl)
    RelativeLayout mShopCartMainRl;
    @BindView(R.id.restaurant_detail_background)
    ImageView mTopBackgroundIv;
    @BindView(R.id.restaurant_detail_toolbar_background)
    ImageView mTopToolBarBackgroundIv;
    @BindView(R.id.restaurant_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.restaurant_detail_logo)
    ImageView mLogoIv;
    @BindView(R.id.restaurant_detail_searchll)
    LinearLayout mSearchLl;
    @BindView(R.id.restaurant_detail_alltv)
    TextView mDetailAll;
    @BindView(R.id.restaurant_detail_descriptiontv)
    TextView mDescriptionTv;
    @BindView(R.id.restaurant_detail_favourable_text_tv)
    TextView mFavourableTextTv;
    @BindView(R.id.restaurant_detail_favourabletv)
    TextView mFavourableTv;
    @BindView(R.id.restaurant_detail_favourable_sizetv)
    TextView mFavourableSizeTv;
    @BindView(R.id.restaurant_detail_favourablell)
    LinearLayout mFavourableLl;
    @BindView(R.id.restaurant_detail_shopCartMain_goToCheckOut)
    TextView mGotoCheckOutTv;
    @BindView(R.id.restaurant_detail_howMoneyToDelivery)
    TextView mHowMoneyToDeliveryTv;
    @BindView(R.id.restaurant_detail_shopCart_background)
    LinearLayout mShopCartBackgroundLl;
    @BindView(R.id.restaurant_detail_more_info)
    ImageView mMoreInfoIv;

    private ViewGroup mAnimMaskLayout;
    private TabFragmentAdapter mTabAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private int mOverHeight;
    private PopupWindow mDetailPopupWindow;
    private PopupWindow mFavourablePopupWindow;
    private PopupWindow mCartPopupWindow;
    private RestaurantDetail mRestaurantDetail;
    private List<Food> mList;
    private CartPopupAdapter mCartPopupAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_restaurant_detail;
    }

    @Override
    protected void initView() {
       /* Intent intent=getIntent();
        mRestaurant= (Restaurant) intent.getExtras().getSerializable("restaurant");
        getRestaurantDetail(mRestaurant.getId());*/
       injectDependences();
        mPresenter.attachView(this);
        mPresenter.getRestaurant();
        setStatusBar();

    }

    //todo
    private void injectDependences() {
        ApplicationComponent applicationComponent = App.getInstance().getApplicationComponent();
        RestaurantDetailComponent component= DaggerRestaurantDetailComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .restaurantDetailModule(new RestaurantDetailModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.restaurant_detail_infoll, R.id.restaurant_detail_favourablell, R.id.restaurant_detail_shopCartMainrl, R.id.restaurant_detail_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.restaurant_detail_infoll:
                showRestaurantDetailWindow(view);
                break;
            case R.id.restaurant_detail_favourablell:
                showRestaurantFavourableWindow(view);
                break;
            case R.id.restaurant_detail_shopCartMainrl:
                if (mList != null && mList.size() > 0)
                    showRestaurantCartWindow(view);
                break;
            case R.id.restaurant_detail_back:
                finish();
                break;
            default:
                break;
        }


    }

    private void showRestaurantCartWindow(View view) {
        View content = LayoutInflater.from(this).inflate(R.layout.layout_restaurant_detail_cart_popupwindow, null);
        mCartPopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mCartPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_transparent));
        mCartPopupWindow.setFocusable(true);
        mCartPopupWindow.setOutsideTouchable(true);
        mCartPopupWindow.setAnimationStyle(R.style.PopupWindowAnimationFavourable);
        mCartPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AlphaUtil.backgroundAlpha(RestaurantDetailActivity.this, 1f);
            }
        });
        mCartPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, DensityUtil.dp2px(50));

        RecyclerView recyclerView = content.findViewById(R.id.restaurant_detail_popup_cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mCartPopupAdapter);
        AlphaUtil.backgroundAlpha(RestaurantDetailActivity.this, 0.5f);
    }

    private void showRestaurantFavourableWindow(View view) {
        View content = LayoutInflater.from(this).inflate(R.layout.layout_restaurant_detail_favourable_popupwindow, null);
        mFavourablePopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(320), true);
        mFavourablePopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_transparent));
        mFavourablePopupWindow.setFocusable(true);
        mFavourablePopupWindow.setOutsideTouchable(true);
        mFavourablePopupWindow.setAnimationStyle(R.style.PopupWindowAnimationFavourable);
        mFavourablePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AlphaUtil.backgroundAlpha(RestaurantDetailActivity.this, 1f);
            }
        });
        mFavourablePopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        FavourablePopupAdapter adapter = new FavourablePopupAdapter(mRestaurantDetail.getActivities());
        RecyclerView recyclerView = content.findViewById(R.id.restaurant_detail_popup_favourable_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        AlphaUtil.backgroundAlpha(RestaurantDetailActivity.this, 0.5f);
        content.findViewById(R.id.restaurant_detail_popup_favourable_canceliv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavourablePopupWindow.dismiss();
            }
        });
    }

    private void showRestaurantDetailWindow(View view) {
        View content = LayoutInflater.from(this).inflate(R.layout.layout_restaurant_detail_info_popupwindow, null);
        mDetailPopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mDetailPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_transparent));
        mDetailPopupWindow.setFocusable(true);
        mDetailPopupWindow.setOutsideTouchable(true);
        mDetailPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        mDetailPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AlphaUtil.backgroundAlpha(RestaurantDetailActivity.this, 1f);
            }
        });
        mDetailPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        AlphaUtil.backgroundAlpha(RestaurantDetailActivity.this, 0.5f);
        ((TextView) content.findViewById(R.id.restaurant_detail_info_name)).setText(mRestaurantDetail.getName());
        ((TextView) content.findViewById(R.id.restaurant_detail_info_rating)).setText(TextUtils.formatRating(mRestaurantDetail.getRating()));
        ((TextView) content.findViewById(R.id.restaurant_detail_info_sales)).setText(TextUtils.formatNum(mRestaurantDetail.getRecent_order_num()));
        ((TextView) content.findViewById(R.id.restaurant_detail_info_delivery)).setText(mRestaurantDetail.getDelivery_mode().getText());
        ((TextView) content.findViewById(R.id.restaurant_detail_info_delivery_money)).setText(TextUtils.formatMoney2(mRestaurantDetail.getFloat_delivery_fee()));
        ((TextView) content.findViewById(R.id.restaurant_detail_info_distance)).setText(TextUtils.formatDistance(mRestaurantDetail.getDistance()));
        ((TextView) content.findViewById(R.id.restaurant_detail_info_delivery_time)).setText(TextUtils.formatDeliveryTime(mRestaurantDetail.getOrder_lead_time()));
        ((TextView) content.findViewById(R.id.restaurant_detail_description)).setText(mRestaurantDetail.getPromotion_info());
        content.findViewById(R.id.restaurant_detail_popupinfo_cancelib).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPopupWindow.dismiss();
            }
        });
    }


    public void showView() {
        setSupportActionBar(mToolbar);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mMoreInfoIv.setVisibility(View.VISIBLE);
        mFavourableLl.setVisibility(View.VISIBLE);
        final int width = outMetrics.widthPixels;
        final int height = DensityUtil.dp2px(96);
        Glide.with(this).load(TextUtils.formatImageUrl(mRestaurantDetail.getImage_path())).apply(bitmapTransform(new BlurTransformation(30, 6))).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Drawable drawable = ImageUtil.zoomDrawable(RestaurantDetailActivity.this, resource, width, height);
                mTopBackgroundIv.setImageDrawable(drawable);
                Bitmap sourceBitmap = ImageUtil.drawableToBitmap(drawable);
                mOverHeight = height - mToolbar.getHeight() - StatusBarUtils.getStatusBarHeight(RestaurantDetailActivity.this);
                Bitmap bitmap = Bitmap.createBitmap(sourceBitmap, 0, mOverHeight, sourceBitmap.getWidth(), sourceBitmap.getHeight() - mOverHeight);
                mTopToolBarBackgroundIv.setImageDrawable(new BitmapDrawable(RestaurantDetailActivity.this.getResources(), bitmap));
                mTopToolBarBackgroundIv.setVisibility(View.INVISIBLE);
                return true;
            }
        }).into(mTopBackgroundIv);

        Glide.with(this).load(TextUtils.formatImageUrl(mRestaurantDetail.getImage_path())).into(mLogoIv);
        mNameTv.setText(mRestaurantDetail.getName());
        mDetailAll.setText(TextUtils.formatAll(mRestaurantDetail.getRating(), mRestaurantDetail.getRecent_order_num(),
                mRestaurantDetail.getDelivery_mode().getText(), mRestaurantDetail.getOrder_lead_time(), mRestaurantDetail.getDistance()));
        mDescriptionTv.setText(mRestaurantDetail.getPromotion_info());
        if (mRestaurantDetail.getActivities().size() > 0) {
            mFavourableTv.setText(mRestaurantDetail.getActivities().get(0).getDescription());
            mFavourableSizeTv.setText(TextUtils.formatFavourable(mRestaurantDetail.getActivities().size()));
            String string = mRestaurantDetail.getActivities().get(0).getIcon_name();
            if ("特".equals(string)) {
                mFavourableTextTv.setText("特价");
            } else if ("首".equals(string)) {
                mFavourableTextTv.setText("首单");
            } else if ("减".equals(string)) {
                mFavourableTextTv.setText("满减");
            } else if ("折".equals(string)) {
                mFavourableTextTv.setText("折扣");
            } else if ("赠".equals(string)) {
                mFavourableTextTv.setText("赠送");
            } else if ("领".equals(string)) {
                mFavourableTextTv.setText("领券");
            }
            mFavourableTextTv.setBackgroundColor(Integer.valueOf(mRestaurantDetail.getActivities().get(0).getIcon_color(), 16) + 0xff000000);
        } else
            mFavourableLl.setVisibility(View.GONE);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int overHeight = mOverHeight + StatusBarUtils.getStatusBarHeight(RestaurantDetailActivity.this) / 2;
                if (-verticalOffset > overHeight) {
                    if (mTopToolBarBackgroundIv.getVisibility() != View.VISIBLE)
                        mTopToolBarBackgroundIv.setVisibility(View.VISIBLE);
                    int height = DensityUtil.dp2px(26);
                    if (-verticalOffset < overHeight + height) {
                        float alpha = 1 - 1f / height * (overHeight + height + verticalOffset);
                        mSearchLl.setAlpha(alpha > 1.0 ? 1f : alpha);
                        mSearchLl.setVisibility(View.VISIBLE);
                    } else {
                        mSearchLl.setAlpha(1f);
                        mSearchLl.setVisibility(View.VISIBLE);
                    }
                } else {
                    mTopToolBarBackgroundIv.setVisibility(View.INVISIBLE);
                    mSearchLl.setVisibility(View.INVISIBLE);
                    int maxPadding = DensityUtil.dp2px(31);
                    int padding = (int) (maxPadding / (double) mOverHeight / 1 * (-verticalOffset));
                    mLogoIv.setPadding(padding, (int) (padding * 1.7), padding, (int) (padding * 0.3));
                }

            }
        });
        mCartPopupAdapter = new CartPopupAdapter(new ArrayList<>());
        setViewPager();
    }

    private void setViewPager() {
        mFragments.add(GoodsFragment.newInstance(mRestaurantDetail));
        mFragments.add(EvaluateFragment.newInstance(mRestaurantDetail));
        mFragments.add(RestaurantFragment.newInstance(mRestaurantDetail));
        mTitles.add("点餐");
        mTitles.add("评价");
        mTitles.add("商家");
        mTabAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        initTab();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mShopCartBackgroundLl.setVisibility(View.VISIBLE);
                        mShopCartMainRl.startAnimation(
                                AnimationUtil.createInAnimation(RestaurantDetailActivity.this, mShopCartMainRl.getMeasuredHeight()));
                        break;
                    case 1:
                    case 2:
                        mShopCartMainRl.startAnimation(
                                AnimationUtil.createOutAnimation(RestaurantDetailActivity.this, mShopCartMainRl.getMeasuredHeight()));
                        mShopCartBackgroundLl.setVisibility(View.GONE);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTab() {
        mMagicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.color_39));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.color_2395ff));
                simplePagerTitleView.setText(mTitles.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(0xff2395ff);
                linePagerIndicator.setLineHeight(DensityUtil.dp2px(2));
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }


    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 添加 或者  删除  商品发送的消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        mList = event.mList;
        int price = 0;
        if (mList != null) {
            if (mList.size() > 0) {
                for (Food food : mList) {
                    price += food.getNum() * food.getFoodsBean().getSpecfoods().get(0).getPrice();
                }
                mShopCartNumTv.setText(String.valueOf(mList.size()));
                mShopCartNumTv.setVisibility(View.VISIBLE);
                mTotalPriceTv.setVisibility(View.VISIBLE);
                mNoShopTv.setVisibility(View.GONE);
                if (price >= mRestaurantDetail.getFloat_minimum_order_amount()) {
                    mGotoCheckOutTv.setVisibility(View.VISIBLE);
                    mHowMoneyToDeliveryTv.setVisibility(View.GONE);
                } else {
                    mHowMoneyToDeliveryTv.setText(TextUtils.formatHowMoney(mRestaurantDetail.getFloat_minimum_order_amount() - price));
                }
            } else {
                mShopCartNumTv.setVisibility(View.GONE);
                mTotalPriceTv.setVisibility(View.GONE);
                mNoShopTv.setVisibility(View.VISIBLE);
                mHowMoneyToDeliveryTv.setVisibility(View.VISIBLE);
                mHowMoneyToDeliveryTv.setText(TextUtils.formatMinMoneyToDeDelivery(mRestaurantDetail.getFloat_minimum_order_amount()));
                mGotoCheckOutTv.setVisibility(View.GONE);
            }
            mTotalPriceTv.setText(TextUtils.formatMoney(price));
            mCartPopupAdapter.setData(mList);
            mCartPopupAdapter.notifyDataSetChanged();
            if (mList.size() == 0)
                mCartPopupWindow.dismiss();
        }

    }


    /**
     * 设置动画（点击添加商品）
     */
    public void setAnim(final View v, int[] startLocation) {
        mAnimMaskLayout = null;
        mAnimMaskLayout = createAnimLayout();
        mAnimMaskLayout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(mAnimMaskLayout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        mShopCartNumTv.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 初始化动画图层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将View添加到动画图层
     */
    private View addViewToAnimLayout(final ViewGroup parent, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void showDatas(RestaurantDetail restaurantDetail) {
        mRestaurantDetail = restaurantDetail;
        showView();
    }
}
