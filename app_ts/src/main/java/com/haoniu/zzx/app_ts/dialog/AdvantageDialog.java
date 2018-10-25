package com.haoniu.zzx.app_ts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.haoniu.zzx.app_ts.R;

/**
 * Created by zzx on 2017/11/6 0006.
 */

public class AdvantageDialog extends Dialog {
    private Context mContext;
    public ImageView ivAdvantage, ivCancel;

    public AdvantageDialog(Context context) {
        super(context, R.style.Theme_Light_FullScreenDialogAct);
        setContentView(R.layout.dialog_ad);
        Window window = this.getWindow();
//        window.setWindowAnimations(R.style.myDialogAnim);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        mContext = context;
        ivAdvantage = (ImageView) findViewById(R.id.ivAdvantage);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setAdvantageClick(View.OnClickListener listener) {
        ivAdvantage.setOnClickListener(listener);
    }
}
