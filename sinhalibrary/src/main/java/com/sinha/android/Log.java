package com.sinha.android;

public class Log {

    public static void d(String name, String value) {
        if(null != name && null != value)
            android.util.Log.d(name, value);
    }

    public static void i(String name, String value) {
        if(null != name && null != value)
            android.util.Log.i(name,value);
    }

    public static void e(String name, String value) {
        if(null != name && null != value)
            android.util.Log.e(name, value);
    }

    public static void e(String name, String value, Throwable throwable) {
        if(null != name && null != value)
            android.util.Log.e(name, value, throwable);
    }

    public static void w(String name, String value) {
        if(null != name && null != value)
            android.util.Log.w(name, value);
    }
}