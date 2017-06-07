package only.sinha.android.mausam.app;

/**
 * Created by deepanshu on 1/6/17.
 */

public interface Constants {
    public interface SharedKeys {
        String LOCAL_WEATHER = "localWeather";
        String NEW_DATA = "newData";
        String FIRST_TIME = "firstTime";
        String DEFAULT_CITY_NAME = "defaultCityName";
    }

    public interface DayTime {
        int DAYTIME_BEGIN_HOUR = 7;
        int DAYTIME_END_HOUR = 18;
        String YYYYMMDD = "yyyy-MM-dd";
        String MDD = "M/d";
    }

    public interface BundleKeys {
        String CITY_KEY = "cityKey";
        String LAT_LNG = "latLng";
    }

    public interface CODE {
        int REQUEST_CODE = 0;
    }

    public interface IntentActions {
        String ACTION_SPLASH = "only.sinha.android.mausam.app.module.splash";
        String ACTION_REFRESH = "only.sinha.android.mausam.app.module.weather.view";
        String ACTION_SERVICE = "only.sinha.android.mausam.app";
    }
}

