package com.example.nutechwallet.utils;



import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    public String Unique;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "NutechPref";

    private static final String SELFIE_PHOTO = "SELFIE_PHOTO";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

//    public void setSelfiePhoto(String selfie) {
//        editor.putString(SELFIE_PHOTO, selfie);
//        editor.commit();
//    }
//
//    public String getSelfiePhoto() {
//        String selfie = pref.getString(SELFIE_PHOTO,"");
//        return selfie;
//    }



}


