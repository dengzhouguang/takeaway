package com.dzg.takeaway.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.repository.RepositoryImpl;
import com.dzg.takeaway.ui.activity.SearchActivity;
import com.dzg.takeaway.util.MathUtil;
import com.dzg.takeaway.util.TextUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dengzhouguang on 2017/12/6.
 */

public class SearchResultAdapter extends RecyclerView.Adapter {
    private List<SearchRestaurant.RestaurantWithFoodsBean> mList;
    private Context mContext;

    public SearchResultAdapter(List<SearchRestaurant.RestaurantWithFoodsBean> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    public void setDatas(List<SearchRestaurant.RestaurantWithFoodsBean> list) {
        this.mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_restaurant_result, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_search_restaurant_result_logoiv)
        ImageView logoIv;
        @BindView(R.id.item_search_restaurant_result_nametv)
        TextView nameTv;
        @BindView(R.id.item_search_restaurant_result_starrb)
        RatingBar starRb;
        @BindView(R.id.item_search_restaurant_result_startv)
        TextView starTv;
        @BindView(R.id.item_search_restaurant_result_salestv)
        TextView salesTv;
        @BindView(R.id.item_search_restaurant_result_sendmoneytv)
        TextView sendMoneyTv;
        @BindView(R.id.item_search_restaurant_result_sendtimetv)
        TextView sendTimeTv;
        @BindView(R.id.item_search_restaurant_result_favourablerv)
        RecyclerView favourableRv;
        @BindView(R.id.item_search_restaurant_result_favourabletv)
        TextView favourableTv;
        @BindView(R.id.item_search_restaurant_result_favourableiv)
        ImageView favourableIv;
        @BindView(R.id.item_search_restaurant_result_favourablell)
        LinearLayout favourableLl;
        @BindView(R.id.item_search_restaurant_result_recommendrv)
        RecyclerView recommendRv;
        @BindView(R.id.item_search_restaurant_result_recommend_moretv)
        TextView recommendMoreTv;
        @BindView(R.id.item_search_restaurant_result_recommend_moreiv)
        ImageView recommendMoreIv;
        @BindView(R.id.item_search_restaurant_result_recommend_morell)
        LinearLayout recommendMoreLl;

        private FavorableAdapter favourableAdapter;
        private RecommendMoreAdapter recommendMoreAdapter;
        List<RestaurantDetail.ActivitiesBean> activities;
        List<SearchRestaurant.RestaurantWithFoodsBean.FoodsBean> foods;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            favourableAdapter = new FavorableAdapter(new ArrayList<>(), mContext);
            recommendMoreAdapter = new RecommendMoreAdapter(new ArrayList<>(), mContext, false);
        }

