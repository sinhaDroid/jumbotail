package only.sinha.android.mausam.app.webservice;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinha.android.ExceptionTracker;
import com.sinha.android.converter.JacksonConverterFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import only.sinha.android.mausam.app.Config;
import only.sinha.android.mausam.app.Constants;
import only.sinha.android.mausam.app.Mausam;
import only.sinha.android.mausam.app.module.model.request.LocalWeatherRequest;
import only.sinha.android.mausam.app.module.model.response.geocode.GeoCodeResponse;
import only.sinha.android.mausam.app.module.model.response.localweather.LocalWeatherResponse;
import only.sinha.android.mausam.app.module.offline.LocalWeatherDataHandler;
import only.sinha.android.mausam.app.module.offline.MausamDataHandler;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static only.sinha.android.mausam.app.Constants.*;

public class MyWebService {

    private static MyWebService sMyWebService = new MyWebService();

    public static MyWebService getInstance() {
        return sMyWebService;
    }

    private MausamService mSportsCafeService;

    private ObjectMapper mObjectMapper;

    public void init(String baseUrl) {
        // Initialize ObjectMapper
        initObjectMapper();

        // Initialize Mausam Api service using retrofit
        initMausamService(baseUrl);
    }

    private void initMausamService(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(mObjectMapper))
                .client(getCustomHttpClient())
                .build();
        mSportsCafeService = retrofit.create(MausamService.class);
    }

    private OkHttpClient getCustomHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }

    private void initObjectMapper() {
        mObjectMapper = new ObjectMapper();
        mObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> String getJsonStringFromObject(T object) {
        try {
            return mObjectMapper.writeValueAsString(object);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public <T> T getObjectFromJson(String json, TypeReference<T> typeReference) {
        try {
            return mObjectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public Call<LocalWeatherResponse> callLocalWeatherApi(Map<String, String> map) {
        initMausamService(Config.API_BASE_URL);
        return mSportsCafeService.callLocalWeatherApi(map);
    }

    public Call<GeoCodeResponse> callGeoApi(String latLng) {
        initMausamService(Config.GEO_BASE_URL);
        return mSportsCafeService.callGeoApi(latLng, true);
    }

    public void callWeatherApi(final String intentActions, final String cityName) {

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
                    LocalWeatherDataHandler.getInstance().saveWeatherByCityName(cityName, response.body());

                    LocalBroadcastManager.getInstance(Mausam.getInstance()).sendBroadcast(new Intent(intentActions));
                } else {

                    callWeatherApi(intentActions, "Bengaluru");
                }
            }
        });
    }
}