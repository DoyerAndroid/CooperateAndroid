package com.compain.libraryshare.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.compain.libraryshare.LibraryApplication;

/**
 * Created by jie.du on 17/1/9.
 */

public class AppCache {
    private static final String PREF_OPEN = "library_app";
    private static final String USER_ID = "user_id";
    private static final String QQ_ID = "qq_id";
    public static String bomb_id = "96fdacceaefd117c5db89c46935a76a2";

    private static SharedPreferences getSharedPreference() {
        return LibraryApplication.getApplication().getSharedPreferences(PREF_OPEN, Context.MODE_PRIVATE);
    }

    public static void putUserId(String value) {
        SharedPreferences preferences = getSharedPreference();
        preferences.edit().putString(USER_ID, value).apply();
    }

    public static String getUserId(String defaultValue) {
        SharedPreferences preferences = getSharedPreference();
        return preferences.getString(USER_ID, defaultValue);
    }

    public static void putQqId(String value) {
        SharedPreferences preferences = getSharedPreference();
        preferences.edit().putString(QQ_ID, value).apply();
    }

    public static String getQqId(String defaultValue) {
        SharedPreferences preferences = getSharedPreference();
        return preferences.getString(QQ_ID, defaultValue);
    }
}
