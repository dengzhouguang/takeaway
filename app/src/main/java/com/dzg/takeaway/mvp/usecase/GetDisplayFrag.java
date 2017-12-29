package com.dzg.takeaway.mvp.usecase;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Category;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.Weather;
import com.dzg.takeaway.mvp.model.WeatherCity;
import com.dzg.takeaway.mvp.usecase.base.UseCase;
import com.dzg.takeaway.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetDisplayFrag extends UseCase<GetDisplayFrag.RequestValues, GetDisplayFrag.ResponseValue> {
    public static final int ACTION_INIT = 1;
    public static final int ACTION_UPDATE = 2;
    public static final int ACTION_OFFSET = 3;
    public static final int ACTION_WEATHER_CITY = 4;
    public static final int ACTION_WEATHER = 5;
    private final Repository mRepository;

    public GetDisplayFrag(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        if (requestValues.action == ACTION_UPDATE || requestValues.action == ACTION_OFFSET)
            return new ResponseValue(ACTION_UPDATE, mRepository.getRestaurantList(requestValues.address.getGeohash(), requestValues.address.getLatitude(), requestValues.address.getLongitude(), requestValues.offset));
        else if (requestValues.action == ACTION_INIT)
            return new ResponseValue(ACTION_INIT, mRepository.getAddressData(requestValues.addr, requestValues.offset));
        else if (requestValues.action == ACTION_WEATHER_CITY)
            return new ResponseValue(ACTION_WEATHER_CITY, mRepository.getWeatherCityID(requestValues.location));
        else if (requestValues.action == ACTION_WEATHER)
            return new ResponseValue(ACTION_WEATHER, mRepository.getWeather((requestValues.cityId)));
        return null;
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private int offset;
        private int action;
        private Address address;
        private String addr;
        private String cityId;
        private String location;

        public RequestValues(int action, Address address, int offset) {
            this.action = action;
            this.address = address;
            this.offset = offset;
        }

        public RequestValues(int action, String address, int offset) {
            this.action = action;
            this.addr = address;
            this.offset = offset;
        }

        public RequestValues(int action, String str) {
            this.action = action;
            if (action == ACTION_WEATHER_CITY)
                this.location = str;
            else if (action == ACTION_WEATHER)
                this.cityId = str;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private Observable<List<Restaurant>> restaurantObservable;
        private Observable<ArrayList<Address>> addressObservable;
        private Observable<WeatherCity> weatherCityObservable;

        public Observable<WeatherCity> getWeatherCityObservable() {
            return weatherCityObservable;
        }


        public Observable<Weather> getWeatherObservable() {
            return weatherObservable;
        }


        private Observable<Weather> weatherObservable;

        public ResponseValue(int action, Observable observable) {
            if (action == ACTION_INIT)
                addressObservable = observable;
            else if (action == ACTION_UPDATE || action == ACTION_OFFSET)
                restaurantObservable = observable;
            else if (action==ACTION_WEATHER_CITY)
                weatherCityObservable=observable;
            else if (action==ACTION_WEATHER)
                weatherObservable=observable;
        }

        public Observable<List<Restaurant>> getObservable() {
            return restaurantObservable;
        }


        public Observable<ArrayList<Address>> getAddressObservable() {
            return addressObservable;
        }


    }

    public List<Category> getCategorys() {
        List<Category> list = new ArrayList<>();
        Category category = new Category();
        category.setName("地方菜系");
        category.setResId(R.mipmap.difangcaixi);
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.difangxiaochi);
        category.setName("地方小吃");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.dangyuemeishi);
        category.setName("当月美食");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.guoshushengxian);
        category.setName("果蔬生鲜");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.kuaicanbiandang);
        category.setName("快餐便当");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.langmanxianhua);
        category.setName("浪漫鲜花");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.malatang);
        category.setName("麻辣烫");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.manjianhaodian);
        category.setName("满减好店");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.meishi);
        category.setName("美食");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.pisayimian);
        category.setName("披萨意面");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.shangchaobianli);
        category.setName("商超便利");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.wucan);
        category.setName("午餐");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.xindiantehui);
        category.setName("新店特惠");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.yinpingtianping);
        category.setName("饮品甜品");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.yiyaojiankang);
        category.setName("医药健康");
        list.add(category);
        category = new Category();
        category.setResId(R.mipmap.zaocan);
        category.setName("早餐");
        list.add(category);
        return list;
    }


}
