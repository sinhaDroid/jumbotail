package only.sinha.android.mausam.app.module.weather.view;

import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.sinha.android.View;

import only.sinha.android.mausam.app.module.adapter.DailyAdapter;
import only.sinha.android.mausam.app.module.adapter.HourlyAdapter;

/**
 * Created by deepanshu on 2/6/17.
 */

public interface CurrentWeatherView extends View {
    void setHourlyAdapter(HourlyAdapter hourlyAdapter);

    void setDailyAdapter(DailyAdapter dailyAdapter);

    void setCityName(String cityName);

    void setTime(String time);

    void setTimeZone(String timeZone);

    void setCurrentBackground(String weatherCode);

    void setCurrentDegreeOne(int drawable);

    void setCurrentDegreeTwo(int drawable);

    void setCurrentMinMax(String minMax);

    void setCurrentDescription(int weatherIconDrawable, String contentDescription);

    void setUpdatedTime(String updateTime);

    void setDailyMinMax(CombinedData data, IAxisValueFormatter formatter);

    void setCurrentSunrise(String sunrise);

    void setCurrentSunset(String sunset);

    void setSunriseSunset(float sunHour);

    void setCurrentWind(String wind);

    void setCurrentFeelsLike(String feelsLike);

    void setCurrentPressure(String pressure);

    void setCurrentVisibility(String visibility);

    void setCurrentPrecipitation(String precipitation);

    void setCurrentHumidity(String humidity);
}
