package com.solory.gankionews.net;
/*
 *
 * Created by 黄伟杰 on 2018/3/7.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManger {

    private NetManger(){
    }

    private static final String BASE_URL=" http://gank.io/api/data/";

    public static Retrofit getRetrofit(){
        return RetrofitHolder.retrofit;
    }

    private static class RetrofitHolder{

        private static final Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .client(new OkHttpClient())
                .baseUrl(BASE_URL)
                .build();
    }

}
