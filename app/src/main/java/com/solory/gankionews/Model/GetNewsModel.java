package com.solory.gankionews.Model;
/*
 *
 * Created by 黄伟杰 on 2018/3/7.
 */

import android.telecom.Call;

import com.solory.gankionews.IContract.IGetNewsContract;
import com.solory.gankionews.net.NetManger;
import com.solory.gankionews.net.NewsRequestService;
import com.solory.gankionews.net.NewsResponse;

public class GetNewsModel implements IGetNewsContract.Model{

    @Override
    public retrofit2.Call<NewsResponse> getNewsResponse(String type, int number, int page) {
        return NetManger.getRetrofit().create(NewsRequestService.class).getNews(type,number,page);
    }
}
