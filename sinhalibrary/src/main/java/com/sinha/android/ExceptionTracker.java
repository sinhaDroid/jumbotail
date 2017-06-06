package com.sinha.android;

/**
 * Created by Sinha on 7/1/15.
 */
public class ExceptionTracker {

    public static void track(Throwable throwable) {
        try {
            throwable.printStackTrace();
            // TODO:  Crashlytics
//            Crashlytics.logException(throwable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void track(String message) {
        try {
//            Crashlytics.log(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}