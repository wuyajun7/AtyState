package com.restoreapp.sharedpreference;

import android.content.Context;


/**
 * 设置数据 工具类
 */
public class SPAppSetting extends AbstractSharedPreference {

    private static final String STORE_NAME = "nnqc_setting";

    //不要手动进行new
    public SPAppSetting(Context context) {
        super(context, STORE_NAME);
    }

}
