package com.dzg.takeaway.mvp.usecase;

import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.mvp.usecase.base.UseCase;
import com.dzg.takeaway.repository.Repository;

import io.reactivex.Observable;

public class GetSearch extends UseCase<GetSearch.RequestValues, GetSearch.ResponseValue> {
    private final Repository mRepository;

    public GetSearch(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
           return new ResponseValue(mRepository.searchRestaurant(requestValues.keyword,requestValues.latitude,requestValues.longititude,requestValues.offset));
    }

    public static final class RequestValues implements UseCase.RequestValues {
        String keyword;
        double latitude;
        double longititude;
        int offset;
        public RequestValues(String keyword,double latitude,double longititude,int offset) {
            this.keyword=keyword;
            this.latitude=latitude;
            this.longititude=longititude;
            this.offset=offset;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        Observable<SearchRestaurant> observable;
        public ResponseValue(Observable<SearchRestaurant> observable) {
        this.observable=observable;
        }

        public Observable<SearchRestaurant> getObservable() {
            return observable;
        }



    }
}
