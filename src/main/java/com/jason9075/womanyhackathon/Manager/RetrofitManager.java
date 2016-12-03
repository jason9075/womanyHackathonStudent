package com.jason9075.womanyhackathon.manager;

import android.location.Location;

import com.jason9075.womanyhackathon.model.GoogleMapLocationResult;
import com.jason9075.womanyhackathon.retrofit.LocationAddressService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jason9075 on 2016/12/3.
 */

public enum RetrofitManager {
    INSTANCE;

    public static final String GOOGLE_API_URL = "http://maps.googleapis.com/";

    private Retrofit retrofit;
    private LocationAddressService addressService;
    private String lastAddress = "";

    RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GOOGLE_API_URL)
                .build();

        addressService = retrofit.create(LocationAddressService.class);
    }

    public Observable<GoogleMapLocationResult> requestAddress(Location location, final boolean isSave) {
        if(location == null)
            return Observable.just(new GoogleMapLocationResult());
        return addressService.requestAddress(location.getLatitude() + "," + location.getLongitude())
                .map(new Func1<GoogleMapLocationResult, GoogleMapLocationResult>() {
                    @Override
                    public GoogleMapLocationResult call(GoogleMapLocationResult googleMapLocationResult) {
                        if(!isSave)
                            return googleMapLocationResult;
                        if (0 < googleMapLocationResult.getResults().size())
                            lastAddress =  googleMapLocationResult.getResults().get(0).getFormattedAddress();
                        else
                            lastAddress = "";
                        return googleMapLocationResult;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getLastAddress() {
        return lastAddress;
    }
}
