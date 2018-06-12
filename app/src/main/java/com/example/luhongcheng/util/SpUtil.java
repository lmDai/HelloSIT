package com.example.luhongcheng.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 作者：uiho_mac
 * 时间：2018/6/12
 * 描述：SharedPreference操作类
 * 版本：1.0
 * 修订历史：
 */

public class SpUtil {
    private static final String spFileName = "app";


    public static String getString(Context context, String strKey) {
        SecuritySharedPreference setPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, "");
        return result;
    }

    public static String getString(Context context, String strKey,
                                   String strDefault) {
        SecuritySharedPreference setPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, strDefault);
        return result;
    }

    public static void putString(Context context, String strKey, String strData) {
        SecuritySharedPreference activityPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString(strKey, strData);
        editor.commit();
    }

    public static Boolean getBoolean(Context context, String strKey) {
        SecuritySharedPreference setPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, false);
        return result;
    }

    public static Boolean getBoolean(Context context, String strKey,
                                     Boolean strDefault) {
        SecuritySharedPreference setPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }


    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SecuritySharedPreference activityPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        SecuritySharedPreference.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }

    public static int getInt(Context context, String strKey) {
        SecuritySharedPreference setPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, -1);
        return result;
    }

    public static int getInt(Context context, String strKey, int strDefault) {
        SecuritySharedPreference setPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, strDefault);
        return result;
    }

    public static void putInt(Context context, String strKey, int strData) {
        SecuritySharedPreference activityPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        SecuritySharedPreference.Editor editor = activityPreferences.edit();
        editor.putInt(strKey, strData);
        editor.commit();
    }

    public static long getLong(Context context, String strKey) {
        SecuritySharedPreference setPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, -1);
        return result;
    }

    public static long getLong(Context context, String strKey, long strDefault) {
        SecuritySharedPreference setPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, strDefault);
        return result;
    }

    public static void putLong(Context context, String strKey, long strData) {
        SecuritySharedPreference activityPreferences = new SecuritySharedPreference(context,
                spFileName, Context.MODE_PRIVATE);
        SecuritySharedPreference.Editor editor = activityPreferences.edit();
        editor.putLong(strKey, strData);
        editor.apply();
    }
}
