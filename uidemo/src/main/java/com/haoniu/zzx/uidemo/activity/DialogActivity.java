package com.haoniu.zzx.uidemo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.haoniu.zzx.uidemo.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUIItemViewsAdapter;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class DialogActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView mListView;
    protected String title = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_dialog);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);
        String[] listItems = new String[]{
                "消息类型对话框（蓝色按钮）",
                "消息类型对话框（红色按钮）",
                "菜单类型对话框",
                "带 Checkbox 的消息确认框",
                "单选菜单类型对话框",
                "多选菜单类型对话框",
                "带输入框的对话框",
                "高度适应键盘升降的对话框",
                "         对话框底部         ",
                "列表",
                "表格"
        };
        List<String> data = new ArrayList<>();

        Collections.addAll(data, listItems);

        mListView.setAdapter(new ArrayAdapter<>(mContext, R.layout.adapter_simple, data));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showMessagePositiveDialog();
                        break;
                    case 1:
                        showMessageNegativeDialog();
                        break;
                    case 2:
                        showMenuDialog();
                        break;
                    case 3:
                        showConfirmMessageDialog();
                        break;
                    case 4:
                        showSingleChoiceDialog();
                        break;
                    case 5:
                        showMultiChoiceDialog();
                        break;
                    case 6:
                        showEditTextDialog();
                        break;
                    case 7:
                        showAutoDialog();
                        break;
                    case 9:
                        showBottonList();
                        break;
                    case 10:
                        showBottomGrid();
                        break;
                }
            }
        });
    }

    /**
     * 底部列表
     */
    private void showBottonList() {
        new QMUIBottomSheet.BottomListSheetBuilder(mContext)
                .setTitle("List")
                .addItem("One")
                .addItem("Two")
                .addItem("Three")
                .addItem("Four")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        toast(" --- " + tag);
                    }
                })
                .build().show();
    }

    /**
     * 底部表格
     */
    private void showBottomGrid() {
        new QMUIBottomSheet.BottomGridSheetBuilder(mContext)
                .addItem(R.mipmap.img_cry, "Cry", 0, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.img_cry_s, "红色的Cry", 1, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.img_cry, "Cry", 0, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.img_cry_s, "红色的Cry", 1, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.img_smile, "Smile", 2, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.img_smile_s, "红色的Smile", 3, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.img_smile, "Smile", 2, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.img_smile_s, "红色的Smile", 3, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView) {
                        int tag = (int) itemView.getTag();
                        if (tag == 0) {
                            toast("Cry");
                        } else if (tag == 1) {
                            toast("红色的Cry");
                        } else if (tag == 2) {
                            toast("Smile");
                        } else if (tag == 3) {
                            toast("红色的Smile");
                        }
                    }
                })
                .build().show();
    }


    private void showMessagePositiveDialog() {
        new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle("标题Title")
                .setMessage("消息类型对话框Msg")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showMessageNegativeDialog() {
        new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle("消息类型对话框Title")
                .setMessage("消息类型对话框Msg")
                .addAction(0, "取消", QMUIDialogAction.ACTION_PROP_NEUTRAL, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "确定(红色)", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showMenuDialog() {
        final String[] items = new String[]{"选项1", "选项2", "选项3"};
        new QMUIDialog.MenuDialogBuilder(mContext)
                .setTitle("请选择")
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast(items[which]);
                    }
                })
                .show();
    }

    private QMUIDialog.CheckBoxMessageDialogBuilder builder;

    private void showConfirmMessageDialog() {
        builder = new QMUIDialog.CheckBoxMessageDialogBuilder(mContext)
                .setChecked(true)
                .setTitle("确定退出吗")
                .setMessage("删除个人信息")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        if (builder.isChecked()) {
                            toast("确定删除");
                        } else {
                            toast("不删除");
                        }
                    }
                });
        builder.show();
    }

    private void showSingleChoiceDialog() {
        final String[] items = new String[]{"A", "B", "C", "D", "E"};
        new QMUIDialog.CheckableDialogBuilder(mContext)
                .setTitle("请选择答案")
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast(items[which]);
                    }
                })
                .show();
    }

    private QMUIDialog.MultiCheckableDialogBuilder multi;

    private void showMultiChoiceDialog() {
        final String[] items = new String[]{"A", "B", "C", "D", "E"};
        multi = new QMUIDialog.MultiCheckableDialogBuilder(mContext)
                .setTitle("请选择答案")
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast(items[which]);
                    }
                })
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        int[] checkedItemIndexes = multi.getCheckedItemIndexes();
                        String msg = "";
                        for (int i = 0; i < checkedItemIndexes.length; i++) {
                            msg = msg + items[checkedItemIndexes[i]];
                        }
                        toast(msg);
                    }
                })
        ;
        multi.show();
    }

    private QMUIDialog.EditTextDialogBuilder edit;

    private void showEditTextDialog() {
        edit = new QMUIDialog.EditTextDialogBuilder(mContext)
                .setPlaceholder("请输入答案")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        String string = edit.getEditText().toString();
                        toast(string + "");
                    }
                });
        edit.show();
    }

    private void showAutoDialog() {
        QMAutoTestDialogBuilder autoTestDialogBuilder = (QMAutoTestDialogBuilder) new QMAutoTestDialogBuilder(mContext)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        Toast.makeText(mContext, "你点了确定", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
        autoTestDialogBuilder.show();
        QMUIKeyboardHelper.showKeyboard(autoTestDialogBuilder.getEditText(), true);
    }

    class QMAutoTestDialogBuilder extends QMUIDialog.AutoResizeDialogBuilder {
        private Context mContext;
        private EditText mEditText;

        public QMAutoTestDialogBuilder(Context context) {
            super(context);
            mContext = context;
        }

        public EditText getEditText() {
            return mEditText;
        }

        @Override
        public View onBuildContent(QMUIDialog dialog, ScrollView parent) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int padding = QMUIDisplayHelper.dp2px(mContext, 20);
            layout.setPadding(padding, padding, padding, padding);
            mEditText = new EditText(mContext);
            QMUIViewHelper.setBackgroundKeepingPadding(mEditText, QMUIResHelper.getAttrDrawable(mContext, R.attr.qmui_list_item_bg_with_border_bottom));
            mEditText.setHint("输入框");
            LinearLayout.LayoutParams editTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dpToPx(50));
            editTextLP.bottomMargin = QMUIDisplayHelper.dp2px(mContext, 15);
            mEditText.setLayoutParams(editTextLP);
            layout.addView(mEditText);
            TextView textView = new TextView(mContext);
            textView.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
            textView.setText("观察聚焦输入框后，键盘升起降下时 dialog 的高度自适应变化。\n\n" +
                    "QMUI Android 的设计目的是用于辅助快速搭建一个具备基本设计还原效果的 Android 项目，" +
                    "同时利用自身提供的丰富控件及兼容处理，让开发者能专注于业务需求而无需耗费精力在基础代码的设计上。" +
                    "不管是新项目的创建，或是已有项目的维护，均可使开发效率和项目质量得到大幅度提升。");
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.app_color_blue));
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(textView);
            return layout;
        }
    }


//    private void showAutoDialog() {
//        new QMUIDialog.AutoResizeDialogBuilder(mContext) {
//            @Override
//            public View onBuildContent(QMUIDialog dialog, ScrollView parent) {
//
//                return null;
//            }
//        }
//                .setTitle("auto")
//                .show();
//    }
}
