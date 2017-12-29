package com.dzg.takeaway.repository.interfaces;


import com.dzg.takeaway.mvp.model.Weather;
import com.dzg.takeaway.mvp.model.WeatherCity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherService {
//    http://weixin.jirengu.com/weather/now?cityid=WX4FBXXFKE4F
    @GET("/weather/now")
    Observable<Weather> getWeather(@Query("cityid") String cityid);
//    https://weixin.jirengu.com/weather/cityid?location=30.1445:120.0830
    @GET("/weather/cityid")
    Observable<WeatherCity> getCityID(@Query("location") String location);
}
