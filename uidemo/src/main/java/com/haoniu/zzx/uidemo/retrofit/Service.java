package com.haoniu.zzx.uidemo.retrofit;

import com.haoniu.zzx.uidemo.model.VoteModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by zzx on 2017/10/31 0031.
 */

public interface Service {
    @GET("/")
    Call<String> getBaidu();
    @POST("act2017/HappyNewYear/Vote/9")
    Call<String> postVote();
}
