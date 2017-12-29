package com.dzg.takeaway.repository.interfaces;


import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.mvp.model.EvaluateCategory;
import com.dzg.takeaway.mvp.model.RatingScore;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.dzg.takeaway.mvp.model.SearchRestaurant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface DataService {
    @GET("/restapi/v2/pois?&type=nearby&terminal=app")
    Observable<ArrayList<Address>> getAddressData(@Query("keyword") String keyword, @Query("limit") int limit);
    @GET("/restapi/shopping/restaurants?extras%5B%5D=activities&limit=24&terminal=app")
    Observable<List<Restaurant>> getData(@Query("geohash") String geohash, @Query("latitude") String latitude, @Query("longitude")String longtitude,@Query("offset")int offset);
    @GET("/restapi/shopping/restaurants/search?extras%5B%5D=activity&limit=100&terminal=app")
    Observable<SearchRestaurant> searchRestaurant(@Query("keyword")String keyword,@Query("latitude")double latitude,@Query("longitude")double longitude,@Query("offset")int offset);
    @GET("/restapi/shopping/restaurant/{restaurantid}?extras%5B%5D=activities&extras%5B%5D=license&extras%5B%5D=identification&extras%5B%5D=albums&extras%5B%5D=flavors")
    Observable<RestaurantDetail>  getRestaurant(@Path("restaurantid")int restaurantid);
    @GET("/restapi/shopping/v2/menu")
    Observable<List<RestaurantMenu>> getRestaurantMenu(@Query("restaurant_id")int restaurant_id);
    @GET("/restapi/ugc/v1/restaurant/{restaurant_id}/ratings?limit=10")
    Observable<List<Evaluate>> getEvaluates(@Path("restaurant_id") int restaurant_id, @Query("offset")int offset,@Query("record_type")int record_type);
    @GET("/restapi/ugc/v1/restaurant/{restaurant_id}/rating_categories")
    Observable<List<EvaluateCategory>> getEvaluateCategorys(@Path("restaurant_id")int restaurant_id);
    @GET("/restapi/ugc/v1/restaurants/{restaurant_id}/rating_scores")
    Observable<RatingScore> getRatingScore(@Path("restaurant_id")int restaurant_id,@Query("latitude")double latitude,@Query("longitude")double longitude);
}
