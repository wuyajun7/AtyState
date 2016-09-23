package com.restoreapp.atyutil;

import java.io.Serializable;

/**
 * Created by wuyajun on 16/9/23.
 */
public class CacheInfo implements Serializable {

    public String id;          //排序使用
    public long lastTime;      //过期时间判断
    public String cacheData;   //缓存的数据

    public CacheInfo() {
        super();
    }

    public CacheInfo(String id, String cacheData) {
        this.id = id;
        this.cacheData = cacheData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getCacheData() {
        return cacheData;
    }

    public void setCacheData(String cacheData) {
        this.cacheData = cacheData;
    }

    public void clear() {
        id = "";
        lastTime = 0;
        cacheData = "";
    }
}
