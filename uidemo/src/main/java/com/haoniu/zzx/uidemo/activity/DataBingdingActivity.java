package com.haoniu.zzx.uidemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.haoniu.zzx.uidemo.R;
import com.haoniu.zzx.uidemo.component.MyComponent;
import com.haoniu.zzx.uidemo.databinding.ActivityDataBingdingBinding;
import com.haoniu.zzx.uidemo.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DataBingdingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data_bingding);
//        DataBindingUtil.setDefaultComponent(new MyComponent());//设置默认的 Component
        ActivityDataBingdingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_bingding,new MyComponent());
        List<UserModel> users = new ArrayList<>();
        UserModel userModel = new UserModel();
        userModel.setTrueName("zzx");
        userModel.setNickName("nickName");
        userModel.setEmail("urnotxx0310@163.com");
        userModel.setIcon("http://f2.topitme.com/2/85/82/115895216032c82852o.jpg");
        userModel.setLevel(5);
        UserModel userModel1 = new UserModel();
        userModel1.setTrueName("zzx(2)");
        userModel1.setNickName("nickName(2)");
        userModel1.setEmail("urnotxx0310@163.com(2)");
        userModel1.setLevel(1);
        users.add(userModel);
        users.add(userModel1);
        binding.setUsers(users);
    }
}
