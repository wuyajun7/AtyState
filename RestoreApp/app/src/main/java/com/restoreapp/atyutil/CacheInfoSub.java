package com.restoreapp.atyutil;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by wuyajun on 16/9/23.
 */
public class CacheInfoSub implements Serializable {

    public String id;
    public LinkedHashMap<String, String> cacheData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedHashMap<String, String> getCacheData() {
        return cacheData;
    }

    public void setCacheData(LinkedHashMap<String, String> cacheData) {
        this.cacheData = cacheData;
    }
}
