package com.haoniu.zzx.uidemo.adapter;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.View;

import com.haoniu.zzx.uidemo.R;
import com.haoniu.zzx.uidemo.activity.AdaptionActivity;
import com.haoniu.zzx.uidemo.activity.MainActivity;
import com.haoniu.zzx.uidemo.activity.TabSegmentActivity;
import com.haoniu.zzx.uidemo.base.BaseRecyclerAdapter;
import com.haoniu.zzx.uidemo.base.RecyclerViewHolder;
import com.haoniu.zzx.uidemo.model.MainClickModel;
import com.haoniu.zzx.uidemo.utils.PreferenceUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by zzx on 2017/10/19 0019.
 */

public class AdapterMain extends BaseRecyclerAdapter<MainClickModel> {
    private Context ctx;
    private List<MainClickModel> models;

    public AdapterMain(Context ctx, List<MainClickModel> list) {
        super(ctx, list);
        this.ctx = ctx;
        models = list;
        pageNames = ctx.getResources().getStringArray(R.array.pageNames);
        selIndex = PreferenceUtils.getIntPreferences(ctx, "selIndex", 0);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.adapter_main_button;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, MainClickModel item) {
        holder.setText(R.id.btCobtent, item.getMsg());
        holder.setClickListener(R.id.btCobtent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 5) {
                    showTabSegmentDialog();
                } else if (position == 10) {
//                    changeLuncher(1, "com.haoniu.zzx.uidemo.icon_tag_1212");
                } else if (position == 11) {
//                    changeLuncher(2, "com.haoniu.zzx.uidemo.newsLuncherActivity");
                } else {
                    ctx.startActivity(new Intent(ctx, models.get(position).getaClass())
                            .putExtra("title", models.get(position).getMsg()));
                }
            }
        });
    }

    private String pageNames[];
    private int selIndex = 0;

    private void changeLuncher(int index, String name) {
        if (selIndex == index)
            return;
        PackageManager pm = ctx.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(ctx, pageNames[selIndex]),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(ctx, name),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        //Intent 重启 Launcher 应用
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        List<ResolveInfo> resolves = pm.queryIntentActivities(intent, 0);
//        for (ResolveInfo res : resolves) {
//            if (res.activityInfo != null) {
//                ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);
//                am.killBackgroundProcesses(res.activityInfo.packageName);
//            }
//        }
        selIndex = index;
        PreferenceUtils.saveIntPreferences(ctx, "selIndex", selIndex);
//        ((MainActivity)ctx).finish();
    }

    /**
     * @param useCode =1、为活动图标 =2 为用普通图标 =3、不启用判断
     */
    private void switchIcon(int useCode) {

        try {
            //要跟manifest的activity-alias 的name保持一致
            String icon_tag = "com.haoniu.zzx.uidemo.activity.MainActivity";
            String icon_tag_1212 = "com.haoniu.zzx.uidemo.icon_tag_1212";
            if (useCode != 3) {
                PackageManager pm = ctx.getPackageManager();
                ComponentName normalComponentName = new ComponentName(
                        ctx,
                        icon_tag);
                //正常图标新状态
                int normalNewState = useCode == 2 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(normalComponentName) != normalNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(
                            normalComponentName,
                            normalNewState,
                            PackageManager.DONT_KILL_APP);
                }

                ComponentName actComponentName = new ComponentName(
                        ctx,
                        icon_tag_1212);
                //正常图标新状态
                int actNewState = useCode == 1 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(actComponentName) != actNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(
                            actComponentName,
                            actNewState,
                            PackageManager.DONT_KILL_APP);
                }
            }
        } catch (Exception e) {
            String s = e.toString();
            Log.d("TAG", "sss:" + s);
        }
    }

    private void showTabSegmentDialog() {
        final String items[] = new String[]{"宽度固定，内容均分", "自适应"};
        final Class classes[] = new Class[]{TabSegmentActivity.class, AdaptionActivity.class};
        new QMUIDialog.CheckableDialogBuilder(ctx)
                .setTitle("请选择")
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ctx.startActivity(new Intent(ctx, classes[which])
                                .putExtra("title", items[which]));
                    }
                })
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}