        public void onBindViewHolder(int position) {
            SearchRestaurant.RestaurantWithFoodsBean.RestaurantBean restaurant = mList.get(position).getRestaurant();
            foods = mList.get(position).getFoods();
            Glide.with(mContext).load(TextUtils.formatImageUrl(restaurant.getImage_path())).into(logoIv);
            nameTv.setText(restaurant.getName());
            starTv.setText(TextUtils.formatRating(restaurant.getRating()));
            starRb.setRating(MathUtil.parseStar((float) restaurant.getRating()));
            salesTv.setText(TextUtils.formatDistance(restaurant.getDistance()));
            sendMoneyTv.setText(TextUtils.formatDelivery(restaurant.getFloat_minimum_order_amount(), restaurant.getFloat_delivery_fee()));
            sendTimeTv.setText(TextUtils.formatDistanceAndTime(restaurant.getDistance(), restaurant.getOrder_lead_time()));
            favourableLl.setVisibility(View.GONE);
            RepositoryImpl.newInstance().getRestaurant(restaurant.getId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(((SearchActivity) mContext).<RestaurantDetail>bindUntilEvent(ActivityEvent.STOP))
                    .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                            return throwableObservable
                                    .flatMap(new Function<Throwable, ObservableSource<?>>() {
                                        @Override
                                        public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                            if (throwable instanceof UnknownHostException) {
                                                return Observable.error(throwable);
                                            } else {
                                                return Observable.timer(5, TimeUnit.SECONDS);
                                            }
                                        }
                                    });
                        }
                    }).subscribe(new Observer<RestaurantDetail>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(RestaurantDetail restaurantDetail) {
                    if (restaurantDetail.getActivities() != null) {
                        activities = restaurantDetail.getActivities();
                        favourableRv.setLayoutManager(new LinearLayoutManager(mContext));
                        favourableRv.setHasFixedSize(false);
                        favourableRv.setNestedScrollingEnabled(false);
                        favourableTv.setText(TextUtils.formatFavourable(activities.size()));
                        if (activities.size() < 4) {
                            favourableAdapter.setDatas(activities);
                            favourableLl.setVisibility(View.GONE);
                        } else {
                            favourableAdapter.setDatas(activities.subList(0, 3));
                            favourableLl.setVisibility(View.VISIBLE);
                        }
                        favourableRv.setAdapter(favourableAdapter);

                    } else
                        favourableLl.setVisibility(View.GONE);
                }

                @Override
                public void onError(Throwable e) {
                    favourableLl.setVisibility(View.GONE);
                }

                @Override
                public void onComplete() {

                }
            });
            favourableRv.setHasFixedSize(false);
            favourableRv.setNestedScrollingEnabled(false);
            favourableRv.setLayoutManager(new LinearLayoutManager(mContext));
            favourableRv.setAdapter(favourableAdapter);


            recommendRv.setHasFixedSize(false);
            recommendRv.setNestedScrollingEnabled(false);
            recommendRv.setLayoutManager(new LinearLayoutManager(mContext));
            recommendMoreLl.setVisibility(View.GONE);
            boolean isTop = false;
            if (position < 3)
                isTop = true;
            if (foods.size() < 4 && isTop) {
                recommendMoreAdapter = new RecommendMoreAdapter(foods, mContext, isTop);
            } else if (foods.size() > 3 && isTop) {
                recommendMoreAdapter = new RecommendMoreAdapter(foods.subList(0, 3), mContext, isTop);
                recommendMoreTv.setText(TextUtils.formatRelative(foods.size() - 3));
                recommendMoreLl.setVisibility(View.VISIBLE);
            } else if (foods.size() > 1) {
                recommendMoreAdapter = new RecommendMoreAdapter(foods.subList(0, 1), mContext, isTop);
                recommendMoreTv.setText(TextUtils.formatRelative(foods.size() - 1));
                recommendMoreLl.setVisibility(View.VISIBLE);
            }

            recommendRv.setAdapter(recommendMoreAdapter);
        }


        @OnClick({R.id.item_search_restaurant_result_favourablell, R.id.item_search_restaurant_result_recommend_morell})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_search_restaurant_result_favourablell:
                    if (favourableAdapter.getItemCount() == activities.size()) {
                        favourableIv.setImageResource(R.mipmap.more_favourable);
                        favourableAdapter.setDatas(activities.subList(0, 3));
                        favourableAdapter.notifyDataSetChanged();
                    } else {
                        favourableIv.setImageResource(R.mipmap.more_favourable_back);
                        favourableAdapter.setDatas(activities);
                        favourableAdapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.item_search_restaurant_result_recommend_morell:
                    if (recommendMoreAdapter.getItemCount() == foods.size()) {
                        recommendMoreIv.setImageResource(R.mipmap.item_search_restaurant_result_recommend_more_default);
                        if (recommendMoreAdapter.isTop()) {
                            recommendMoreAdapter.setData(foods.subList(0, 3));
                            recommendMoreTv.setText(TextUtils.formatRelative(foods.size() - 3));
                        } else {
                            recommendMoreAdapter.setData(foods.subList(0, 1));
                            recommendMoreTv.setText(TextUtils.formatRelative(foods.size() - 1));
                        }
                        recommendMoreAdapter.notifyDataSetChanged();
                    } else {
                        recommendMoreIv.setImageResource(R.mipmap.item_search_restaurant_result_recommend_more_selected);
                        recommendMoreTv.setText("收起");
                        recommendMoreAdapter.setData(foods);
                        recommendMoreAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    public static final int SORT_COMPREHENSIVE = 0;
    public static final int SORT_RATING = 1;
    public static final int SORT_DELIVERY_FEE_MIN = 2;
    public static final int SORT_DELIVERY_MOSTFAST = 3;
    public static final int SORT_MOST_SALES = 4;
    public static final int SORT_DISTANCE_MIN = 5;
    public static final int SORT_FILTER = 6;

    public void sort(int type) {
        Collections.sort(mList, new Comparator<SearchRestaurant.RestaurantWithFoodsBean>() {
            @Override
            public int compare(SearchRestaurant.RestaurantWithFoodsBean bean1, SearchRestaurant.RestaurantWithFoodsBean bean2) {
                switch (type) {
                    case SORT_COMPREHENSIVE:
                        return 0;
                    case SORT_RATING:
                        return bean1.getRestaurant().getRating() <= bean2.getRestaurant().getRating() ? 1 : -1;
                    case SORT_DELIVERY_MOSTFAST:
                        return bean1.getRestaurant().getOrder_lead_time() >= bean2.getRestaurant().getOrder_lead_time() ? 1 : -1;
                    case SORT_DELIVERY_FEE_MIN:
                        return bean1.getRestaurant().getFloat_delivery_fee() <= bean2.getRestaurant().getFloat_delivery_fee() ? 1 : -1;
                    case SORT_MOST_SALES:
                        return bean1.getRestaurant().getRecent_order_num() <= bean2.getRestaurant().getRecent_order_num() ? 1 : -1;
                    case SORT_DISTANCE_MIN:
                        return bean1.getRestaurant().getDistance() >= bean2.getRestaurant().getDistance() ? 1 : -1;
                    case SORT_FILTER:
                        return 0;
                    default:
                        return 0;
                }
            }
        });
    }
}
