package com.sinha.android;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by Jeeva on 16/10/14.
 */
public interface View {

    FragmentActivity getActivity();

    Context getContext();

    void showMessage(String message);

    void showMessage(int messageRes);

    void showMessage(String message, int duration);

    void showMessage(int messageRes, int duration);

    boolean isMessageShowing();

    void showProgressbar();

    void updateProgressbar();

    void dismissProgressbar();

    void showSoftKeyboard(android.view.View view);

    void hideSoftKeyboard();

    LayoutInflater getLayoutInflater();

    String getTrimmedText(TextView textView);
}