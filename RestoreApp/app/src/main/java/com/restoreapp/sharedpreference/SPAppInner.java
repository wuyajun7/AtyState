package com.restoreapp.sharedpreference;

import android.content.Context;

/**
 * 应用内部设置 工具类
 */
public class SPAppInner extends AbstractSharedPreference {

    public static final String IS_SHOW_BRAND_TIP = "IS_SHOW_BRAND_TIP";
    public static final String IS_SHOW_FOLLOW_BRAND_TIP = "IS_SHOW_FOLLOW_BRAND_TIP";
    /* 是否浏览过引导页 */
    public static final String IS_CHECK_GUIDE = "IS_CHECK_GUIDE";
    /* 是否有寻车提示 */
    public static final String IS_HUNT_TIP = "IS_HUNT_TIP";
    /* 是否有报价提示 */
    public static final String IS_TENDER_TIP = "IS_TENDER_TIP";
    /* 是否有过期资源提示 */
    public static final String IS_RES_OVERDUE_TIP = "IS_RES_OVERDUE_TIP";
    /* 是否有上架资源三天未更新的最近一条的更新日期提示 */
    public static final String IS_RES_NOT_UPDATED_TIP = "IS_RES_NOT_UPDATED_TIP";
    /* 是否有系统消息 */
    public static final String IS_SYSTEM_MSG_TIP = "IS_SYSTEM_MSG_TIP";
    /* 是否弹出了升级弹窗 */
    public static final String IS_SHOW_UPGRAD_TIP = "IS_SHOW_UPGRAD_TIP";
    /* 是否弹出过 群组引导测试 弹窗 */
    public static final String IS_SHOW_GROUP_TEST_TIP = "IS_SHOW_GROUP_TEST_TIP";
    /* 上传的用户应用信息 */
    public static final String UPLOAD_USER_INFO = "UPLOAD_USER_INFO";
    /* 是否有未读交易消息 */
    public static final String UNREAD_TRADE_MESSAGES = "UNREAD_TRADE_MESSAGES";
    /* 记录用户是否有主营信息 */
    public static final String IS_HAVE_MAIN_BRAND = "IS_HAVE_MAIN_BRAND";
    public static final String TOKEN = "TOKEN";
    public static final String MOBILE = "MOBILE";
    public static final String USER_ID = "USER_ID";
    public static final String SPLASH_IMG = "SPLASH_IMG";
    /* 用户状态 */
    public static final String USER_STATE = "USER_STATE";
    public static final String IS_SILENT = "IS_SILENT";
    public static final String GROUP_MAX_MEMBERS = "GROUP_MAX_MEMBERS";
    //版本号比较 检测是否升级
    public static final String VERSION_CODE = "VERSION_CODE";
    //存储最后一次选择的分享的title
    public static final String SHARE_LAST_TITLE = "SHARE_LAST_TITLE";
    //存储最后一次选择的分享的方式
    public static final String SHARE_LAST_MODE_INDEX = "SHARE_LAST_MODE_INDEX";
    //同步到群资源
    public static final String IS_NIGHT_MODE = "IS_NIGHT_MODE";
    private static final String STORE_NAME = "nnqc_share_data";

    //自动下架资源最近一条的过期日期
    public static final String SOLD_OUT_TIME = "SOLD_OUT_TIME";

    //上架资源三天未更新的最近一条的更新日期
    public static final String NOT_UPDATED_TIME = "NOT_UPDATED_TIME";

    //是否开启Log模式
    public static final String LOG_MODE = "LOG_MODE";

    //我的订单记忆坐标
    public static final String MEMORY_INDEX = "MEMORY_INDEX";


    //不要手动进行new
    public SPAppInner(Context context) {
        super(context, STORE_NAME);
    }


}
