package com.songlcy.androidnative.plugin;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.songlcy.androidnative.broadcastreceiver.BatteryChangeReceiver;

import java.util.Observable;

import io.flutter.plugin.common.EventChannel;
import io.flutter.view.FlutterView;

/**
 * Native端主动向Flutter发送消息
 */
public class FlutterEventHandler implements EventChannel.StreamHandler {

    public static final String CHANNEL_NAME = "com.songlcy.flutter.event/plugin";
    private static EventChannel eventChannel;
    private static Activity mActivity;
    private BatteryChangeReceiver batteryChangeReceiver;
    private FlutterEventHandler(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        // Native端主动被触发的情况，我们可以借助监听系统广播来实现，例如手机电量
        batteryChangeReceiver = new BatteryChangeReceiver(eventSink);
        mActivity.registerReceiver(batteryChangeReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        eventSink.success("123");
    }

    @Override
    public void onCancel(Object o) {
        mActivity.unregisterReceiver(batteryChangeReceiver);
        batteryChangeReceiver = null;
        Log.i("FlutterEventHandler", "FlutterEventHandler:onCancel");
    }

    /**
     * 注册
     */
    public static void registerWith(FlutterView flutterView) {
        eventChannel = new EventChannel(flutterView, CHANNEL_NAME);
        FlutterEventHandler instance = new FlutterEventHandler((Activity)flutterView.getContext());
        eventChannel.setStreamHandler(instance);
    }
}
