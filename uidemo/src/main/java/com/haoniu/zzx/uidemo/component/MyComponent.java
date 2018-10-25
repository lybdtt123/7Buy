package com.haoniu.zzx.uidemo.component;

import android.databinding.DataBindingComponent;

import com.haoniu.zzx.uidemo.utils.MyDataBingdingUtil;

/**
 * Created by zzx on 2017/10/30 0030.
 */

public class MyComponent implements DataBindingComponent {
    private MyDataBingdingUtil dataBingdingUtil;

    @Override
    public MyDataBingdingUtil getMyDataBingdingUtil() {
        if (dataBingdingUtil==null){
            dataBingdingUtil = new MyDataBingdingUtil();
        }
        return dataBingdingUtil;
    }
}
