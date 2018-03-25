package com.solory.gankionews.Presenter;
/*
 *
 * Created by 黄伟杰 on 2018/3/8.
 */

import com.solory.gankionews.IContract.IGetNewsContract;
import com.solory.gankionews.Model.GetNewsModel;
import com.solory.gankionews.net.NewsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNewsPresenter implements IGetNewsContract.Presenter{
    private IGetNewsContract.Model model;
    private IGetNewsContract.View view;

    public GetNewsPresenter(IGetNewsContract.View view) {
        this.view = view;
        model=new GetNewsModel();
    }


    @Override
    public void doGetNews(String type, int number, int page) {
        //将View层传进来的请求参数传入Model层去请求网络。
        model.getNewsResponse(type,number,page).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                try {
                    view.onGetSucceed(response.body());
                }catch (Exception e){
                    view.onError();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                view.onError();
            }
        });
    }
}
