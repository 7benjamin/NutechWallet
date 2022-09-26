package com.example.nutechwallet.utils;

import java.text.DecimalFormat;

public final class CurrencyUtils {

    public static String thousandFormat(Double number) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(number);
    }

}