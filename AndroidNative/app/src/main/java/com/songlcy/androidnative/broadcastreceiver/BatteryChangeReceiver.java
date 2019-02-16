package com.songlcy.androidnative.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import io.flutter.plugin.common.EventChannel;

/**
 * 监测手机电量 Receiver
 * create by Songlcy 2019-02-15
 */
public class BatteryChangeReceiver extends BroadcastReceiver {

    private EventChannel.EventSink eventSink;

    public BatteryChangeReceiver(EventChannel.EventSink eventSink) {
        this.eventSink = eventSink;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
            eventSink.error("BATTERY_STATUS_UNKNOWN", " Battery Charging status unavailable", null);
        } else {
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            eventSink.success(isCharging);
        }
    }
}
