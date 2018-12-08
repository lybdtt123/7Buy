//package com.haoniu.zzx.app_ts.recriver;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.ContextWrapper;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.haoniu.zzx.app_ts.R;
//import com.irozon.sneaker.Sneaker;
//import com.meiqia.core.MQMessageManager;
//import com.meiqia.core.bean.MQAgent;
//import com.meiqia.core.bean.MQMessage;
//
///**
// * 作者： liuyuanbo on 2018/12/7 20:38.
// * 时间： 2018/12/7 20:38
// * 邮箱： 972383753@qq.com
// * 用途：
// */
//public class MessageReceiver extends BroadcastReceiver {
//    /*public Activity findActivity(Context context) {
//        if (context instanceof Activity) {
//            return (Activity) context;
//        }
//        if (context instanceof ContextWrapper) {
//            ContextWrapper wrapper = (ContextWrapper) context;
//            return findActivity(wrapper.getBaseContext());
//        } else {
//            return null;
//        }
//    }*/
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        View view = LayoutInflater.from(context).inflate(R.layout.message, null);
//        Sneaker.with(this)
//                .setTitle("Title", R.color.white) // Title and title color
//                .setMessage("This is the message.", R.color.white) // Message and message color
//                .setDuration(4000) // Time duration to show
//                .autoHide(true) // Auto hide Sneaker view
//                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
//                .setIcon(R.mipmap.ic_launcher, R.color.white, false) // Icon, icon tint color and circular icon view
////                .setTypeface(Typeface.createFromAsset(context.getAssets(), "font/" + "123")) // Custom font for title and message
////                .setOnSneakerClickListener(this) // Click listener for Sneaker
////                .setOnSneakerDismissListener(this) // Dismiss listener for Sneaker. - Version 1.0.2
////                .setCornerRadius(radius, margin) // Radius and margin for round corner Sneaker. - Version 1.0.2
//                .sneak(R.color.colorAccent); // Sneak with background color
////        final ViewGroup viewGroup = (ViewGroup) context.findViewById(android.R.id.content).getRootView();//注意getRootView()最为重要，直接关系到TSnackBar的位置
//        MQMessageManager messageManager = MQMessageManager.getInstance(context);
//        // 获取 ACTION
//        final String action = intent.getAction();
//
//        // 接收新消息
//        if (MQMessageManager.ACTION_NEW_MESSAGE_RECEIVED.equals(action)) {
//            // 从 intent 获取消息 id
//            String msgId = intent.getStringExtra("msgId");
//            // 从 MCMessageManager 获取消息对象
//
//            MQMessage message = messageManager.getMQMessage(msgId);
//            Log.e("meiqia_receiver", message.getContent());
//            Toast.makeText(context, message.getContent(), Toast.LENGTH_SHORT).show();
//            // do something
//        }
//
//        // 客服正在输入
//        else if (MQMessageManager.ACTION_AGENT_INPUTTING.equals(action)) {
//            // do something
//        }
//
//        // 客服转接
//        else if (MQMessageManager.ACTION_AGENT_CHANGE_EVENT.equals(action)) {
//            // 获取转接后的客服
//            MQAgent mqAgent = messageManager.getCurrentAgent();
//            // do something
//        }
//    }
//}
