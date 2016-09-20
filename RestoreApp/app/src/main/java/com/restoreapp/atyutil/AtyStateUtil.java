package com.restoreapp.atyutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.restoreapp.MainActivity;
import com.restoreapp.MyApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by wuyajun on 16/8/2.
 * <p/>
 * APP KILL STATE UTIL
 */
public class AtyStateUtil {

    private static final String TAG = "StateUtil";
    private static final String SAVED_ATY_S = "SAVED_ATY_S";//目标Activity列表|参数

    private Intent mIntent;

    private String mSavedAtyJson;
    private LinkedHashMap<String, String> mSavedAtyList;


    private AtyStateUtil() {
    }

    private static AtyStateUtil single = null;

    public static AtyStateUtil getInstance() {
        if (single == null) {
            single = new AtyStateUtil();
        }
        return single;
    }

    /**
     * 主页 判断是否有目标Activity ，有判断目标Activity是不是自己，不是则跳转
     *
     * @param activity
     */
    public void jumpSavedActivity(final Activity activity) {
        mSavedAtyJson = MyApp.asp.read(SAVED_ATY_S, "");
        if (!TextUtils.isEmpty(mSavedAtyJson)) {
            mSavedAtyList = JSON.parseObject(mSavedAtyJson, new TypeReference<LinkedHashMap<String, String>>() {
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String targetAty;
                    String targetAtyParamJson;
                    Map<String, Object> targetAtyParam;
                    Class targetClass;

                    ListIterator<Map.Entry<String, String>> listIterator = new ArrayList<>(mSavedAtyList.entrySet())
                            .listIterator(mSavedAtyList.size());
                    while (listIterator.hasPrevious()) {
                        Map.Entry<String, String> atyStateEntry = listIterator.previous();

                        targetAty = atyStateEntry.getKey();
                        targetAtyParamJson = atyStateEntry.getValue();
                        targetAtyParam = JSON.parseObject(targetAtyParamJson);

                        if (targetAty != null) {
                            try {
                                if (!MainActivity.class.getCanonicalName().equals(targetAty)) {
                                    targetClass = Class.forName(targetAty);
                                    mIntent = new Intent(activity, targetClass);
                                    if (targetAtyParam != null) {//目标Activity 参数不为空则设置参数
                                        for (Map.Entry<String, Object> entry : targetAtyParam.entrySet()) {
                                            setIntent(mIntent, entry);
                                        }
                                    }
                                    activity.startActivity(mIntent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    MyApp.asp.write(SAVED_ATY_S, "");
                }
            }, 300);
        }
    }

    private LinkedHashMap<String, String> mSaveAtyList;

    /**
     * 每个Activity onSaveInstanceState 时 保存目标 Activity
     *
     * @param targetAty
     */
    public void saveTargetData(String targetAty, String targetAtyParam) {
        Log.i(TAG, "SAVE_ATY : " + targetAty);
        String allAtyJson = MyApp.asp.read(SAVED_ATY_S, "");

        if (!TextUtils.isEmpty(allAtyJson)) {
            mSaveAtyList = JSON.parseObject(allAtyJson, new TypeReference<LinkedHashMap<String, String>>() {
            });
        }

        if (mSaveAtyList == null) {
            mSaveAtyList = new LinkedHashMap<>();
            mSaveAtyList.put(targetAty, targetAtyParam == null ? "" : targetAtyParam);
        } else {
            if (!mSaveAtyList.containsKey(targetAty)) {
                mSaveAtyList.put(targetAty, targetAtyParam == null ? "" : targetAtyParam);
            }
        }

        MyApp.asp.write(SAVED_ATY_S, JSONObject.toJSON(mSaveAtyList).toString());
    }

    private LinkedHashMap<String, String> mRemoveAtyList;

    /**
     * 每个Activity onCreate时 清除 目标 Activity 主界面不清除
     *
     * @param targetAty
     */
    public void removeTargetData(String targetAty) {
        Log.i(TAG, "SAVE_ATY : doTargetActivityEmpty");
        if (!MainActivity.class.getCanonicalName().equals(targetAty)) {
            String allAtyJson = MyApp.asp.read(SAVED_ATY_S, "");
            if (!TextUtils.isEmpty(allAtyJson)) {
                mRemoveAtyList = JSON.parseObject(allAtyJson, new TypeReference<LinkedHashMap<String, String>>() {
                });
                if (mRemoveAtyList != null && mRemoveAtyList.size() > 0) {
                    mRemoveAtyList.remove(targetAty);
                }
                MyApp.asp.write(SAVED_ATY_S, JSONObject.toJSON(mRemoveAtyList).toString());
            }
        }
    }

    public void clearAllData() {
        MyApp.asp.write(SAVED_ATY_S, "");
    }

    private Map<String, Object> atyParamMapTemp;

    /**
     * 目标Activity 参数转换成JSON 方便存储
     *
     * @param key
     * @param value
     * @return
     */
    public String getTargetParam(String[] key, Object[] value) {
        if (atyParamMapTemp == null) {
            atyParamMapTemp = new HashMap<>();
        } else {
            atyParamMapTemp.clear();
        }
        if (key != null && value != null && key.length == value.length && key.length != 0) {
            for (int i = 0; i < key.length; i++) {
                atyParamMapTemp.put(key[i], value[i]);
            }
        }
        return JSONObject.toJSON(atyParamMapTemp).toString();
    }

    private Object param;

    /**
     * 设置目标Activity 参数
     *
     * @param mIntent
     * @param entry
     */
    private void setIntent(Intent mIntent, Map.Entry<String, Object> entry) {
        param = entry.getValue();
        if (param instanceof Integer) {
            int value = ((Integer) param).intValue();
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof String) {
            String value = (String) param;
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof Double) {
            double value = ((Double) param).doubleValue();
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof Float) {
            float value = ((Float) param).floatValue();
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof Long) {
            long value = ((Long) param).longValue();
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof Boolean) {
            boolean value = ((Boolean) param).booleanValue();
            mIntent.putExtra(entry.getKey(), value);
        }
    }
}
