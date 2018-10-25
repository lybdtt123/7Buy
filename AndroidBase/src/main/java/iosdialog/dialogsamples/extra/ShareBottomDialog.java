package iosdialog.dialogsamples.extra;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;

import iosdialog.animation.FlipEnter.FlipVerticalSwingEnter;
import iosdialog.dialog.widget.base.BottomBaseDialog;
import iosdialog.dialogsamples.utils.T;
import iosdialog.dialogsamples.utils.ViewFindUtils;
import self.androidbase.R;


public class ShareBottomDialog extends BottomBaseDialog<ShareBottomDialog> implements View.OnClickListener {
    private LinearLayout ll_wechat_friend_circle;
    private LinearLayout ll_wechat_friend;
    private LinearLayout ll_qq;
    private LinearLayout ll_sms;

    public ShareBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public ShareBottomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        showAnim(new FlipVerticalSwingEnter());
        dismissAnim(null);
        View inflate = View.inflate(context, R.layout.dialog_share, null);
        ll_wechat_friend_circle = ViewFindUtils.find(inflate, R.id.ll_wechat_friend_circle);
        ll_wechat_friend = ViewFindUtils.find(inflate, R.id.ll_wechat_friend);
        ll_qq = ViewFindUtils.find(inflate, R.id.ll_qq);
        ll_sms = ViewFindUtils.find(inflate, R.id.ll_sms);

        return inflate;
    }

    @Override
    public void setUiBeforShow() {

        ll_wechat_friend_circle.setOnClickListener(this);
        ll_wechat_friend.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
    }

    private OnClickListener wxcicle;
    private OnClickListener wx;
    private OnClickListener qq;

    public void setShareClickListener(DialogInterface.OnClickListener wxcicle, DialogInterface.OnClickListener wx, DialogInterface.OnClickListener qq) {
        this.wxcicle = wxcicle;
        this.wx = wx;
        this.qq = qq;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_wechat_friend_circle) {
            if (wxcicle != null) {
                wxcicle.onClick(this, 0);
            }
        } else if (i == R.id.ll_wechat_friend) {
            if (wx != null) {
                wx.onClick(this, 0);
            }
        } else if (i == R.id.ll_qq) {
            if (qq != null) {
                qq.onClick(this, 0);
            }
        }
    }
}
