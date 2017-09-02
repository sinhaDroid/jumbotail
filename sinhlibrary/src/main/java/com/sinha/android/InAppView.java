package com.sinha.android;

/**
 * Created by Sinha on 16/10/14.
 */
public interface InAppView extends View {

    void showInAppMessage(int messageRes);

    void showMessage(int messageRes, int positiveButtonRes, android.view.View.OnClickListener positiveClickListener);

    void showMessage(int messageRes, int positiveButtonRes, android.view.View.OnClickListener positiveClickListener,
                     int negativeButtonRes, android.view.View.OnClickListener negativeClickListener);
}