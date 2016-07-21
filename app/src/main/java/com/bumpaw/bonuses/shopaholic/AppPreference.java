package com.bumpaw.bonuses.shopaholic;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 20/07/2016.
 */
public class AppPreference {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String PREFS_NAME = "ShopaholicAppPrefs";
    private String KEYS_USERNAME = "USERNAME";
    private String KEYS_USERID = "USERID";

    public AppPreference(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUsername(String username){
        editor.putString(KEYS_USERNAME, username);
        editor.commit();
    }

    public String getUsername(){
        return sharedPreferences.getString(KEYS_USERNAME,"");
    }

    public void setUserid(String userid){
        editor.putString(KEYS_USERID, userid);
        editor.commit();
    }

    public String getUserid(){
        return sharedPreferences.getString(KEYS_USERID,"");
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }
}
