package com.songlcy.androidnative;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import io.flutter.facade.Flutter;
import io.flutter.view.FlutterView;

/**
 * 此 Activity 中向 Flutter 端发送消息
 * create by Songlcy 2019-02-15
 */
public class FlutterContainerActy extends AppCompatActivity {

    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter);
        // 通过Flutter.createView创建FlutterView组件方式
        FlutterView flutterView = Flutter.createView(this, getLifecycle(), "flutterView");
        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(flutterView, layoutParams);
    }
}
