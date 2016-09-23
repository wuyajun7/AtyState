package com.restoreapp.atyutil;

import java.io.Serializable;

/**
 * Created by wuyajun on 16/9/23.
 */
public class CacheInfo implements Serializable {

    public long lastTime;
    public String cacheData;

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
        lastTime = 0;
        cacheData = "";
    }
}
