package com.haoniu.zzx.uidemo.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoniu.zzx.uidemo.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TabSegmentActivity extends BaseActivity {

    @BindView(R.id.mTabSegment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    protected String title = "";
    private Map<ContentPage, View> mPageMap = new HashMap<>();
    private ContentPage mDestPage = ContentPage.Item1;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_tab_segment);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mDestPage.getPosition(), false);
        mTabSegment.addTab(new QMUITabSegment.Tab("One"));
        mTabSegment.addTab(new QMUITabSegment.Tab("Two"));
        mTabSegment.setupWithViewPager(mViewPager, false);
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onDoubleTap(int index) {

            }
        });

    }

    @OnClick({R.id.bt0, R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt0:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(false);
                mTabSegment.addTab(new QMUITabSegment.Tab("One"));
                mTabSegment.addTab(new QMUITabSegment.Tab("Two"));
                break;
            case R.id.bt1:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(false);
                mTabSegment.setIndicatorWidthAdjustContent(false);
                mTabSegment.addTab(new QMUITabSegment.Tab("One"));
                mTabSegment.addTab(new QMUITabSegment.Tab("Two"));
                break;
            case R.id.bt2:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(true);
                mTabSegment.setIndicatorWidthAdjustContent(false);
                mTabSegment.addTab(new QMUITabSegment.Tab("One"));
                mTabSegment.addTab(new QMUITabSegment.Tab("Two"));
                break;
            case R.id.bt3:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(true);
                mTabSegment.setIndicatorWidthAdjustContent(true);
                mTabSegment.addTab(new QMUITabSegment.Tab("One"));
                mTabSegment.addTab(new QMUITabSegment.Tab("Two"));
                break;
            case R.id.bt4:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(false);
                QMUITabSegment.Tab One = new QMUITabSegment.Tab(
                        ContextCompat.getDrawable(mContext, R.mipmap.img_cry),
                        null,
                        "One", true
                );
                One.setTextColor(getResources().getColor(R.color.app_color_blue), getResources().getColor(R.color.qmui_config_color_red));
                QMUITabSegment.Tab Two = new QMUITabSegment.Tab(
                        ContextCompat.getDrawable(mContext, R.mipmap.img_smile),
                        null,
                        "Two", true
                );
                Two.setTextColor(getResources().getColor(R.color.app_color_blue), getResources().getColor(R.color.qmui_config_color_red));
                mTabSegment.addTab(One);
                mTabSegment.addTab(Two);
                break;
            case R.id.bt5:
                QMUITabSegment.Tab tab = mTabSegment.getTab(1);
                tab.setSignCountMargin(0, -QMUIDisplayHelper.dp2px(mContext, 4));
                tab.showSignCountView(mContext, 3);
                break;
            case R.id.bt6:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(false);
                QMUITabSegment.Tab one = new QMUITabSegment.Tab(ContextCompat.getDrawable(mContext, R.mipmap.img_cry)
                        , ContextCompat.getDrawable(mContext, R.mipmap.img_cry_s), "One", false);
                QMUITabSegment.Tab two = new QMUITabSegment.Tab(ContextCompat.getDrawable(mContext, R.mipmap.img_smile)
                        , ContextCompat.getDrawable(mContext, R.mipmap.img_smile_s), "Two", false);
                mTabSegment.addTab(one);
                mTabSegment.addTab(two);
                break;
            case R.id.bt7:
                mTabSegment.reset();
                mTabSegment.setHasIndicator(false);
                QMUITabSegment.Tab Cry = new QMUITabSegment.Tab(ContextCompat.getDrawable(mContext, R.mipmap.img_cry)
                        , ContextCompat.getDrawable(mContext, R.mipmap.img_cry_s), "Cry", false);
                QMUITabSegment.Tab Smile = new QMUITabSegment.Tab(ContextCompat.getDrawable(mContext, R.mipmap.img_smile)
                        , ContextCompat.getDrawable(mContext, R.mipmap.img_smile_s), "Smile", false);
                mTabSegment.addTab(Cry);
                mTabSegment.addTab(Smile);
                break;
            case R.id.bt8:
                mTabSegment.updateTabText(0, "First Cry Second Smile");
                break;
            case R.id.bt9:
                QMUITabSegment.Tab mTab = new QMUITabSegment.Tab(ContextCompat.getDrawable(mContext, R.mipmap.img_smile)
                        , ContextCompat.getDrawable(mContext, R.mipmap.img_smile_s), "First Cry Second Smile", false);
                mTabSegment.replaceTab(0, mTab);
                break;
        }
        mTabSegment.notifyDataChanged();
    }


    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return ContentPage.SIZE;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            ContentPage page = ContentPage.getPage(position);
            View view = getPageView(page);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    };

    private View getPageView(ContentPage page) {
        View view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.app_color_blue));

            if (page == ContentPage.Item1) {
                textView.setText("This is One");
            } else if (page == ContentPage.Item2) {
                textView.setText("This is Two");
            }
            view = textView;
            mPageMap.put(page, view);
        }
        return view;
    }

    public enum ContentPage {
        Item1(0),
        Item2(1);
        public static final int SIZE = 2;
        private final int position;

        ContentPage(int pos) {
            position = pos;
        }

        public static ContentPage getPage(int position) {
            switch (position) {
                case 0:
                    return Item1;
                case 1:
                    return Item2;
                default:
                    return Item1;
            }
        }

        public int getPosition() {
            return position;
        }
    }

}
