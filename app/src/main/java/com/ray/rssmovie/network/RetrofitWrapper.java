package com.ray.rssmovie.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by guolei on 17-4-6.
 */

public class RetrofitWrapper {

    private static RetrofitWrapper instance;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private NetWorkApi mNetWorkApi;

    private RetrofitWrapper() {
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(NetWorkUrl.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mNetWorkApi = mRetrofit.create(NetWorkApi.class);
    }

    public static RetrofitWrapper getInstance() {
        if (instance == null) {
            synchronized (RetrofitWrapper.class) {
                if (instance == null) {
                    instance = new RetrofitWrapper();
                }
            }
        }
        return instance;
    }

    public NetWorkApi getNetWorkApi() {
        return mNetWorkApi;
    }

    public <T> T create(Class<T> tClass) {
        if (mRetrofit != null) {
            return mRetrofit.create(tClass);
        }
        return null;
    }
}

