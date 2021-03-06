package com.jason9075.womanyhackathon.manager;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by jason9075 on 2016/12/3.
 */

public class SharedPreferencesManager {

    private static final String PREF_USER_TOKEN = "PREF_USER_TOKEN";
    private static final String PREF_USER_NAME = "PREF_USER_NAME";
    private static final String PREF_UPDATE_MINS = "PREF_UPDATE_MINS";

    private final Context context;

    private static final String PREFERENCE_NAME = "DEFAULT";

    @Inject
    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public SharedPreferences getPref() {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /* 以下為getter 和setter */


    public String getUserToken() {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getString(PREF_USER_TOKEN, "");
    }

    public void setUserToken(String token) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(PREF_USER_TOKEN, token)
                .apply();
    }

    public String getUserName() {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getString(PREF_USER_NAME, "");
    }

    public void setUserName(String token) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(PREF_USER_NAME, token)
                .apply();
    }

    public int getUpdateMinus() {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getInt(PREF_UPDATE_MINS, 15);
    }

    public void setUpdateMinus(int minus) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(PREF_UPDATE_MINS, minus)
                .apply();
    }


}
