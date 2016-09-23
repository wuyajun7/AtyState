package com.restoreapp.atyutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.restoreapp.NNApplication;
import com.restoreapp.NNMainAty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wuyajun on 16/8/2.
 * <p/>
 * APP KILL STATE UTIL
 */
public class AtyStateUtil {

    private static final String TAG = "StateUtil";
    private static final String SPLIT_KEY = "=-=";
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
     * 排序
     *
     * @param oldMap
     * @return
     */
    public static ArrayList sortMap(Map oldMap) {
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {

            @Override
            public int compare(Map.Entry<String, String> arg0, Map.Entry<String, String> arg1) {
                return arg0.getValue().compareTo(arg1.getValue());
            }
        });
        return list;
    }

    /**
     * 检测缓存数据时间
     * 目前缓存保留 60 分钟
     *
     * @param lastTime
     * @return
     */
    public boolean checkCacheTime(long lastTime) {
        long time = /*60l * */60l * 1000l;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > time) {
            return true;
        }
        return false;
    }

    /**
     * 主页 判断是否有目标Activity ，有判断目标Activity是不是自己，不是则跳转
     *
     * @param activity
     */
    public void jumpSavedActivity(final Activity activity) {
        try {
            mSavedAtyJson = NNApplication.asp.read(SAVED_ATY_S, "");
            if (!TextUtils.isEmpty(mSavedAtyJson)) {
                CacheInfo cacheInfo = JSONObject.parseObject(mSavedAtyJson, CacheInfo.class);

                if (cacheInfo != null) {
                    if (!checkCacheTime(cacheInfo.lastTime) && !TextUtils.isEmpty(cacheInfo.cacheData)) {
                        mSavedAtyList = JSON.parseObject(cacheInfo.cacheData, new TypeReference<LinkedHashMap<String, String>>() {
                        }, Feature.OrderedField);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String targetAty;
                                String targetAtyParamJson;
                                Map<String, Object> targetAtyParam = null;
                                Class targetClass;

                                ArrayList<Map.Entry<String, String>> list = sortMap(mSavedAtyList);
                                Map.Entry<String, String> item;
                                for (int i = 0; i < list.size(); i++) {
                                    item = list.get(i);

                                    targetAty = item.getKey();
                                    targetAtyParamJson = item.getValue();
                                    if (!TextUtils.isEmpty(targetAtyParamJson)) {
                                        String[] params = targetAtyParamJson.split(SPLIT_KEY);
                                        if (params != null && params.length > 1) {
                                            targetAtyParamJson = params[1];
                                            targetAtyParam = JSON.parseObject(targetAtyParamJson);
                                        }
                                    }

                                    if (targetAty != null) {
                                        try {
                                            if (!NNMainAty.class.getCanonicalName().equals(targetAty)) {
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
                                NNApplication.asp.write(SAVED_ATY_S, "");
                            }
                        }, 500);
                    } else {//数据过期
                        NNApplication.asp.write(SAVED_ATY_S, "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            NNApplication.asp.write(SAVED_ATY_S, "");
        }
    }

    private LinkedHashMap<String, String> mSaveAtyList;

    /**
     * 每个Activity onSaveInstanceState 时 保存目标 Activity
     *
     * @param targetAty
     */
    public void saveTargetData(String targetAty, String targetAtyParam) {
        String allAtyJson = NNApplication.asp.read(SAVED_ATY_S, "");

        if (!TextUtils.isEmpty(allAtyJson)) {
            CacheInfo cacheInfo = JSONObject.parseObject(allAtyJson, CacheInfo.class);
            if (cacheInfo != null && !TextUtils.isEmpty(cacheInfo.cacheData)) {
                mSaveAtyList = JSON.parseObject(cacheInfo.cacheData, new TypeReference<LinkedHashMap<String, String>>() {
                }, Feature.OrderedField);
            }
        }

        if (mSaveAtyList == null) {
            mSaveAtyList = new LinkedHashMap<>();
            mSaveAtyList.put(targetAty, mSaveAtyList.size() + SPLIT_KEY + (targetAtyParam == null ? "" : targetAtyParam));
        } else {
            if (!mSaveAtyList.containsKey(targetAty)) {
                mSaveAtyList.put(targetAty, mSaveAtyList.size() + SPLIT_KEY + (targetAtyParam == null ? "" : targetAtyParam));
            }
        }

        CacheInfo cacheInfo = getCacheInfo();
        cacheInfo.setLastTime(System.currentTimeMillis());
        cacheInfo.setCacheData(JSONObject.toJSON(mSaveAtyList).toString());

        String cacheData = JSON.toJSONString(cacheInfo);
        NNApplication.asp.write(SAVED_ATY_S, cacheData);
    }

    private CacheInfo curCacheInfo;

    private CacheInfo getCacheInfo() {
        if (curCacheInfo == null) {
            curCacheInfo = new CacheInfo();
        } else {
            curCacheInfo.clear();
        }
        return curCacheInfo;
    }

    private LinkedHashMap<String, String> mRemoveAtyList;

    /**
     * 每个Activity onCreate时 清除 目标 Activity 主界面不清除
     *
     * @param targetAty
     */
    public void removeTargetData(String targetAty) {
        if (!NNMainAty.class.getCanonicalName().equals(targetAty)) {

            String allAtyJson = NNApplication.asp.read(SAVED_ATY_S, "");
            if (!TextUtils.isEmpty(allAtyJson)) {
                CacheInfo cacheInfo = JSONObject.parseObject(allAtyJson, CacheInfo.class);
                if (cacheInfo != null && !TextUtils.isEmpty(cacheInfo.cacheData)) {
                    mRemoveAtyList = JSON.parseObject(cacheInfo.cacheData, new TypeReference<LinkedHashMap<String, String>>() {
                    }, Feature.OrderedField);
                }
            }

            if (mRemoveAtyList != null && mRemoveAtyList.size() > 0) {
                mRemoveAtyList.remove(targetAty);

                CacheInfo cacheInfo = getCacheInfo();
                cacheInfo.setLastTime(System.currentTimeMillis());
                cacheInfo.setCacheData(JSONObject.toJSON(mRemoveAtyList).toString());

                String cacheData = JSON.toJSONString(cacheInfo);
                NNApplication.asp.write(SAVED_ATY_S, cacheData);
            }
        }
    }

    public void clearAllData() {
        NNApplication.asp.write(SAVED_ATY_S, "");
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

    /**
     * 设置目标Activity 参数
     *
     * @param mIntent
     * @param entry
     */
    private void setIntent(Intent mIntent, Map.Entry<String, Object> entry) {
        Object param = entry.getValue();
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
