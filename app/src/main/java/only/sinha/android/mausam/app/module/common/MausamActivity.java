package only.sinha.android.mausam.app.module.common;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.sinha.android.BaseActivity;
import com.sinha.android.ExceptionTracker;

import butterknife.ButterKnife;
import only.sinha.android.mausam.app.R;

public abstract class MausamActivity extends BaseActivity {

    private static final String NORMAL_UPDATE = "Normal";

    private static final String FORCE_UPDATE = "Force";

    protected abstract int getContentView();

    protected abstract void onViewCreated(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        ButterKnife.bind(this);

        onViewCreated(savedInstanceState);
    }

    private void navigateToPlayStore() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.play_store_url))));
        } catch (ActivityNotFoundException e) {
            ExceptionTracker.track(e);
        }
    }
}