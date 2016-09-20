package com.restoreapp.sharedpreference;

public class SPHelper {

//    （1）用单例注册表模式来管理所有的SharedPreferences文件
//    （2）支持批量写入
//    （3）支持按版本号写入key
//    （4）对key的写入是异步，提高效率

//    AbstractSharedPreference asp = SharedPreferenceFactory.getSharedPreference(getApplicationContext(), SharedPreference1.class);
//    asp.write("key1",1);
//    asp.write("key2",true);
//    asp.write("key3",1.0f);
//    asp.write("key4",1234567890L);
//    asp.write("key5","helloworld");
//    int v1 = asp.read("key1", 0);
//    boolean v2 = asp.read("key2", false);
//    float v3 = asp.read("key3", 0f);
//    long v4 = asp.read("key4", 0L);
//    String v5 = asp.read("key5", "");
//    Log.e("test",""+v1+","+v2+","+v3+","+v4+","+v5);
//
//    Map<String, ?> all = asp.readAll();
//    Log.e("test",all.toString());
//
//    HashMap<String, Object> map = new HashMap<String, Object>();
//    map.put("key1",2);
//    asp.write("key2",false);
//    asp.write("key3",2.0f);
//    asp.write("key4",9876543210L);
//    asp.write("key5","worldhelllo");
//    asp.batchWrite(map);
//
//    all=asp.readAll();
//    Log.e("test",all.toString());

}
