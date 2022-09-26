package com.example.nutechwallet.utils;

import java.util.regex.Pattern;

public class Config {

    public static final String url = "https://tht-api.nutech-integrasi.app/";
    public static final String  SQLITE_NAME            = "NUTECHWALLET";
    public static final Integer SQLITE_VERSION         = 1;
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
}
