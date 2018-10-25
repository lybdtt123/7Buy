package com.haoniu.zzx.uidemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haoniu.zzx.uidemo.R;
import com.haoniu.zzx.uidemo.adapter.AdapterMain;
import com.haoniu.zzx.uidemo.model.MainClickModel;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.topbar)
    QMUITopBar topbar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        initView();
    }

    private List<MainClickModel> clickModels;
    private AdapterMain adapterMain;

    //      QMUITipDialog   提示消息
    private void initData() {
        clickModels = new ArrayList<>();
        clickModels.add(new MainClickModel("QMUIRoundButton", ButtonActivity.class));
        clickModels.add(new MainClickModel("QMUIDialog", DialogActivity.class));
        clickModels.add(new MainClickModel("FloatLayout", FloatLayoutActivity.class));
        clickModels.add(new MainClickModel("Camera", CameraActivity.class));
        clickModels.add(new MainClickModel("EmptyView", EmptyViewActivity.class));
        clickModels.add(new MainClickModel("TabSegment", TabSegmentActivity.class));
        clickModels.add(new MainClickModel("ProgressBar", ProgressBarActivity.class));
        clickModels.add(new MainClickModel("DataBingding", DataBingdingActivity.class));
        clickModels.add(new MainClickModel("Retrofit", RetrofitActivity.class));
        clickModels.add(new MainClickModel("自定义View", DiyViewActivity.class));
        clickModels.add(new MainClickModel("更换logo1", null));
        clickModels.add(new MainClickModel("更换logo2", null));
        clickModels.add(new MainClickModel("模糊", BlurredViewActivity.class));
        clickModels.add(new MainClickModel("测试View", DrawTestActivity.class));
        clickModels.add(new MainClickModel("图片", PhotoActivity.class));

    }

    private void initView() {
        adapterMain = new AdapterMain(mContext, clickModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterMain);

//        Beta.checkUpgrade();
    }
}
