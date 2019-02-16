import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';
import 'dart:ui';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FlutterInAndroid',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter界面'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  Object eventVal;
  String result = '';
  static const methodPlugin = const MethodChannel('com.songlcy.flutter.method/plugin');
  static const eventPlugin = const EventChannel('com.songlcy.flutter.event/plugin');
  
  StreamSubscription _streamSubscription;

  @override
  void initState() {
    super.initState();
    _streamSubscription = eventPlugin.receiveBroadcastStream()
        .listen(_onData, onError: _onError, onDone: _onDone, cancelOnError: true);
  }

  void _onData(Object event) {
    // 接收数据
    setState(() {
      eventVal = event;
    });
  }

  void _onError(Object error) {
    // 发生错误时被回调
    setState((){
      eventVal = "错误";
    });
  }

  void _onDone() {
    //结束时调用
  }

  @override
  void dispose() {
    super.dispose();
    if(_streamSubscription != null) {
      _streamSubscription.cancel();
    }
  }

  @override
  Widget build(BuildContext context) {
    switch(window.defaultRouteName) {
      case "flutterView":
       return Scaffold(
          body: Center(
            child: Text('这是Flutter 文本组件')
          )
       );
      default:
        return Scaffold(
          body: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Text(
                  widget.title,
                ),
                Center(
                  child:Text(
                    result,
                  )
                ),
                Text(
                  '接收到Native端传来的数据：$eventVal'
                ),
                MaterialButton(
                  color: Theme.of(context).primaryColor,
                  child: Text("跳转到原生界面", style: TextStyle(color: Colors.white)),
                  onPressed: () => onPressCallback('startActivity'),
                ),
                MaterialButton(
                  color: Theme.of(context).primaryColor,
                  child: Text("向原生端发送消息", style: TextStyle(color: Colors.white)),
                  onPressed: () => onPressCallback('sendMsg'),
                ),
                MaterialButton(
                  color: Theme.of(context).primaryColor,
                  child: Text("向原生端发送消息, 并传递参数", style: TextStyle(color: Colors.white)),
                  onPressed: () => onPressCallback('sendMsg', {"msg":"Hello, Android"}),
                ),
              ],
            ),
          ),
        );
    }
  }

  Future<Null> onPressCallback(String type, [dynamic arguments]) async {
    String callbackResult = await methodPlugin.invokeMethod(type, arguments);
    if(callbackResult != null) {
      setState(() {
        result = callbackResult;
      });
    }
  }
}
