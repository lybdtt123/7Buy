package com.haoniu.zzx.app_ts.http;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by liuzhu
 * on 2017/5/26.
 */

public interface FileUploadService {
    @Multipart
    @POST("getData/index.php?m=shop&c=index&a=uploadFile")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
    @Multipart
    @POST("getData/index.php?m=shop&c=index&a=uploadCard")
    Call<ResponseBody> uploadCard(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);

}
