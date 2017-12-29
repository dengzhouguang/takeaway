package com.dzg.takeaway.mvp.usecase;

import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.usecase.base.UseCase;
import com.dzg.takeaway.repository.Repository;

import io.reactivex.Observable;

public class GetRestaurantDetail extends UseCase<GetRestaurantDetail.RequestValues, GetRestaurantDetail.ResponseValue> {
    private final Repository mRepository;

    public GetRestaurantDetail(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
           return new ResponseValue(mRepository.getRestaurant(requestValues.id));
    }

    public static final class RequestValues implements UseCase.RequestValues {
        int id;
        public RequestValues(int id) {
            this.id=id;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        Observable<RestaurantDetail> restaurantDetailObservable;
        public ResponseValue( Observable<RestaurantDetail> restaurantDetailObservable) {
        this.restaurantDetailObservable=restaurantDetailObservable;
        }

        public Observable<RestaurantDetail> getObservable() {
            return restaurantDetailObservable;
        }



    }
}
