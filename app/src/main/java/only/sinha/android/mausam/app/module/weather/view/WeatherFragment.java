package only.sinha.android.mausam.app.module.weather.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import butterknife.BindView;
import only.sinha.android.mausam.app.BroadcastService;
import only.sinha.android.mausam.app.Constants;
import only.sinha.android.mausam.app.R;
import only.sinha.android.mausam.app.module.AppSnippet;
import only.sinha.android.mausam.app.module.adapter.DailyAdapter;
import only.sinha.android.mausam.app.module.adapter.HourlyAdapter;
import only.sinha.android.mausam.app.module.common.MausamFragment;
import only.sinha.android.mausam.app.module.common.widget.SunRiseSetView;
import only.sinha.android.mausam.app.module.offline.LocalWeatherDataHandler;
import only.sinha.android.mausam.app.module.weather.presenter.CurrentWeatherPresenter;
import only.sinha.android.mausam.app.module.weather.presenter.CurrentWeatherPresenterImpl;
import only.sinha.android.mausam.app.webservice.MyWebService;

/**
 * Created by deepanshu on 1/6/17.
 */

public class WeatherFragment extends MausamFragment implements CurrentWeatherView, NestedScrollView.OnScrollChangeListener {

    @BindView(R.id.weather_background_iv)
    ImageView backgroundImage;

    @BindView(R.id.blur_background_iv)
    ImageView blurBackgroundImage;

