package com.cresset.asimjofaofficial.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cresset on 12/06/2017.
 */

public class UserSessionManager {
    //Logcat initialization
    private static String TAG = UserSessionManager.class.getSimpleName();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Context context;

    //prefs mode

    int PRIVATE_MODE = 0;

    //shared preferences file name
    private static final  String PREF_NAME = "AsimJofaLofin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    //create constructor
    public UserSessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        //commit change
        editor.commit();

    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }

}
