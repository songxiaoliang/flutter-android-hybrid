package com.songlcy.androidnative;

import android.os.Bundle;

import com.songlcy.androidnative.plugin.FlutterEventHandler;
import com.songlcy.androidnative.plugin.FlutterMethodHandler;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterMain;
import io.flutter.view.FlutterView;

public class FlutterActy extends FlutterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FlutterMain.startInitialization(this);
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
        registerPlugins(getFlutterView());
    }

    private void registerPlugins(FlutterView flutterView) {
        FlutterMethodHandler.registerWith(flutterView);
        FlutterEventHandler.registerWith(flutterView);
    }
}
