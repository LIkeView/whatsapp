package com.likeview.bulkwhatsappmsg.Services;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.likeview.bulkwhatsappmsg.R;

import java.util.List;

public class WhatAppAccessibilityService extends AccessibilityService {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.getEventType()){
            Log.e("TAG::0::","ACC::onAccessibilityEvent = "+event);

        AccessibilityNodeInfo nodeInfo = event.getSource();
        Log.i( "TAG::1::","ACC::onAccessibilityEvent: nodeInfo= "+nodeInfo );
        if (nodeInfo == null){
            return;
        }

        // get whatsapp send message button node list
        List<AccessibilityNodeInfo> sendMessageNodeList = nodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
        for (AccessibilityNodeInfo node : sendMessageNodeList){
            Log.e( "TAG::3::","ACC::onAccessibilityEvent: send_button"+ node );
            node.performAction( AccessibilityNodeInfo.ACTION_CLICK );
            try {
                Thread.sleep( 2000 );
                performGlobalAction( GLOBAL_ACTION_BACK );
                Thread.sleep( 2000 );
                performGlobalAction( GLOBAL_ACTION_BACK );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        }


//        buildNotification();


//        if(getRootInActiveWindow() == null){
//            return;
//        }
//
//        //getting root node
//        AccessibilityNodeInfoCompat rootNodeInfo = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());
//
//        //get edit text if message from whats app
//        List<AccessibilityNodeInfoCompat> messageNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
//        if(messageNodeList == null || messageNodeList.isEmpty())
//            return;
//
//
//        //checking if message field if filled with text and ending with our suffix
//
//        AccessibilityNodeInfoCompat messageField = messageNodeList.get(0);
//        if(messageField == null || messageField.getText().length() == 0 || !messageField.getText().toString().endsWith("   "))
//            return;
//
//
//        // get whatsapp send message button node list
//        List<AccessibilityNodeInfoCompat> sendMessageNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
//        if(sendMessageNodeList == null || sendMessageNodeList.isEmpty())
//            return;
//
//        AccessibilityNodeInfoCompat sendMessage = sendMessageNodeList.get(0);
//        if(!sendMessage.isVisibleToUser())
//            return;
//
//
//        //fire send button
//        sendMessage.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//
//
//        //go back to our app by clicking back button twice
//
//        try{
//            Thread.sleep(5000); // some devices cant handle instant back click
//            performGlobalAction(GLOBAL_ACTION_BACK);
//            Thread.sleep(5000);
//            performGlobalAction(GLOBAL_ACTION_BACK);
//        }catch (InterruptedException ignored) {}



    }

//    private void buildNotification() {
//
//            // Create the persistent notification
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
////                .setContentTitle(getString(R.string.app_name))
////                .setContentText(getString(R.string.notification_text))
////                .setOngoing(true)
////                .setContentIntent(broadcastIntent)
////                .setSmallIcon(R.drawable.ic_drawerbg_two);
////        startForeground(1, builder.build());
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                String NOTIFICATION_CHANNEL_ID = "com.likeview.bulkwhatsappmsg";
//                String channelName = "My Background Service";
//                NotificationChannel chan = null;
//                chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
//                chan.setLightColor( Color.BLUE);
//                chan.setLockscreenVisibility( Notification.VISIBILITY_PRIVATE);
//                NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
//                assert manager != null;
//                manager.createNotificationChannel(chan);
//                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
//                Notification notification = notificationBuilder.setOngoing(true)
//                        .setSmallIcon( R.mipmap.ic_launcher)
//                        .setContentTitle("App is running in background")
//                        .setPriority(NotificationManager.IMPORTANCE_MIN)
//                        .setCategory(Notification.CATEGORY_SERVICE)
//                        .build();
//                startForeground(2, notification);
//            }
//
//    }

    @Override
    public void onInterrupt() {

    }
}
