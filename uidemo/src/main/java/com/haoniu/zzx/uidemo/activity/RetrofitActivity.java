package com.haoniu.zzx.uidemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.haoniu.zzx.uidemo.R;
import com.haoniu.zzx.uidemo.model.VoteModel;
import com.haoniu.zzx.uidemo.retrofit.Service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitActivity extends BaseActivity {
    protected String title = "";
    private TextView tvCount;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_retrofit);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);
        tvCount = (TextView) findViewById(R.id.tvCount);
    }

    @OnClick({R.id.btVote, R.id.btPause})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btVote:
                isPause = false;
                mHandle.sendEmptyMessageDelayed(310, 10);
                break;
            case R.id.btPause:
                isPause = true;
                mHandle.sendEmptyMessageDelayed(310, 10);
                break;
        }
    }

    Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 310 && !isPause) {
                postVote();
            }
        }
    };

    private void getBaidu() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
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
        Call<String> baidu = service.getBaidu();
        baidu.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                print(call.request().url() + "---" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                toast(call.toString() + "---" + t.getMessage());
            }
        });
    }

    private int count;
    private boolean isPause;

    private void postVote() {
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
                if (response != null && response.body() != null && response.body().contains("投票成功")) {
                    count++;
                    tvCount.setText("" + count);
                }
                mHandle.sendEmptyMessageDelayed(310, 10);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                toast(call.toString() + "---" + t.getMessage());
                mHandle.sendEmptyMessageDelayed(310, 10);
            }
        });
    }

}
