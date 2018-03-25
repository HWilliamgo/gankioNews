package com.solory.gankionews.IContract;
/*
 *
 * Created by 黄伟杰 on 2018/3/8.
 */

import com.solory.gankionews.net.NewsResponse;

import retrofit2.Call;

public interface IGetNewsContract {
    public interface Model{
        Call<NewsResponse> getNewsResponse(String type,int number,int page);
    }
    public interface View{
        void onGetSucceed(NewsResponse newsResponse);
        void onError();
    }
    public interface Presenter{
        void doGetNews(String type,int number,int page);
    }
}
