package com.limingyilr.hongxin.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lanqx on 2014/5/8.
 */
public class SharedPreferencesManager {
    public static int MODE = Context.MODE_WORLD_READABLE
            + Context.MODE_WORLD_WRITEABLE;
    public static String PREFERENCE_NAME;

    public final static String AUTO_LOGIN = "AUTO_LOGIN";
    public final static String USERNAME = "USERNAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String ID = "ID";
    public final static String TYPE = "TYPE";

    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        this.context = context;
        this.MODE = Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE;
        this.PREFERENCE_NAME = "SaveSetting";
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                MODE);
        this.editor = sharedPreferences.edit();
    }

    public void saveAutoLogin(boolean isFirstRun) {
        editor.putBoolean(AUTO_LOGIN, isFirstRun);
        editor.commit();
    }

    public boolean readAutoLogin() {
        return sharedPreferences.getBoolean(AUTO_LOGIN, false);
    }

    public void saveUsername(String username) {
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public String readUsername() {
        return sharedPreferences.getString(USERNAME, "");
    }

    public void savePassword(String password) {
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public String readPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    public void saveId(String id) {
        editor.putString(ID, id);
        editor.commit();
    }

    public String readId() {
        return sharedPreferences.getString(ID, "");
    }

    public void saveType(int type) {
        editor.putInt(TYPE, type);
        editor.commit();
    }

    public int readType() {
        return sharedPreferences.getInt(TYPE, -1);
    }

    public void init(){
        saveAutoLogin(false);
        saveUsername("");
        savePassword("");
        saveId("");
        saveType(-1);
    }
}
