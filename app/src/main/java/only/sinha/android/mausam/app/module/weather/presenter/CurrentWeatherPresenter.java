package only.sinha.android.mausam.app.module.weather.presenter;

import android.os.Bundle;

/**
 * Created by deepanshu on 2/6/17.
 */

public interface CurrentWeatherPresenter {
    void onViewCreated(Bundle bundle);

    void onRefresh();

    void refreshAllViews();
}
