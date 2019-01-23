package com.bw.movie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bw.movie.app.MyApp;

public class SpUtil{
    private static final String file_name = "sp_name";
    private static int sp_mode = Context.MODE_PRIVATE;
    private static Context context = MyApp.context;

    public static boolean put(String key,Object value){
        SharedPreferences preferences = context.getSharedPreferences(file_name, sp_mode);
        SharedPreferences.Editor edit = preferences.edit();
        if(value instanceof String){
            if(!TextUtils.isEmpty((CharSequence) value)){
                edit.putString(key, (String) value);
            }
        }else if(value instanceof Boolean){
            edit.putBoolean(key, (Boolean) value);
        }else if (value instanceof Float){
            edit.putFloat(key, (Float) value);
        }else if (value instanceof Integer){
            edit.putInt(key, (Integer) value);
        }else {
            edit.putLong(key, (Long) value);
        }
        boolean commit = edit.commit();
        return commit;
    }
    public static String getString(String key,String defualt){
        SharedPreferences sharedPreferences = context.getSharedPreferences(file_name, sp_mode);
        return sharedPreferences.getString(key,defualt);
    }
    public static int getInt(String key, int defualt) {
        SharedPreferences preferences = context.getSharedPreferences(file_name, sp_mode);
        return preferences.getInt(key, defualt);
    }

    public static long getLong(String key, long defualt) {
        SharedPreferences preferences = context.getSharedPreferences(file_name, sp_mode);
        return preferences.getLong(key, defualt);
    }

    public static boolean getBoolean(String key, boolean defualt) {
        SharedPreferences preferences = context.getSharedPreferences(file_name, sp_mode);
        return preferences.getBoolean(key, defualt);
    }


    public static void remove() {
        SharedPreferences preferences = context.getSharedPreferences(file_name, sp_mode);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.commit();
    }
}
