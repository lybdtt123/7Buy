package com.haoniu.zzx.uidemo.view;

import android.support.annotation.Nullable;

import com.haoniu.zzx.uidemo.retrofit.Service;
import com.haoniu.zzx.uidemo.utils.DateUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zzx on 2017/12/15 0015.
 */

public class TestJava {
    private static int count;

    public static void main(String[] args) {
//        String msg = "+4564++daa";
//        msg = msg.replaceFirst("\\+", "");
//        System.out.print(msg + " -------");
        System.out.print("开始时间：" + DateUtils.getNowDate(DateUtils.DatePattern.ALL_TIME) + "\n");
        postVote();
    }

    private static void postVote() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hd2.jstv.com/")
                .addConverterFactory(new Converter.Factory() {
                    @Nullable
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                }).build();
        Service service = retrofit.create(Service.class);
        Call<String> vote = service.postVote();
        vote.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && response.body() != null && response.body().contains("投票成功") && count < 20000) {
                    count++;
//                    System.out.print("\n" + count + " -------");
                    postVote();
                    return;
                }
                System.out.print("结束时间：" + DateUtils.getNowDate(DateUtils.DatePattern.ALL_TIME));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.print("失败：" + DateUtils.getNowDate(DateUtils.DatePattern.ALL_TIME) + "  count ：" + count + "\n");
            }
        });
    }
}
