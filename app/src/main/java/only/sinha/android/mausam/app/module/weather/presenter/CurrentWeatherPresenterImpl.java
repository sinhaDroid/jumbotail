package only.sinha.android.mausam.app.module.weather.presenter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import only.sinha.android.mausam.app.Constants;
import only.sinha.android.mausam.app.Mausam;
import only.sinha.android.mausam.app.R;
import only.sinha.android.mausam.app.module.AppSnippet;
import only.sinha.android.mausam.app.module.adapter.DailyAdapter;
import only.sinha.android.mausam.app.module.adapter.HourlyAdapter;
import only.sinha.android.mausam.app.module.model.request.LocalWeatherRequest;
import only.sinha.android.mausam.app.module.model.response.localweather.AstronomyItem;
import only.sinha.android.mausam.app.module.model.response.localweather.CurrentConditionItem;
import only.sinha.android.mausam.app.module.model.response.localweather.Data;
import only.sinha.android.mausam.app.module.model.response.localweather.HourlyItem;
import only.sinha.android.mausam.app.module.model.response.localweather.LocalWeatherResponse;
import only.sinha.android.mausam.app.module.model.response.localweather.WeatherItem;
import only.sinha.android.mausam.app.module.offline.LocalWeatherDataHandler;
import only.sinha.android.mausam.app.module.offline.MausamDataHandler;
import only.sinha.android.mausam.app.module.weather.view.CurrentWeatherView;
import only.sinha.android.mausam.app.webservice.MausamCallBack;
import only.sinha.android.mausam.app.webservice.MyWebService;
import retrofit2.Call;
import retrofit2.Response;

import static android.R.attr.entries;
import static android.R.attr.isAuxiliary;

/**
 * Created by deepanshu on 2/6/17.
 */

public class CurrentWeatherPresenterImpl implements CurrentWeatherPresenter {

    private CurrentWeatherView mCurrentWeatherView;

    private HourlyAdapter mHourlyAdapter;

    private DailyAdapter mDailyAdapter;

    private String cityName;

    private CurrentWeatherPresenterImpl(CurrentWeatherView currentWeatherView) {
        this.mCurrentWeatherView = currentWeatherView;
    }

    public static CurrentWeatherPresenterImpl newInstance(CurrentWeatherView currentWeatherView) {
        return new CurrentWeatherPresenterImpl(currentWeatherView);
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        cityName = bundle.getString(Constants.BundleKeys.CITY_KEY);

        Context context = mCurrentWeatherView.getContext();

        if (null != context) {
            mCurrentWeatherView.setHourlyAdapter(mHourlyAdapter = new HourlyAdapter(context));

            mCurrentWeatherView.setDailyAdapter(mDailyAdapter = new DailyAdapter(context));
        }

        setAllViews(LocalWeatherDataHandler.getInstance().getWeatherByCityName().get(cityName));
    }

    @Override
    public void onRefresh() {
        callWeatherApi(cityName);
    }

