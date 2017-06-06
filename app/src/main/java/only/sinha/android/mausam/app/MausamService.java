package only.sinha.android.mausam.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sinha.android.Log;

import only.sinha.android.mausam.app.module.model.request.LocalWeatherRequest;
import only.sinha.android.mausam.app.module.model.response.localweather.LocalWeatherResponse;
import only.sinha.android.mausam.app.module.offline.LocalWeatherDataHandler;
import only.sinha.android.mausam.app.module.offline.MausamDataHandler;
import only.sinha.android.mausam.app.webservice.MausamCallBack;
import only.sinha.android.mausam.app.webservice.MyWebService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by deepanshu on 5/6/17.
 */

public class MausamService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Mausam", "onReceive");
        callWeatherApi(intent.getStringExtra(Constants.BundleKeys.CITY_KEY));
    }

    private void callWeatherApi(String cityName) {
        if (Mausam.getInstance().hasNetworkConnection()) {

            MyWebService.getInstance().callLocalWeatherApi(
                    new LocalWeatherRequest.Builder()
                            .setQ(cityName)
                            .setNum_of_days(9)
                            .setTp("1")
                            .setShowlocaltime("yes")
                            .build()
            ).enqueue(new MausamCallBack<LocalWeatherResponse>() {
                @Override
                public void onResponse(Call<LocalWeatherResponse> call, Response<LocalWeatherResponse> response) {

                    if (response.isSuccessful())
                        handleResponse(response.body());
                }
            });
        }
    }

    private void handleResponse(LocalWeatherResponse response) {

        LocalWeatherDataHandler.getInstance().saveWeatherByCityName(MausamDataHandler.getInstance().getCurrentCityName(), response);
        Log.d("Mausam", "stored in db");
    }
}
