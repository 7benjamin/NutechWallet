package com.example.nutechwallet.utils;

import android.content.Context;

public class NumberSeparator {

    private static String dot = ".";
    private static String comma = ",";
    private static Context context;


    public NumberSeparator(Context context){
        super();
        this.context = context;
    }

    public static String replaceComma(String input) {
        return input.replace(",", ".");
    }






}
