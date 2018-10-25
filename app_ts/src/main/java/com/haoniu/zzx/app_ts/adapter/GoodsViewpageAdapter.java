package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.NormalModel;

import java.util.List;

/**
 * Created by zzx on 2017/9/21 0021.
 */

public class GoodsViewpageAdapter implements Holder<List<NormalModel>> {
    private View rootview;
    private GridView gv_recommend_goods;
    private Context mContext;

    @Override
    public View createView(Context context) {
        mContext = context;
        rootview = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_viewpage_goods, null);
        gv_recommend_goods = (GridView) rootview.findViewById(R.id.gv_recommend_goods);
        return rootview;
    }

    @Override
    public void UpdateUI(Context context, final int position, final List<NormalModel> data) {
        gv_recommend_goods.setAdapter(new GoodsItemAdapter(context, data));
//        gv_recommend_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
    }
}
