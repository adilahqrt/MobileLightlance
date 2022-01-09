package com.lightlance.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lightlance.app.api.model.User;

public class PreferencesHelper {

    private static PreferencesHelper instance = null;

    private Gson gson = null;

    protected PreferencesHelper() {
        if (gson == null) gson = new Gson();
    }

    public static PreferencesHelper getInstance() {
        if (instance == null) instance = new PreferencesHelper();

        return instance;
    }

    private SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getPrefEditor(Context context) {
        SharedPreferences preferences = getPreferences(context);

        return preferences.edit();
    }

    public void saveItem(Context context, String key, Object value) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key can't be empty!");
        }

        SharedPreferences.Editor editor = getPrefEditor(context);

        if (value instanceof String) {
            editor.putString(key, (String)value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int)value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean)value);
        } else {
            String strItem = gson.toJson(value);
            editor.putString(key, strItem);
        }

        editor.commit();
    }

    public Object getItem(Context context, String key, Object defaultValue) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key can't be empty!");
        }

        if (defaultValue instanceof String) {
            return getPreferences(context).getString(key, defaultValue.toString());
        } else if (defaultValue instanceof Integer) {
            return getPreferences(context).getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return getPreferences(context).getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof User){
            String json = getPreferences(context).getString(key, defaultValue.toString());
            User obj = gson.fromJson(json, User.class);
            return obj;
        }

        return null;
    }
}
