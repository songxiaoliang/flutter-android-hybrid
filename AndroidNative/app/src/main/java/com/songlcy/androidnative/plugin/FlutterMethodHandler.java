package com.songlcy.androidnative.plugin;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.songlcy.androidnative.FlutterContainerActy;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

/**
 * Flutter端主动向Native端发送消息
 * Create by Songlcy 2019-02-13
 */
public class FlutterMethodHandler implements MethodChannel.MethodCallHandler {

    // 名称可自定义，保持唯一即可
    public static final String CHANNLE_NAME = "com.songlcy.flutter.method/plugin";
    private static MethodChannel methodChannel;
    private Activity mActivity;

    private FlutterMethodHandler(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 接收Flutter传来的指令，进一步处理
     * @param methodCall
     * @param result
     */
    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        // methodCall 中携带 Flutter 传递过来的指令参数
        if(methodCall.method.equals("startActivity")) {
            // 跳转原生界面
            Intent intent = new Intent(mActivity, FlutterContainerActy.class);
            mActivity.startActivity(intent);
        } else if(methodCall.method.equals("sendMsg")) {
            Log.e("======","");
            Toast.makeText(mActivity, "收到flutter端传来的数据", Toast.LENGTH_SHORT).show();
            // 获取flutter端传来的参数"msg"对应的值： {"msg": "123"}
            String msg = methodCall.<String>argument("msg");
            // 可以使用result 的success、error方法将处理结果返回到Flutter
            result.success("Android原生端说: 我已经收到了你发送的数据," + msg);
        }
    }

    /**
     * 注册处理回调
     */
    public static void registerWith(FlutterView flutterView) {
        methodChannel = new MethodChannel(flutterView, CHANNLE_NAME);
        FlutterMethodHandler instance = new FlutterMethodHandler((Activity) flutterView.getContext());
        methodChannel.setMethodCallHandler(instance);
    }
}
