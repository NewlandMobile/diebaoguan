package com.lin.diebaoguan.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyApplication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.activity.GyjOriginalDetailsActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.PushResponse;
import com.lin.diebaoguan.network.send.BaseSendTemplate;
import com.lin.lib_volley_https.VolleyListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PushNotificationService extends Service {

    private Context context;
    private boolean isStop = false;

    public PushNotificationService() {
    }

    /**
     * 只在bound service的时候有用到
     */
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        LogUtils.e("==push service is create==");
        context = getApplicationContext();
        super.onCreate();
    }

    /**
     * START_STICKY  即使由于内存空间不足，被系统kill，也会在内存足够的时候恢复
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new PushThread().start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        LogUtils.e("==push service is stop==");
        isStop = true;
        super.onDestroy();
    }

    class PushThread extends Thread {
        @Override
        public void run() {
            while (!isStop) {
                try {
                    LogUtils.e("==获取信息线程开启==");
                    getData();
                    sleep(600 * 1000);//每隔10分钟去获取一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取推送的数据
     */
    private void getData() {
        final BaseSendTemplate baseSendTemplate = new BaseSendTemplate();
        baseSendTemplate.setModule("api_libraries_sjdbg_docpush");
        baseSendTemplate.initTimePart();
        CommonUtils.httpPost(baseSendTemplate.parseParams(), new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtils.e(getString(R.string.getdatafail) + ":" + volleyError.toString());
                    }

                    @Override
                    public void onResponse(String s) {
                        LogUtils.e(s);
                        PushResponse response = PushResponse.parseObject(s, PushResponse.class);
                        int status = response.getStatus();
                        if (status == 1) {
                            LogUtils.e("==获取数据成功==");
                            Data[] data = response.getData();
                            if (data.length > 0) {
                                for (Data aData : data) {
//                                    Data dataItem = data[0];
                                    String title = aData.getTitle();
                                    String type = aData.getType();
                                    int docId = aData.getDocid();
                                    createInform(title, type, docId);
                                }
                            }
                        }
                    }
                }

        );
    }

    private void createInform(String title, String type, int docid) {
        List<Result> dataList = new ArrayList<>();
        //创建一个通知
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title)
                .setContentText("===点击查看详细内容===")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setSmallIcon(R.drawable.cnmo_logo_80_16);
//        builder.setContentText("===点击查看详细内容===");
//        builder.setWhen(System.currentTimeMillis());//设置显示时间
//        builder.setPriority(Notification.PRIORITY_DEFAULT);//设置优先级
//        builder.setOngoing(false);
//        builder.setSmallIcon(R.drawable.cnmo_logo_80_16);
        // 设置  声音等
        int defaultSet=0;
        if (MyApplication.isSound()) {
            defaultSet |= Notification.DEFAULT_SOUND;
        }
        if (MyApplication.isVibrate()) {
            defaultSet |= Notification.DEFAULT_VIBRATE;
        }
        builder.setDefaults(defaultSet);//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
        Result result = new Result();
        result.setTitle(title);
        result.setType(type);
        if (type.equals("wz")) {
            result.setDocid("" + docid);

        } else if (type.equals("tu")) {
//            Result result = new Result();
//            result.setTitle(title);
//            result.setType(type);
            result.setPicid(docid);
//            dataList.add(result);
//            Intent intent = new Intent(context, GyjOriginalDetailsActivity.class);
//            intent.putExtra("currentOffset", 0);
//            intent.putExtra("allItem", (Serializable) dataList);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 12315, intent, Intent.FILL_IN_ACTION);
//            builder.setContentIntent(pendingIntent);
        }
        dataList.add(result);
        Intent intent = new Intent(context, ArticleDetailsActivity.class);
        intent.putExtra("currentOffset", 0);
        intent.putExtra("allItem", (Serializable) dataList);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 12315, intent, Intent.FILL_IN_ACTION);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        if (MyApplication.isSound()) {
//            notification.flags |= Notification.DEFAULT_SOUND;
//        }
////        else {
////            notification.flags |= Notification.DEFAULT_SOUND;
////        }
//        if (MyApplication.isVibrate()) {
//            notification.flags = Notification.DEFAULT_VIBRATE;
//        }
////        else {
////            notification.flags |= Notification.DEFAULT_VIBRATE;
////        }
        NotificationManager systemService = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        systemService.notify(12315, notification);
    }
}
