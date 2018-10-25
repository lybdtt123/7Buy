package com.haoniu.zzx.uidemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.haoniu.zzx.uidemo.R;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProgressBarActivity extends BaseActivity {
    protected String title = "";
    @BindView(R.id.mProgressBarRect)
    QMUIProgressBar mProgressBarRect;
    @BindView(R.id.mProgressBarCicle)
    QMUIProgressBar mProgressBarCicle;
    @BindView(R.id.btStart)
    Button btStart;
    @BindView(R.id.btPause)
    Button btPause;
    @BindView(R.id.btBack)
    Button btBack;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_progress_bar);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);
        mProgressBarRect.setQMUIProgressBarTextGenerator(new QMUIProgressBar.QMUIProgressBarTextGenerator() {
            @Override
            public String generateText(QMUIProgressBar progressBar, int value, int maxValue) {
                String msg = "";
                if (value < 30) {
                    msg = "才刚开始，稍等呀！";
                } else if (value < 50) {
                    msg = "快到一半了，加油！";
                } else if (value < 80) {
                    msg = "hurry up!";
                } else if (value < 99) {
                    msg = "almost!";
                } else if (value == maxValue) {
                    msg = "It's can!";
                }
                return msg;
            }
        });
        mProgressBarCicle.setQMUIProgressBarTextGenerator(new QMUIProgressBar.QMUIProgressBarTextGenerator() {
            @Override
            public String generateText(QMUIProgressBar progressBar, int value, int maxValue) {
                String msg = "";
                if (value < 30) {
                    msg = "才刚开始，稍等呀！";
                } else if (value < 50) {
                    msg = "快到一半了，加油！";
                } else if (value < 80) {
                    msg = "hurry up!";
                } else if (value < 99) {
                    msg = "almost!";
                } else if (value == maxValue) {
                    msg = "It's can!";
                }
                return msg;
            }
        });
        buttonList = new ArrayList<>();
        buttonList.add(btStart);
        buttonList.add(btPause);
        buttonList.add(btBack);
    }

    private List<Button> buttonList;

    @OnClick({R.id.btStart, R.id.btPause, R.id.btBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btStart:
                initClick(0);
                tag = 0;
                mHandler.sendEmptyMessageDelayed(310, 100);
                break;
            case R.id.btPause:
                initClick(1);
                tag = -1;
                mProgressBarRect.setProgress(progress);
                mProgressBarCicle.setProgress(progress);
                break;
            case R.id.btBack:
                initClick(2);
                tag = 1;
                mHandler.sendEmptyMessageDelayed(311, 100);
                break;
        }
    }

    private void initClick(int tag) {
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setClickable(true);
        }
        buttonList.get(tag).setClickable(false);
    }

    private int tag = 0;
    private int progress;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 310 && tag == 0) {//开始
                if (progress < 100) {
                    progress++;
                }
                mHandler.sendEmptyMessageDelayed(310, 100);
            } else if (msg.what == 311 && tag == 1) {
                if (progress > 0) {
                    progress--;
                }
                mHandler.sendEmptyMessageDelayed(311, 100);
            }
            mProgressBarRect.setProgress(progress);
            mProgressBarCicle.setProgress(progress);
        }
    };
}
