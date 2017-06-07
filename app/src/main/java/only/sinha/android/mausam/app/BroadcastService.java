package only.sinha.android.mausam.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import only.sinha.android.mausam.app.webservice.MyWebService;

/**
 * Created by deepanshu on 5/6/17.
 */

public class BroadcastService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String cityKey = intent.getStringExtra(Constants.BundleKeys.CITY_KEY);

        if (null != cityKey) {
            callWeatherApi(cityKey);
        }
    }

    private void callWeatherApi(String cityName) {
        if (Mausam.getInstance().hasNetworkConnection()) {

            MyWebService.getInstance().callWeatherApi(Constants.IntentActions.ACTION_SERVICE, cityName);
        }
    }
}
