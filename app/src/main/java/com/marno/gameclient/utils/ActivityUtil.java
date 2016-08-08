package com.marno.gameclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by marno on 2016/8/7/16:57.
 */
public class ActivityUtil {

    public static final String ARG_1 = "arg1";

    public static void start(Context context, Class<? extends Activity> clazz, String arg1) {
        Intent intent = new Intent(context, clazz);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_1, arg1);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
