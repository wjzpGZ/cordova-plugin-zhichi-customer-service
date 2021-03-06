package com.wjzp.cordova.sobot;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhoujianghua on 2015/8/25.
 */
public class SobotPreferences {
    private static Context context;
    private final static String KEY_YX_USER_ID = "YSF_FOREIGN_ID";
    private final static String KEY_YX_USER_NAME = "YSF_FOREIGN_NAME";
    private final static String KEY_YX_USER_AVATAR = "KEY_FOREIGN_AVATAR";
    public final static String KEY_USER_INFO = "key_user_info";


    public static void init(Context context) {
        SobotPreferences.context = context.getApplicationContext();
    }

    public static String getYsfUserId() {
        return getString(KEY_YX_USER_ID);
    }

    public static void saveYsfUserId(String userId) {
        saveString(KEY_YX_USER_ID, userId);
    }

    public static String getYsfUserName() {
        return getString(KEY_YX_USER_NAME);
    }

    public static String getYsfUserAvatar() {
        return getString(KEY_YX_USER_AVATAR);
    }

    public static void saveYsfUserName(String userId) {
        saveString(KEY_YX_USER_NAME, userId);
    }

    public static void saveYsfUserAvatar(String avatar) {
        saveString(KEY_YX_USER_AVATAR, avatar);
    }

    private static boolean getBoolean(String key, boolean value) {
        return getSharedPreferences().getBoolean(key, value);
    }

    private static void saveBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static void saveString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    private static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("Sobot.Plugin", Context.MODE_PRIVATE);
    }
}
