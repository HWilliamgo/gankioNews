package com.solory.gankionews.net;
/*
 *
 * Created by 黄伟杰 on 2018/3/7.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsRequestService {
    @GET("{type}/{number}/{page}")
    Call<NewsResponse> getNews(@Path("type")String type,@Path("number") int number,@Path("page")int page);
}
