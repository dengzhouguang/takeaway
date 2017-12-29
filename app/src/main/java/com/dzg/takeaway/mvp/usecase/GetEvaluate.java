package com.dzg.takeaway.mvp.usecase;

import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.mvp.model.EvaluateCategory;
import com.dzg.takeaway.mvp.model.RatingScore;
import com.dzg.takeaway.mvp.usecase.base.UseCase;
import com.dzg.takeaway.repository.Repository;

import java.util.List;

import io.reactivex.Observable;

public class GetEvaluate extends UseCase<GetEvaluate.RequestValues, GetEvaluate.ResponseValue> {
    public static final int ACTION_RATING_SCORE = 1;
    public static final int ACTION_EVALUATE = 2;
    public static final int ACTION_EVALUATE_CATEGORY = 3;
    private final Repository mRepository;

    public GetEvaluate(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        switch (requestValues.action) {
            case ACTION_RATING_SCORE:
                return new ResponseValue(ACTION_RATING_SCORE, mRepository.getRatingScore(requestValues.id, requestValues.latitude, requestValues.longtitude));
            case ACTION_EVALUATE:
                return new ResponseValue(ACTION_EVALUATE, mRepository.getEvaluates(requestValues.id, requestValues.offset, requestValues.recordType));
            case ACTION_EVALUATE_CATEGORY:
                return new ResponseValue(ACTION_EVALUATE_CATEGORY, mRepository.getEvaluateCategorys(requestValues.id));
        }


        return null;
    }

    public static final class RequestValues implements UseCase.RequestValues {
        int action;
        int id;
        double latitude;
        double longtitude;
        int offset;
        int recordType;

        public RequestValues(int action, int id, double latitude, double longtitude) {
            this.id = id;
            this.latitude = latitude;
            this.longtitude = longtitude;
            this.action = action;
        }


        public RequestValues(int action, int id) {
            this.action = action;
            this.id = id;
        }

        public RequestValues(int action, int id, int offset, int recordType) {
            this.action = action;
            this.offset = offset;
            this.recordType = recordType;
            this.id = id;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        Observable<List<Evaluate>> evaluateObs;
        Observable<List<EvaluateCategory>> evaluateCategoryObs;
        Observable<RatingScore> ratingScoreObservable;

        public ResponseValue(int action, Observable observable) {
            switch (action) {
                case ACTION_RATING_SCORE:
                    ratingScoreObservable = observable;
                    break;
                case ACTION_EVALUATE:
                    evaluateObs = observable;
                    break;
                case ACTION_EVALUATE_CATEGORY:
                    evaluateCategoryObs = observable;
                    break;
            }
        }

        public Observable<List<Evaluate>> getEvaluateObservable() {
            return evaluateObs;
        }

        public Observable<List<EvaluateCategory>> getEvaluateCategoryObservable() {
            return evaluateCategoryObs;
        }

        public Observable<RatingScore> getRatingScoreObservable() {
            return ratingScoreObservable;
        }


    }
}
