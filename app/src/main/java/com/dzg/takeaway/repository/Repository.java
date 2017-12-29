package com.dzg.takeaway.repository;

import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.mvp.model.EvaluateCategory;
import com.dzg.takeaway.mvp.model.RatingScore;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.mvp.model.Weather;
import com.dzg.takeaway.mvp.model.WeatherCity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/6/22.
 */

public interface Repository {
    Observable<ArrayList<Address>> getAddressData(String keyword, int limit);

    Observable<List<Restaurant>> getRestaurantList(String geohash, double latitude, double longitude, int offset);

    Observable<Weather> getWeather(String city);

    Observable<WeatherCity> getWeatherCityID(String location);

    Observable<SearchRestaurant> searchRestaurant(String keyword, double latitude, double longitude, int offset);

    Observable<RestaurantDetail> getRestaurant(int restaurantid);

    Observable<List<RestaurantMenu>> getRestaurantMenus(int restaurant_id);

    Observable<List<Evaluate>> getEvaluates(int restaurant_id,int offset,int record_type);

    Observable<List<EvaluateCategory>> getEvaluateCategorys(int restaurant_id);

    Observable<RatingScore> getRatingScore(int restaurant_id,double latitude,double longitude);
}
