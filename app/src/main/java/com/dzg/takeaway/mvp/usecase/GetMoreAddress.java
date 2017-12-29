package com.dzg.takeaway.mvp.usecase;

import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.usecase.base.UseCase;
import com.dzg.takeaway.repository.Repository;

import java.util.ArrayList;

import io.reactivex.Observable;

public class GetMoreAddress extends UseCase<GetMoreAddress.RequestValues, GetMoreAddress.ResponseValue> {
    private final Repository mRepository;

    public GetMoreAddress(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
           return new ResponseValue(mRepository.getAddressData(requestValues.addr,requestValues.offset));
    }

    public static final class RequestValues implements UseCase.RequestValues {
        String addr;
        int offset;
        public RequestValues(String address,int offset) {
            this.addr=address;
            this.offset=offset;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        Observable<ArrayList<Address>> addresses;
        public ResponseValue( Observable<ArrayList<Address>> addresses) {
        this.addresses=addresses;
        }

        public Observable<ArrayList<Address>> getObservable() {
            return addresses;
        }



    }
}