    private void callWeatherApi(String cityName) {
        if (Mausam.getInstance().hasNetworkConnection()) {
            mCurrentWeatherView.showProgressbar();

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

                    if (response.isSuccessful()) {
                        handleResponse(response.body());
                        mCurrentWeatherView.dismissProgressbar();
                    } else {
                        mCurrentWeatherView.showMessage(response.message());
                        mCurrentWeatherView.dismissProgressbar();
                    }
                }
            });
        } else {
            mCurrentWeatherView.showMessage(R.string.no_internet_connection_available);
            mCurrentWeatherView.dismissProgressbar();
        }
    }

    private void handleResponse(LocalWeatherResponse response) {

        LocalWeatherDataHandler.getInstance().saveWeatherByCityName(cityName, response);

        // set All Views
        setAllViews(LocalWeatherDataHandler.getInstance().getWeatherByCityName().get(cityName));
    }

    private void setAllViews(LocalWeatherResponse weatherResponse) {
        Data data = weatherResponse.getData();
        CurrentConditionItem currentConditionItem = data.getCurrentCondition().get(0);
        List<WeatherItem> weather = data.getWeather();

        // set City Name
        mCurrentWeatherView.setCityName(cityName);

        // set Current Time
        mCurrentWeatherView.setTime(data.getTimeZone().get(0).getLocaltime());

        // set Current Timezone
        mCurrentWeatherView.setTimeZone("IST");

        // set weather background
        mCurrentWeatherView.setCurrentBackground(currentConditionItem.getWeatherCode());

        // set Current Degree
        String tempC = currentConditionItem.getTempC();
        int num = Integer.parseInt(tempC.substring(0, tempC.length() - 1));

        int tens = num / 10;
        int ones = num - (tens * 10);

        mCurrentWeatherView.setCurrentDegreeOne(AppSnippet.getDrawable(ones));
        mCurrentWeatherView.setCurrentDegreeTwo(AppSnippet.getDrawable(tens));

        // set current min max
        WeatherItem weatherItem = weather.get(0);
        mCurrentWeatherView.setCurrentMinMax(String.format("%s\u00B0/%s\u00B0", weatherItem.getMintempC(), weatherItem.getMaxtempC()));

        // set description
        mCurrentWeatherView.setCurrentDescription(AppSnippet.getWeatherBitMapResource(currentConditionItem.getWeatherCode()), currentConditionItem.getWeatherDesc().get(0).getValue());

        // set update time
        mCurrentWeatherView.setUpdatedTime(String.format("%s %s update", weatherItem.getDate().replace("-", "/"), currentConditionItem.getObservationTime()));

        // set hourly adapter
        List<HourlyItem> hourly = new ArrayList<>(20);
        hourly.addAll(weatherItem.getHourly());
        hourly.addAll(weather.get(1).getHourly());
        hourly.addAll(weather.get(2).getHourly());

        mHourlyAdapter.clear();
        mHourlyAdapter.addAll(hourly);

        // set daily adapter
        mDailyAdapter.clear();
        mDailyAdapter.addAll(weather);

        float minInitial = 0f;
        float maxInitial = 0f;
        float uvIndexInitial = 0f;
        List<Entry> minList = new ArrayList<>();
        List<Entry> maxList = new ArrayList<>();
        List<BarEntry> uvIndexList = new ArrayList<>();

        // the labels that should be drawn on the XAxis
        final String[] quarters = new String[weather.size()];
        int i = 0;

        for (WeatherItem item : weather) {
            minList.add(new Entry(minInitial++, Float.parseFloat(item.getMintempC())));
            maxList.add(new Entry(maxInitial++, Float.parseFloat(item.getMaxtempC())));
            uvIndexList.add(new BarEntry(uvIndexInitial++, Float.parseFloat(item.getUvIndex())));

            try {
                quarters[i++] = AppSnippet.getDayMonth(item.getDate(), Constants.DayTime.YYYYMMDD);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        LineDataSet setComp1 = new LineDataSet(minList, "min \u00B0C");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setValueTextColor(Color.WHITE);
        setComp1.setCircleColor(Color.RED);
        setComp1.setColor(Color.RED);
        LineDataSet setComp2 = new LineDataSet(maxList, "max \u00B0C");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp2.setValueTextColor(Color.WHITE);

        BarDataSet uv = new BarDataSet(uvIndexList, "UV");
        uv.setValueTextColor(Color.WHITE);
        uv.setColor(Color.MAGENTA);
        uv.setBarShadowColor(Color.MAGENTA);

        // temp line data set
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        // bar uvindex data set
        List<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(uv);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(new LineData(dataSets));
        combinedData.setData(new BarData(barDataSets));

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        mCurrentWeatherView.setDailyMinMax(combinedData, formatter);

        // set sunrise sunset
        AstronomyItem astronomyItem = weatherItem.getAstronomy().get(0);

        mCurrentWeatherView.setCurrentSunset(String.format("%s", astronomyItem.getSunset()));
        mCurrentWeatherView.setCurrentSunrise(String.format("%s", astronomyItem.getSunrise()));
        mCurrentWeatherView.setSunriseSunset(Float.parseFloat(weatherItem.getSunHour())/100);

        // set wind
        mCurrentWeatherView.setCurrentWind(String.format("%s %sKm/hour", currentConditionItem.getWinddir16Point(), currentConditionItem.getWindspeedKmph()));

        // set feels like
        mCurrentWeatherView.setCurrentFeelsLike(String.format("%s\u00B0", currentConditionItem.getFeelsLikeC()));

        // set pressure
        mCurrentWeatherView.setCurrentPressure(String.format("%sHpa", currentConditionItem.getPressure()));

        // set visibility
        mCurrentWeatherView.setCurrentVisibility(String.format("%sKm", currentConditionItem.getVisibility()));

        // set precipitation
        mCurrentWeatherView.setCurrentPrecipitation(String.format("%s%%", currentConditionItem.getPrecipMM()));

        // set humidity
        mCurrentWeatherView.setCurrentHumidity(String.format("%s%%", currentConditionItem.getHumidity()));
    }
}
