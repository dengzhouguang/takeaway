package com.dzg.takeaway.repository;


import com.dzg.takeaway.App;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Constants;
import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.mvp.model.EvaluateCategory;
import com.dzg.takeaway.mvp.model.RatingScore;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.mvp.model.Weather;
import com.dzg.takeaway.mvp.model.WeatherCity;
import com.dzg.takeaway.repository.interfaces.DataService;
import com.dzg.takeaway.repository.interfaces.WeatherService;
import com.dzg.takeaway.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryImpl implements Repository {
    private static DataService mDataService;
    private static WeatherService mWeathService;
    private static RepositoryImpl instance;

    public static Repository newInstance() {
        synchronized (RepositoryImpl.class) {
            if (instance == null) {
                instance = new RepositoryImpl();
                OkHttpClient client = new OkHttpClient.Builder()
                        .cache(new Cache(FileUtil.getHttpCacheDir(App.getInstance()), Constants.HTTP_CACHE_SIZE))
                        .connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                        .readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                        .build();
                mDataService = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("https://www.ele.me")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build().create(DataService.class);
                mWeathService=new Retrofit.Builder()
                        .client(client)
                        .baseUrl("https://weixin.jirengu.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build().create(WeatherService.class);
            }
        }
        return instance;
    }

    @Override
    public Observable<ArrayList<Address>> getAddressData(String keyword, int limit) {
        return mDataService.getAddressData(keyword,limit);
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantList(String geohash, double latitude, double longitude, int offset) {
        return mDataService.getData(geohash,String.valueOf(latitude),String.valueOf(longitude),offset);
    }

    @Override
    public Observable<Weather> getWeather(String city) {
        return mWeathService.getWeather(city);
    }

    @Override
    public Observable<WeatherCity> getWeatherCityID(String location) {
        return mWeathService.getCityID(location);
    }

    @Override
    public Observable<SearchRestaurant> searchRestaurant(String keyword, double latitude, double longitude, int offset) {
        return mDataService.searchRestaurant(keyword,latitude,longitude,offset);
    }

    @Override
    public Observable<RestaurantDetail> getRestaurant(int restaurantid) {
        return mDataService.getRestaurant(restaurantid);
    }

    @Override
    public Observable<List<RestaurantMenu>> getRestaurantMenus(int restaurant_id) {
        return mDataService.getRestaurantMenu(restaurant_id);
    }

    @Override
    public Observable<List<Evaluate>> getEvaluates(int restaurant_id, int offset,int record_type) {
        return mDataService.getEvaluates(restaurant_id,offset,record_type);
    }

    @Override
    public Observable<List<EvaluateCategory>> getEvaluateCategorys(int restaurant_id) {
        return mDataService.getEvaluateCategorys(restaurant_id);
    }

    @Override
    public Observable<RatingScore> getRatingScore(int restaurant_id, double latitude, double longitude) {
        return mDataService.getRatingScore(restaurant_id, latitude, longitude);
    }
}
