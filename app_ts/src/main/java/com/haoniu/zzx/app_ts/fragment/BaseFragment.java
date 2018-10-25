package com.haoniu.zzx.app_ts.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.activity.BaseActivity;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.myinterface.EventInterface;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Fragment的基类
 * Created by liuzhu
 * on 2017/5/31.
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    protected View mRoot;

    private Bundle mBundle;
    private EventInterface eventInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mBundle = getArguments();
        initBundle(mBundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null) {
                parent.removeView(mRoot);
            }
        } else {
            mRoot = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, mRoot);
            EventBus.getDefault().register(this);
        }
        return mRoot;
    }

//    /**
//     * 用户是否登录
//     * @return
//     */
//    protected boolean isUserLogin(){
//        return SPUtils.getInstance(getActivity()).getBooleanData(HuiHaoConfig.USER_LOGIN, false);
//    }

    protected void initData() {

    }

    protected void initBundle(Bundle bundle) {

    }

    protected abstract int getLayoutId();


    /**
     * EventBus接收消息
     *
     * @param center 消息接收
     */
    @Subscribe
    public void onEventMainThread(CommonEnity center) {

        if (eventInterface != null) {
            eventInterface.setEvent(center);
        }
    }

    public void setEventInterface(EventInterface eventInterface) {
        if (eventInterface != null) {
            this.eventInterface = eventInterface;
        }
    }

    public void displayImage(String url, ImageView imageView) {
        Glide.with(mContext )//
                .load(url)//
//                .error(R.mipmap.img_head)//
                .into(imageView);
    }

    public void displayImage(String url, ImageView imageView, int res) {
        Glide.with(mContext )//
                .load(url)//
                .error(res)//
                .into(imageView);
    }

    /**
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    private ProgressDialog mProgressDialog;

    public void showPro(String msg) {
        if (mProgressDialog != null) {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } else {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    public void hidePro() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public boolean checkEmpty(View view) {
        boolean b = ((BaseActivity) mContext).checkEmpty(view);
        return b;
    }

    public void showToast(String msg) {
        ToastUtils.showTextToast(mContext, msg);
    }

    public void showToast(int msg) {
        ToastUtils.showTextToast(mContext, msg);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public boolean checkEmpty(String msg) {
        if (msg == null || StringUtils.isEmpty(msg)) {
            return true;
        }
        return false;
    }
}
