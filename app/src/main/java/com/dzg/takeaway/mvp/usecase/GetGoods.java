package com.dzg.takeaway.mvp.usecase;

import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.dzg.takeaway.mvp.usecase.base.UseCase;
import com.dzg.takeaway.repository.Repository;

import java.util.List;

import io.reactivex.Observable;

public class GetGoods extends UseCase<GetGoods.RequestValues, GetGoods.ResponseValue> {
    private final Repository mRepository;

    public GetGoods(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        return  new ResponseValue(mRepository.getRestaurantMenus(requestValues.id));
    }

    public static final class RequestValues implements UseCase.RequestValues {
        int  id;
        public RequestValues(int action) {
            id=action;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        Observable<List<RestaurantMenu>> listObservable;
        public ResponseValue(Observable<List<RestaurantMenu>> observable) {
         listObservable=observable;
        }

        public Observable<List<RestaurantMenu>> getObservable() {
            return listObservable;
        }



    }
}
