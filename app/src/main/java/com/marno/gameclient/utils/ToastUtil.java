package com.marno.gameclient.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Marnon on 2016/1/19.
 * 统一管理用到的Toast等等
 */
public class ToastUtil {

    private static Toast mToast;
    private static Context mContext;

    public static void initToast(Context context) {
        mContext = context;
    }

    /**
     * 显示自定义的Toast
     *
     * @param content
     */
    public static void show(String content) {
        int duration = Toast.LENGTH_SHORT;
        if (content.length() > 10) duration = Toast.LENGTH_LONG;

        if (mToast == null) {
            mToast = Toast.makeText(mContext, content, duration);
        } else {
            mToast.setText(content);
            mToast.setDuration(duration);
        }
        mToast.show();
    }


    /**
     * 取消Toast
     */
    public static void cancleToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
