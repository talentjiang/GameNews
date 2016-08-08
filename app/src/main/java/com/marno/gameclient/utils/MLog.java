package com.marno.gameclient.utils;


import com.marno.gameclient.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by marno on 2016/4/18/14:33.
 * 日志辅助工具
 */
public abstract class MLog {

    public static String TAG = "Marno";

    private MLog() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 是否需要打印bug，在App的debug版本里会打印日志，而在release则不会，使用Gradle自动管理
    public static final boolean isDebug = BuildConfig.DEBUG;

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Logger.i(msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Logger.d(msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Logger.e(msg);
    }

    public static void json(String tag, String jsonStr) {
        if (isDebug)
            Logger.json(tag, jsonStr);
    }
}
