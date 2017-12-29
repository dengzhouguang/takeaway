package com.dzg.takeaway.mvp.usecase;

import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.usecase.base.UseCase;
import com.dzg.takeaway.repository.Repository;

import java.util.List;

import io.reactivex.Observable;

public class GetDefaultAct extends UseCase<GetDefaultAct.RequestValues, GetDefaultAct.ResponseValue> {
    private final Repository mRepository;

    public GetDefaultAct(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
           return null;
    }

    public static final class RequestValues implements UseCase.RequestValues {
        String str;
        public RequestValues(String action) {
            str=action;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        public ResponseValue(int action, Observable observable) {

        }

        public Observable<List<Restaurant>> getObservable() {
            return null;
        }



    }
}
