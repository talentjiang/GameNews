package com.marno.gameclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by marno on 2016/3/16/16:16.
 * 封装的SharedPreferences工具类
 */
public class SPUtil {
    //*************************SP文件**********************************/
    public static final String F_SETTINGS = "settings";//软件配置
    public static final String F_USERINFO = "userinfo";//用户信息
    public static final String F_VISITORINFO = "visitorinfo";//用户信息
    public static final String F_COOKIE = "cookie";//cookie
    public static final String F_DATA = "data";//缓存小量数据

    //*************************记录用户信息**********************************/
    public static final String IS_LOGIN = "isLogin";//用户是否登录
    public static final String IS_FIRST_RUN = "isFirstRun";//是否是第一次运行App
    public static final String USER_ID = "userId";//id
    public static final String USER_NICKNAME = "userNickName";//昵称
    public static final String USER_ACCOUNT = "userAccount";//账号
    public static final String USER_LOGO_URL = "userLogoUrl";//头像
    public static final String USER_GENDER = "userGender";//性别(0是女或1是男)
    public static final String USER_CITY = "userCity";//城市
    public static final String USER_AGE = "userAge";//年龄
    public static final String USER_BIRTHDAY = "userBirthday";//生日(yyyy-MM-dd HH:mm:ss)
    public static final String USER_COUNTRY = "userCountry";//国家
    public static final String USER_MONEY = "userMoney";//用户money
    public static final String USER_EMAIL = "userEmail";//邮箱
    public static final String USER_ADDRESS = "userAddress";//地址
    public static final String USER_TYPE = "userType";//账号类型（是否为三方登录）
    public static final String USER_FOLLOW = "userFollow";//关注
    public static final String USER_FOLLOWER = "userFollower";//粉丝
    public static final String USER_ACCESS_TYPE = "accessType";//用户权限
    public static final String USER_RONG_TOKEN = "rongToken";//融云token


    //*************************保存cookie信息**********************************/
    public static final String COOKIE_NAME = "name";//name
    public static final String COOKIE_VALUE = "value";//value
    public static final String COOKIE_DOMAIN = "domain";//域名

    //*************************保存其他信息**********************************/
    public static final String REFUND_REASON = "refund_reason";//退款原因
    public static final String ORDER_COMMENT = "order_comment";//订单评价
    public static final String VERSION_NAME = "versionName";
    public static final String MODULE_DATA = "module_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(String fileName, Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String fileName, Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(F_SETTINGS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clearAll(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(F_SETTINGS,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
