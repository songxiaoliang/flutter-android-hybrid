import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FlutterInAndroid',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter'),
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

  int eventVal = 0;
  String result = '';
  StreamSubscription _streamSubscription;
  static const plugin = const MethodChannel('com.songlcy.flutter.jump/plugin');
  static const eventPlugin = const EventChannel('com.songlcy.flutter.event/plugin');

  @override
  void initState() {
    super.initState();
    _streamSubscription = eventPlugin.receiveBroadcastStream()
        .listen(_onData, onError: _onError, onDone: _onDone, cancelOnError: true);
  }

  void _onData(event) {
    // 接收数据
    setState((){
      eventVal = event;
    });
  }

  void _onError() {
    // 发生错误时被回调
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
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              widget.title,
            ),
            Text(
              result,
            ),
            MaterialButton(
              color: Theme.of(context).primaryColor,
              child: Text("调用原生端", style: TextStyle(color: Colors.white)),
              onPressed: () => onPressCallback(),
            ),
          ],
        ),
      ),
    );
  }

  Future<Null> onPressCallback([dynamic arguments]) async {
   await plugin.invokeMethod('123',arguments);
  }
}