    @BindView(R.id.main_srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.weather_nsv)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.current_degree_one_iv)
    ImageView mCurrentDegreeOne;

    @BindView(R.id.current_degree_two_iv)
    ImageView mCurrentDegreeTwo;

    @BindView(R.id.current_min_max_tv)
    TextView mMinMax;

    @BindView(R.id.current_icon_iv)
    ImageView mCurrentIcon;

    @BindView(R.id.current_desc_tv)
    TextView mCurrentDesc;

    @BindView(R.id.current_updated_time_tv)
    TextView mUpdatedTime;

    @BindView(R.id.hourly_fl)
    FrameLayout mHourlyFl;

    @BindView(R.id.hourly_rcv)
    RecyclerView mHourlyRCV;

    @BindView(R.id.daily_rcv)
    RecyclerView mDailyRCV;

    @BindView(R.id.daily_hltv)
    CombinedChart mDailyHighLowTempView;

    @BindView(R.id.detail_ll)
    LinearLayout mDetailLL;

    @BindView(R.id.sunrise_graph)
    SunRiseSetView mSunRiseSetView;

    @BindView(R.id.detail_sunrise_tv)
    TextView mSunrise;

    @BindView(R.id.detail_sunset_tv)
    TextView mSunset;

    @BindView(R.id.detail_wind_tv)
    TextView mWind;

    @BindView(R.id.detail_feels_like_tv)
    TextView mFeelsLike;

    @BindView(R.id.detail_pressure_tv)
    TextView mPressure;

    @BindView(R.id.detail_visibility_tv)
    TextView mVisibility;

    @BindView(R.id.detail_precipitation_tv)
    TextView mPrecipitation;

    @BindView(R.id.detail_humidity_tv)
    TextView mHumidity;

    private OnWeatheListener mOnWeatheListener;

    private CurrentWeatherPresenter mCurrentWeatherPresenter;

    private boolean firstScroll2Sun = true;

    private boolean firstScroll2Chart = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnWeatheListener) {
            mOnWeatheListener = (OnWeatheListener) context;
        } else
            throw new IllegalArgumentException("Called activity should implement the OnNavBarListener");
    }

    public static WeatherFragment newInstance(String cityKey) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKeys.CITY_KEY, cityKey);

        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setArguments(bundle);
        return weatherFragment;
    }

    @Override
    protected int getInflater() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {

        setInitialViews();

        this.mCurrentWeatherPresenter = CurrentWeatherPresenterImpl.newInstance(this);
        mCurrentWeatherPresenter.onViewCreated(getArguments());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentWeatherPresenter.onRefresh();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // register the broadcast
        IntentFilter intentFilter = new IntentFilter(Constants.IntentActions.ACTION_REFRESH);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        // unregister the broadcast
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);

        super.onStop();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            mCurrentWeatherPresenter.refreshAllViews();
        }
    };

    private void setInitialViews() {
        mNestedScrollView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.weather_info_dark_bg));
        mNestedScrollView.getBackground().setAlpha(0);

        mNestedScrollView.setOnScrollChangeListener(this);
    }

    @Override
    public void setHourlyAdapter(HourlyAdapter hourlyAdapter) {
        mHourlyRCV.setHasFixedSize(true);

        mHourlyRCV.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        mHourlyRCV.setAdapter(hourlyAdapter);
    }

    @Override
    public void setDailyAdapter(DailyAdapter dailyAdapter) {
        mDailyRCV.setHasFixedSize(true);

        mDailyRCV.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        mDailyRCV.setAdapter(dailyAdapter);

        mDailyRCV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDailyHighLowTempView.moveViewToX(AppSnippet.getViewTox(recyclerView.computeHorizontalScrollOffset()));
            }
        });
    }

    @Override
    public void setCityName(String cityName) {
        mOnWeatheListener.setTitle(cityName);
    }

    @Override
    public void setTime(String time) {
        mOnWeatheListener.setTime(time);
    }

    @Override
    public void setTimeZone(String timeZone) {
        mOnWeatheListener.setTimeZone(timeZone);
    }

    @Override
    public void setCurrentBackground(String weatherCode) {
        setImageUsingGlide(backgroundImage, AppSnippet.getCurrentWeatherBg(Integer.parseInt(weatherCode)));
    }

    @Override
    public void setCurrentDegreeOne(int num) {
        if (num < 0) {
            mCurrentDegreeOne.setVisibility(View.GONE);
        } else {
            mCurrentDegreeOne.setVisibility(View.VISIBLE);
            setImageUsingGlide(mCurrentDegreeOne, num);
        }
    }

    @Override
    public void setCurrentDegreeTwo(int num) {
        if (num < 0) {
            mCurrentDegreeTwo.setVisibility(View.GONE);
        } else {
            mCurrentDegreeTwo.setVisibility(View.VISIBLE);
            setImageUsingGlide(mCurrentDegreeTwo, num);
        }
    }

    @Override
    public void setCurrentMinMax(String minMax) {
        mMinMax.setText(minMax);
    }

    @Override
    public void setCurrentDescription(int weatherIconDrawable, String contentDescription) {
        mCurrentIcon.setImageResource(weatherIconDrawable);
        mCurrentDesc.setText(contentDescription);
    }

    @Override
    public void setUpdatedTime(String updateTime) {
        mUpdatedTime.setText(updateTime);
    }

    @Override
    public void setDailyMinMax(CombinedData data, IAxisValueFormatter formatter) {

        mDailyHighLowTempView.setData(data);
        mDailyHighLowTempView.setPinchZoom(false);
        mDailyHighLowTempView.setVisibleXRangeMaximum(5);
        mDailyHighLowTempView.setDoubleTapToZoomEnabled(false);
        mDailyHighLowTempView.setTouchEnabled(false);

        Description description = new Description();
        description.setText("Daily Graph");
        description.setTextColor(Color.WHITE);

        mDailyHighLowTempView.setDescription(description);
        mDailyHighLowTempView.setNoDataTextColor(Color.WHITE);

        XAxis xAxis = mDailyHighLowTempView.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-0.3f);
        xAxis.setAxisMaximum(8.3f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(formatter);
    }

    @Override
    public void setCurrentSunrise(String sunrise) {
        mSunrise.setText(sunrise);
    }

    @Override
    public void setCurrentSunset(String sunset) {
        mSunset.setText(sunset);
    }

    @Override
    public void setSunriseSunset(float sunHour) {
        mSunRiseSetView.setSunPercentage(sunHour);
    }

    @Override
    public void setCurrentWind(String wind) {
        mWind.setText(wind);
    }

    @Override
    public void setCurrentFeelsLike(String feelsLike) {
        mFeelsLike.setText(feelsLike);
    }

    @Override
    public void setCurrentPressure(String pressure) {
        mPressure.setText(pressure);
    }

    @Override
    public void setCurrentVisibility(String visibility) {
        mVisibility.setText(visibility);
    }

    @Override
    public void setCurrentPrecipitation(String precipitation) {
        mPrecipitation.setText(precipitation);
    }

    @Override
    public void setCurrentHumidity(String humidity) {
        mHumidity.setText(humidity);
    }

    private void setImageUsingGlide(ImageView imageView, int drawable) {
        Glide.with(getActivity())
                .load(drawable)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    private void setImageUsingGlide(ImageView imageView, String url) {
        Glide.with(getActivity())
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        float f = (float) scrollY;
        int min = (int) (Math.min(1.0f, f / (((float) v.getHeight()) * 0.9f)) * 255.0f);
        v.getBackground().setAlpha(min);

        if (null != blurBackgroundImage.getDrawable()) {
            int min2 = (int) (Math.min(1.0f, f / (((float) v.getHeight()) * 0.6f)) * 255.0f);
            blurBackgroundImage.getDrawable().setAlpha(min2);
            mOnWeatheListener.getBackgrounColor().setAlpha(min2);
            mHourlyFl.getBackground().setAlpha(255 - min2);
        }


        if (firstScroll2Sun && mDetailLL.getTop() - (v.getHeight() / 2) < scrollY) {
            firstScroll2Sun = false;
            mSunRiseSetView.playSunAnimation();
        }
        if (firstScroll2Chart && ((View) mDailyRCV.getParent()).getTop() - v.getHeight() < scrollY) {
            firstScroll2Chart = false;
            mDailyHighLowTempView.animateXY(1000, 1000);
            mDailyHighLowTempView.invalidate();
        }
    }

    @Override
    public void showProgressbar() {
        super.showProgressbar();
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void dismissProgressbar() {
        super.dismissProgressbar();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public interface OnWeatheListener {
        void setTitle(String title);

        void setTime(String title);

        void setTimeZone(String title);

        Drawable getBackgrounColor();
    }
}
