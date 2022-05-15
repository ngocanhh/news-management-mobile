package com.group22.news_management.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.group22.news_management.model.UserModel;

public class Session {

    private SharedPreferences sharedPreferences;
    private static final String SESSION_FILE_NAME = "session";

    public Session(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SESSION_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void put(UserModel userModel){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("userId", userModel.getId());
        editor.putString("username", userModel.getUsername());
        editor.putString("fullName", userModel.getFullName());
        editor.putString("password", userModel.getPassword());
        editor.putString("avatar", userModel.getAvatarStr());
        editor.apply();
    }

    public UserModel get(){
        UserModel userModel = new UserModel();
        userModel.setId(sharedPreferences.getLong("userId", 0));
        userModel.setUsername(sharedPreferences.getString("username", ""));
        userModel.setFullName(sharedPreferences.getString("fullName", ""));
        userModel.setPassword(sharedPreferences.getString("password", ""));
        userModel.setAvatarStr(sharedPreferences.getString("avatar", ""));
        return userModel;
    }

    public void remove(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");
        editor.remove("username");
        editor.remove("fullName");
        editor.remove("password");
        editor.remove("avatar");
        editor.apply();
    }

}
