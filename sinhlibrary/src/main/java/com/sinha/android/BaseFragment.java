package com.sinha.android;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sinha 16/10/14.
 */
public class BaseFragment extends Fragment {

    public Context getContext(){
        return getActivity();
    }

    public void showMessage(String message) {
        View view = getActivityView();
        if (null != view) {
            view.showMessage(message);
        }
    }

    public void showMessage(int messageRes) {
        View view = getActivityView();
        if (null != view) {
            view.showMessage(messageRes);
        }
    }

    public void showMessage(String message, int duration) {
        View view = getActivityView();
        if (null != view) {
            view.showMessage(message, duration);
        }
    }

    public void showMessage(int messageRes, int duration) {
        View view = getActivityView();
        if (null != view) {
            view.showMessage(messageRes, duration);
        }
    }

    public boolean isMessageShowing() {
        View view = getActivityView();
        if (null != view) {
            return view.isMessageShowing();
        }
        return false;
    }

    public void showProgressbar() {
        getActivityView().showProgressbar();
    }

    public void updateProgressbar() {
    }

    public void dismissProgressbar() {
        if(getActivityView() != null)
            getActivityView().dismissProgressbar();
    }

    public void showSoftKeyboard(android.view.View view) {
        getActivityView().showSoftKeyboard(view);
    }

    public void hideSoftKeyboard() {
        getActivityView().hideSoftKeyboard();
    }

    public LayoutInflater getLayoutInflater() {
        if(getActivityView() != null) {
            return getActivityView().getLayoutInflater();
        }
        return null;
    }

    public String getTrimmedText(TextView textView) {
        return getActivityView().getTrimmedText(textView);
    }

    private View getActivityView() {
        return (View) getActivity();
    }

    public android.view.View findViewById(int id) {
        return getView().findViewById(id);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if(null == getParentFragment()) {
            super.startActivityForResult(intent, requestCode);
        } else {
            getParentFragment().startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //noinspection RestrictedApi
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if(null != fragment) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }
}