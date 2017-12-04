package com.example.random.http;

import com.example.random.Entity.Result;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by John on 2017/11/14.
 */

public interface LoadDataService {

    String ContentType = "Content-Type: application/json";
    String Accept = "Accept: application/json";
    String Token = "token:fd72f8db822f4a6795db20f31f659d07";

    @Headers({ContentType, Accept})
    @POST("/api/i/userLogin/v1")
    Observable<Result> loginService(@Body RequestBody body);

    @Headers({Accept, ContentType, Token})
    @GET("/api/crowd/compare/v1")
    Observable<Result> compare();

    @Headers({Accept, ContentType, Token})
    @POST("/mission/getDetail/v1")
    Observable<Result> getDetail(@Body RequestBody requestBody);

    @Headers({Accept, ContentType, Token})
    @GET("/api/crowd/checkAllowBuy/v1")
    Observable<Result> getCanByCrowd(@Query("vipId") String id);

}