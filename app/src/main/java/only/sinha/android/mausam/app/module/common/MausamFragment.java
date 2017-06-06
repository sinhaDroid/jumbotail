package only.sinha.android.mausam.app.module.common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinha.android.BaseFragment;

import butterknife.ButterKnife;

public abstract class MausamFragment extends BaseFragment {

    protected abstract int getInflater();

    protected abstract void onViewCreated(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getInflater(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onViewCreated(savedInstanceState);
    }
}