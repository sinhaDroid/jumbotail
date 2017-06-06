package only.sinha.android.mausam.app.module.offline;

import android.content.Context;
import android.content.SharedPreferences;

import only.sinha.android.mausam.app.Constants;
import only.sinha.android.mausam.app.module.common.AbstractDataHandler;

public class MausamDataHandler extends AbstractDataHandler implements Constants {

    /**
     * Variable to hold the sharedPreference data
     */
    private SharedPreferences mSharedPreferences;

    private static MausamDataHandler sMausamHandler = new MausamDataHandler();

    private MausamDataHandler() {
    }

    public static MausamDataHandler getInstance() {
        return sMausamHandler;
    }

    public void init(Context context) {
        mSharedPreferences = context.getSharedPreferences(MausamDataHandler.class.getCanonicalName(), Context.MODE_PRIVATE);
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public boolean isFirstTime() {
        return getSharedBooleanData(SharedKeys.FIRST_TIME, true);
    }

    public void setFirstTimeOver() {
        setSharedBooleanData(SharedKeys.FIRST_TIME, false);
    }

    public void setCurrentCityName(String cityName) {
        setSharedStringData(SharedKeys.DEFAULT_CITY_NAME, cityName);
    }

    public String getCurrentCityName() {
        String cityName = getSharedStringData(SharedKeys.DEFAULT_CITY_NAME);

        if (null == cityName || cityName.isEmpty())
            cityName = "Bengaluru";

        return cityName;
    }

    public boolean isNewData() {
        return getSharedBooleanData(SharedKeys.NEW_DATA, true);
    }

    public void setNewData(boolean value) {
        setSharedBooleanData(SharedKeys.NEW_DATA, value);
    }
}