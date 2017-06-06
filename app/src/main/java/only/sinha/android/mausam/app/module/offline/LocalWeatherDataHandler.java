package only.sinha.android.mausam.app.module.offline;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

import only.sinha.android.mausam.app.Constants;
import only.sinha.android.mausam.app.module.common.AbstractDataHandler;
import only.sinha.android.mausam.app.module.model.response.localweather.LocalWeatherResponse;
import only.sinha.android.mausam.app.webservice.MyWebService;

/**
 * Created by deepanshu on 3/6/17.
 */

public class LocalWeatherDataHandler extends AbstractDataHandler {

    private SharedPreferences mSharedPreferences;

    private static final LocalWeatherDataHandler ourInstance = new LocalWeatherDataHandler();

    public static LocalWeatherDataHandler getInstance() {
        return ourInstance;
    }

    private LocalWeatherDataHandler() {
    }

    public void init(Context context) {
        mSharedPreferences = context.getSharedPreferences(LocalWeatherDataHandler.class.getCanonicalName(), Context.MODE_PRIVATE);

    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public void saveWeatherByCityName(String cityName, LocalWeatherResponse response) {
        Map<String, LocalWeatherResponse> weatherByCityName = getWeatherByCityName();
        weatherByCityName.put(cityName, response);
        setSharedStringData(Constants.SharedKeys.LOCAL_WEATHER, MyWebService.getInstance().getJsonStringFromObject(weatherByCityName));
    }

    public Map<String, LocalWeatherResponse> getWeatherByCityName() {
        String sharedData = getSharedStringData(SharedKeys.LOCAL_WEATHER);

        if (null != sharedData && sharedData.length() > 0) {
            return MyWebService.getInstance().getObjectFromJson(sharedData, new TypeReference<Map<String, LocalWeatherResponse>>() {
            });
        }

        return new HashMap<>();
    }
}
